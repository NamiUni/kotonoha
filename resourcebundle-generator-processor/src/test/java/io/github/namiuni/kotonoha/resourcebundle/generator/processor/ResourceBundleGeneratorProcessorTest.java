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

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.tools.JavaFileObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ResourceBundleGeneratorProcessor tests")
class ResourceBundleGeneratorProcessorTest {

    private static final String TEST_PACKAGE = "test";

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {

        @Test
        @DisplayName("Verify that supported annotation types are correct")
        void testGetSupportedAnnotationTypes() {
            final ResourceBundleGeneratorProcessor processor = new ResourceBundleGeneratorProcessor();
            final Set<String> supportedTypes = processor.getSupportedAnnotationTypes();

            assertTrue(supportedTypes.contains("io.github.namiuni.kotonoha.annotations.Key"));
            assertTrue(supportedTypes.contains("io.github.namiuni.kotonoha.annotations.ResourceBundle"));
            assertTrue(supportedTypes.contains("io.github.namiuni.kotonoha.annotations.Message"));
            assertTrue(supportedTypes.contains("io.github.namiuni.kotonoha.annotations.Messages"));

            assertEquals(4, supportedTypes.size());

            assertFalse(supportedTypes.contains("java.lang.Override"));
            assertFalse(supportedTypes.contains("java.lang.Deprecated"));
        }

        @Test
        @DisplayName("Verify that supported Java source version is correct")
        void testGetSupportedSourceVersion() {
            final ResourceBundleGeneratorProcessor processor = new ResourceBundleGeneratorProcessor();
            assertEquals(SourceVersion.RELEASE_21, processor.getSupportedSourceVersion());
        }
    }

    @Nested
    @DisplayName("Successful processing tests")
    class SuccessfulProcessingTests {

        @Test
        @DisplayName("Verify that valid interface is processed correctly")
        void testProcessWithValidInterface() {
            final JavaFileObject testMessageService = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".TestMessageService",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            import io.github.namiuni.kotonoha.annotations.Messages;
                            
                            @ResourceBundle(baseName = "test-messages")
                            public interface TestMessageService {
                            
                                @Key("test.simple.message")
                                @Message(locale = Locales.EN_US, content = "Hello World")
                                void simpleMessage();
                            
                                @Key("test.multiple.locales")
                                @Messages({
                                    @Message(locale = Locales.EN_US, content = "Welcome"),
                                    @Message(locale = Locales.JA_JP, content = "ようこそ")
                                })
                                void multipleLocales();
                            
                                @Key("test.with.parameters")
                                @Message(locale = Locales.EN_US, content = "Hello <name>!")
                                void withParameters(String name);
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testMessageService);

            assertThat(compilation).succeeded();
            assertThat(compilation).hadNoteContaining("Generated resource bundle");
        }

        @Test
        @DisplayName("Verify that method with single @Message annotation is processed correctly")
        void testProcessWithSingleMessageAnnotation() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".SingleMessageInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "single-message")
                            public interface SingleMessageInterface {
                                @Key("hello.world")
                                @Message(locale = Locales.EN_US, content = "Hello, World!")
                                void helloWorld();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
        }

        @Test
        @DisplayName("Verify that empty interface is processed without warnings")
        void testProcessWithEmptyInterface() {
            final JavaFileObject emptyInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".EmptyInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "empty")
                            public interface EmptyInterface {
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(emptyInterface);

            assertThat(compilation).succeeded();
        }

        @Test
        @DisplayName("Verify that repeatable @Message annotations are processed correctly")
        void testProcessWithRepeatableMessageAnnotations() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".RepeatableMessageInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "repeatable-messages")
                            public interface RepeatableMessageInterface {
                                @Key("test.repeatable.message")
                                @Message(locale = Locales.EN_US, content = "Hello")
                                @Message(locale = Locales.JA_JP, content = "こんにちは")
                                @Message(locale = Locales.DE_DE, content = "Hallo")
                                void multipleRepeatableMessages();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            assertThat(compilation).hadNoteContaining("Generated resource bundle");
        }

        @Test
        @DisplayName("Verify that mixed @Message and @Messages annotations work correctly")
        void testProcessWithMixedMessageAnnotations() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".MixedMessageInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            import io.github.namiuni.kotonoha.annotations.Messages;
                            
                            @ResourceBundle(baseName = "mixed-messages")
                            public interface MixedMessageInterface {
                                @Key("test.repeatable")
                                @Message(locale = Locales.EN_US, content = "Repeatable Hello")
                                @Message(locale = Locales.JA_JP, content = "リピータブル こんにちは")
                                void repeatableMessages();
                            
                                @Key("test.wrapped")
                                @Messages({
                                    @Message(locale = Locales.EN_US, content = "Wrapped Hello"),
                                    @Message(locale = Locales.DE_DE, content = "Eingepackt Hallo")
                                })
                                void wrappedMessages();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            assertThat(compilation).hadNoteContaining("Generated resource bundle");
        }

        @Test
        @DisplayName("Verify that Locales.ROOT is processed correctly")
        void testProcessWithRootLocale() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".RootLocaleInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "root-locale")
                            public interface RootLocaleInterface {
                                @Key("test.root.message")
                                @Message(locale = Locales.ROOT, content = "Default message")
                                void rootLocaleMessage();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            assertThat(compilation).hadNoteContaining("Generated resource bundle: root-locale.properties");
        }

        @Test
        @DisplayName("Verify that static methods are ignored during processing")
        void testProcessWithStaticMethod() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".StaticMethodInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "static-method")
                            public interface StaticMethodInterface {
                                @Key("test.static.message")
                                @Message(locale = Locales.EN_US, content = "Static message")
                                static void staticMethod() {
                                    // Static method implementation
                                }
                            
                                @Key("test.instance.message")
                                @Message(locale = Locales.EN_US, content = "Instance message")
                                void instanceMethod();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            // Static method should be ignored, only instance method should be processed
            assertThat(compilation).hadNoteContaining("Generated resource bundle");
        }

        @Test
        @DisplayName("Verify that default methods are ignored during processing")
        void testProcessWithDefaultMethod() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".DefaultMethodInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "default-method")
                            public interface DefaultMethodInterface {
                                @Key("test.default.message")
                                @Message(locale = Locales.EN_US, content = "Default message")
                                default void defaultMethod() {
                                    // Default method implementation
                                }
                            
                                @Key("test.abstract.message")
                                @Message(locale = Locales.EN_US, content = "Abstract message")
                                void abstractMethod();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            // Default method should be ignored, only abstract method should be processed
            assertThat(compilation).hadNoteContaining("Generated resource bundle");
        }

        @Test
        @DisplayName("Verify that interface with both static and default methods is processed correctly")
        void testProcessWithMixedMethodTypes() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".MixedMethodTypesInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "mixed-methods")
                            public interface MixedMethodTypesInterface {
                                @Key("test.static.method")
                                @Message(locale = Locales.EN_US, content = "Static content")
                                static void staticMethod() {}
                            
                                @Key("test.default.method")
                                @Message(locale = Locales.EN_US, content = "Default content")
                                default void defaultMethod() {}
                            
                                @Key("test.abstract.method")
                                @Message(locale = Locales.EN_US, content = "Abstract content")
                                void abstractMethod();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            // Only abstract method should be processed
            assertThat(compilation).hadNoteContaining("Generated resource bundle");
        }
    }

