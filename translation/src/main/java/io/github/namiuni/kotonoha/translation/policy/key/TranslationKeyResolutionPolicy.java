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
package io.github.namiuni.kotonoha.translation.policy.key;

import io.github.namiuni.kotonoha.translation.context.InvocationContext;
import io.github.namiuni.kotonoha.translation.policy.TranslationPolicy;
import org.jspecify.annotations.NullMarked;

/**
 * Defines the strategy for resolving the translation key from a proxy interface method invocation.
 * <p>
 * The key is typically derived from an {@link io.github.namiuni.kotonoha.annotations.Key} annotation on the method or a convention based on the method name.
 *
 * @see java.lang.reflect.InvocationHandler
 * @see net.kyori.adventure.text.TranslatableComponent
 * @since 0.1.0
 */
@NullMarked
public sealed interface TranslationKeyResolutionPolicy extends TranslationPolicy permits CustomTranslationKeyResolutionPolicy, AnnotationTranslationKeyResolutionPolicy {

    /**
     * Returns a standard policy that resolves the translation key from {@link io.github.namiuni.kotonoha.annotations.Key} annotations on the interface method.
     *
     * @return the policy for resolving translation keys via {@link io.github.namiuni.kotonoha.annotations.Key} annotations
     * @see net.kyori.adventure.text.TranslatableComponent
     * @see io.github.namiuni.kotonoha.annotations.Key
     * @since 0.1.0
     */
    static TranslationKeyResolutionPolicy annotationKeyResolutionPolicy() {
        return AnnotationTranslationKeyResolutionPolicy.INSTANCE;
    }

    /**
     * Resolves and returns the translation key associated with the method invocation.
     *
     * @param context the invocation context containing the method and its arguments
     * @return the resolved translation key
     * @see net.kyori.adventure.text.TranslatableComponent
     * @see java.lang.reflect.InvocationHandler
     * @since 0.1.0
     */
    String resolveKey(InvocationContext context);
}
