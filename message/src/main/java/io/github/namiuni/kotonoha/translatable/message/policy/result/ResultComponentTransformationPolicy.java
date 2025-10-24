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
import io.github.namiuni.kotonoha.translatable.message.policy.InvocationPolicy;
import io.github.namiuni.kotonoha.translatable.message.utility.ComponentTransformer;
import java.util.Objects;
import net.kyori.adventure.text.TranslatableComponent;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

/**
 * Defines the policy for transforming the final {@link TranslatableComponent}
 * into the required return type of the proxied interface method.
 *
 * @see TranslatableComponent
 * @see java.lang.reflect.InvocationHandler
 * @since 0.1.0
 */
@NullMarked
public sealed interface ResultComponentTransformationPolicy extends InvocationPolicy permits CustomResultComponentTransformationPolicy, NoOperationResultComponentTransformationPolicy, StandardResultComponentTransformationPolicy {

    /**
     * Returns a standard policy that uses the specified transformer to handle the {@link TranslatableComponent} transformation.
     *
     * @param transformer the transformer to use for transforming the {@link TranslatableComponent} to the return type
     * @return a InvocationResultTransformationPolicy instance
     * @since 0.1.0
     */
    static ResultComponentTransformationPolicy noOperation(final ComponentTransformer transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return new StandardResultComponentTransformationPolicy(transformer);
    }

    /**
     * Returns a policy that does not transform {@link TranslatableComponent}.
     *
     * @return the policy of not transforming components
     * @since 0.1.0
     */
    static ResultComponentTransformationPolicy noOperation() {
        return NoOperationResultComponentTransformationPolicy.INSTANCE;
    }

    /**
     * Transforms the given {@link TranslatableComponent} into the object required by the method's return type.
     *
     * @param component the created translatable component
     * @param context   the invocation context
     * @return the transformed object, which will be the final return value of the proxy method call.
     * @see java.lang.reflect.InvocationHandler
     * @since 0.1.0
     */
    @UnknownNullability
    Object transformComponent(TranslatableComponent component, InvocationContext context);
}
