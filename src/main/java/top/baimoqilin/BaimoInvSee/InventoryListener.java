package top.baimoqilin.BaimoInvSee;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryCloseEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.InventoryTransaction;

public class InventoryListener implements Listener {

    private BaimoInvSee plugin;

    public InventoryListener(BaimoInvSee plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInventoryTransaction(InventoryTransactionEvent event) {
        InventoryTransaction transaction = event.getTransaction();
        Player player = transaction.getSource();

        if (plugin.isRealtimeSave()) {
            for (InventoryAction action : transaction.getActions()) {
                // 检查玩家是否正在操作目标玩家的背包，并执行相应的保存操作
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = event.getPlayer();

        // 检查玩家是否正在关闭目标玩家的背包，并执行相应的保存操作
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // 保存退出游戏玩家的背包
    }
}
