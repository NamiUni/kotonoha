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
package io.github.namiuni.kotonoha.translatable.message.policy.key;

import io.github.namiuni.kotonoha.annotations.Key;
import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import java.lang.reflect.Method;
import org.jspecify.annotations.NullMarked;

@NullMarked
record AnnotationTranslationKeyResolutionPolicy() implements TranslationKeyResolutionPolicy {

    static final AnnotationTranslationKeyResolutionPolicy INSTANCE;

    static {
        INSTANCE = new AnnotationTranslationKeyResolutionPolicy();
    }

    @Override
    public String resolveKey(final InvocationContext context) throws IllegalStateException {
        final Key keyAnnotation = context.method().getAnnotation(Key.class);
        if (keyAnnotation != null) {
            return context.method().getAnnotation(Key.class).value();
        }

        final String message = "Missing annotation '@Key' on method '%s'".formatted(context.method().getName());
        throw new IllegalStateException(message);
    }

    @Override
    public void validate(final Method method) throws KotonohaValidationException {
        if (!method.isAnnotationPresent(Key.class)) {
            final String message = "Missing annotation '@Key' on method '%s'".formatted(method.getName());
            throw new KotonohaValidationException(message);
        }
    }
}
