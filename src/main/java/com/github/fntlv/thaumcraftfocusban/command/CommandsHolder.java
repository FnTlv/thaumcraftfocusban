package com.github.fntlv.thaumcraftfocusban.command;

import com.github.fntlv.thaumcraftfocusban.data.DataConfig;
import com.github.fntlv.thaumcraftfocusban.data.DataHolder;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class CommandsHolder {

    public static HashMap<PlayerCommand, Method> commandMap = new HashMap<>();

    @PlayerCommand(
            cmd = "add",
            msg = "添加核心",
            type = SenderType.ALL
    )
    public static void onAddFocus(CommandSource src, List<String> args){
        if (args.size()==2){
            if (src.hasPermission("tfb.admin")){
                String focus = args.get(1);
                if (!DataHolder.getFocusList().contains(focus)){
                    DataHolder.getFocusList().add(focus);
                    DataConfig.populate(focus,"true");
                    src.sendMessage(Text.of("§7[§6系统§7] §f成功禁用核心§a"+focus));
                } else {
                    src.sendMessage(Text.of("§7[§6系统§7] §f禁用列表中已存在核心§a"+focus));
                }
            } else {
                src.sendMessage(Text.of("§7[§6系统§7] §c你没有权限使用该命令"));
            }
        } else {
            src.sendMessage(Text.of("§7[§6系统§7] §c参数错误"));
        }
    }

    @PlayerCommand(
            cmd = "remove",
            msg = "删除核心效果",
            type = SenderType.ALL
    )
    public static void onRemoveFocus(CommandSource src,List<String> args){
        if (args.size()==2){
            if (src.hasPermission("tfb.admin")){
                String focus = args.get(1);
                if (DataHolder.getFocusList().contains(focus)){
                    DataHolder.getFocusList().remove(focus);
                    DataConfig.populate(focus,null);
                    src.sendMessage(Text.of("§7[§6系统§7] §f成功解除禁用核心§a"+focus));
                } else {
                    src.sendMessage(Text.of("§7[§6系统§7] §f禁用列表中已存在核心§a"+focus));
                }
            } else {
                src.sendMessage(Text.of("§7[§6系统§7] §c你没有权限使用该命令"));
            }
        } else {
            src.sendMessage(Text.of("§7[§6系统§7] §c参数错误"));
        }
    }

    @PlayerCommand(
            cmd = "list",
            msg = "禁用列表",
            type = SenderType.ALL
    )
    public static void onShowList(CommandSource src,List<String> args){
        if (args.size() ==1){
            if (src.hasPermission("tfb.admin")){
                src.sendMessage(Text.of("§c核心禁用列表"));
                if (DataHolder.getFocusList().size()!=0){
                    for (String fous:DataHolder.getFocusList()){
                        src.sendMessage(Text.of("§a"+fous));
                    }
                } else {
                    src.sendMessage(Text.of("§7[§6系统§7] §c禁用列表为空"));
                }
            } else {
                src.sendMessage(Text.of("§7[§6系统§7] §c你没有权限使用该命令"));
            }
        } else {
            src.sendMessage(Text.of("§7[§6系统§7] §c参数错误"));
        }
    }
}
