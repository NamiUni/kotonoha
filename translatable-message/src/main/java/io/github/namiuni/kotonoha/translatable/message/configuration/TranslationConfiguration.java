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
package io.github.namiuni.kotonoha.translatable.message.configuration;

import io.github.namiuni.kotonoha.translatable.message.KotonohaMessages;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.key.TranslationKeyResolutionPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.result.InvocationResultTransformationPolicy;
import java.util.Objects;
import org.jspecify.annotations.NullMarked;

/**
 * Holds translation policies used when creating proxy instances in {@link KotonohaMessages}.
 * It defines how translation keys, arguments, and results are handled during method invocation.
 *
 * @see KotonohaMessages
 * @since 0.1.0
 */
@NullMarked
@SuppressWarnings("unused")
public sealed interface TranslationConfiguration permits TranslationConfigurationImpl {

    /**
     * Creates a new translation configuration instance using the specified policies.
     *
     * @param keyPolicy      the policy for resolving {@link net.kyori.adventure.text.TranslatableComponent} key during method invocation
     * @param argumentPolicy the policy for adapting {@link net.kyori.adventure.text.TranslatableComponent} arguments during method invocation
     * @param resultPolicy   the policy for transforming {@link net.kyori.adventure.text.TranslatableComponent} generated during method invocation
     * @return a new {@code TranslationConfiguration} instance using the specified policies
     * @see KotonohaMessages
     * @since 0.1.0
     */
    static TranslationConfiguration of(
            final TranslationKeyResolutionPolicy keyPolicy,
            final TranslationArgumentAdaptationPolicy argumentPolicy,
            final InvocationResultTransformationPolicy resultPolicy
    ) {
        Objects.requireNonNull(keyPolicy, "keyPolicy");
        Objects.requireNonNull(argumentPolicy, "argumentPolicy");
        Objects.requireNonNull(resultPolicy, "resultPolicy");

        return new TranslationConfigurationImpl(keyPolicy, argumentPolicy, resultPolicy);
    }

    /**
     * Gets the policy for resolving translation key during method invocation.
     *
     * @return the policy for resolving translation key
     * @since 0.1.0
     */
    TranslationKeyResolutionPolicy keyPolicy();

    /**
     * Gets the policy for adapting translation arguments during method invocation.
     *
     * @return the policy for adapting translation arguments
     * @since 0.1.0
     */
    TranslationArgumentAdaptationPolicy argumentPolicy();

    /**
     * Gets the policy for transforming result components.
     *
     * @return the policy for transforming result components
     * @since 0.1.0
     */
    InvocationResultTransformationPolicy resultPolicy();

    /**
     * Gets a new translation configuration with the modified translation key resolution policy.
     *
     * @param keyPolicy the new policy for resolving translation key
     * @return a new {@code TranslationConfiguration} with the specified policy
     * @since 0.1.0
     */
    default TranslationConfiguration withKeyPolicy(final TranslationKeyResolutionPolicy keyPolicy) {
        Objects.requireNonNull(keyPolicy, "keyPolicy");
        return TranslationConfiguration.of(keyPolicy, this.argumentPolicy(), this.resultPolicy());
    }

    /**
     * Gets a new translation configuration with the modified translation arguments adapting policy.
     *
     * @param argumentPolicy the new policy for adapting translation arguments
     * @return a new {@code TranslationConfiguration} with the specified policy
     * @since 0.1.0
     */
    default TranslationConfiguration withArgumentPolicy(final TranslationArgumentAdaptationPolicy argumentPolicy) {
        Objects.requireNonNull(argumentPolicy, "argumentPolicy");
        return TranslationConfiguration.of(this.keyPolicy(), argumentPolicy, this.resultPolicy());
    }

    /**
     * Gets a new translation configuration with the modified result transforming policy.
     *
     * @param resultPolicy the new policy for transforming result components
     * @return a new {@code TranslationConfiguration} with the specified policy
     * @since 0.1.0
     */
    default TranslationConfiguration withResultPolicy(final InvocationResultTransformationPolicy resultPolicy) {
        Objects.requireNonNull(resultPolicy, "resultPolicy");
        return TranslationConfiguration.of(this.keyPolicy(), this.argumentPolicy(), resultPolicy);
    }
}
