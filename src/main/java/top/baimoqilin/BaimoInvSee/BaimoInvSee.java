package top.baimoqilin.BaimoInvSee;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class BaimoInvSee extends PluginBase {

    private Map<String, Inventory> playerInventories = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info(TextFormat.GREEN + "BaimoInvSee has been enabled!");
    }

    @Override
    public void onDisable() {
        for (Player player : this.getServer().getOnlinePlayers().values()) {
            playerInventories.put(player.getName(), player.getInventory());
        }
        getLogger().info(TextFormat.RED + "BaimoInvSee has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("invsee")) {
            if (sender.isOp()) {
                if (args.length == 1) {
                    Player target = this.getServer().getPlayer(args[0]);
                    if (target != null) {
                        Inventory inv = target.getInventory();
                        sender.sendMessage(TextFormat.GREEN + "Displaying " + target.getName() + "'s inventory:");
                        if (sender instanceof Player) {
                            ((Player) sender).addWindow(inv);
                        }
                        return true;
                    } else {
                        if (playerInventories.containsKey(args[0])) {
                            Inventory inv = playerInventories.get(args[0]);
                            sender.sendMessage(TextFormat.GREEN + "Displaying " + args[0] + "'s inventory:");
                            if (sender instanceof Player) {
                                ((Player) sender).addWindow(inv);
                            }
                            return true;
                        } else {
                            sender.sendMessage(TextFormat.RED + "Player not found!");
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(TextFormat.RED + "Usage: /invsee <player>");
                    return true;
                }
            } else {
                sender.sendMessage(TextFormat.RED + "You do not have permission to use this command!");
                return true;
            }
        }
        return false;
    }
}
