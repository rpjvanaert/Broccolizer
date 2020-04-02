package broccolizer;

import org.javacord.api.entity.channel.TextChannel;

public class TUI {

    public static void sendLobbyInstruction(TextChannel sentChannel) {
        sentChannel.sendMessage(
                "Hi this is a game of the-werewolves-of-millers-hollow\n" +
                        "\"Weerwolven van Wakkerdam\"\n" +
                        "---------------------------\n" +
                        "Command\t\tFunction\n" +
                        "\n" +
                        "!info\t\t\t\t\tTo get this info panel\n" +
                        "!state\t\t\t\t\tTo get state of bot\n" +
                        "!join\t\t\t\t\tYou join the game lobby\n" +
                        "!join @<MentionTag>\tTo add a list of players\n" +
                        "!leave\t\t\t\t\tYou leave the game lobby\n" +
                        "!start\t\t\t\t\tTo start the game\n" +
                        "!players\t\t\t\t\tPlayers in lobby\n"
        );
    }
}
