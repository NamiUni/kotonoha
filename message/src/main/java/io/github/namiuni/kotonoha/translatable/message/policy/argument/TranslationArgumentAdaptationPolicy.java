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

import io.github.namiuni.kotonoha.translatable.message.context.InvocationArgument;
import io.github.namiuni.kotonoha.translatable.message.context.InvocationContext;
import io.github.namiuni.kotonoha.translatable.message.policy.InvocationPolicy;
import io.github.namiuni.kotonoha.translatable.message.policy.argument.tag.TagNameResolver;
import io.github.namiuni.kotonoha.translatable.message.utility.TranslationArgumentAdapter;
import java.lang.reflect.Method;
import net.kyori.adventure.text.ComponentLike;
import org.jspecify.annotations.NullMarked;

/**
 * Defines how method arguments are adapted into {@link ComponentLike} values
 * for {@link net.kyori.adventure.text.TranslatableComponent} construction.
 *
 * @see java.lang.reflect.InvocationHandler
 * @see net.kyori.adventure.text.TranslationArgument
 * @since 0.1.0
 */
@NullMarked
public interface TranslationArgumentAdaptationPolicy extends InvocationPolicy {

    /**
     * Empty {@link ComponentLike} array.
     */
    ComponentLike[] EMPTY_COMPONENT_LIKE_ARRAY = new ComponentLike[0];

    /**
     * Returns a MessageFormat-style policy using the standard argument adapter.
     *
     * @return a MessageFormat argument adaptation policy
     * @see java.text.MessageFormat
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy messageFormat() {
        return MessageFormatTranslationArgumentAdaptationPolicy.STANDARD_INSTANCE;
    }

    /**
     * Returns a MessageFormat-style policy with a custom argument adapter.
     *
     * @param argumentAdapter the adapter for converting method parameter types
     * @return a MessageFormat argument adaptation policy
     * @throws NullPointerException if {@code argumentAdapter} is null
     * @see java.text.MessageFormat
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy messageFormat(final TranslationArgumentAdapter argumentAdapter) throws NullPointerException {
        return new MessageFormatTranslationArgumentAdaptationPolicy(argumentAdapter);
    }

    /**
     * Returns a MiniMessage-style policy using the standard adapter and annotation-based name resolution.
     *
     * <p>Special parameter handling:
     * <ul>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.Tag} - Passed through as a tag</li>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.resolver.TagResolver} - Passed through as a tag resolver</li>
     * <li>{@link net.kyori.adventure.pointer.Pointered} - When annotated with
     * a {@link io.github.namiuni.kotonoha.annotations.Target} or {@link net.kyori.adventure.pointer.Pointered} type,
     * used as a translation target</li>
     * </ul>
     *
     * @return a MiniMessage argument adaptation policy
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @see net.kyori.adventure.text.minimessage.translation.MiniMessageTranslator
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy miniMessage() {
        return MiniMessageTranslationArgumentAdaptationPolicy.STANDARD_INSTANCE;
    }

    /**
     * Returns a MiniMessage-style policy with a custom name resolver.
     *
     * <p>Special parameter handling:
     * <ul>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.Tag} - Passed through as a tag</li>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.resolver.TagResolver} - Passed through as a tag resolver</li>
     * <li>{@link net.kyori.adventure.pointer.Pointered} - When annotated with
     * a {@link io.github.namiuni.kotonoha.annotations.Target} or {@link net.kyori.adventure.pointer.Pointered} type,
     * used as a translation target</li>
     * </ul>
     *
     * @param nameResolver the resolver for extracting tag names from parameters
     * @return a MiniMessage argument adaptation policy
     * @throws NullPointerException if {@code nameResolver} is null
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy miniMessage(final TagNameResolver nameResolver) {
        return new MiniMessageTranslationArgumentAdaptationPolicy(TranslationArgumentAdapter.standard(), nameResolver);
    }

    /**
     * Returns a MiniMessage-style policy with a custom type adapter.
     *
     * <p>Special parameter handling:
     * <ul>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.Tag} - Passed through as a tag</li>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.resolver.TagResolver} - Passed through as a tag resolver</li>
     * <li>{@link net.kyori.adventure.pointer.Pointered} - When annotated with
     * a {@link io.github.namiuni.kotonoha.annotations.Target} or {@link net.kyori.adventure.pointer.Pointered} type,
     * used as a translation target</li>
     * </ul>
     *
     * @param argumentAdapter the adapter for converting method parameter types
     * @return a MiniMessage argument adaptation policy
     * @throws NullPointerException if {@code argumentAdapter} is null
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy miniMessage(final TranslationArgumentAdapter argumentAdapter) {
        return new MiniMessageTranslationArgumentAdaptationPolicy(argumentAdapter, TagNameResolver.annotationNameResolver());
    }

    /**
     * Returns a MiniMessage-style policy with custom adapter and name resolver.
     *
     * <p>Special parameter handling:
     * <ul>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.Tag} - Passed through as a tag</li>
     * <li>{@link net.kyori.adventure.text.minimessage.tag.resolver.TagResolver} - Passed through as a tag resolver</li>
     * <li>{@link net.kyori.adventure.pointer.Pointered} - When annotated with
     * a {@link io.github.namiuni.kotonoha.annotations.Target} or {@link net.kyori.adventure.pointer.Pointered} type,
     * used as a translation target</li>
     * </ul>
     *
     * @param argumentAdapter the adapter for converting method parameter types
     * @param nameResolver    the resolver for extracting tag names from parameters
     * @return a MiniMessage argument adaptation policy
     * @throws NullPointerException if either parameter is null
     * @see net.kyori.adventure.text.minimessage.MiniMessage
     * @since 0.1.0
     */
    static TranslationArgumentAdaptationPolicy miniMessage(final TranslationArgumentAdapter argumentAdapter, final TagNameResolver nameResolver) {
        return new MiniMessageTranslationArgumentAdaptationPolicy(argumentAdapter, nameResolver);
    }

