package com.github.fntlv.thaumcraftfocusban.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Commander implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        SenderType senderType = SenderType.CONSOLE;

        if (src instanceof Player){
            senderType = SenderType.PLAYER;
        }

        Collection<String> argCollection = args.getAll(Text.of("命令"));
        List<String> commandArgs = new ArrayList<>(argCollection);

        if (args.getAll(Text.of("命令")).size() != 0){
            for (PlayerCommand playerCommand : CommandsHolder.commandMap.keySet()){
                if (playerCommand.cmd().equals(commandArgs.get(0))){
                    if (playerCommand.type().equals(senderType) || playerCommand.type().equals(SenderType.ALL)){
                        try {
                            CommandsHolder.commandMap.get(playerCommand).invoke(null,src,commandArgs);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } else {
                        src.sendMessage(Text.of("§7[§6系统§7] §c你不能以此方式执行该命令"));
                    }
                    return CommandResult.success();
                }
            }
            src.sendMessage(Text.of("§7[§6系统§7] §c不存在该指令"));
        } else {
            src.sendMessage(Text.of("§a神秘核心禁用系统指令帮助"));
            src.sendMessage(Text.of("§f/tfb add 核心名字 §e添加核心到禁用列表"));
            src.sendMessage(Text.of("§f/tfb remove 核心名字 §e从禁用列表中删除核心"));
            src.sendMessage(Text.of("§f/tfb list §e查看禁用核心列表"));
            return CommandResult.success();
        }

        return null;
    }
}
