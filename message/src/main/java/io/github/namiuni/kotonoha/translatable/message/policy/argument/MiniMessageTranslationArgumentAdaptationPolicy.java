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

import io.github.namiuni.kotonoha.annotations.Target;
import io.github.namiuni.kotonoha.translatable.message.context.InvocationArgument;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.tag.TagNameResolver;
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
        TagNameResolver nameResolver
) implements TranslationArgumentAdaptationPolicy {

    static final MiniMessageTranslationArgumentAdaptationPolicy STANDARD_INSTANCE = new MiniMessageTranslationArgumentAdaptationPolicy(
            TranslationArgumentAdapter.standard(),
            TagNameResolver.annotationNameResolver()
    );

    MiniMessageTranslationArgumentAdaptationPolicy {
        Objects.requireNonNull(argumentAdapter, "argumentAdapter");
        Objects.requireNonNull(nameResolver, "nameResolver");
    }

    @Override
    public ComponentLike adaptArgument(final InvocationArgument invocationArgument) throws IllegalArgumentException, NullPointerException {
        final Parameter parameter = invocationArgument.parameter();
        final Object value = invocationArgument.value();
        Objects.requireNonNull(value, parameter.getName());

        final Type parameterizedType = parameter.getParameterizedType();

        return switch (value) {
            // Special argument
            case TagResolver tagResolver when parameterizedType == TagResolver.class -> Argument.tagResolver(tagResolver);
            case Pointered pointered when parameterizedType == Pointered.class -> Argument.target(pointered);
            case Pointered pointered when parameter.isAnnotationPresent(Target.class) -> Argument.target(pointered);
            case Tag tag when parameterizedType == Tag.class -> {
                final String tagName = this.nameResolver.resolve(parameter);
                yield Argument.tag(tagName, tag);
            }
            // Standard argument
            default -> {
                final TranslationArgument argumentValue = this.argumentAdapter.adapt(parameterizedType, value);
                final String argumentName = this.nameResolver.resolve(parameter);
                yield Argument.argument(argumentName, argumentValue);
            }
        };
    }

    @Override
    public void validate(final Method method) throws KotonohaValidationException {

        boolean hasTarget = false;
        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            final Type parameterType = parameter.getParameterizedType();

            if (parameterType.equals(TagResolver.class)) {
                continue;
            }

            if (GenericTypeReflector.isSuperType(Pointered.class, parameterType)) {
                if (parameterType.equals(Pointered.class) || parameter.isAnnotationPresent(Target.class)) {
                    if (hasTarget) {
                        final String message = "Multiple target arguments have been set.";
                        throw new KotonohaValidationException(message);
                    }

                    hasTarget = true;
                    continue;
                }
            }

            if (parameterType.equals(Tag.class) && this.nameResolver.supports(parameter)) {
                continue;
            }

            if (this.argumentAdapter.supports(parameterType) && this.nameResolver.supports(parameter)) {
                continue;
            }

            final String message = "Unsupported parameter type '%s' at position '%d' in method '%s'.";
            final String formatted = message.formatted(
                    GenericTypeReflector.getTypeName(parameterType),
                    i,
                    method.getName()
            );
            throw new KotonohaValidationException(formatted);
        }
    }
}
