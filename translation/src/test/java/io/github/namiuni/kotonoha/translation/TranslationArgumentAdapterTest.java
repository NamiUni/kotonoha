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
package io.github.namiuni.kotonoha.translation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.github.namiuni.kotonoha.translation.utility.TranslationArgumentAdapter;
import io.leangen.geantyref.TypeToken;
import java.lang.reflect.Type;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslationArgument;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@NullMarked
@DisplayName("TranslationArgumentAdapter")
class TranslationArgumentAdapterTest {

    @Nested
    @DisplayName("Standard adapter")
    class StandardAdapterTest {

        private final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.standard();

        @Nested
        @DisplayName("Component types")
        class ComponentTypeTest {

            @Test
            @DisplayName("should adapt Component")
            void shouldAdaptComponent() {
                final Component component = Component.text("test");
                final TranslationArgument result = adapter.adapt(Component.class, component);

                assertNotNull(result);
                assertEquals(component, result.asComponent());
            }

            @Test
            @DisplayName("should adapt TranslationArgument")
            void shouldAdaptTranslationArgument() {
                final TranslationArgument arg = TranslationArgument.numeric(42);
                final TranslationArgument result = adapter.adapt(TranslationArgument.class, arg);

                assertSame(arg, result);
            }
        }

        @Nested
        @DisplayName("Numeric types")
        class NumericTypeTest {

            @ParameterizedTest(name = "should adapt {0}")
            @MethodSource("numericTypes")
            @DisplayName("should adapt all numeric types")
            void shouldAdaptNumericTypes(Type type, Object value) {
                final TranslationArgument result = adapter.adapt(type, value);

                assertNotNull(result);
                assertInstanceOf(Number.class, result.value());
            }

            static Stream<Arguments> numericTypes() {
                return Stream.of(
                        Arguments.of(int.class, 42),
                        Arguments.of(Integer.class, 42),
                        Arguments.of(long.class, 42L),
                        Arguments.of(Long.class, 42L),
                        Arguments.of(float.class, 42.0f),
                        Arguments.of(Float.class, 42.0f),
                        Arguments.of(double.class, 42.0),
                        Arguments.of(Double.class, 42.0),
                        Arguments.of(Number.class, 42)
                );
            }
        }

        @Nested
        @DisplayName("Boolean types")
        class BooleanTypeTest {

            @ParameterizedTest
            @ValueSource(booleans = {true, false})
            @DisplayName("should adapt primitive boolean")
            void shouldAdaptPrimitiveBoolean(boolean value) {
                final TranslationArgument result = adapter.adapt(boolean.class, value);

                assertNotNull(result);
                assertEquals(value, result.value());
            }

            @ParameterizedTest
            @ValueSource(booleans = {true, false})
            @DisplayName("should adapt Boolean wrapper")
            void shouldAdaptBooleanWrapper(boolean value) {
                final TranslationArgument result = adapter.adapt(Boolean.class, value);

                assertNotNull(result);
                assertEquals(value, result.value());
            }
        }

        @Nested
        @DisplayName("String type")
        class StringTypeTest {

            @Test
            @DisplayName("should adapt String")
            void shouldAdaptString() {
                final String text = "Hello, World!";
                final TranslationArgument result = adapter.adapt(String.class, text);

                assertNotNull(result);
                final Component component = result.asComponent();
                assertNotNull(component);
            }

            @Test
            @DisplayName("should adapt empty String")
            void shouldAdaptEmptyString() {
                final TranslationArgument result = adapter.adapt(String.class, "");

                assertNotNull(result);
            }
        }

        @Nested
        @DisplayName("Character types")
        class CharacterTypeTest {

            @Test
            @DisplayName("should adapt primitive char")
            void shouldAdaptPrimitiveChar() {
                final TranslationArgument result = adapter.adapt(char.class, 'A');

                assertNotNull(result);
                final Component component = result.asComponent();
                assertNotNull(component);
            }

            @Test
            @DisplayName("should adapt Character wrapper")
            void shouldAdaptCharacterWrapper() {
                final TranslationArgument result = adapter.adapt(Character.class, 'Z');

                assertNotNull(result);
            }
        }

        @Nested
        @DisplayName("Type support")
        class TypeSupportTest {