    /**
     * Adapts all method arguments from the invocation context into translation arguments.
     *
     * @param context the invocation context containing method information and arguments
     * @return an array of adapted {@link ComponentLike} values for the translatable component
     * @throws IllegalArgumentException if any argument cannot be adapted according to policy rules
     * @throws NullPointerException     if any required argument is null
     * @see java.lang.reflect.InvocationHandler#invoke(Object, Method, Object[])
     * @see net.kyori.adventure.text.TranslatableComponent
     * @see net.kyori.adventure.text.TranslationArgument
     * @since 0.1.0
     */
    default ComponentLike[] adaptArgumentArray(final InvocationContext context) throws IllegalArgumentException, NullPointerException {
        final InvocationArgument[] invocationArguments = context.invocationArguments();
        if (invocationArguments.length == 0) {
            return EMPTY_COMPONENT_LIKE_ARRAY;
        }

        final ComponentLike[] translationArguments = new ComponentLike[invocationArguments.length];
        for (int i = 0; i < invocationArguments.length; i++) {
            final InvocationArgument invocationArgument = invocationArguments[i];
            translationArguments[i] = this.adaptArgument(invocationArgument);
        }

        return translationArguments;
    }

    /**
     * Adapts a single method argument into a translation argument.
     *
     * @param invocationArgument the argument to adapt, containing parameter metadata and value
     * @return a adapted {@link ComponentLike} value
     * @throws IllegalArgumentException if the argument cannot be adapted
     * @throws NullPointerException     if the argument value is null when required
     * @see net.kyori.adventure.text.TranslatableComponent
     * @see net.kyori.adventure.text.TranslationArgument
     * @since 0.1.0
     */
    ComponentLike adaptArgument(InvocationArgument invocationArgument) throws IllegalArgumentException, NullPointerException;
}
