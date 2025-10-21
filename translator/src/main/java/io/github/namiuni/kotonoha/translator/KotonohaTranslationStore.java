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
package io.github.namiuni.kotonoha.translator;

import io.github.namiuni.kotonoha.annotations.Message;
import java.text.MessageFormat;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.TranslationStore;
import org.jspecify.annotations.NullMarked;

/**
 * Define a translation store for registering translations from the message interface.
 *
 * @param <T> the type of the translation
 * @since 0.1.0
 */
@NullMarked
public sealed interface KotonohaTranslationStore<T> extends TranslationStore.StringBased<T> permits KotonohaDeligationTranslationStore {

    /**
     * Returns a {@link java.text.MessageFormat}-based translation store.
     *
     * @param name the unique {@link Key} identifying the translation store
     * @return a translation store that uses {@code MessageFormat} for translations
     * @see TranslationStore#messageFormat(Key)
     * @since 0.1.0
     */
    static KotonohaTranslationStore<MessageFormat> messageFormat(final Key name) {
        return new KotonohaMessageFormatTranslationStore(name);
    }

    /**
     * Returns a {@link MiniMessage}-based translation store using a custom {@link MiniMessage}.
     *
     * @param name        the unique {@link Key} identifying the translation store
     * @param miniMessage the {@link MiniMessage} instance used for deserialization
     * @return a translation store that uses {@code MiniMessage} for translations
     * @see net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore
     * @since 0.1.0
     */
    static KotonohaTranslationStore<String> miniMessage(final Key name, final MiniMessage miniMessage) {
        return new KotonohaMiniMessageTranslationStore(name, miniMessage);
    }

    /**
     * Returns a {@link MiniMessage}-based translation store using the default {@link MiniMessage}.
     *
     * @param name the unique {@link Key} identifying the translation store
     * @return a translation store that uses the default {@code MiniMessage} instance
     * @see net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore
     * @since 0.1.0
     */
    static KotonohaTranslationStore<String> miniMessage(final Key name) {
        return new KotonohaMiniMessageTranslationStore(name, MiniMessage.miniMessage());
    }

    /**
     * Registers a message interface containing annotated translations.
     * <p>
     * This method scans the given interface for {@link io.github.namiuni.kotonoha.annotations.Key} and {@link Message} annotated methods
     * and registers their localized content into the translation store.
     *
     * @param messageInterface the message interface
     * @throws IllegalStateException if a method is not annotated with either {@link io.github.namiuni.kotonoha.annotations.Key} or {@link Message}
     * @see Message
     * @since 0.1.0
     */
    void registerInterface(Class<?> messageInterface) throws IllegalStateException;
}
