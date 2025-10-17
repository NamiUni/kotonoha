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

import io.github.namiuni.kotonoha.translatable.message.configuration.TranslationConfiguration;
import io.github.namiuni.kotonoha.translatable.message.policy.TranslationValidationException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class ProxyFactory {

    private ProxyFactory() {
    }

    static <I> I createProxy(final Class<I> proxiedType, final TranslationConfiguration config) throws IllegalArgumentException {

        if (!proxiedType.isInterface()) {
            throw new IllegalArgumentException("The specified class is not an interface.");
        }

        // Validate policies
        for (final Method method : proxiedType.getMethods()) {

            // Ignore Object method
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }

            // Ignore default method
            if (method.isDefault()) {
                continue;
            }

            // Validate method
            ProxyFactory.validate(config, method);
        }

        // Create proxy instance
        final TranslationInvocationHandler handler = new TranslationInvocationHandler(config);
        final Object proxyInstance = Proxy.newProxyInstance(
                proxiedType.getClassLoader(),
                new Class<?>[] {proxiedType},
                handler
        );

        return proxiedType.cast(proxyInstance);
    }

    private static void validate(final TranslationConfiguration config, final Method method) throws TranslationValidationException {
        config.keyPolicy().validate(method);
        config.argumentPolicy().validate(method);
        config.resultPolicy().validate(method);
    }
}