            @ParameterizedTest(name = "should support {0}")
            @MethodSource("supportedTypes")
            @DisplayName("should support all standard types")
            void shouldSupportStandardTypes(Type type) {
                assertTrue(adapter.supports(type));
            }

            @Test
            @DisplayName("should not support unsupported type")
            void shouldNotSupportUnsupportedType() {
                assertFalse(adapter.supports(Object.class));
            }

            static Stream<Type> supportedTypes() {
                return Stream.of(
                        Component.class,
                        TranslationArgument.class,
                        int.class, Integer.class,
                        long.class, Long.class,
                        float.class, Float.class,
                        double.class, Double.class,
                        boolean.class, Boolean.class,
                        String.class,
                        char.class, Character.class,
                        Number.class
                );
            }
        }

        @Nested
        @DisplayName("Error handling")
        class ErrorHandlingTest {

            @Test
            @DisplayName("should throw IllegalArgumentException for unsupported type")
            void shouldThrowForUnsupportedType() {
                final Object unsupported = new Object();

                final IllegalArgumentException exception = assertThrows(
                        IllegalArgumentException.class,
                        () -> adapter.adapt(Object.class, unsupported)
                );

                assertTrue(exception.getMessage().contains("No adapter registered for type"));
            }
        }
    }

    @Nested
    @DisplayName("Custom adapter builder")
    class CustomAdapterBuilderTest {

        @Nested
        @DisplayName("Builder pattern")
        class BuilderPatternTest {

            @Test
            @DisplayName("should create adapter with custom class mapping")
            void shouldCreateWithCustomClassMapping() {
                record Person(String name, int age) {
                }

                final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder()
                        .argument(Person.class, p -> TranslationArgument.component(
                                Component.text(p.name() + " (" + p.age() + ")")
                        ))
                        .build();

                final Person person = new Person("Alice", 30);
                final TranslationArgument result = adapter.adapt(Person.class, person);

                assertNotNull(result);
            }

            @Test
            @DisplayName("should create adapter with TypeToken mapping")
            void shouldCreateWithTypeTokenMapping() {
                final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder()
                        .string(new TypeToken<String>() { }, String::toUpperCase)
                        .component(String.class, Component::text)
                        .build();

                assertTrue(adapter.supports(String.class));
            }

            @Test
            @DisplayName("should create adapter with component helper")
            void shouldCreateWithComponentHelper() {
                record Status(String label) {
                }

                final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder()
                        .component(Status.class, s -> Component.text(s.label()))
                        .build();

                final Status status = new Status("Active");
                final TranslationArgument result = adapter.adapt(Status.class, status);

                assertNotNull(result);
            }

            @Test
            @DisplayName("should create adapter with string helper")
            void shouldCreateWithStringHelper() {
                record Id(int value) {
                }

                final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder()
                        .string(Id.class, id -> "ID-" + id.value())
                        .build();

                final Id id = new Id(123);
                final TranslationArgument result = adapter.adapt(Id.class, id);

                assertNotNull(result);
            }
        }

        @Nested
        @DisplayName("Builder composition")
        class BuilderCompositionTest {

            @Test
            @DisplayName("should chain multiple registrations")
            void shouldChainMultipleRegistrations() {
                record Point(int x, int y) {
                }
                record Color(String hex) {
                }

                final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder()
                        .string(Point.class, p -> "(" + p.x() + ", " + p.y() + ")")
                        .string(Color.class, Color::hex)
                        .build();

                assertTrue(adapter.supports(Point.class));
                assertTrue(adapter.supports(Color.class));
            }

            @Test
            @DisplayName("should override previous registration")
            void shouldOverridePreviousRegistration() {
                final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder()
                        .argument(String.class, s -> TranslationArgument.component(Component.text("first")))
                        .argument(String.class, s -> TranslationArgument.component(Component.text("second")))
                        .build();

                final TranslationArgument result = adapter.adapt(String.class, "test");

                assertNotNull(result);
            }
        }

        @Nested
        @DisplayName("toBuilder method")
        class ToBuilderTest {

            @Test
            @DisplayName("should create builder from existing adapter")
            void shouldCreateBuilderFromExisting() {
                final TranslationArgumentAdapter original = TranslationArgumentAdapter.builder()
                        .argument(String.class, s -> TranslationArgument.component(Component.text(s)))
                        .build();

                final TranslationArgumentAdapter extended = original.toBuilder()
                        .argument(Integer.class, TranslationArgument::numeric)
                        .build();

                assertTrue(extended.supports(String.class));
                assertTrue(extended.supports(Integer.class));
            }

