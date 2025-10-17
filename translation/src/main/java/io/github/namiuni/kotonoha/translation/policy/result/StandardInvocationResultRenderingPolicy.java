/*
 * This file is part of kotonoha, licensed under the MIT License.
 *
 * Copyright (c) 2025 Namiu (Unitarou)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.namiuni.kotonoha.translation.policy.result;

import io.github.namiuni.kotonoha.translation.context.InvocationContext;
import io.github.namiuni.kotonoha.translation.policy.TranslationValidationException;
import io.github.namiuni.kotonoha.translation.utility.ComponentTransformer;
import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import net.kyori.adventure.text.TranslatableComponent;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
record StandardInvocationResultRenderingPolicy(@Nullable ComponentTransformer transformer) implements InvocationResultRenderingPolicy {

    @Override
    public @UnknownNullability Object renderResult(final TranslatableComponent component, final InvocationContext context) {

        final Method method = context.method();
        final Type genericReturnType = method.getGenericReturnType();

        if (this.transformer != null && this.transformer.supports(genericReturnType)) {
            return this.transformer.transform(genericReturnType, component);
        }

        // Don't do anything unnecessary.
        return component;
    }

    @Override
    public void validate(final Method method) throws TranslationValidationException {
        if (this.transformer != null && this.transformer.supports(method.getGenericReturnType())) {
            return;
        }

        if (GenericTypeReflector.isSuperType(method.getGenericReturnType(), TranslatableComponent.class)) {
            return;
        }

        final String message = "Unsupported return type '%s' from method '%s'. " +
                "Register a transformer for this type in ComponentTransformer";
        throw new TranslationValidationException(message.formatted(method.getGenericReturnType(), method.getName()));
    }
}
