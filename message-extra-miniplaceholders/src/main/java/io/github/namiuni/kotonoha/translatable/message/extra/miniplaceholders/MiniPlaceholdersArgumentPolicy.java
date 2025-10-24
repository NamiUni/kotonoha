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
package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import io.github.namiuni.kotonoha.translatable.message.context.InvocationArgument;
import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.KotonohaValidationException;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.TranslationArgumentAdaptationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.tag.TagNameResolver;
import io.github.namiuni.kotonoha.translatable.message.utility.TranslationArgumentAdapter;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.jspecify.annotations.NullMarked;

/**
 * A policy that integrates {@code MiniPlaceholders} tag resolvers into the translation argument
 * adaptation process.
 * <p>
 * This policy extends the standard MiniMessage argument adaptation by automatically
 * injecting MiniPlaceholders {@link TagResolver} instances as additional translation
 * arguments when a method is annotated with {@link WithPlaceholders}.
 * <p>
 * The policy works by:
 * <ol>
 *   <li>Delegating to the standard MiniMessage policy to adapt method arguments</li>
 *   <li>Checking if the invoked method has a {@link WithPlaceholders} annotation</li>
 *   <li>If present, appending the appropriate MiniPlaceholders {@link TagResolver}
 *       based on the specified {@link PlaceholderScope}</li>
 * </ol>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * public interface ExampleMessages {
 *     @Key("welcome.message")
 *     @WithPlaceholders(PlaceholderScope.GLOBAL)
 *     Component welcomeMessage(@Name("player_name") String playerName);
 * }
 *
 * // The policy will automatically inject global placeholders
 * // Translation message: "Welcome <player_name>! Server TPS: <server_tps>"
 * }</pre>
 *
 * @see WithPlaceholders
 * @see PlaceholderScope
 * @see io.github.miniplaceholders.api.MiniPlaceholders
 * @since 0.1.0
 */
@NullMarked
public final class MiniPlaceholdersArgumentPolicy implements TranslationArgumentAdaptationPolicy {

    private final TranslationArgumentAdaptationPolicy minimessagePolicy;

    private MiniPlaceholdersArgumentPolicy(final TranslationArgumentAdaptationPolicy minimessagePolicy) {
        this.minimessagePolicy = minimessagePolicy;
    }

    /**
     * Returns a {@link io.github.miniplaceholders.api.MiniPlaceholders} policy.
     * <p>
     * This policy internally creates a standard MiniMessage argument adaptation policy
     * using the provided adapter and name resolver, then wraps it to add MiniPlaceholders
     * support.
     *
     * @param argumentAdapter the adapter used to transform method argument types to translation arguments
     * @param nameResolver the resolver used to obtain the argument name from parameter
     * @return a miniplaceholders argument adaptation policy
     * @see io.github.miniplaceholders.api.MiniPlaceholders
     * @see TranslationArgumentAdaptationPolicy#miniMessage(TranslationArgumentAdapter, TagNameResolver)
     * @see WithPlaceholders
     * @see PlaceholderScope
     * @since 0.1.0
     */
    public static MiniPlaceholdersArgumentPolicy of(final TranslationArgumentAdapter argumentAdapter, final TagNameResolver nameResolver) {
        final TranslationArgumentAdaptationPolicy minimessagePolicy = TranslationArgumentAdaptationPolicy.miniMessage(argumentAdapter, nameResolver);
        return new MiniPlaceholdersArgumentPolicy(minimessagePolicy);
    }

    @Override
    public ComponentLike[] adaptArgumentArray(final InvocationContext context) throws IllegalArgumentException, NullPointerException {
        final ComponentLike[] standardArguments = this.minimessagePolicy.adaptArgumentArray(context);

        if (!MiniPlaceholdersIntegration.miniPlaceholdersLoaded()) {
            return standardArguments;
        }

        final Method method = context.method();
        if (!method.isAnnotationPresent(WithPlaceholders.class)) {
            return standardArguments;
        }

        final ComponentLike[] placeholdersIncludedArguments = Arrays.copyOf(standardArguments, standardArguments.length + 1);

        // Gets the MiniPlaceholders placeholders
        final WithPlaceholders placeholderAnnotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = placeholderAnnotation.value();
        final TagResolver placeholders = placeholderScope.placeholders();

        // Add the placeholder argument
        final ComponentLike placeholderArgument = Argument.tagResolver(placeholders);
        placeholdersIncludedArguments[standardArguments.length] = placeholderArgument;

        return placeholdersIncludedArguments;
    }

    @Override
    public ComponentLike adaptArgument(final InvocationArgument invocationArgument) throws IllegalArgumentException, NullPointerException {
        return this.minimessagePolicy.adaptArgument(invocationArgument);
    }

    @Override
    public void validate(final Method method) throws KotonohaValidationException {
        this.minimessagePolicy.validate(method);
    }
}