            @Test
            @DisplayName("should preserve original adapter mappings")
            void shouldPreserveOriginalMappings() {
                final TranslationArgumentAdapter original = TranslationArgumentAdapter.builder()
                        .argument(String.class, s -> TranslationArgument.component(Component.text(s)))
                        .build();

                @SuppressWarnings("unused") final TranslationArgumentAdapter extended = original.toBuilder()
                        .argument(Integer.class, TranslationArgument::numeric)
                        .build();

                // Original should remain unchanged
                assertTrue(original.supports(String.class));
                assertFalse(original.supports(Integer.class));
            }

            @Test
            @DisplayName("should allow modification of standard adapter")
            void shouldAllowModificationOfStandardAdapter() {
                record CustomType(String value) {
                }

                final TranslationArgumentAdapter custom = TranslationArgumentAdapter.standard()
                        .toBuilder()
                        .string(CustomType.class, CustomType::value)
                        .build();

                assertTrue(custom.supports(String.class)); // from standard
                assertTrue(custom.supports(CustomType.class)); // custom addition
            }
        }
    }

    @Nested
    @DisplayName("Null safety")
    @SuppressWarnings("DataFlowIssue")
    class NullSafetyTest {

        @Test
        @DisplayName("should throw NullPointerException for null type in builder")
        void shouldThrowForNullTypeInBuilder() {
            final TranslationArgumentAdapter.Builder builder = TranslationArgumentAdapter.builder();

            assertThrows(NullPointerException.class,
                    () -> builder.argument((Class<String>) null, s -> TranslationArgument.component(Component.text(s))));
        }

        @Test
        @DisplayName("should throw NullPointerException for null adapter in builder")
        void shouldThrowForNullAdapterInBuilder() {
            final TranslationArgumentAdapter.Builder builder = TranslationArgumentAdapter.builder();

            assertThrows(NullPointerException.class,
                    () -> builder.argument(String.class, null));
        }

        @Test
        @DisplayName("should throw NullPointerException for null TypeToken")
        void shouldThrowForNullTypeToken() {
            final TranslationArgumentAdapter.Builder builder = TranslationArgumentAdapter.builder();

            assertThrows(NullPointerException.class,
                    () -> builder.argument((TypeToken<String>) null, s -> TranslationArgument.component(Component.text(s))));
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCasesTest {

        @Test
        @DisplayName("should handle empty builder")
        void shouldHandleEmptyBuilder() {
            final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.builder().build();

            assertNotNull(adapter);
            assertFalse(adapter.supports(String.class));
        }

        @Test
        @DisplayName("should handle extreme numeric values")
        void shouldHandleExtremeNumericValues() {
            final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.standard();

            assertDoesNotThrow(() -> {
                adapter.adapt(int.class, Integer.MAX_VALUE);
                adapter.adapt(int.class, Integer.MIN_VALUE);
                adapter.adapt(double.class, Double.MAX_VALUE);
                adapter.adapt(double.class, Double.MIN_VALUE);
            });
        }

        @Test
        @DisplayName("should handle special characters in strings")
        void shouldHandleSpecialCharactersInStrings() {
            final TranslationArgumentAdapter adapter = TranslationArgumentAdapter.standard();
            final String special = "Hello\n\t\"World\"©®™";

            final TranslationArgument result = assertDoesNotThrow(
                    () -> adapter.adapt(String.class, special)
            );

            assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("Immutability")
    class ImmutabilityTest {

        @Test
        @DisplayName("should create immutable adapter")
        void shouldCreateImmutableAdapter() {
            final TranslationArgumentAdapter.Builder builder = TranslationArgumentAdapter.builder()
                    .argument(String.class, s -> TranslationArgument.component(Component.text(s)));

            final TranslationArgumentAdapter adapter1 = builder.build();
            final TranslationArgumentAdapter adapter2 = builder
                    .argument(Integer.class, TranslationArgument::numeric)
                    .build();

            // adapter1 should not be affected by further builder modifications
            assertTrue(adapter1.supports(String.class));
            assertFalse(adapter1.supports(Integer.class));

            assertTrue(adapter2.supports(String.class));
            assertTrue(adapter2.supports(Integer.class));
        }
    }
}
