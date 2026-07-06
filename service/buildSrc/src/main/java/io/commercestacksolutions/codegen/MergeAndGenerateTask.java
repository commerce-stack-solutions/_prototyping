package io.commercestacksolutions.codegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.commercestacksolutions.codegen.model.CdfDefinition;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Gradle task that reads CDF JSON files, merges definitions for the same entity,
 * and generates Java source files in the configured output directory.
 *
 * <p>Merge order:
 * <ol>
 *   <li>CDF files from <em>dependency modules</em> ({@link #getDependencyCdfDirectories()})
 *       are treated as the <strong>base</strong> definitions.</li>
 *   <li>CDF files from the <em>current module</em> ({@link #getPrimaryCdfDirectory()})
 *       are the <strong>extensions</strong> — they override or extend the base.</li>
 * </ol>
 *
 * <p>Only entities whose CDF files appear in the primary (current-module) directory
 * are written to the output.
 */
public abstract class MergeAndGenerateTask extends DefaultTask {

    /**
     * The CDF directory of the current (owning) module.
     * Only entities found here are generated as output files.
     */
    @InputFiles
    @SkipWhenEmpty
    public abstract ConfigurableFileCollection getPrimaryCdfDirectory();

    /**
     * CDF directories from dependency modules.
     * Used only for merging (base definitions); no output is generated for
     * entities found only here.
     */
    @InputFiles
    @Optional
    public abstract ConfigurableFileCollection getDependencyCdfDirectories();

    /** Directory where the generated {@code .java} files are written. */
    @OutputDirectory
    public abstract DirectoryProperty getOutputDirectory();

    @TaskAction
    public void mergeAndGenerate() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CdfMerger merger = new CdfMerger();
        JavaCodeGenerator generator = new JavaCodeGenerator();

        // Step 1: load base definitions from dependency module CDFs (base first)
        Map<String, List<CdfDefinition>> allDefinitions = new LinkedHashMap<>();
        getDependencyCdfDirectories().forEach(dir -> {
            if (dir.exists() && dir.isDirectory()) {
                loadCdfDirectory(dir, mapper, allDefinitions);
            }
        });

        // Step 2: load primary (current module) CDFs (extensions, applied after base)
        Set<String> ownedEntities = new LinkedHashSet<>();
        getPrimaryCdfDirectory().forEach(dir -> {
            if (dir.exists() && dir.isDirectory()) {
                loadCdfDirectory(dir, mapper, allDefinitions);
                // Track entity names defined in the primary module
                File[] jsonFiles = dir.listFiles((d, name) -> name.endsWith(".json"));
                if (jsonFiles != null) {
                    for (File f : jsonFiles) {
                        try {
                            CdfDefinition def = mapper.readValue(f, CdfDefinition.class);
                            ownedEntities.add(def.getEntity());
                        } catch (IOException e) {
                            getLogger().warn("Could not re-read CDF to determine owned entities: " + f, e);
                        }
                    }
                }
            }
        });

        if (ownedEntities.isEmpty()) {
            getLogger().lifecycle("No CDF files found in primary directory – skipping generation.");
            return;
        }

        File outputRoot = getOutputDirectory().get().getAsFile();
        int generated = 0;

        for (String entityName : ownedEntities) {
            List<CdfDefinition> defs = allDefinitions.get(entityName);
            if (defs == null || defs.isEmpty()) continue;

            CdfDefinition merged = merger.merge(defs);
            String javaSource = generator.generate(merged);

            String packagePath = merged.getPkg().replace('.', File.separatorChar);
            File packageDir = new File(outputRoot, packagePath);
            packageDir.mkdirs();

            Path outputFile = new File(packageDir, entityName + ".java").toPath();
            Files.writeString(outputFile, javaSource);
            getLogger().lifecycle("Generated: {}", outputFile);
            generated++;
        }

        getLogger().lifecycle("CDF code generation complete. {} class(es) generated.", generated);
    }

    private void loadCdfDirectory(File dir, ObjectMapper mapper,
                                   Map<String, List<CdfDefinition>> target) {
        File[] jsonFiles = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (jsonFiles == null) return;
        for (File jsonFile : jsonFiles) {
            try {
                CdfDefinition def = mapper.readValue(jsonFile, CdfDefinition.class);
                target.computeIfAbsent(def.getEntity(), k -> new ArrayList<>()).add(def);
                getLogger().debug("Loaded CDF: {} from {}", def.getEntity(), jsonFile);
            } catch (IOException e) {
                getLogger().error("Failed to parse CDF file: " + jsonFile, e);
                throw new RuntimeException("Failed to parse CDF file: " + jsonFile, e);
            }
        }
    }
}
