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
package io.github.namiuni.kotonoha.translatable.message;

import io.github.namiuni.kotonoha.translatable.message.configuration.InvocationConfiguration;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import org.jspecify.annotations.NullMarked;

/**
 * A factory class for creating proxy instances of interfaces that create
 * {@link net.kyori.adventure.text.TranslatableComponent}s, possibly transforming
 * method arguments or return values according to a {@link InvocationConfiguration}.
 * <p>
 * It uses {@link java.lang.reflect.Proxy} and {@link java.lang.reflect.InvocationHandler}
 * to dynamically handle method calls, resolve translation keys, and adapt arguments
 * based on the provided configuration.
 *
 * @see java.lang.reflect.Proxy
 * @see java.lang.reflect.InvocationHandler
 * @see net.kyori.adventure.text.TranslatableComponent
 * @since 0.1.0
 */
@NullMarked
public final class KotonohaMessage {

    private KotonohaMessage() {
    }

    /**
     * Returns a proxy instance using an invocation configuration.
     * <p>
     * Handles method invocations according to the policies defined in the provided {@code config}.
     *
     * <p>{@link InvocationConfiguration} provides standard configurations for {@link java.text.MessageFormat}
     * and {@link net.kyori.adventure.text.minimessage.MiniMessage}
     * from {@link io.github.namiuni.kotonoha.translatable.message.configuration.FormatTypes}.</p>
     * <pre>{@code
     * // For MessageFormat
     * ExampleMessages messages = KotonohaMessage.createProxy(ExampleMessages.class, FormatTypes.MESSAGE_FORMAT);
     * }</pre>
     * is equivalent to
     * <pre>{@code
     * TranslationConfiguration config = InvocationConfiguration.of(
     *     TranslationKeyResolutionPolicy.annotationKeyResolutionPolicy(),
     *     TranslationArgumentAdaptationPolicy.messageFormat(),
     *     InvocationResultTransformationPolicy.noOperation()
     * );
     * ExampleMessages messages = KotonohaMessage.createProxy(ExampleMessages.class, config);
     * }</pre>
     *
     * @param <I>           the proxied interface type
     * @param interfaceType the interface type to be proxied
     * @param config        the invocation configuration
     * @return a proxy instance of the specified interface
     * @throws NullPointerException if {@code interfaceType} or {@code config} is {@code null}
     * @throws IllegalArgumentException if the specified class is not an interface
     * @throws KotonohaValidationException if the interface cannot be used with the given config
     * @see java.lang.reflect.Proxy
     * @see java.lang.reflect.InvocationHandler
     * @see net.kyori.adventure.text.TranslatableComponent
     * @since 0.1.0
     */
    public static <I> I createProxy(
            final Class<I> interfaceType,
            final InvocationConfiguration config
    ) throws NullPointerException, IllegalArgumentException, KotonohaValidationException {

        // Check null
        Objects.requireNonNull(interfaceType, "interfaceType");
        Objects.requireNonNull(config, "config");

        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("The specified class is not an interface.");
        }

        // Validate policies
        for (final Method method : interfaceType.getMethods()) {

            // Ignore Object method
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }

            // Ignore default method
            if (method.isDefault()) {
                continue;
            }

            // Validate method
            config.keyPolicy().validate(method);
            config.argumentPolicy().validate(method);
            config.resultPolicy().validate(method);
        }

        // Create proxy instance
        final KotonohaInvocationHandler handler = new KotonohaInvocationHandler(config);
        final Object proxyInstance = Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[] {interfaceType},
                handler
        );

        return interfaceType.cast(proxyInstance);
    }
}
