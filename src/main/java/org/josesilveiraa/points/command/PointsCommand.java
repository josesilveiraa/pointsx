package org.josesilveiraa.points.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.josesilveiraa.points.manager.UserManager;
import org.josesilveiraa.points.object.User;

@CommandAlias("points|p")
public class PointsCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    @Description("Shows your points.")
    public void onPoints(Player p, @Optional OnlinePlayer target) {
        Player toSee = target != null ? target.getPlayer() : p;
        User user = UserManager.getByUuid(toSee.getUniqueId());
        if (user != null) {
            p.sendMessage("§6Points: §f" + user.getPoints());
            return;
        }
        p.sendMessage("§cAn unknown error occurred while trying to contact the database.");
    }

    @Subcommand("set")
    @Syntax("<player> <points>")
    @CommandCompletion("@players")
    @Description("Sets the target balance to the desired value.")
    @CommandPermission("points.command.set")
    public static void onSet(CommandSender sender, OnlinePlayer target, Double points) {
        User user = UserManager.getByUuid(target.getPlayer().getUniqueId());
        if (user == null) {
            sender.sendMessage("§cAn unknown error occurred while trying to contact the database.");
            return;
        }
        user.setPoints(points);
        sender.sendMessage("§f" + target.getPlayer().getName() + "§a's points set to §f" + points + "§a!");
    }

    @Subcommand("remove")
    @Syntax("<player> <points>")
    @CommandCompletion("@players")
    @Description("Removes the desired points amount from the target account.")
    @CommandPermission("points.command.remove")
    public static void onRemove(CommandSender sender, Player target, Double points) {
        User user = UserManager.getByUuid(target.getUniqueId());
        if (user == null) {
            sender.sendMessage("§cAn unknown error occurred while trying to contact the database.");
            return;
        }
        user.removePoints(points);
        sender.sendMessage("§aRemoved §f" + points + " §apoints from §f" + target.getName() + "§a's account.");
    }

    @Subcommand("add")
    @Syntax("<player> <points>")
    @CommandCompletion("@players")
    @Description("Adds the desired points amount to the target account.")
    @CommandPermission("points.command.add")
    public static void onAdd(CommandSender sender, OnlinePlayer target, Double points) {
        User user = UserManager.getByUuid(target.getPlayer().getUniqueId());
        if (user == null) {
            sender.sendMessage("§cAn unknown error occurred while trying to contact the database.");
            return;
        }
        user.addPoints(points);
        sender.sendMessage("§aAdded §f" + points + " §apoints from §f" + target.getPlayer().getName() + "§a's account.");
    }

    @CatchUnknown
    public static void onUnknown(CommandSender sender) {
        sender.sendMessage("§cUnknown command. Please try /points help for more help.");
    }

    @HelpCommand
    public static void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
