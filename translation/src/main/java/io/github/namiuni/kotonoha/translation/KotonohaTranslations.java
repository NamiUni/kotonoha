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
package io.github.namiuni.kotonoha.translation;

import io.github.namiuni.kotonoha.translation.configuration.FormatType;
import io.github.namiuni.kotonoha.translation.configuration.TranslationConfiguration;
import io.github.namiuni.kotonoha.translation.policy.TranslationValidationException;
import java.util.Objects;
import org.jspecify.annotations.NullMarked;

/**
 * Factory class for creating proxy instances of interfaces for creates {@link net.kyori.adventure.text.TranslatableComponent}s purposes.
 * <p>
 * It uses {@link java.lang.reflect.Proxy} and {@link java.lang.reflect.InvocationHandler} to generate
 * proxy objects that handle translation key resolution and argument adaptation based on the provided configuration.
 *
 * @see java.lang.reflect.Proxy
 * @see java.lang.reflect.InvocationHandler
 * @see net.kyori.adventure.text.TranslatableComponent
 * @since 0.1.0
 */
@NullMarked
public final class KotonohaTranslations {

    private KotonohaTranslations() {
    }

    /**
     * Creates a translation proxy instance using a custom configuration.
     * <p>
     * The returned proxy implements the specified interface, handling method calls
     * according to the policies defined in {@code config}.
     *
     * @param <I>           the proxied interface type
     * @param interfaceType the interface type to be proxied
     * @param config        the translation configuration, defining key, argument, and result policies
     * @return a new proxy instance of the specified interface
     * @throws IllegalArgumentException if the specified class is not an interface
     * @throws TranslationValidationException if the interface cannot be used with the given config
     * @see java.lang.reflect.Proxy
     * @see java.lang.reflect.InvocationHandler
     * @see net.kyori.adventure.text.TranslatableComponent
     * @since 0.1.0
     */
    public static <I> I of(final Class<I> interfaceType, final TranslationConfiguration config) throws IllegalArgumentException, TranslationValidationException {
        Objects.requireNonNull(interfaceType, "interfaceType");
        Objects.requireNonNull(config, "config");
        return ProxyFactory.createProxy(interfaceType, config);
    }

    /**
     * Creates a translation proxy instance using a configuration derived from the specified message format type.
     * <p>
     * This is a convenience method for creating a proxy with a standard configuration
     * based on a common message formatting style (e.g., MiniMessage, MessageFormat).
     *
     * @param <I>           the proxied interface type
     * @param interfaceType the interface type to be proxied
     * @param formatType    the base message format type used to create the default translation configuration
     * @return a new proxy instance of the specified interface
     * @throws IllegalArgumentException if the specified class is not an interface
     * @throws TranslationValidationException if the interface cannot be used with the derived config
     * @see java.lang.reflect.Proxy
     * @see java.lang.reflect.InvocationHandler
     * @see net.kyori.adventure.text.TranslatableComponent
     * @since 0.1.0
     */
    public static <I> I of(final Class<I> interfaceType, final FormatType formatType) throws TranslationValidationException {
        Objects.requireNonNull(interfaceType, "interfaceType");
        Objects.requireNonNull(formatType, "formatType");
        return ProxyFactory.createProxy(interfaceType, formatType.configure());
    }
}
