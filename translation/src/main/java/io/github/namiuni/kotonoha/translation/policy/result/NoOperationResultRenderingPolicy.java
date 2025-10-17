package io.github.namiuni.kotonoha.translation.policy.result;

import io.github.namiuni.kotonoha.translation.context.InvocationContext;
import io.github.namiuni.kotonoha.translation.policy.TranslationValidationException;
import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Method;
import net.kyori.adventure.text.TranslatableComponent;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

@NullMarked
record NoOperationResultRenderingPolicy() implements InvocationResultRenderingPolicy {

    static NoOperationResultRenderingPolicy INSTANCE = new NoOperationResultRenderingPolicy();

    @Override
    public @UnknownNullability Object renderResult(final TranslatableComponent component, final InvocationContext context) {
        return component;
    }

    @Override
    public void validate(final Method method) throws TranslationValidationException {
        if (GenericTypeReflector.isSuperType(method.getGenericReturnType(), TranslatableComponent.class)) {
            return;
        }

        final String message = "The return type '%s' of the method '%s' is not a supertype of 'TranslatableComponent'";
        throw new TranslationValidationException(message.formatted(method.getGenericReturnType(), method.getName()));
    }
}
