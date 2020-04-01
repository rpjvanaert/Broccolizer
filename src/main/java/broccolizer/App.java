package broccolizer;

import broccolizer.Listeners.setupListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.CustomEmoji;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.event.ListenerManager;

import javax.sound.sampled.Line;

public class App {

    public static void main(String[] args) {


        DiscordApi api = new DiscordApiBuilder().setToken(Information.getToken()).login().join();

        api.addListener(new setupListener());


        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}