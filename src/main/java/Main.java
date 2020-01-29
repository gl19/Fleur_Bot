import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.permission.PermissionType;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        String token = "NjE3NDczMTAxODUyMTgwNDg4.XjC3IQ.5D_wj1ZBVy1so7RJC5h2TS7hs-E";

        List<Long> miningPlayers = new ArrayList<Long>();

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addMessageCreateListener(event -> {

            // Testing first commands
            if (event.getMessage().getContent().equalsIgnoreCase("nani")) {
                event.getChannel().sendMessage("https://www.youtube.com/watch?v=vxKBHX9Datw");
            }

            if (event.getMessage().getContent().equalsIgnoreCase("no u")
                    || event.getMessage().getContent().equalsIgnoreCase("no you")) {
                event.getChannel().sendMessage("https://i.imgur.com/yXEiYQ4.jpg");
            }

            // Delete # of messages
            if (event.getMessage().getContent().toLowerCase().startsWith("!delete")) {
                try {
                    int numDelete = Integer.parseInt(event.getMessageContent().split(" ")[1]);
                    if (numDelete < 0) {
                        event.getChannel().sendMessage("You must delete a positive number of messages!");
                    } else if (numDelete > 30) {
                        event.getChannel().sendMessage("Be careful, that's a lot of messages!");
                    } else {
                        event.getChannel().getMessages(numDelete + 1).get().deleteAll();
                        event.getChannel().sendMessage(event.getMessageAuthor().asUser().get().getNicknameMentionTag() + " just deleted " + numDelete + " messages!");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException | InterruptedException | ExecutionException e) {
                    if (e instanceof NumberFormatException) {
                        event.getChannel().sendMessage("\"" + event.getMessageContent().substring(8) + "\" is not a number!");
                    } else {
                        event.getChannel().sendMessage("You must specify a number of messages to delete!");
                    }
                }

            }

            //event.getMessage().addReaction(parseEmojiUnicode("\uD83C\uDF46"));

        });

        // Bot status
        /*
        String[] gameArray = new String[5];
        gameArray[0] = "Cherry Blossoms";
        gameArray[1] = "Daffodils";
        gameArray[2] = "Carnations";
        gameArray[3] = "Violets";
        gameArray[4] = "Roses";

        Random random = new Random();
        int randomGame = random.nextInt(gameArray.length);

        api.updateActivity(ActivityType.WATCHING, gameArray[randomGame] + " Grow");
        */
        api.updateActivity(ActivityType.WATCHING, "RYAN SLEEP");

        // Checking if players are mining
        new Timer().scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        api.getServerById(592488893484630016l).get().getMembers().forEach(m -> {
                            m.getActivity().ifPresent(a -> {
                                if (a.getType().equals(ActivityType.PLAYING) && a.getName().toLowerCase().contains("minecraft")) {
                                    if (!miningPlayers.contains(m.getId())) api.getTextChannelById(671848020660453388l).get().sendMessage(m.getNicknameMentionTag() + " IS MINING "
                                            + "https://www.youtube.com/watch?v=kMlLz7stjwc");
                                    miningPlayers.add(m.getId());
                                } else if (miningPlayers.contains(m.getId())) {
                                    miningPlayers.remove(m.getId());
                                }
                            });
                        });
                    }}, 0, 10000);
    }

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