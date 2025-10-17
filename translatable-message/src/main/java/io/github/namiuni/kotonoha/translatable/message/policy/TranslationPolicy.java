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

import io.github.namiuni.kotonoha.translatable.message.KotonohaMessages;
import io.github.namiuni.kotonoha.translatable.message.configuration.TranslationConfiguration;
import java.lang.reflect.Method;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Defines the base contract for policies that govern the behavior of a translation proxy.
 * <p>
 * Implementations of this interface define a specific strategy for handling
 * a part of the translation process (e.g., key resolution, argument adaptation, result transformation).
 * This policy is primarily used to validate whether a method is eligible for proxying.
 *
 * @see TranslationConfiguration
 * @since 0.1.0
 */
@NullMarked
@ApiStatus.Internal
public interface TranslationPolicy {

    /**
     * Validates whether the given method is compatible with this translation policy.
     *
     * @param method the method being validated
     * @throws TranslationValidationException if the method fails validation based on this policy’s requirements
     * @see KotonohaMessages
     * @since 0.1.0
     */
    void validate(Method method) throws TranslationValidationException;
}
