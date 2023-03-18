# BaimoInvSee

BaimoInvSee 是一个适用于 PowerNukkitX 的插件，允许具有 OP 权限的玩家查看和修改其他玩家的背包。

## 功能

1. 拥有 OP 权限的玩家可以查看其他玩家的背包，无论玩家离线还是在线。背包以箱子 GUI 的形式展示，且可以改动背包物品。
2. 可以使用指令配置几分钟保存一次玩家背包，还是实时保存。
3. 玩家退出游戏时自动保存背包。

## 指令

* `/baimoinvsee <player_name>`: 查看和修改指定玩家的背包。
* `/baimoinvconfig <interval_in_minutes>`: 配置背包保存间隔。

## 权限

* `baimoinvsee.command`: 允许使用 `/baimoinvsee` 命令（默认为 OP）。
* `baimoinvsee.config`: 允许使用 `/baimoinvconfig` 命令（默认为 OP）。

## 安装

将构建好的 `BaimoInvSee.jar` 文件放入 PowerNukkitX 服务器的 `plugins` 文件夹中，然后重启服务器。

## 构建

要构建此项目，您需要使用 [Maven](https://maven.apache.org/)。

在项目根目录中执行以下命令：

```bash
mvn clean package
```
构建完成后，可以在 target 文件夹中找到生成的 BaimoInvSee.jar 文件。