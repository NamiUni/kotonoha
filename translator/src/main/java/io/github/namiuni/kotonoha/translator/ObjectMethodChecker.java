package io.github.namiuni.kotonoha.translator;

import java.lang.reflect.Method;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class ObjectMethodChecker {

    private ObjectMethodChecker() {
    }

    static boolean isObjectMethod(final Method method) {
        try {
            Object.class.getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (final NoSuchMethodException ignored) {
            return false;
        }
    }
}
