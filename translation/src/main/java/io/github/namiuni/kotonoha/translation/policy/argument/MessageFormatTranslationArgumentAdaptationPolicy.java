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
package io.github.namiuni.kotonoha.translation.policy.argument;

import io.github.namiuni.kotonoha.translation.context.InvocationArgument;
import io.github.namiuni.kotonoha.translation.context.InvocationContext;
import io.github.namiuni.kotonoha.translation.policy.TranslationValidationException;
import io.github.namiuni.kotonoha.translation.utility.TranslationArgumentAdapter;
import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Objects;
import net.kyori.adventure.text.ComponentLike;
import org.jspecify.annotations.NullMarked;

@NullMarked
record MessageFormatTranslationArgumentAdaptationPolicy(TranslationArgumentAdapter argumentAdapter) implements TranslationArgumentAdaptationPolicy {

    @Override
    public ComponentLike[] adaptArguments(final InvocationContext context) throws IllegalArgumentException, NullPointerException {
        final InvocationArgument[] invocationArguments = context.invocationArguments();
        if (invocationArguments.length == 0) {
            return EMPTY_COMPONENT_LIKE_ARRAY;
        }

        final ComponentLike[] translationArguments = new ComponentLike[invocationArguments.length];
        for (int i = 0; i < invocationArguments.length; i++) {
            final InvocationArgument invocationArgument = invocationArguments[i];
            translationArguments[i] = this.adaptSingle(invocationArgument);
        }

        return translationArguments;
    }

    private ComponentLike adaptSingle(final InvocationArgument invocationArgument) throws IllegalArgumentException, NullPointerException {
        final Parameter parameter = invocationArgument.parameter();
        final Object value = invocationArgument.value();
        Objects.requireNonNull(value, parameter.getName());

        final Type parameterizedType = parameter.getParameterizedType();
        return this.argumentAdapter.adapt(parameterizedType, value);
    }

    @Override
    public void validate(final Method method) throws TranslationValidationException {
        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            final Type parameterizedType = parameter.getParameterizedType();
            if (!this.argumentAdapter.supports(parameterizedType)) {
                final String message = "Unsupported parameter type '%s' at position '%d' in method '%s'. " +
                        "Register an adapter for this type in TranslationArgumentAdapter";
                final String formatted = message.formatted(
                        GenericTypeReflector.getTypeName(parameterizedType),
                        i,
                        method.getName()
                );
                throw new TranslationValidationException(formatted);
            }
        }
    }
}
