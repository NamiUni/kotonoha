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
package io.github.namiuni.kotonoha.translatable.message.policy.result;

import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import io.leangen.geantyref.GenericTypeReflector;
import java.lang.reflect.Method;
import net.kyori.adventure.text.TranslatableComponent;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

@NullMarked
record NoOperationResultTransformationPolicy() implements InvocationResultTransformationPolicy {

    static final NoOperationResultTransformationPolicy INSTANCE = new NoOperationResultTransformationPolicy();

    @Override
    public @UnknownNullability Object transformResult(final TranslatableComponent component, final InvocationContext context) {
        return component;
    }

    @Override
    public void validate(final Method method) throws KotonohaValidationException {
        if (GenericTypeReflector.isSuperType(method.getGenericReturnType(), TranslatableComponent.class)) {
            return;
        }

        final String message = "The return type '%s' of the method '%s' is not a supertype of 'TranslatableComponent'";
        throw new KotonohaValidationException(message.formatted(method.getGenericReturnType(), method.getName()));
    }
}
