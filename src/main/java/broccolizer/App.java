package broccolizer;

import broccolizer.ChannelManagement.ChannelManager;
import broccolizer.GameLogic.DiscordController;
import broccolizer.ChannelManagement.Listeners.MainChannelListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;

public class App {

    public static BotStates state;

    public static void main(String[] args) {

        state = BotStates.LOBBY;

        DiscordApi api = new DiscordApiBuilder().setToken(Information.getToken()).login().join();

        Server server = api.getServerById(Information.getServerID()).get();

        DiscordController.getInstance().setServer(server);

        api.addListener(new MainChannelListener());

        ChannelManager.setupChannels();

        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
    }
}