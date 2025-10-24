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

import io.github.namiuni.kotonoha.translatable.message.KotonohaMessage;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.key.TranslationKeyResolutionPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.result.ResultComponentTransformationPolicy;
import java.util.Objects;
import org.jspecify.annotations.NullMarked;

/**
 * Holds invocation policies used when creating proxy instances in {@link KotonohaMessage}.
 * It defines how translation keys, arguments, and results are handled during method invocation.
 *
 * @see KotonohaMessage
 * @since 0.1.0
 */
@NullMarked
@SuppressWarnings("unused")
public sealed interface InvocationConfiguration permits InvocationConfigurationImpl {

    /**
     * Returns an invocation configuration using the specified policies.
     *
     * @param keyPolicy      the policy for resolving {@link net.kyori.adventure.text.TranslatableComponent} key during method invocation
     * @param argumentPolicy the policy for adapting {@link net.kyori.adventure.text.TranslatableComponent} arguments during method invocation
     * @param resultPolicy   the policy for transforming {@link net.kyori.adventure.text.TranslatableComponent} created during method invocation
     * @return a translation configuration
     * @see KotonohaMessage
     * @since 0.1.0
     */
    static InvocationConfiguration of(
            final TranslationKeyResolutionPolicy keyPolicy,
            final TranslationArgumentAdaptationPolicy argumentPolicy,
            final ResultComponentTransformationPolicy resultPolicy
    ) {
        Objects.requireNonNull(keyPolicy, "keyPolicy");
        Objects.requireNonNull(argumentPolicy, "argumentPolicy");
        Objects.requireNonNull(resultPolicy, "resultPolicy");

        return new InvocationConfigurationImpl(keyPolicy, argumentPolicy, resultPolicy);
    }

    /**
     * Returns a policy for resolving translation key during method invocation.
     *
     * @return the policy for resolving translation key
     * @since 0.1.0
     */
    TranslationKeyResolutionPolicy keyPolicy();

    /**
     * Returns a policy for adapting translation arguments during method invocation.
     *
     * @return the policy for adapting translation arguments
     * @since 0.1.0
     */
    TranslationArgumentAdaptationPolicy argumentPolicy();

    /**
     * Returns a policy for transforming result components during method invocation.
     *
     * @return the policy for transforming result components
     * @since 0.1.0
     */
    ResultComponentTransformationPolicy resultPolicy();

    /**
     * Returns an invocation configuration with the modified translation key resolution policy.
     *
     * @param keyPolicy the policy for resolving translation key
     * @return an invocation configuration with the specified policy
     * @since 0.1.0
     */
    default InvocationConfiguration withKeyPolicy(final TranslationKeyResolutionPolicy keyPolicy) {
        Objects.requireNonNull(keyPolicy, "keyPolicy");
        return InvocationConfiguration.of(keyPolicy, this.argumentPolicy(), this.resultPolicy());
    }

    /**
     * Returns an invocation configuration with the modified translation arguments adapting policy.
     *
     * @param argumentPolicy the policy for adapting translation arguments
     * @return an invocation configuration with the specified policy
     * @since 0.1.0
     */
    default InvocationConfiguration withArgumentPolicy(final TranslationArgumentAdaptationPolicy argumentPolicy) {
        Objects.requireNonNull(argumentPolicy, "argumentPolicy");
        return InvocationConfiguration.of(this.keyPolicy(), argumentPolicy, this.resultPolicy());
    }

    /**
     * Returns an invocation configuration with the modified result transforming policy.
     *
     * @param resultPolicy the policy for transforming result components
     * @return an invocation configuration with the specified policy
     * @since 0.1.0
     */
    default InvocationConfiguration withResultPolicy(final ResultComponentTransformationPolicy resultPolicy) {
        Objects.requireNonNull(resultPolicy, "resultPolicy");
        return InvocationConfiguration.of(this.keyPolicy(), this.argumentPolicy(), resultPolicy);
    }
}
