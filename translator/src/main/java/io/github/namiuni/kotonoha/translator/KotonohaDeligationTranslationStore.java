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
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.translation.TranslationStore;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
abstract sealed class KotonohaDeligationTranslationStore<T> implements KotonohaTranslationStore<T> permits KotonohaMessageFormatTranslationStore, KotonohaMiniMessageTranslationStore {

    private final TranslationStore.StringBased<T> store;

    protected KotonohaDeligationTranslationStore(final TranslationStore.StringBased<T> store) {
        this.store = store;
    }

    @Override
    public void registerInterface(final Class<?> messageInterface) throws IllegalStateException {
        final Method[] methods = messageInterface.getMethods();
        for (final Method method : methods) {
            this.registerMethod(method);
        }
    }

    private void registerMethod(final Method method) throws IllegalStateException {
        if (method.isDefault()) {
            return;
        }

        if (ObjectMethodChecker.isObjectMethod(method)) {
            return;
        }

        final String translationKey = getTranslationKey(method);

        final Message[] messageAnnotations = method.getAnnotationsByType(Message.class);
        if (messageAnnotations.length == 0) {
            final String annotationName = "@" + Message.class.getSimpleName();
            final String message = "Missing annotation '%s' on method '%s'".formatted(annotationName, method.getName());
            throw new IllegalStateException(message);
        }

        for (final Message messageAnnotation : messageAnnotations) {
            final String localeString = messageAnnotation.locale();
            final Locale locale = LocaleParser.parseLocale(localeString);

            final String translationMessage = messageAnnotation.content();
            final T parsedMessage = this.parse(translationMessage, locale);

            this.store.register(translationKey, locale, parsedMessage);
        }
    }

    abstract T parse(String translationMessage, Locale locale);

    private static String getTranslationKey(final Method method) {
        final var keyClass = io.github.namiuni.kotonoha.annotations.Key.class;
        if (!method.isAnnotationPresent(keyClass)) {
            final String annotationName = "@" + keyClass.getSimpleName();
            final String message = "Missing annotation '%s' on method '%s'".formatted(annotationName, method.getName());
            throw new IllegalStateException(message);
        }

        final var keyAnnotation = method.getAnnotation(keyClass);
        return keyAnnotation.value();
    }

    @Override
    public void registerAll(final Locale locale, final Path path, final boolean escapeSingleQuotes) {
        this.store.registerAll(locale, path, escapeSingleQuotes);
    }

    @Override
    public void registerAll(final Locale locale, final ResourceBundle bundle, final boolean escapeSingleQuotes) {
        this.store.registerAll(locale, bundle, escapeSingleQuotes);
    }

    @Override
    public boolean contains(final String key) {
        return this.store.contains(key);
    }

    @Override
    public boolean contains(final String key, final Locale locale) {
        return this.store.contains(key, locale);
    }

    @Override
    public void defaultLocale(final Locale locale) {
        this.store.defaultLocale(locale);
    }

    @Override
    public void register(final String key, final Locale locale, final T translation) {
        this.store.register(key, locale, translation);
    }

    @Override
    public void registerAll(final Locale locale, final Map<String, T> translations) {
        this.store.registerAll(locale, translations);
    }

    @Override
    public void registerAll(final Locale locale, final Set<String> keys, final Function<String, T> function) {
        this.store.registerAll(locale, keys, function);
    }

    @Override
    public void unregister(final String key) {
        this.store.unregister(key);
    }

    @Override
    public Key name() {
        return this.store.name();
    }

    @Override
    public @Nullable MessageFormat translate(final String key, final Locale locale) {
        return this.store.translate(key, locale);
    }

    @Override
    public @Nullable Component translate(final TranslatableComponent component, final Locale locale) {
        return this.store.translate(component, locale);
    }
}
