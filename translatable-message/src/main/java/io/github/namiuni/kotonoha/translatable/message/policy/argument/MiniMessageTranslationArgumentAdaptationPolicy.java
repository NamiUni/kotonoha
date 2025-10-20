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
package io.github.namiuni.kotonoha.translatable.message.policy.argument;

import io.github.namiuni.kotonoha.translatable.message.context.InvocationArgument;
import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.name.ArgumentNameResolver;
import io.github.namiuni.kotonoha.translatable.message.utility.TranslationArgumentAdapter;
import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Objects;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Experimental
@SuppressWarnings("PatternValidation")
record MiniMessageTranslationArgumentAdaptationPolicy(
        TranslationArgumentAdapter argumentAdapter,
        ArgumentNameResolver nameResolver
) implements TranslationArgumentAdaptationPolicy {

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

        // Standard argument
        final Type parameterizedType = parameter.getParameterizedType();
        if (this.argumentAdapter.supports(parameterizedType)) {
            final TranslationArgument argumentValue = this.argumentAdapter.adapt(parameterizedType, value);
            final String argumentName = this.nameResolver.resolve(parameter);
            return Argument.argument(argumentName, argumentValue);
        }

        // Special argument
        return switch (value) {
            case Tag tag when parameterizedType == Tag.class -> Argument.tag(this.nameResolver.resolve(parameter), tag);
            case TagResolver tagResolver when parameterizedType == TagResolver.class -> Argument.tagResolver(tagResolver);
            case Pointered pointered when parameterizedType == Pointered.class -> Argument.target(pointered);
            default -> {
                final String message = "Unsupported parameter type '%s' at position '%d'. " +
                        "Register an adapter for this type in TranslationArgumentAdapter";
                throw new IllegalArgumentException(message.formatted(parameterizedType, invocationArgument.index()));
            }
        };
    }

    @Override
    public void validate(final Method method) throws KotonohaValidationException {

        boolean hasTarget = false;
        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];

            // Standard argument
            final Type parameterizedType = parameter.getParameterizedType();
            if (this.argumentAdapter.supports(parameterizedType) && this.nameResolver.supports(parameter)) {
                continue;
            }

            // Special argument
            if (parameterizedType.equals(Tag.class) && this.nameResolver.supports(parameter)) {
                continue;
            }

            if (parameterizedType.equals(TagResolver.class)) {
                continue;
            }

            if (parameterizedType.equals(Pointered.class)) {
                if (hasTarget) {
                    final String message = "Multiple target arguments have been set!";
                    throw new KotonohaValidationException(message);
                }

                hasTarget = true;
                continue;
            }

            final String message = "Unsupported parameter type '%s' at position '%d' in method '%s'. " +
                    "Register an adapter for this type in TranslationArgumentAdapter";
            final String formatted = message.formatted(
                    GenericTypeReflector.getTypeName(parameterizedType),
                    i,
                    method.getName()
            );
            throw new KotonohaValidationException(formatted);
        }
    }
}
