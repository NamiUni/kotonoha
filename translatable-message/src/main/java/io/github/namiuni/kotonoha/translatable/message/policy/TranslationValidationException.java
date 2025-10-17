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
package io.github.namiuni.kotonoha.translatable.message.policy;

import org.jspecify.annotations.NullMarked;

/**
 * Signals that a translation validation process has failed.
 * <p>
 * This exception is thrown when a translation does not meet the
 * required validation rules defined by a {@link TranslationPolicy}
 * or other translation-related components.
 * </p>
 *
 * <p>
 * Typical causes include missing translation keys, invalid placeholders,
 * or violations of language consistency rules.
 * </p>
 *
 * @see TranslationPolicy
 * @since 0.1.0
 */
@NullMarked
@SuppressWarnings("unused")
public final class TranslationValidationException extends RuntimeException {

    /**
     * Creates a new exception indicating that a translation validation has failed.
     *
     * @param message a detail message explaining the validation failure
     * @since 0.1.0
     */
    public TranslationValidationException(final String message) {
        super(message);
    }

    /**
     * Creates a new exception indicating that a translation validation has failed,
     * with an underlying cause.
     *
     * @param message a detail message explaining the validation failure
     * @param cause the cause of this exception (may be {@code null})
     * @since 0.1.0
     */
    public TranslationValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
