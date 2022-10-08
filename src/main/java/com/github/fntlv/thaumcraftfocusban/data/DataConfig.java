package com.github.fntlv.thaumcraftfocusban.data;

import com.github.fntlv.thaumcraftfocusban.Thaumcraftfocusban;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataConfig {

    private static Path configFile;
    private static ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private static CommentedConfigurationNode configNode;

    public DataConfig(){
        configFile = Paths.get(Thaumcraftfocusban.getInstance().configDir.getParent() + File.separator + "focuslist.conf");
        configLoader = (HoconConfigurationLoader.builder().setPath(configFile)).build();
        setUp();
        initData();
    }

    public static void setUp(){
        if (!Files.exists(configFile, new LinkOption[0])) {
            try {
                Files.createFile(configFile);
                load();
                save();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        } else {
            load();
        }
    }

    public static void load() {
        try {
            configNode = configLoader.load();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void save() {
        try {
            configLoader.save(configNode);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public static void populate(String vaule1,String vaule2){
        configNode.getNode(new Object[]{"banlist", vaule1}).setValue(vaule2);
        save();
    }

    public static void initData(){
        Thaumcraftfocusban.getInstance().getLogger().info("§b加载数据准备中....");
        for (Object a: configNode.getNode("banlist").getChildrenMap().keySet()){
            Object b =configNode.getNode("banlist").getChildrenMap().get(a).getValue();
            DataHolder.getFocusList().add(String.valueOf(a));
            Thaumcraftfocusban.getInstance().getLogger().info("§b加载数据 §a"+a);
        }
    }

}
