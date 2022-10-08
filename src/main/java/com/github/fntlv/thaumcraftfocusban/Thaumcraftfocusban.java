package com.github.fntlv.thaumcraftfocusban;

import com.github.fntlv.thaumcraftfocusban.command.Commander;
import com.github.fntlv.thaumcraftfocusban.command.CommandsHolder;
import com.github.fntlv.thaumcraftfocusban.command.PlayerCommand;
import com.github.fntlv.thaumcraftfocusban.data.DataConfig;
import com.github.fntlv.thaumcraftfocusban.listener.FocusListener;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.lang.reflect.Method;
import java.nio.file.Path;

@Plugin(
        id = "thaumcraftfocusban",
        name = "Thaumcraftfocusban",
        description = "a thaumcraft plugin",
        authors = {
                "fntlv"
        },
        dependencies = {@Dependency(
                id = "thaumcraft",
                optional = true
        ),@Dependency(
                id = "ThaumicAugmentation",
                optional = true
        )}
)
public class Thaumcraftfocusban {

    private static Thaumcraftfocusban instance;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer pluginContainer;

    @Inject
    @DefaultConfig(
            sharedRoot = false
    )
    public Path configDir;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        this.logger.info("§e------------------");
        this.logger.info("§e神秘核心管理插件加载中...");
        this.registerCmd();
        new DataConfig();
        this.registerListener();
    }

    public void registerCmd(){
        CommandSpec commandSpec = CommandSpec.builder()
                .executor(new Commander())
                .arguments(
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("命令"))
                        ),
                        GenericArguments.optional(
                                GenericArguments.string(Text.of("命令"))
                        ),GenericArguments.optional(
                                GenericArguments.string(Text.of("命令"))
                        )
                ).build();

        Sponge.getCommandManager().register(pluginContainer,commandSpec,"tfb");

        for (Method method: CommandsHolder.class.getDeclaredMethods()){
            if (method.isAnnotationPresent(PlayerCommand.class)){
                CommandsHolder.commandMap.put(method.getAnnotation(PlayerCommand.class),method);
            }
        }
        this.logger.info("§3命令注册成功");
    }

    public void registerListener(){
        Sponge.getEventManager().registerListeners(this,new FocusListener());
    }

    public static Thaumcraftfocusban getInstance(){
        return instance;
    }

    public Logger getLogger(){
        return this.logger;
    }
}
