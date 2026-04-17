/*
 * This file is part of kotonoha, licensed under the MIT License.
 *
 * Copyright (c) 2026 Namiu (うにたろう)
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
package io.github.namiuni.kotonoha.resourcebundle.generator.processor;

import io.github.namiuni.kotonoha.annotations.Key;
import io.github.namiuni.kotonoha.annotations.Locales;
import io.github.namiuni.kotonoha.annotations.Message;
import io.github.namiuni.kotonoha.annotations.Messages;
import io.github.namiuni.kotonoha.annotations.ResourceBundle;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/// An annotation processor that generates Java [java.util.ResourceBundle] property files
/// from interfaces annotated with [ResourceBundle].
///
/// This processor scans methods within annotated interfaces for [Key], [ResourceBundle], [Message],
/// [Messages] annotations
/// to construct translation keys and messages. It then writes these into standard
/// `.properties` files, grouped by locale.
///
/// Keys in the generated files are written in the same order as the method declarations
/// in the source interface, because [javax.lang.model.element.TypeElement#getEnclosedElements()]
/// preserves source declaration order per the `javax.lang.model` specification.
///
/// @since 0.1.0
@NullMarked
@SupportedAnnotationTypes({
        "io.github.namiuni.kotonoha.annotations.Key",
        "io.github.namiuni.kotonoha.annotations.ResourceBundle",
        "io.github.namiuni.kotonoha.annotations.Message",
        "io.github.namiuni.kotonoha.annotations.Messages"
})
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public final class ResourceBundleGeneratorProcessor extends AbstractProcessor {

    private @Nullable Filer filer;
    private @Nullable Messager messager;

    /**
     * Creates a new {@code ResourceBundleGeneratorProcessor} instance.
     *
     * @since 0.1.0
     */
    public ResourceBundleGeneratorProcessor() {
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }

        final Set<? extends Element> resourceBundleElements = roundEnv.getElementsAnnotatedWith(ResourceBundle.class);

        for (final Element element : resourceBundleElements) {
            if (element.getKind() != ElementKind.INTERFACE) {
                Objects.requireNonNull(this.messager).printMessage(Diagnostic.Kind.ERROR, "@ResourceBundle can only be applied to interfaces", element);
                continue;
            }

            this.processResourceBundleInterface((TypeElement) element);
        }

        return true;
    }

    private void processResourceBundleInterface(final TypeElement typeElement) {
        final ResourceBundle resourceBundleAnnotation = typeElement.getAnnotation(ResourceBundle.class);
        if (resourceBundleAnnotation == null) {
            return;
        }

        final String baseName = resourceBundleAnnotation.baseName();

        // LinkedHashMap preserves locale insertion order.
        // The inner LinkedHashMap preserves key insertion order, which corresponds
        // to method declaration order because getEnclosedElements() is ordered by
        // source position per the javax.lang.model specification.
        final Map<String, Map<String, String>> localeEntries = new LinkedHashMap<>();

        for (final Element enclosedElement : typeElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                this.processMethod((ExecutableElement) enclosedElement, localeEntries);
            }
        }

        this.writePropertiesFiles(baseName, localeEntries);
    }

    private void processMethod(
            final ExecutableElement method,
            final Map<String, Map<String, String>> localeEntries
    ) {
        if (method.isDefault() || method.getModifiers().contains(Modifier.STATIC)) {
            return;
        }

        final Key keyAnnotation = method.getAnnotation(Key.class);
        if (keyAnnotation == null) {
            final String message = "Method missing @Key annotation: %s";
            Objects.requireNonNull(this.messager).printMessage(Diagnostic.Kind.WARNING, message.formatted(method.getSimpleName()), method);
            return;
        }

        final String key = keyAnnotation.value();
        final Message[] messageAnnotations = this.getMessageAnnotations(method);

        for (final Message messageAnnotation : messageAnnotations) {
            final String localeKey = this.getLocaleKey(messageAnnotation.locale());
            localeEntries.computeIfAbsent(localeKey, _ -> new LinkedHashMap<>())
                    .put(key, messageAnnotation.content());
        }
    }

    private Message[] getMessageAnnotations(final ExecutableElement method) {
        final Messages messagesAnnotation = method.getAnnotation(Messages.class);
        if (messagesAnnotation != null) {
            return messagesAnnotation.value();
        }

        final Message messageAnnotation = method.getAnnotation(Message.class);
        if (messageAnnotation != null) {
            return new Message[] {messageAnnotation};
        }

        return new Message[0];
    }

    private String getLocaleKey(final Locales locale) {
        if (locale == Locales.ROOT) {
            return "";
        }
        return "_" + locale.asLocale();
    }

    private void writePropertiesFiles(
            final String baseName,
            final Map<String, Map<String, String>> localeEntries
    ) {
        for (final Map.Entry<String, Map<String, String>> entry : localeEntries.entrySet()) {
            final String localeKey = entry.getKey();
            final Map<String, String> entries = entry.getValue();
            final String fileName = baseName + localeKey + ".properties";

            try (Writer writer = Objects.requireNonNull(this.filer).createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter()) {
                writer.write("# Generated by ResourceBundleGeneratorProcessor\n");
                for (final Map.Entry<String, String> prop : entries.entrySet()) {
                    writer.write(escapeKey(prop.getKey()));
                    writer.write('=');
                    writer.write(escapeValue(prop.getValue()));
                    writer.write('\n');
                }
                Objects.requireNonNull(this.messager).printMessage(Diagnostic.Kind.NOTE, "Generated resource bundle: " + fileName);
            } catch (final IOException exception) {
                final String message = "Failed to write properties file: %s - %s";
                Objects.requireNonNull(this.messager).printMessage(Diagnostic.Kind.ERROR, message.formatted(fileName, exception.getMessage()));
            }
        }
    }

    private static String escapeKey(final String key) {
        return escape(key, true);
    }

    private static String escapeValue(final String value) {
        return escape(value, false);
    }

    private static String escape(final String input, final boolean escapeSpace) {
        final int length = input.length();
        final StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            final char ch = input.charAt(i);
            switch (ch) {
                case '\\' -> builder.append("\\\\");
                case '\n' -> builder.append("\\n");
                case '\r' -> builder.append("\\r");
                case '\t' -> builder.append("\\t");
                case '\f' -> builder.append("\\f");
                case ' ' -> {
                    if (escapeSpace || i == 0) {
                        builder.append("\\ ");
                    } else {
                        builder.append(ch);
                    }
                }
                case '=', ':', '#', '!' -> {
                    builder.append('\\');
                    builder.append(ch);
                }
                default -> {
                    if (ch < 0x0020 || ch > 0x007e) {
                        builder.append(String.format("\\u%04x", (int) ch));
                    } else {
                        builder.append(ch);
                    }
                }
            }
        }

        return builder.toString();
    }
}
