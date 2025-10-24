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
package io.github.namiuni.kotonoha.translatable.message.context;

import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.key.TranslationKeyResolutionPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.result.ResultComponentTransformationPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents the complete context of a proxy method invocation.
 *
 * <p>This interface provides access to the invoked {@link Method}
 * and the arguments supplied during the call. It is used
 * by translation policies to inspect or process invocation data
 * when resolving translation keys or rendering results.</p>
 *
 * <p>The context is immutable and reflects the state of a single
 * method invocation as seen by a proxy or handler.</p>
 *
 * @see java.lang.reflect.InvocationHandler
 * @see TranslationKeyResolutionPolicy
 * @see TranslationArgumentAdaptationPolicy
 * @see ResultComponentTransformationPolicy
 * @since 0.1.0
 */
@NullMarked
public sealed interface InvocationContext permits InvocationContextImpl {

    /**
     * Returns an invocation context for the given method and arguments.
     *
     * @param method the invoked method (must not be {@code null})
     * @param args   the actual arguments passed to the method (must not be {@code null})
     * @return an invocation context
     * @see java.lang.reflect.InvocationHandler
     * @since 0.1.0
     */
    @ApiStatus.Internal
    static InvocationContext of(final Method method, final @Nullable Object[] args) {
        Objects.requireNonNull(method, "method");
        Objects.requireNonNull(args, "args");

        final Parameter[] parameters = method.getParameters();
        final InvocationArgument[] arguments = new InvocationArgument[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            final InvocationArgument argument = new InvocationArgumentImpl(parameter, args[i], i);
            arguments[i] = argument;
        }

        return new InvocationContextImpl(method, arguments);
    }

    /**
     * Returns the method that was invoked.
     *
     * @return the invoked method
     * @since 0.1.0
     */
    Method method();

    /**
     * Returns the arguments passed to the method invocation,
     * wrapped as {@link InvocationArgument} objects.
     *
     * @return an array of invocation arguments
     * @see java.lang.reflect.InvocationHandler
     * @since 0.1.0
     */
    InvocationArgument[] invocationArguments();
}
