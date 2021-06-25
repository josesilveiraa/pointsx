package org.josesilveiraa.pointsx.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.josesilveiraa.pointsx.PointsX;
import org.josesilveiraa.pointsx.api.event.PointsAddEvent;
import org.josesilveiraa.pointsx.api.event.PointsPayEvent;
import org.josesilveiraa.pointsx.api.event.PointsRemoveEvent;
import org.josesilveiraa.pointsx.api.event.PointsSetEvent;
import org.josesilveiraa.pointsx.manager.UserManager;
import org.josesilveiraa.pointsx.manager.shop.ShopManager;
import org.josesilveiraa.pointsx.object.User;

import java.util.UUID;

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

    @Subcommand("shop")
    @Description("Opens the shop menu.")
    @CommandPermission("points.command.shop")
    public static void onShop(Player player) {
        ShopManager.openGui(player);
    }

    @Subcommand("pay")
    @Syntax("<player> <points>")
    @CommandCompletion("@players")
    @Description("Takes the desired quantity of points from your account and send it to another player.")
    @CommandPermission("points.command.pay")
    public static void onPay(Player player, OnlinePlayer target, Double points) {

        UUID senderUuid = player.getUniqueId();
        UUID receiverUuid = target.getPlayer().getUniqueId();

        User sender = UserManager.getByUuid(senderUuid);
        User receiver = UserManager.getByUuid(receiverUuid);

        if (receiver == null || sender == null) {
            player.sendMessage("§cAn unknown error occurred while trying to contact the database.");
            return;
        }

        PointsPayEvent event = new PointsPayEvent(sender, receiver, player, target.getPlayer());
        PointsX.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            return;
        }

        sender.removePoints(points);
        receiver.addPoints(points);

        player.sendMessage("§aYou paid §f" + points + " §apoints to §f" + target.getPlayer().getName() + "§a!");
        target.getPlayer().sendMessage("§aYou received §f" + points + " §apoints from §f" + player.getName() + "§a!");

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

        PointsSetEvent event = new PointsSetEvent(sender, user, target.getPlayer());
        PointsX.getInstance().getServer().getPluginManager().callEvent(event);

        if(event.isCancelled()) {
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
    public static void onRemove(CommandSender sender, OnlinePlayer target, Double points) {
        User user = UserManager.getByUuid(target.getPlayer().getUniqueId());

        if (user == null) {
            sender.sendMessage("§cAn unknown error occurred while trying to contact the database.");
            return;
        }

        PointsRemoveEvent event = new PointsRemoveEvent(sender, target.getPlayer(), user);

        user.removePoints(points);
        sender.sendMessage("§aRemoved §f" + points + " §apoints from §f" + target.getPlayer().getName() + "§a's account.");
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

        PointsAddEvent event = new PointsAddEvent(sender, user, target.getPlayer());
        PointsX.getInstance().getServer().getPluginManager().callEvent(event);

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
