package org.kgromov.system;

import io.quarkus.runtime.util.StringUtil;
import jakarta.annotation.Nullable;

public class Utils {
    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isBlank();
    }
}
