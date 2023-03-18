package top.baimoqilin.BaimoInvSee;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.NukkitRunnable;
import cn.nukkit.utils.TextFormat;

public class BaimoInvSee extends PluginBase {

    private int saveInterval;
    private boolean realtimeSave;

    @Override
    public void onEnable() {
        this.getLogger().info(TextFormat.GREEN + "BaimoInvSee 插件已启用！");

        this.saveConfig();
        this.loadConfig();

        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.RED + "BaimoInvSee 插件已禁用！");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("baimoinvsee")) {
            if (sender.hasPermission("baimoinvsee.command")) {
                if (args.length >= 1) {
                    String targetName = args[0];
                    Player targetPlayer = this.getServer().getPlayerExact(targetName);

                    if (targetPlayer != null) {
                        PlayerInventory targetInventory = targetPlayer.getInventory();
                        // 打开背包 GUI 以查看和修改背包物品
                    } else {
                        // 玩家离线时加载并打开背包
                    }
                } else {
                    sender.sendMessage(TextFormat.RED + "用法: /baimoinvsee <玩家名>");
                }
            } else {
                sender.sendMessage(TextFormat.RED + "你没有权限执行此命令。");
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("baimoinvconfig")) {
            if (sender.hasPermission("baimoinvsee.config")) {
                if (args.length >= 1) {
                    try {
                        int newInterval = Integer.parseInt(args[0]);
                        this.setSaveInterval(newInterval);
                        sender.sendMessage(TextFormat.GREEN + "保存间隔已更改为 " + newInterval + " 分钟。");
                    } catch (NumberFormatException e) {
                        sender.sendMessage(TextFormat.RED + "请输入一个有效的数字。");
                    }
                } else {
                    sender.sendMessage(TextFormat.RED + "用法: /baimoinvconfig <分钟>");
                }
            } else {
                sender.sendMessage(TextFormat.RED + "你没有权限执行此命令。");
            }
            return true;
        }

        return false;
    }

    public void loadConfig() {
        this.saveInterval = this.getConfig().getInt("save-interval", 5);
        this.realtimeSave = this.getConfig().getBoolean("realtime-save", false);

        if (!realtimeSave) {
            new NukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : getServer().getOnlinePlayers().values()) {
                        // 保存玩家背包
                    }
                }
            }.runTaskTimer(this, saveInterval * 60 * 20, saveInterval * 60 * 20);
        }
    }

    public void saveConfig() {
        this.getConfig().set("save-interval", this.saveInterval);
        this.getConfig().set("realtime-save", this.realtimeSave);
        this.getConfig().save();
    }

    public void setSaveInterval(int interval) {
        this.saveInterval = interval;
        this.saveConfig();
        this.loadConfig();
    }

    public int getSaveInterval() {
        return this.saveInterval;
    }

    public void setRealtimeSave(boolean realtimeSave) {
        this.realtimeSave = realtimeSave;
        this.saveConfig();
        this.loadConfig();
    }

    public boolean isRealtimeSave() {
        return this.realtimeSave;
    }
}
