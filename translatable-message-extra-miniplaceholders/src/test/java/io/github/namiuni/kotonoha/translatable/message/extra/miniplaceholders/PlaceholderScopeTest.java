package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import static io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders.PlaceholderScope.AUDIENCE;
import static io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders.PlaceholderScope.AUDIENCE_GLOBAL;
import static io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders.PlaceholderScope.GLOBAL;
import static io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders.PlaceholderScope.RELATIONAL;
import static io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders.PlaceholderScope.RELATIONAL_GLOBAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.Tags;
import java.lang.reflect.Method;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@NullMarked
final class PlaceholderScopeTest {

    static {
        Expansion.builder("test")
                .globalPlaceholder("global", Tags.EMPTY_TAG)
                .audiencePlaceholder(Audience.class, "audience", Tags.emptyAudienceResolver())
                .build()
                .register();
    }

    interface TestMessages {

        @WithPlaceholders(GLOBAL)
        Component testGlobal();

        @WithPlaceholders(AUDIENCE)
        Component testAudience();

        @WithPlaceholders(AUDIENCE_GLOBAL)
        Component testAudienceGlobal();

        @WithPlaceholders(RELATIONAL)
        Component testRelational();

        @WithPlaceholders(RELATIONAL_GLOBAL)
        Component testRelationalGlobal();
    }

    @Test
    @DisplayName("should return the global placeholders")
    void shouldReturnGlobalPlaceholders() throws NoSuchMethodException {

        final Method method = TestMessages.class.getMethod("testGlobal");
        final WithPlaceholders annotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = annotation.value();

        final TagResolver annotationGlobalPlaceholders = placeholderScope.placeholders();
        final TagResolver globalPlaceholders = io.github.miniplaceholders.api.MiniPlaceholders.globalPlaceholders();

        assertEquals(globalPlaceholders, annotationGlobalPlaceholders);
    }

    @Test
    @DisplayName("should return the global placeholders")
    void shouldReturnAudiencePlaceholders() throws NoSuchMethodException {

        final Method method = TestMessages.class.getMethod("testAudience");
        final WithPlaceholders annotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = annotation.value();

        final TagResolver annotationAudiencePlaceholders = placeholderScope.placeholders();
        final TagResolver audiencePlaceholders = io.github.miniplaceholders.api.MiniPlaceholders.audiencePlaceholders();

        assertEquals(audiencePlaceholders, annotationAudiencePlaceholders);
    }

    @Test
    @DisplayName("should return the global placeholders")
    void shouldReturnAudienceGlobalPlaceholders() throws NoSuchMethodException {

        final Method method = TestMessages.class.getMethod("testAudienceGlobal");
        final WithPlaceholders annotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = annotation.value();

        final TagResolver annotationAudienceGlobalPlaceholders = placeholderScope.placeholders();
        final TagResolver audienceGlobalPlaceholders = io.github.miniplaceholders.api.MiniPlaceholders.audienceGlobalPlaceholders();

        assertEquals(audienceGlobalPlaceholders, annotationAudienceGlobalPlaceholders);
    }

    @Test
    @DisplayName("should return the global placeholders")
    void shouldRelationalPlaceholders() throws NoSuchMethodException {

        final Method method = TestMessages.class.getMethod("testRelational");
        final WithPlaceholders annotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = annotation.value();

        final TagResolver annotationRelationalPlaceholders = placeholderScope.placeholders();
        final TagResolver relationalPlaceholders = io.github.miniplaceholders.api.MiniPlaceholders.relationalPlaceholders();

        assertEquals(relationalPlaceholders, annotationRelationalPlaceholders);
    }

    @Test
    @DisplayName("should return the global placeholders")
    void shouldRelationalGlobalPlaceholders() throws NoSuchMethodException {

        final Method method = TestMessages.class.getMethod("testRelationalGlobal");
        final WithPlaceholders annotation = method.getAnnotation(WithPlaceholders.class);
        final PlaceholderScope placeholderScope = annotation.value();

        final TagResolver annotationRelationalGlobalPlaceholders = placeholderScope.placeholders();
        final TagResolver relationalGlobalPlaceholders = io.github.miniplaceholders.api.MiniPlaceholders.relationalGlobalPlaceholders();

        assertEquals(relationalGlobalPlaceholders, annotationRelationalGlobalPlaceholders);
    }
}
