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
import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.key.TranslationKeyResolutionPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.result.InvocationResultTransformationPolicy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TranslatableComponent;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
final class TranslationInvocationHandler implements InvocationHandler {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    private final TranslationKeyResolutionPolicy keyPolicy;
    private final TranslationArgumentAdaptationPolicy argumentPolicy;
    private final InvocationResultTransformationPolicy resultPolicy;

    TranslationInvocationHandler(
            final TranslationConfiguration config
    ) {
        this.keyPolicy = config.keyPolicy();
        this.argumentPolicy = config.argumentPolicy();
        this.resultPolicy = config.resultPolicy();
    }

    @Override
    public @Nullable Object invoke(final Object proxy, final Method method, final @Nullable Object @Nullable [] args) throws Throwable {

        // Handle methods from Object class (e.g., equals, hashCode, toString)
        if (method.getDeclaringClass() == Object.class) {
            return this.handleObjectMethod(proxy, method, args);
        }

        // Handle default methods defined in the interface
        if (method.isDefault()) {
            return InvocationHandler.invokeDefault(proxy, method, args);
        }

        final InvocationContext context = this.createContext(method, args); // Create context
        final String key = this.keyPolicy.resolveKey(context); // Resolve key
        final ComponentLike[] arguments = this.argumentPolicy.adaptArguments(context); // Adapt arguments
        final TranslatableComponent component = Component.translatable(key, arguments); // Create translatable component

        return this.resultPolicy.transformResult(component, context); // Render result
    }

    private InvocationContext createContext(final Method method, final @Nullable Object @Nullable [] args) {
        final @Nullable Object[] values = Objects.requireNonNullElse(args, EMPTY_OBJECT_ARRAY);
        return InvocationContext.of(method, values);
    }

    private Object handleObjectMethod(final Object proxy, final Method method, final @Nullable Object @Nullable [] args) {
        return switch (method.getName()) {
            case "equals" -> args != null && args.length == 1 && proxy == args[0];
            case "hashCode" -> System.identityHashCode(proxy);
            case "toString" -> proxy.getClass().getTypeName() + "Impl@" + System.identityHashCode(proxy);
            default -> throw new UnsupportedOperationException("Unsupported Object method: " + method.getName());
        };
    }
}
