package io.commercestacksolutions.codegen;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;

import java.io.File;

/**
 * Gradle plugin that registers the {@code mergeAndGenerate} task for a module and
 * wires generated sources into the {@code main} source set.
 *
 * <p>Apply in a module's {@code build.gradle.kts}:
 * <pre>{@code
 * plugins {
 *     id("io.commercestacksolutions.cdf-codegen")
 * }
 * }</pre>
 *
 * <p>CDF JSON files are read from {@code src/main/resources/cdf/}.
 * Generated Java sources are written to {@code src/generated/main/java/}
 * (this path is git-ignored but kept as a source set for IDE visibility).
 */
public class CdfCodeGenerationPlugin implements Plugin<Project> {

    /** Location of CDF JSON source files within a module. */
    public static final String CDF_SOURCE_DIR = "src/main/resources/cdf";

    /** Root of the generated Java source tree within a module. */
    public static final String GENERATED_SOURCE_DIR = "src/generated/main/java";

    @Override
    public void apply(Project project) {
        // Register the task eagerly so it is always visible (e.g., `./gradlew mergeAndGenerate`)
        TaskProvider<MergeAndGenerateTask> generateTask =
                project.getTasks().register("mergeAndGenerate", MergeAndGenerateTask.class, task -> {
                    task.setDescription("Generates Java source files from CDF JSON definitions.");
                    task.setGroup("build");

                    // Set output directory for generated sources
                    task.getOutputDirectory().set(
                            project.getLayout().getProjectDirectory().dir(GENERATED_SOURCE_DIR));

                    // Primary CDF directory for this module (current module's CDF files are the extension)
                    File primaryCdfDir = project.file(CDF_SOURCE_DIR);
                    task.getPrimaryCdfDirectory().from(primaryCdfDir);
                });

        // After evaluation, add dependency project CDF dirs as base definitions.
        // In Gradle 9, ProjectDependency.getDependencyProject() was removed; we resolve
        // the dependency project by matching on project name across subprojects.
        project.afterEvaluate(p -> {
            generateTask.configure(task -> {
                p.getConfigurations()
                        .matching(c -> c.getName().equals("implementation") || c.getName().equals("api"))
                        .forEach(config -> {
                            config.getDependencies()
                                    .withType(ProjectDependency.class)
                                    .forEach(dep -> {
                                        // Resolve the dependency project via root project subprojects
                                        String depName = dep.getName();
                                        p.getRootProject().getSubprojects().forEach(subproject -> {
                                            if (subproject.getName().equals(depName)) {
                                                File depCdfDir = subproject.file(CDF_SOURCE_DIR);
                                                if (depCdfDir.exists()) {
                                                    task.getDependencyCdfDirectories().from(depCdfDir);
                                                }
                                            }
                                        });
                                    });
                        });
            });
        });

        // Add generated sources to the main Java source set
        project.getPlugins().withId("java", javaPlugin -> {
            JavaPluginExtension javaExt = project.getExtensions().getByType(JavaPluginExtension.class);
            SourceSetContainer sourceSets = javaExt.getSourceSets();
            SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
            mainSourceSet.getJava().srcDir(GENERATED_SOURCE_DIR);
        });

        // Make compileJava depend on mergeAndGenerate so generation always runs first
        project.getTasks().named("compileJava", task -> task.dependsOn(generateTask));
    }
}
