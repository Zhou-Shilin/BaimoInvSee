package top.baimoqilin.BaimoInvSee;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.plugin.PluginBase;
import.nukkit.utils.TextFormat;

public class BaimoInvSee extends PluginBase {

   Override
    public void onEnable() {
        getLogger().info("BaimoInvSee been enabled.");
    }

    @Override
    public void onDisable() {
        getLoggerinfo("BaimoInvSee has been disabled.");
    }

    @Override
    boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        (!command.getName().equalsIgnoreCase("invsee")) {
            return;
        }

        ifargs.length == 0) {
            sender.sendMessage(TextFormat.RED + "Usage: /inv <player>");
            return true;
        }

        String playerName = args[0];

        player = getServer().getPlayer(playerName);

        if (player == null) {
           .sendMessage(TextFormat.RED + "That player is not online.");
            return true;
        }

       Inventory inventory = player.getInventory();
        Inventory chest = inventory.getArmorInventory().getHolder
            sender.getServer().getScheduler().scheduleTask(this, () -> {
            ((Player)senderaddWindow(chest);
        });

        sender.sendMessage(TextFormat.GREEN + "Opening " + playerName "'s backpack...");

        return;
    }
}
