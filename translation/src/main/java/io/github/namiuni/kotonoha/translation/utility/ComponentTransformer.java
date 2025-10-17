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
package io.github.namiuni.kotonoha.translation.utility;

import io.github.namiuni.kotonoha.translation.policy.result.InvocationResultTransformationPolicy;
import io.leangen.geantyref.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

/**
 * Provides utilities for transforming a {@link Component} into a different object type
 * (e.g., converting a component to a {@code String}).
 *
 * @see Component
 * @see InvocationResultTransformationPolicy
 * @since 0.1.0
 */
// Design Note:
// このクラスはリフレクション操作に慣れていない開発者でも手軽に扱えることを目的としているため、実装が複雑になるような拡張はしない。
// 複雑な実装が必要な場合はInvocationResultRenderingPolicyを実装する。
@NullMarked
public sealed interface ComponentTransformer permits ComponentTransformerImpl {

    /**
     * Creates a new {@code Builder} for constructing a custom {@code ComponentTransformer}.
     *
     * @return a new Builder instance
     * @since 0.1.0
     */
    static Builder builder() {
        return new ComponentTransformerBuilder();
    }

    /**
     * Transforms the given {@link Component} into an object required by the specified target type.
     *
     * @param type      the type of the target value
     * @param component the {@code Component} to transform
     * @return the transformed object instance, which may be null depending on the transformation function
     * @throws IllegalArgumentException if no transformer is registered for the specified type
     * @since 0.1.0
     */
    @UnknownNullability
    Object transform(Type type, Component component);

    /**
     * Checks if this transformer supports transformation to the specified type.
     *
     * @param type the type of the target value
     * @return {@code true} if a transformer is registered for the specified type; {@code false} otherwise
     * @since 0.1.0
     */
    boolean supports(Type type);

    /**
     * A builder for creating immutable {@link ComponentTransformer} instances.
     *
     * @since 0.1.0
     */
    sealed interface Builder permits ComponentTransformerBuilder {

        /**
         * Registers a custom function to transform a {@link Component} into a value of the specified class.
         *
         * @param <V>         the target type of the transformation
         * @param type        the class of the target type
         * @param transformer the function to perform the transformation
         * @return this Builder instance
         * @since 0.1.0
         */
        <V> Builder register(Class<V> type, Function<Component, @UnknownNullability V> transformer);

        /**
         * Registers a custom function to transform a {@link Component} into a value of the specified type token.
         *
         * @param <V>         the target type of the transformation
         * @param type        the type token of the target type
         * @param transformer the function to perform the transformation
         * @return this Builder instance
         * @since 0.1.0
         */
        <V> Builder register(TypeToken<V> type, Function<Component, @UnknownNullability V> transformer);

        /**
         * Builds and returns an immutable {@code ComponentTransformer}.
         *
         * @return a new {@code ComponentTransformer}
         * @since 0.1.0
         */
        ComponentTransformer build();
    }
}
