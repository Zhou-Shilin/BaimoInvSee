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
        this.getLogger().info("BaimoInvSee已被成功加载。版本：1.0.0");

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
            if (!sender.isOp()) {
                sender.sendMessage(TextFormat.RED + "你没有权限执行该命令。");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(TextFormat.RED + "用法: /invsee <玩家名>");
                return true;
            }

            Player target = this.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(TextFormat.RED + "找不到该玩家。");
                return true;
            }

            // 获取玩家背包内容
            String inventory = target.getInventory().getContents().toString();

            // 保存玩家背包内容
            if (this.config.getBoolean("auto-save")) {
                this.saveInventory(target.getName(), inventory);
            }

            sender.sendMessage(TextFormat.YELLOW + target.getName() + " 的背包内容：");
            sender.sendMessage(inventory);

            return true;
        }

        return false;
    }

    /**
     * 保存玩家背包
     *
     * @param playerName 玩家名
     * @param inventory  背包内容
     */
    private void saveInventory(String playerName, String inventory) {
        long currentTime = System.currentTimeMillis();

        // 判断距离上次保存是否超过指定时间间隔
        if (lastSaved.containsKey(playerName) && currentTime - lastSaved.get(playerName) < this.config.getInt("save-interval") * 60 * 1000) {
            return;
        }

        // 保存背包内容
        this.config.set("inventories." + playerName, inventory);
        this.config.save();

        // 更新上次保存时间
        lastSaved.put(playerName, currentTime);
    }

}

