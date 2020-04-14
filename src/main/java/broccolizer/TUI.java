package broccolizer;

import broccolizer.GameLogic.DiscordController;
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

    public static void sendRoleChannelInstruction(){
        DiscordController.getInstance().getWolvesChannel().sendMessage(
                "Hello there!\n" +
                        "Wolves, you are.\n" +
                        "Every night you get to choose who to kill!\n" +
                        "Instructions will come when necessarily...\n" +
                        "Good luck and have fun! :wolf:"
        );

        DiscordController.getInstance().getOracleChannel().sendMessage(
                "Hello there!\n" +
                        "Oracle, you are.\n" +
                        "You will have 1 prophecy each night.\n" +
                        "Use it wisely, idenities reveal sorrow truths.\n" +
                        "When time arrives, instructions will appear from dusk.\n" +
                        "Good luck and have fun! :woman_genie:"
        );

        DiscordController.getInstance().getCupidChannel().sendMessage(
                "Hello there!\n" +
                        "Cupid, you are.\n" +
                        "\"Love looks not with the eyes, but with the mind, And therefore is winged Cupid painted blind.\" - William Shakespeare\n" +
                        "You will make 2 players fall in love, dear Cupid.\n" +
                        "These 2 players will live and die together.\n" +
                        "One dies, other dies.\n" +
                        ":angle: :heart:" +
                        "type \"!bond <@MentionTag> <@MentionTag> to bond 2 players.\n" +
                        "Good luck and have a whole lotta love! :wink:"
        );

        DiscordController.getInstance().getWitchChannel().sendMessage(
                "Hello there!\n" +
                        "Witch, you are.\n" +
                        "Many consider 'Witch' an evil word, but you and the evil know better...\n" +
                        "Power from the evil gives you the ability to kill and revive 1 player.\n" +
                        "Your turn is after the werewolves.\n" +
                        "You can only revive someone who recently got killed by the wolves,\n" +
                        "including yourself.\n" +
                        "Good luck and have fun! :woman_mage:"
        );

        DiscordController.getInstance().getLobbyChannel().sendMessage(
                "Hello there!\n" +
                        "Players, you are.\n" +
                        "This is the village TextChannel any instruction around voting lynches will follow.\n" +
                        "The goal is to kill the werewolves,\n" +
                        "except for the wolves themselves, they just want to feast! :wolf:\n" +
                        "Toast on Millers Hollow! :beers:\n" +
                        "Good luck and have fun! :smile:"
        );
    }

    public static void sendGameInstruction(TextChannel sentChannel) {
    }
}
