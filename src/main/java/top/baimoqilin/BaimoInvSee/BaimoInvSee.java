package top.baimoqilin.BaimoInvSee;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;

public class BaimoInvSee extends PluginBase {

    private Config config;
    private HashMap<String, Long> lastSaved = new HashMap<>();

    @Override
    public void onEnable() {
        this.getLogger().info("BaimoInvSee已被成功加载。版本：1.0.1");

        // 加载配置文件
        this.config = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("正在卸载BaimoInvSee...");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("invsee")) {
package top.baimoqilin;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.HashMap;

public class BaimoInvSee extends PluginBase implements Listener, InventoryHolder {
    private Config config;
    private int saveInterval;
    private HashMap<String, Inventory> playerInventories;

    @Override
    public void onEnable() {
        getLogger().info("BaimoInvSee已被成功加载。版本：1.0.1");

        getServer().getPluginManager().registerEvents(this, this);

        playerInventories = new HashMap<>();

        config = new Config(new File(getDataFolder(), "config.yml"), Config.YAML);
        saveInterval = config.getInt("saveInterval", 1);
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, this::saveAllInventories, saveInterval * 60 * 20, saveInterval * 60 * 20);
    }

    @Override
    public void onDisable() {
        getLogger().info("正在卸载BaimoInvSee...");
        saveAllInventories();
    }

    private void saveAllInventories() {
        for (String playerName : playerInventories.keySet()) {
            Player player = getServer().getPlayerExact(playerName);
            if (player != null) {
                player.getInventory().save();
            } else {
                playerInventories.get(playerName).save();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("你没有权限使用此命令。");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("用法: /invsee <player>");
            return true;
        }

        Player targetPlayer = getServer().getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage("玩家" + args[0] + "不存在或不在线。");
            return true;
        }

        Inventory targetInventory = playerInventories.get(targetPlayer.getName());
        if (targetInventory == null) {
            targetInventory = targetPlayer.getInventory();
            playerInventories.put(targetPlayer.getName(), targetInventory);
        }

        sender.sendMessage("正在查看玩家" + targetPlayer.getName() + "的背包。");
        sender.getInventory().setContents(targetInventory.getContents());
        sender.addWindow(this);

        return true;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.isOnline()) {
            playerInventories.put(player.getName(), player.getInventory());
            player.getInventory().save();
        }
    }

    @Override
    public Inventory getInventory() {
        return InventoryType.CHEST.getInventory(null);
    }
}
