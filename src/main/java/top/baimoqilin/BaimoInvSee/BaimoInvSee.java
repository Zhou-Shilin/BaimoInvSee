package top.baimoqilin.BaimoInvSee;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;

public class BaimoInvSee extends PluginBase implements Listener {

    private HashMap<String, Inventory> inventories = new HashMap<>();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("invsee")) {
            if (args.length == 1) {
                Player target = this.getServer().getPlayer(args[0]);
                if (target != null) {
                    if (sender.isOp()) {
                        Inventory inventory = target.getInventory();
                        inventories.put(target.getName(), inventory);
                        sender.sendMessage(TextFormat.GREEN + "Opened " + target.getName() + "'s inventory.");
                        if (sender instanceof Player) {
                            ((Player) sender).addWindow(inventory);
                        }
                    } else {
                        sender.sendMessage(TextFormat.RED + "You do not have permission to use this command.");
                    }
                } else {
                    sender.sendMessage(TextFormat.RED + "Player not found.");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("invex") && sender.isOp()) {
                Player player1 = this.getServer().getPlayer(args[1]);
                Player player2 = this.getServer().getPlayer(args[2]);
                if (player1 != null && player2 != null) {
                    Inventory inventory1 = player1.getInventory();
                    Inventory inventory2 = player2.getInventory();
                    player1.getInventory().setContents(inventory2.getContents());
                    player2.getInventory().setContents(inventory1.getContents());
                    sender.sendMessage(TextFormat.GREEN + "Swapped " + player1.getName() + "'s and " + player2.getName() + "'s inventories.");
                } else {
                    sender.sendMessage(TextFormat.RED + "One or both players not found.");
                }
            } else {
                sender.sendMessage(TextFormat.RED + "Usage: /invsee <player>");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        inventories.put(player.getName(), inventory);
    }

    public Inventory getSavedInventory(String playerName) {
        return inventories.get(playerName);
    }

    public boolean hasSavedInventory(String playerName) {
        return inventories.containsKey(playerName);
    }

    public void removeSavedInventory(String playerName) {
        inventories.remove(playerName);
    }
}
