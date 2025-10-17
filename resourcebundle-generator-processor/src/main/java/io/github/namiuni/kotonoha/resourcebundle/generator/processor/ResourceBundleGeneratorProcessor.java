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
package io.github.namiuni.kotonoha.resourcebundle.generator.processor;

import io.github.namiuni.kotonoha.annotations.Key;
import io.github.namiuni.kotonoha.annotations.Message;
import io.github.namiuni.kotonoha.annotations.Messages;
import io.github.namiuni.kotonoha.annotations.ResourceBundle;
import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
import org.jspecify.annotations.NonNull;

/**
 * An annotation processor that generates Java {@link java.util.ResourceBundle} property files
 * from interfaces annotated with {@link ResourceBundle}.
 *
 * <p>This processor scans methods within annotated interfaces for {@link Key}, {@link ResourceBundle}, {@link Message},
 * {@link Messages} annotations
 * to construct translation keys and messages. It then writes these into standard
 * `.properties` files, grouped by locale.</p>
 *
 * @since 0.1.0
 */
@SupportedAnnotationTypes({
        "io.github.namiuni.kotonoha.annotations.Key",
        "io.github.namiuni.kotonoha.annotations.ResourceBundle",
        "io.github.namiuni.kotonoha.annotations.Message",
        "io.github.namiuni.kotonoha.annotations.Messages"
})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public final class ResourceBundleGeneratorProcessor extends AbstractProcessor {

    private static final Supplier<Properties> SORTED_PROPERTIES = () -> new Properties() {
        @Override
        public synchronized @NonNull Set<Map.Entry<Object, Object>> entrySet() {
            return Set.copyOf(
                    (Set<? extends Map.Entry<Object, Object>>) super.entrySet()
                            .stream()
                            .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
                            .collect(Collectors.toCollection(LinkedHashSet::new)));
        }
    };

    private Filer filer;
    private Messager messager;

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
                final String message = "@ResourceBundle can only be applied to interfaces";
                this.messager.printMessage(Diagnostic.Kind.ERROR, message, element);
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
        final Map<String, Properties> localeProperties = new HashMap<>();

        // Process all methods in the interface
        for (final Element enclosedElement : typeElement.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                this.processMethod((ExecutableElement) enclosedElement, localeProperties);
            }
        }

        // Write properties files for each locale
        this.writePropertiesFiles(baseName, localeProperties);
    }

    private void processMethod(final ExecutableElement method, final Map<String, Properties> localeProperties) {
        if (method.isDefault() || method.getModifiers().contains(Modifier.STATIC)) {
            return;
        }

        final Key keyAnnotation = method.getAnnotation(Key.class);
        if (keyAnnotation == null) {
            final String message = "Method missing @Key annotation: %s";
            this.messager.printMessage(Diagnostic.Kind.WARNING, message.formatted(method.getSimpleName()), method);
            return;
        }

        final String key = keyAnnotation.value();

        // Process @Message annotations (both single and repeatable)
        final Message[] messageAnnotations = this.getMessageAnnotations(method);

        for (final Message messageAnnotation : messageAnnotations) {
            final String localeKey = this.getLocaleKey(messageAnnotation.locale());
            final String content = messageAnnotation.content();

            localeProperties.computeIfAbsent(localeKey, k -> SORTED_PROPERTIES.get()).setProperty(key, content);
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

    private String getLocaleKey(final String localeString) {
        final Locale locale = Locale.of(localeString);
        if (locale.equals(Locale.ROOT)) {
            return "";
        }
        return "_" + locale;
    }

    private void writePropertiesFiles(final String baseName, final Map<String, Properties> localeProperties) {
        for (final Map.Entry<String, Properties> entry : localeProperties.entrySet()) {
            final String localeKey = entry.getKey();
            final Properties properties = entry.getValue();

            final String fileName = baseName + localeKey + ".properties";
            try (Writer writer = this.filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName).openWriter()) {
                properties.store(writer, "Generated by ResourceBundleGeneratorProcessor");
                this.messager.printMessage(Diagnostic.Kind.NOTE, "Generated resource bundle: " + fileName);

            } catch (final IOException exception) {
                final String message = "Failed to write properties file: %s - %s";
                this.messager.printMessage(Diagnostic.Kind.ERROR, message.formatted(fileName, exception.getMessage()));
            }
        }
    }
}
