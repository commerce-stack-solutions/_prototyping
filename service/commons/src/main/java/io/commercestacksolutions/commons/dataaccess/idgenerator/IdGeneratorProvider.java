package io.commercestacksolutions.commons.dataaccess.idgenerator;

import java.util.UUID;

public final class IdGeneratorProvider {

    private IdGeneratorProvider() {
    }

    public static String generate(Class<?> entityClass) {
        String prefix = entityClass.getSimpleName()
                .replaceAll("Entity$", "")
                .toUpperCase();
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
