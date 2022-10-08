package com.github.fntlv.thaumcraftfocusban.listener;

import com.github.fntlv.thaumcraftfocusban.data.DataHolder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;
import thaumcraft.api.casters.FocusModSplit;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.casters.IFocusElement;
import thaumcraft.common.items.casters.ItemCaster;
import thaumcraft.common.items.casters.ItemFocus;
import thecodex6824.thaumicaugmentation.common.item.ItemTieredCasterGauntlet;

import java.util.ArrayList;
import java.util.HashSet;

public class FocusListener {


    @Listener
    public void onItemRightClick(InteractItemEvent.Secondary event, @First Player player){
        ItemStackSnapshot itemStackSnapshot = event.getItemStack();
        ItemStack itemStackUsed = itemStackSnapshot.createStack();
        if (itemStackUsed.getType().getId().equals("thaumcraft:caster_basic")){
            net.minecraft.item.ItemStack isNative = ItemStackUtil.toNative(itemStackUsed);
            ItemCaster itemCaster =(ItemCaster) isNative.getItem();
            net.minecraft.item.ItemStack focusStack = itemCaster.getFocusStack(isNative);
            FocusPackage fp = ItemFocus.getPackage(focusStack);
            HashSet<String> uniqueFocusSpellKeys = this.getUniqueSpellNodeKeysForFocusPackage(fp);
            for (String keys:uniqueFocusSpellKeys){
                if (player.hasPermission("tfb.admin")){
                    player.sendMessage(Text.of("§7[§3调试§7] §f你当前护手上核心的核心效果有§a"+keys));
                }
                //player.sendMessage(Text.of("此时的核心效果为"+keys));
                if (DataHolder.getFocusList().contains(keys)){
                    event.setCancelled(true);
                    player.sendMessage(Text.of("§c抱歉,您不能使用该核心!"));
                }
            }
        } else if (itemStackUsed.getType().getId().equals("thaumicaugmentation:gauntlet")){
            net.minecraft.item.ItemStack isNative = ItemStackUtil.toNative(itemStackUsed);
            ItemTieredCasterGauntlet itemTieredCasterGauntlet =(ItemTieredCasterGauntlet) isNative.getItem();
            net.minecraft.item.ItemStack focusStack = itemTieredCasterGauntlet.getFocusStack(isNative);
            FocusPackage fp = ItemFocus.getPackage(focusStack);
            HashSet<String> uniqueFocusSpellKeys = this.getUniqueSpellNodeKeysForFocusPackage(fp);
            for (String keys:uniqueFocusSpellKeys){
                if (player.hasPermission("tfb.admin")){
                    player.sendMessage(Text.of("§7[§3调试§7] §f你当前护手上核心的核心效果有§a"+keys));
                }
                if (DataHolder.getFocusList().contains(keys)){
                    event.setCancelled(true);
                    player.sendMessage(Text.of("§c抱歉,您不能使用该核心!"));
                }
            }
        }
    }

    public HashSet<String> getUniqueSpellNodeKeysForFocusPackage(FocusPackage fp){
        HashSet<String> uniqueKeys = new HashSet();
        if (fp !=null && fp.nodes !=null){
            for (IFocusElement iFocusElement:fp.nodes){
                uniqueKeys.add(iFocusElement.getKey());
                if (iFocusElement instanceof FocusPackage){
                    uniqueKeys.addAll(this.getUniqueSpellNodeKeysForFocusPackage((FocusPackage) iFocusElement));
                } else if (iFocusElement instanceof FocusModSplit){
                    FocusModSplit focusModSplit = (FocusModSplit) iFocusElement;
                    ArrayList<FocusPackage> splitPackages = focusModSplit.getSplitPackages();
                    for (FocusPackage focusPackage:splitPackages){
                        uniqueKeys.addAll(this.getUniqueSpellNodeKeysForFocusPackage(focusPackage));
                    }
                }
            }
            return uniqueKeys;
        } else {
            return uniqueKeys;
        }
    }
}
