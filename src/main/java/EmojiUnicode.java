import org.javacord.api.entity.emoji.Emoji;

import java.util.Optional;

public class EmojiUnicode {
    // Creating unicode emojis
    public static Emoji parseEmojiUnicode(String unicodeEmoji) {
        return new Emoji() {
            @Override
            public Optional<String> asUnicodeEmoji() {
                return Optional.of(unicodeEmoji);
            }

            @Override
            public boolean isAnimated() {
                return false;
            }

            @Override
            public String getMentionTag() {
                return null;
            }
        };
    }
}