    @Nested
    @DisplayName("Error case tests")
    class ErrorCasesTests {

        @Test
        @DisplayName("Verify error when @ResourceBundle is applied to class")
        void testProcessWithNonInterface() {
            final JavaFileObject testClass = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".TestClass",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public class TestClass {
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testClass);

            assertThat(compilation).hadErrorContaining("@ResourceBundle can only be applied to interfaces");
        }

        @Test
        @DisplayName("Verify error when @ResourceBundle is applied to abstract class")
        void testProcessWithAbstractClass() {
            final JavaFileObject abstractClass = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".TestAbstractClass",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public abstract class TestAbstractClass {
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(abstractClass);

            assertThat(compilation).hadErrorContaining("@ResourceBundle can only be applied to interfaces");
        }

        @Test
        @DisplayName("Verify error when @ResourceBundle is applied to enum")
        void testProcessWithEnum() {
            final JavaFileObject testEnum = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".TestEnum",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public enum TestEnum {
                                MESSAGE1, MESSAGE2
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testEnum);

            assertThat(compilation).hadErrorContaining("@ResourceBundle can only be applied to interfaces");
        }

        @Test
        @DisplayName("Verify error when @ResourceBundle is applied to record")
        void testProcessWithRecord() {
            final JavaFileObject testRecord = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".TestRecord",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public record TestRecord(String message) {
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testRecord);

            assertThat(compilation).hadErrorContaining("@ResourceBundle can only be applied to interfaces");
        }
    }

    @Nested
    @DisplayName("Warning case tests")
    class WarningCasesTests {

        @Test
        @DisplayName("Verify warning for abstract method without @Key annotation")
        void testMethodWithoutKeyAnnotation() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".TestInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public interface TestInterface {
                                void methodWithoutAnnotations();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).hadWarningContaining("Method missing @Key annotation");
        }

        @Test
        @DisplayName("Verify no warning for static method without @Key annotation")
        void testStaticMethodWithoutKeyAnnotation() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".StaticNoKeyInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public interface StaticNoKeyInterface {
                                static void staticMethodWithoutKey() {}
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            // Should not have warning because static methods are ignored
        }

        @Test
        @DisplayName("Verify no warning for default method without @Key annotation")
        void testDefaultMethodWithoutKeyAnnotation() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".DefaultNoKeyInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "test")
                            public interface DefaultNoKeyInterface {
                                default void defaultMethodWithoutKey() {}
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            // Should not have warning because default methods are ignored
        }

        @Test
        @DisplayName("Verify processing of method with @Key but no @Message/@Messages")
        void testMethodWithKeyButNoMessage() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".IncompleteInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            
                            @ResourceBundle(baseName = "incomplete")
                            public interface IncompleteInterface {
                                @Key("test.key")
                                void methodWithKeyOnly();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
        }

        @Test
        @DisplayName("Verify processing when multiple methods are mixed")
        void testMixedMethods() {
            final JavaFileObject testInterface = JavaFileObjects.forSourceString(
                    TEST_PACKAGE + ".MixedInterface",
                    """
                            package test;
                            
                            import io.github.namiuni.kotonoha.annotations.Key;
                            import io.github.namiuni.kotonoha.annotations.Locales;
                            import io.github.namiuni.kotonoha.annotations.ResourceBundle;
                            import io.github.namiuni.kotonoha.annotations.Message;
                            
                            @ResourceBundle(baseName = "mixed")
                            public interface MixedInterface {
                                @Key("valid.key")
                                @Message(locale = Locales.EN_US, content = "Valid content")
                                void validMethod();
                            
                                void methodWithoutAnnotations();
                            
                                @Key("incomplete.key")
                                void incompleteMethod();
                            }
                            """
            );

            final Compilation compilation = javac()
                    .withProcessors(new ResourceBundleGeneratorProcessor())
                    .compile(testInterface);

            assertThat(compilation).succeeded();
            assertThat(compilation).hadWarningContaining("Method missing @Key annotation");
        }
    }
}
