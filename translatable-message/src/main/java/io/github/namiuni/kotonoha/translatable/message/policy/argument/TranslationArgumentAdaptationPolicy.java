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
package io.github.namiuni.kotonoha.translatable.message.policy.argument;

import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.InvocationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.name.ArgumentNameResolver;
import io.github.namiuni.kotonoha.translatable.message.utility.TranslationArgumentAdapter;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.pointer.Pointered;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Defines a strategy for adapting interface method arguments into
 * {@link ComponentLike} values used by a {@link net.kyori.adventure.text.TranslatableComponent}.
 * <p>
 * This policy is executed during the proxy method invocation to prepare the arguments
 * for the translatable component creation.
 *
 * @see java.lang.reflect.InvocationHandler
 * @see net.kyori.adventure.text.TranslationArgument
 * @since 0.1.0
 */
@NullMarked
@ApiStatus.Experimental
public sealed interface TranslationArgumentAdaptationPolicy extends InvocationPolicy permits CustomTranslationArgumentAdaptationPolicy, MessageFormatTranslationArgumentAdaptationPolicy, MiniMessageTranslationArgumentAdaptationPolicy {

    /**
     * An empty {@link ComponentLike} array returned when the invoked method has no arguments.
     */
    ComponentLike[] EMPTY_COMPONENT_LIKE_ARRAY = new ComponentLike[0];

    /**
     * Returns a policy that adapts arguments based on their positional order
     * for use with {@link java.text.MessageFormat} style translation strings.
     *
     * @param argumentAdapter the adapter used to transform method argument types to translation arguments
     * @return a policy for {@code MessageFormat} argument handling
     * @see java.text.MessageFormat
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy messageFormat(final TranslationArgumentAdapter argumentAdapter) {
        Objects.requireNonNull(argumentAdapter, "argumentAdapter");
        return new MessageFormatTranslationArgumentAdaptationPolicy(argumentAdapter);
    }

    /**
     * Returns a policy that adapts arguments based on their resolved name
     * for use with {@link net.kyori.adventure.text.minimessage.MiniMessage} style translation strings.
     * <p>
     * This policy supports special parameter types for MiniMessage, which are directly
     * passed to the translation context without standard adaptation:
     * <ul>
     * <li>Arguments of type {@link net.kyori.adventure.text.minimessage.tag.Tag} are passed through to the translation context without further adaptation.</li>
     * <li>Arguments of type {@link net.kyori.adventure.text.minimessage.tag.resolver.TagResolver} are passed through to the translation context without further adaptation.</li>
     * <li>A single argument of type {@link Pointered} is used as the target for context-aware translation.</li>
     * </ul>
     *
     * @param argumentAdapter the adapter used to transform method argument types to translation arguments
     * @param nameResolver    the resolver used to obtain the argument name from parameter.
     * @return a policy for {@code MiniMessage} argument handling.
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @see net.kyori.adventure.text.minimessage.translation.MiniMessageTranslator
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy miniMessage(final TranslationArgumentAdapter argumentAdapter, final ArgumentNameResolver nameResolver) {
        Objects.requireNonNull(argumentAdapter, "argumentAdapter");
        Objects.requireNonNull(nameResolver, "nameResolver");
        return new MiniMessageTranslationArgumentAdaptationPolicy(argumentAdapter, nameResolver);
    }

    /**
     * Adapts the method arguments found in the invocation context to an array of
     * {@link ComponentLike} objects suitable for a {@link net.kyori.adventure.text.TranslatableComponent}.
     *
     * @param context the invocation context containing the method and its arguments
     * @return an array of {@link ComponentLike} values representing the adapted translation arguments
     * @throws IllegalArgumentException if an argument value is invalid or cannot be adapted based on the policy rules
     * @throws NullPointerException     if an argument in the method is null
     * @see java.lang.reflect.InvocationHandler#invoke(Object, Method, Object[])
     * @see net.kyori.adventure.text.TranslatableComponent
     * @see net.kyori.adventure.text.TranslationArgument
     * @since 0.1.0
     */
    ComponentLike[] adaptArguments(InvocationContext context) throws IllegalArgumentException, NullPointerException;
}
