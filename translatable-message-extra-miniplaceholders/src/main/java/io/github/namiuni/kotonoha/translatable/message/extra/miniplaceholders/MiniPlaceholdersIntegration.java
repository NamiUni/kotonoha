package io.github.namiuni.kotonoha.translatable.message.extra.miniplaceholders;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.util.TriState;
import org.jspecify.annotations.NullMarked;

@NullMarked
final class MiniPlaceholdersIntegration {

    private static TriState miniplaceholdersIsLoaded = TriState.NOT_SET;

    private MiniPlaceholdersIntegration() {
    }

    static boolean miniPlaceholdersLoaded() {
        if (miniplaceholdersIsLoaded == TriState.NOT_SET) {
            try {
                Class.forName("io.github.miniplaceholders.api.MiniPlaceholders");
                miniplaceholdersIsLoaded = TriState.TRUE;
            } catch (final ClassNotFoundException ignored) {
                miniplaceholdersIsLoaded = TriState.FALSE;
            }
        }
        return miniplaceholdersIsLoaded == TriState.TRUE;
    }

    static TagResolver global() {
        return MiniPlaceholders.globalPlaceholders();
    }

    static TagResolver audience() {
        return MiniPlaceholders.audiencePlaceholders();
    }

    static TagResolver audienceGlobal() {
        return MiniPlaceholders.audienceGlobalPlaceholders();
    }

    static TagResolver relational() {
        return MiniPlaceholders.relationalPlaceholders();
    }

    static TagResolver relationalGlobal() {
        return MiniPlaceholders.relationalGlobalPlaceholders();
    }
}
