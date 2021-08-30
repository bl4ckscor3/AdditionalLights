package com.mgen256.al;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
//import net.minecraft.item.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
//import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mgen256.al.blocks.StandingTorch_S;
import com.mgen256.al.items.*;
import com.mgen256.al.blocks.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AdditionalLights.MOD_ID)
public class AdditionalLights {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AdditionalLights.MOD_ID);

    public static final String MOD_ID = "additional_lights";

    public static Item.Properties ItemProps; 
    public static Map<ModBlockList, IModBlock> modBlocks;
    public static Map<ModItemList, IModItem> modItems;
    public static Map<ModSoundList, SoundEvent> modSounds;

    private static final Logger LOGGER = LogManager.getLogger();

    static {
        CreativeModeTab category = new CreativeModeTab(MOD_ID) {

            @Override
            public ItemStack makeIcon() {
                return new ItemStack( getBlock(ModBlockList.ALTorch_Oak) );
            }
            
            @Override
            public boolean hasSearchBar() {
                return true;
            }

            @Override
            public void fillItemList(NonNullList<ItemStack> itemStacks) {
                itemStacks.clear();

                // items
                modItems.forEach( (key, item) -> itemStacks.add(new ItemStack( (Item)item )) );

                // blocks
                modBlocks.forEach( (key, block) -> {
                    if( block.notRequireItemRegistration())
                        return;

                    Item item = block.getBlockItem();
                    //if (item.getCreativeTabs().contains(this))
                    itemStacks.add(new ItemStack(item));
                });
            }

        }.setBackgroundSuffix( "item_search.png" ).hideTitle();
        ItemProps = new Item.Properties().tab(category);
    }



    public AdditionalLights() {
        // Register the setup method for modloading
        
        // FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
        // Register ourselves for server and other game events we are interested in
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }
 

    public static void init() {
        if (modBlocks != null)
            return;

        modSounds = new LinkedHashMap<ModSoundList, SoundEvent>(){
            private static final long serialVersionUID = 4L;
            {
                put( ModSoundList.Change, new SoundEvent(new ResourceLocation( MOD_ID, "change" ) ).setRegistryName( "change" ) );
                put( ModSoundList.Undo, new SoundEvent(new ResourceLocation( MOD_ID, "undo" ) ).setRegistryName( "undo" ) );
                put( ModSoundList.Fire_Ignition_S, new SoundEvent(new ResourceLocation( MOD_ID, "fire_ignition_s" ) ).setRegistryName( "fire_ignition_s" ) );
                put( ModSoundList.Fire_Ignition_L, new SoundEvent(new ResourceLocation( MOD_ID, "fire_ignition_l" ) ).setRegistryName( "fire_ignition_l" ) );
                put( ModSoundList.Fire_Extinguish, new SoundEvent(new ResourceLocation( MOD_ID, "fire_extinguish" ) ).setRegistryName( "fire_extinguish" ) );
            }};
 
            
        // init blocks
        modBlocks = new LinkedHashMap<ModBlockList, IModBlock>(){ 
            private static final long serialVersionUID = 2L;
            {

            put(ModBlockList.ALLamp_Acacia, new ALLamp(Blocks.ACACIA_PLANKS));
            put(ModBlockList.ALLamp_Birch, new ALLamp(Blocks.BIRCH_PLANKS));
            put(ModBlockList.ALLamp_Oak, new ALLamp(Blocks.OAK_PLANKS));
            put(ModBlockList.ALLamp_Dark_Oak, new ALLamp(Blocks.DARK_OAK_PLANKS));
            put(ModBlockList.ALLamp_Spruce, new ALLamp(Blocks.SPRUCE_PLANKS));
            put(ModBlockList.ALLamp_Jungle, new ALLamp(Blocks.JUNGLE_PLANKS));
            put(ModBlockList.ALLamp_CrimsonPlanks, new ALLamp(Blocks.CRIMSON_PLANKS));
            put(ModBlockList.ALLamp_WarpedPlanks, new ALLamp(Blocks.WARPED_PLANKS));
            put(ModBlockList.ALLamp_CobbleStone, new ALLamp(Blocks.COBBLESTONE));
            put(ModBlockList.ALLamp_Mossy_CobbleStone, new ALLamp(Blocks.MOSSY_COBBLESTONE));
            put(ModBlockList.ALLamp_Stone, new ALLamp(Blocks.STONE));
            put(ModBlockList.ALLamp_Sand_Stone, new ALLamp(Blocks.SANDSTONE));
            put(ModBlockList.ALLamp_End_Stone, new ALLamp(Blocks.END_STONE));
            put(ModBlockList.ALLamp_Nether_Bricks, new ALLamp(Blocks.NETHER_BRICKS));
            put(ModBlockList.ALLamp_Red_Nether_Bricks, new ALLamp(Blocks.RED_NETHER_BRICKS));
            put(ModBlockList.ALLamp_BlackStone, new ALLamp(Blocks.BLACKSTONE));
            put(ModBlockList.ALLamp_Glass, new ALLamp(Blocks.GLASS));
            put(ModBlockList.ALLamp_Iron, new ALLamp(Blocks.IRON_BLOCK));
            put(ModBlockList.ALLamp_Gold, new ALLamp(Blocks.GOLD_BLOCK));
            put(ModBlockList.ALLamp_Diamond, new ALLamp(Blocks.DIAMOND_BLOCK));
            put(ModBlockList.ALLamp_Ice, new ALLamp(Blocks.PACKED_ICE));
            put(ModBlockList.ALLamp_Pink_Wool, new ALLamp(Blocks.PINK_WOOL));
            put(ModBlockList.ALLamp_Magenta_Wool, new ALLamp(Blocks.MAGENTA_WOOL));

            put(ModBlockList.ALTorch_Acacia, new ALTorch(Blocks.ACACIA_PLANKS, ModBlockList.ALTorch_Wall_Acacia));
            put(ModBlockList.ALTorch_Birch, new ALTorch(Blocks.BIRCH_PLANKS, ModBlockList.ALTorch_Wall_Birch));
            put(ModBlockList.ALTorch_Oak, new ALTorch(Blocks.OAK_PLANKS, ModBlockList.ALTorch_Wall_Oak));
            put(ModBlockList.ALTorch_Dark_Oak, new ALTorch(Blocks.DARK_OAK_PLANKS, ModBlockList.ALTorch_Wall_Dark_Oak));
            put(ModBlockList.ALTorch_Spruce, new ALTorch(Blocks.SPRUCE_PLANKS, ModBlockList.ALTorch_Wall_Spruce));
            put(ModBlockList.ALTorch_Jungle, new ALTorch(Blocks.JUNGLE_PLANKS, ModBlockList.ALTorch_Wall_Jungle));
            put(ModBlockList.ALTorch_Crimson, new ALTorch(Blocks.CRIMSON_PLANKS, ModBlockList.ALTorch_Wall_Crimson));
            put(ModBlockList.ALTorch_Warped, new ALTorch(Blocks.WARPED_PLANKS, ModBlockList.ALTorch_Wall_Warped));
            put(ModBlockList.ALTorch_CobbleStone, new ALTorch(Blocks.COBBLESTONE, ModBlockList.ALTorch_Wall_CobbleStone));
            put(ModBlockList.ALTorch_Mossy_CobbleStone, new ALTorch(Blocks.MOSSY_COBBLESTONE, ModBlockList.ALTorch_Wall_Mossy_CobbleStone));
            put(ModBlockList.ALTorch_Stone, new ALTorch(Blocks.STONE, ModBlockList.ALTorch_Wall_Stone));
            put(ModBlockList.ALTorch_Stone_Bricks, new ALTorch(Blocks.STONE_BRICKS, ModBlockList.ALTorch_Wall_Stone_Bricks));
            put(ModBlockList.ALTorch_Mossy_Stone_Bricks, new ALTorch(Blocks.MOSSY_STONE_BRICKS, ModBlockList.ALTorch_Wall_Mossy_Stone_Bricks));
            put(ModBlockList.ALTorch_Smooth_Stone, new ALTorch(Blocks.SMOOTH_STONE, ModBlockList.ALTorch_Wall_Smooth_Stone));
            put(ModBlockList.ALTorch_Sand_Stone, new ALTorch(Blocks.SANDSTONE, ModBlockList.ALTorch_Wall_Sand_Stone));
            put(ModBlockList.ALTorch_End_Stone, new ALTorch(Blocks.END_STONE, ModBlockList.ALTorch_Wall_End_Stone));
            put(ModBlockList.ALTorch_End_Stone_Bricks, new ALTorch(Blocks.END_STONE_BRICKS, ModBlockList.ALTorch_Wall_End_Stone_Bricks));
            put(ModBlockList.ALTorch_Nether_Bricks, new ALTorch(Blocks.NETHER_BRICKS, ModBlockList.ALTorch_Wall_Nether_Bricks));
            put(ModBlockList.ALTorch_Red_Nether_Bricks, new ALTorch(Blocks.RED_NETHER_BRICKS, ModBlockList.ALTorch_Wall_Red_Nether_Bricks));
            put(ModBlockList.ALTorch_BlackStone, new ALTorch(Blocks.BLACKSTONE, ModBlockList.ALTorch_Wall_BlackStone));
            put(ModBlockList.ALTorch_Glass, new ALTorch(Blocks.GLASS, ModBlockList.ALTorch_Wall_Glass));
            put(ModBlockList.ALTorch_Iron, new ALTorch(Blocks.IRON_BLOCK, ModBlockList.ALTorch_Wall_Iron));
            put(ModBlockList.ALTorch_Gold, new ALTorch(Blocks.GOLD_BLOCK, ModBlockList.ALTorch_Wall_Gold));
            put(ModBlockList.ALTorch_Diamond, new ALTorch(Blocks.DIAMOND_BLOCK, ModBlockList.ALTorch_Wall_Diamond));
            put(ModBlockList.ALTorch_Ice, new ALTorch(Blocks.PACKED_ICE, ModBlockList.ALTorch_Wall_Ice));
            put(ModBlockList.ALTorch_Pink_Wool, new ALTorch(Blocks.PINK_WOOL, ModBlockList.ALTorch_Wall_Pink_Wool));
            put(ModBlockList.ALTorch_Magenta_Wool, new ALTorch(Blocks.MAGENTA_WOOL, ModBlockList.ALTorch_Wall_Magenta_Wool));

            put(ModBlockList.ALTorch_Wall_Acacia, new ALTorch_Wall(Blocks.ACACIA_PLANKS, ModBlockList.ALTorch_Acacia));
            put(ModBlockList.ALTorch_Wall_Birch, new ALTorch_Wall(Blocks.BIRCH_PLANKS, ModBlockList.ALTorch_Birch));
            put(ModBlockList.ALTorch_Wall_Oak, new ALTorch_Wall(Blocks.OAK_PLANKS, ModBlockList.ALTorch_Oak));
            put(ModBlockList.ALTorch_Wall_Dark_Oak, new ALTorch_Wall(Blocks.DARK_OAK_PLANKS, ModBlockList.ALTorch_Dark_Oak));
            put(ModBlockList.ALTorch_Wall_Jungle, new ALTorch_Wall(Blocks.JUNGLE_PLANKS, ModBlockList.ALTorch_Jungle));
            put(ModBlockList.ALTorch_Wall_Crimson, new ALTorch_Wall(Blocks.CRIMSON_PLANKS, ModBlockList.ALTorch_Crimson));
            put(ModBlockList.ALTorch_Wall_Warped, new ALTorch_Wall(Blocks.WARPED_PLANKS, ModBlockList.ALTorch_Warped));
            put(ModBlockList.ALTorch_Wall_Spruce, new ALTorch_Wall(Blocks.SPRUCE_PLANKS, ModBlockList.ALTorch_Spruce));
            put(ModBlockList.ALTorch_Wall_CobbleStone, new ALTorch_Wall(Blocks.COBBLESTONE, ModBlockList.ALTorch_CobbleStone));
            put(ModBlockList.ALTorch_Wall_Mossy_CobbleStone, new ALTorch_Wall(Blocks.MOSSY_COBBLESTONE, ModBlockList.ALTorch_Mossy_CobbleStone));
            put(ModBlockList.ALTorch_Wall_Stone, new ALTorch_Wall(Blocks.STONE, ModBlockList.ALTorch_Stone));
            put(ModBlockList.ALTorch_Wall_Stone_Bricks, new ALTorch_Wall(Blocks.STONE_BRICKS, ModBlockList.ALTorch_Stone_Bricks));
            put(ModBlockList.ALTorch_Wall_Mossy_Stone_Bricks, new ALTorch_Wall(Blocks.MOSSY_STONE_BRICKS, ModBlockList.ALTorch_Mossy_Stone_Bricks));
            put(ModBlockList.ALTorch_Wall_Smooth_Stone, new ALTorch_Wall(Blocks.SMOOTH_STONE, ModBlockList.ALTorch_Smooth_Stone));
            put(ModBlockList.ALTorch_Wall_Sand_Stone, new ALTorch_Wall(Blocks.SANDSTONE, ModBlockList.ALTorch_Sand_Stone));
            put(ModBlockList.ALTorch_Wall_End_Stone, new ALTorch_Wall(Blocks.END_STONE, ModBlockList.ALTorch_End_Stone));
            put(ModBlockList.ALTorch_Wall_End_Stone_Bricks, new ALTorch_Wall(Blocks.END_STONE_BRICKS, ModBlockList.ALTorch_End_Stone_Bricks));
            put(ModBlockList.ALTorch_Wall_Nether_Bricks, new ALTorch_Wall(Blocks.NETHER_BRICKS, ModBlockList.ALTorch_Nether_Bricks));
            put(ModBlockList.ALTorch_Wall_Red_Nether_Bricks, new ALTorch_Wall(Blocks.RED_NETHER_BRICKS, ModBlockList.ALTorch_Red_Nether_Bricks));
            put(ModBlockList.ALTorch_Wall_BlackStone, new ALTorch_Wall(Blocks.BLACKSTONE, ModBlockList.ALTorch_BlackStone));
            put(ModBlockList.ALTorch_Wall_Glass, new ALTorch_Wall(Blocks.GLASS, ModBlockList.ALTorch_Glass));
            put(ModBlockList.ALTorch_Wall_Iron, new ALTorch_Wall(Blocks.IRON_BLOCK, ModBlockList.ALTorch_Iron));
            put(ModBlockList.ALTorch_Wall_Gold, new ALTorch_Wall(Blocks.GOLD_BLOCK, ModBlockList.ALTorch_Gold));
            put(ModBlockList.ALTorch_Wall_Diamond, new ALTorch_Wall(Blocks.DIAMOND_BLOCK, ModBlockList.ALTorch_Diamond));
            put(ModBlockList.ALTorch_Wall_Ice, new ALTorch_Wall(Blocks.PACKED_ICE, ModBlockList.ALTorch_Ice));
            put(ModBlockList.ALTorch_Wall_Pink_Wool, new ALTorch_Wall(Blocks.PINK_WOOL, ModBlockList.ALTorch_Pink_Wool));
            put(ModBlockList.ALTorch_Wall_Magenta_Wool, new ALTorch_Wall(Blocks.MAGENTA_WOOL, ModBlockList.ALTorch_Magenta_Wool));


            put(ModBlockList.StandingTorch_S_CobbleStone, new StandingTorch_S(Blocks.COBBLESTONE));
            put(ModBlockList.StandingTorch_S_Mossy_CobbleStone, new StandingTorch_S(Blocks.MOSSY_COBBLESTONE));
            put(ModBlockList.StandingTorch_S_Stone, new StandingTorch_S(Blocks.STONE));
            put(ModBlockList.StandingTorch_S_Stone_Bricks, new StandingTorch_S(Blocks.STONE_BRICKS));
            put(ModBlockList.StandingTorch_S_Mossy_Stone_Bricks, new StandingTorch_S(Blocks.MOSSY_STONE_BRICKS));
            put(ModBlockList.StandingTorch_S_Smooth_Stone, new StandingTorch_S(Blocks.SMOOTH_STONE));
            put(ModBlockList.StandingTorch_S_Polished_Andesite, new StandingTorch_S(Blocks.POLISHED_ANDESITE));
            put(ModBlockList.StandingTorch_S_Polished_Diorite, new StandingTorch_S(Blocks.POLISHED_DIORITE));
            put(ModBlockList.StandingTorch_S_Polished_Granite, new StandingTorch_S(Blocks.POLISHED_GRANITE));
            put(ModBlockList.StandingTorch_S_Sand_Stone, new StandingTorch_S(Blocks.SANDSTONE));
            put(ModBlockList.StandingTorch_S_Cut_Sand_Stone, new StandingTorch_S(Blocks.CUT_SANDSTONE));
            put(ModBlockList.StandingTorch_S_End_Stone, new StandingTorch_S(Blocks.END_STONE));
            put(ModBlockList.StandingTorch_S_End_Stone_Bricks, new StandingTorch_S(Blocks.END_STONE_BRICKS));
            put(ModBlockList.StandingTorch_S_Nether_Bricks, new StandingTorch_S(Blocks.NETHER_BRICKS));
            put(ModBlockList.StandingTorch_S_Red_Nether_Bricks, new StandingTorch_S(Blocks.RED_NETHER_BRICKS));
            put(ModBlockList.StandingTorch_S_Polished_BlackStone, new StandingTorch_S(Blocks.POLISHED_BLACKSTONE));
            put(ModBlockList.StandingTorch_S_Iron, new StandingTorch_S(Blocks.IRON_BLOCK));
            put(ModBlockList.StandingTorch_S_Gold, new StandingTorch_S(Blocks.GOLD_BLOCK));
            put(ModBlockList.StandingTorch_S_Diamond, new StandingTorch_S(Blocks.DIAMOND_BLOCK));
            put(ModBlockList.StandingTorch_S_Ice, new StandingTorch_S(Blocks.PACKED_ICE));
            put(ModBlockList.StandingTorch_S_Pink_Wool, new StandingTorch_S(Blocks.PINK_WOOL));
            put(ModBlockList.StandingTorch_S_Magenta_Wool, new StandingTorch_S(Blocks.MAGENTA_WOOL));

            put(ModBlockList.StandingTorch_L_CobbleStone, new StandingTorch_L(Blocks.COBBLESTONE));
            put(ModBlockList.StandingTorch_L_Mossy_CobbleStone, new StandingTorch_L(Blocks.MOSSY_COBBLESTONE));
            put(ModBlockList.StandingTorch_L_Stone, new StandingTorch_L(Blocks.STONE));
            put(ModBlockList.StandingTorch_L_Stone_Bricks, new StandingTorch_L(Blocks.STONE_BRICKS));
            put(ModBlockList.StandingTorch_L_Mossy_Stone_Bricks, new StandingTorch_L(Blocks.MOSSY_STONE_BRICKS));
            put(ModBlockList.StandingTorch_L_Smooth_Stone, new StandingTorch_L(Blocks.SMOOTH_STONE));
            put(ModBlockList.StandingTorch_L_Polished_Andesite, new StandingTorch_L(Blocks.POLISHED_ANDESITE));
            put(ModBlockList.StandingTorch_L_Polished_Diorite, new StandingTorch_L(Blocks.POLISHED_DIORITE));
            put(ModBlockList.StandingTorch_L_Polished_Granite, new StandingTorch_L(Blocks.POLISHED_GRANITE));
            put(ModBlockList.StandingTorch_L_Sand_Stone, new StandingTorch_L(Blocks.SANDSTONE));
            put(ModBlockList.StandingTorch_L_Cut_Sand_Stone, new StandingTorch_L(Blocks.CUT_SANDSTONE));
            put(ModBlockList.StandingTorch_L_Cut_Sand_Stone, new StandingTorch_L(Blocks.CUT_SANDSTONE));
            put(ModBlockList.StandingTorch_L_End_Stone, new StandingTorch_L(Blocks.END_STONE));
            put(ModBlockList.StandingTorch_L_End_Stone_Bricks, new StandingTorch_L(Blocks.END_STONE_BRICKS));
            put(ModBlockList.StandingTorch_L_Nether_Bricks, new StandingTorch_L(Blocks.NETHER_BRICKS));
            put(ModBlockList.StandingTorch_L_Red_Nether_Bricks, new StandingTorch_L(Blocks.RED_NETHER_BRICKS));
            put(ModBlockList.StandingTorch_L_Polished_BlackStone, new StandingTorch_L(Blocks.POLISHED_BLACKSTONE));
            put(ModBlockList.StandingTorch_L_Iron, new StandingTorch_L(Blocks.IRON_BLOCK));
            put(ModBlockList.StandingTorch_L_Gold, new StandingTorch_L(Blocks.GOLD_BLOCK));
            put(ModBlockList.StandingTorch_L_Diamond, new StandingTorch_L(Blocks.DIAMOND_BLOCK));
            put(ModBlockList.StandingTorch_L_Ice, new StandingTorch_L(Blocks.PACKED_ICE));
            put(ModBlockList.StandingTorch_L_Pink_Wool, new StandingTorch_L(Blocks.PINK_WOOL));
            put(ModBlockList.StandingTorch_L_Magenta_Wool, new StandingTorch_L(Blocks.MAGENTA_WOOL));

            put(ModBlockList.FirePit_S_CobbleStone, new FirePit_S(Blocks.COBBLESTONE));
            put(ModBlockList.FirePit_S_Mossy_CobbleStone, new FirePit_S(Blocks.MOSSY_COBBLESTONE));
            put(ModBlockList.FirePit_S_Stone, new FirePit_S(Blocks.STONE));
            put(ModBlockList.FirePit_S_Stone_Bricks, new FirePit_S(Blocks.STONE_BRICKS));
            put(ModBlockList.FirePit_S_Mossy_Stone_Bricks, new FirePit_S(Blocks.MOSSY_STONE_BRICKS));
            put(ModBlockList.FirePit_S_Smooth_Stone, new FirePit_S(Blocks.SMOOTH_STONE));
            put(ModBlockList.FirePit_S_Polished_Andesite, new FirePit_S(Blocks.POLISHED_ANDESITE));
            put(ModBlockList.FirePit_S_Polished_Diorite, new FirePit_S(Blocks.POLISHED_DIORITE));
            put(ModBlockList.FirePit_S_Polished_Granite, new FirePit_S(Blocks.POLISHED_GRANITE));
            put(ModBlockList.FirePit_S_Sand_Stone, new FirePit_S(Blocks.SANDSTONE));
            put(ModBlockList.FirePit_S_Cut_Sand_Stone, new FirePit_S(Blocks.CUT_SANDSTONE));
            put(ModBlockList.FirePit_S_End_Stone, new FirePit_S(Blocks.END_STONE));
            put(ModBlockList.FirePit_S_End_Stone_Bricks, new FirePit_S(Blocks.END_STONE_BRICKS));
            put(ModBlockList.FirePit_S_Nether_Bricks, new FirePit_S(Blocks.NETHER_BRICKS));
            put(ModBlockList.FirePit_S_Red_Nether_Bricks, new FirePit_S(Blocks.RED_NETHER_BRICKS));
            put(ModBlockList.FirePit_S_Polished_BlackStone, new FirePit_S(Blocks.POLISHED_BLACKSTONE));
            put(ModBlockList.FirePit_S_Iron, new FirePit_S(Blocks.IRON_BLOCK));
            put(ModBlockList.FirePit_S_Gold, new FirePit_S(Blocks.GOLD_BLOCK));
            put(ModBlockList.FirePit_S_Diamond, new FirePit_S(Blocks.DIAMOND_BLOCK));
            put(ModBlockList.FirePit_S_Ice, new FirePit_S(Blocks.PACKED_ICE));
            put(ModBlockList.FirePit_S_Pink_Wool, new FirePit_S(Blocks.PINK_WOOL));
            put(ModBlockList.FirePit_S_Magenta_Wool, new FirePit_S(Blocks.MAGENTA_WOOL));


            put(ModBlockList.FirePit_L_CobbleStone, new FirePit_L(Blocks.COBBLESTONE));
            put(ModBlockList.FirePit_L_Mossy_CobbleStone, new FirePit_L(Blocks.MOSSY_COBBLESTONE));
            put(ModBlockList.FirePit_L_Stone, new FirePit_L(Blocks.STONE));
            put(ModBlockList.FirePit_L_Stone_Bricks, new FirePit_L(Blocks.STONE_BRICKS));
            put(ModBlockList.FirePit_L_Mossy_Stone_Bricks, new FirePit_L(Blocks.MOSSY_STONE_BRICKS));
            put(ModBlockList.FirePit_L_Smooth_Stone, new FirePit_L(Blocks.SMOOTH_STONE));
            put(ModBlockList.FirePit_L_Polished_Andesite, new FirePit_L(Blocks.POLISHED_ANDESITE));
            put(ModBlockList.FirePit_L_Polished_Diorite, new FirePit_L(Blocks.POLISHED_DIORITE));
            put(ModBlockList.FirePit_L_Polished_Granite, new FirePit_L(Blocks.POLISHED_GRANITE));
            put(ModBlockList.FirePit_L_Sand_Stone, new FirePit_L(Blocks.SANDSTONE));
            put(ModBlockList.FirePit_L_Cut_Sand_Stone, new FirePit_L(Blocks.CUT_SANDSTONE));
            put(ModBlockList.FirePit_L_End_Stone, new FirePit_L(Blocks.END_STONE));
            put(ModBlockList.FirePit_L_End_Stone_Bricks, new FirePit_L(Blocks.END_STONE_BRICKS));
            put(ModBlockList.FirePit_L_Nether_Bricks, new FirePit_L(Blocks.NETHER_BRICKS));
            put(ModBlockList.FirePit_L_Red_Nether_Bricks, new FirePit_L(Blocks.RED_NETHER_BRICKS));
            put(ModBlockList.FirePit_L_Polished_BlackStone, new FirePit_L(Blocks.POLISHED_BLACKSTONE));
            put(ModBlockList.FirePit_L_Iron, new FirePit_L(Blocks.IRON_BLOCK));
            put(ModBlockList.FirePit_L_Gold, new FirePit_L(Blocks.GOLD_BLOCK));
            put(ModBlockList.FirePit_L_Diamond, new FirePit_L(Blocks.DIAMOND_BLOCK));
            put(ModBlockList.FirePit_L_Ice, new FirePit_L(Blocks.PACKED_ICE));
            put(ModBlockList.FirePit_L_Pink_Wool, new FirePit_L(Blocks.PINK_WOOL));
            put(ModBlockList.FirePit_L_Magenta_Wool, new FirePit_L(Blocks.MAGENTA_WOOL));

                                        
            put(ModBlockList.Fire_For_StandingTorch_S, new Fire( PedestalTypes.standing_torch_s ));
            put(ModBlockList.Fire_For_StandingTorch_L, new Fire( PedestalTypes.standing_torch_l ));
            put(ModBlockList.Fire_For_FirePit_S, new Fire( PedestalTypes.fire_pit_s ));
            put(ModBlockList.Fire_For_FirePit_L, new Fire( PedestalTypes.fire_pit_l ));
                                                    
            put(ModBlockList.SoulFire_For_StandingTorch_S, new Fire_Soul( PedestalTypes.standing_torch_s ));
            put(ModBlockList.SoulFire_For_StandingTorch_L, new Fire_Soul( PedestalTypes.standing_torch_l ));
            put(ModBlockList.SoulFire_For_FirePit_S, new Fire_Soul( PedestalTypes.fire_pit_s ));
            put(ModBlockList.SoulFire_For_FirePit_L, new Fire_Soul( PedestalTypes.fire_pit_l ));

       }
        };
        
        modItems = new LinkedHashMap<ModItemList, IModItem>(){ 
            private static final long serialVersionUID = 3L;
            {
                put( ModItemList.SoulWand, new SoulWand() );
            }};

        modBlocks.forEach( ( key, block ) -> block.init() );
    }

   
    private void doClientStuff(FMLClientSetupEvent event) {
        modBlocks.forEach( ( key, block ) -> block.setRenderLayer() );
    }
    
    private void setup(FMLCommonSetupEvent event) {
    }

    public static void Log(String message) {
        LOGGER.info(MOD_ID + "::" + message);
    }

    public static Block getBlock( ModBlockList key )
    {
        return (Block)modBlocks.get( key );
    }


    public static BlockItem getBlockItem( ModBlockList key )
    {
        return modBlocks.get( key ).getBlockItem();
    }





    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        
        @SubscribeEvent
        public static void onItemsRegistry(RegistryEvent.Register<Item> event) {
            init();

            IForgeRegistry<Item> registry = event.getRegistry();

            modBlocks.forEach( ( key, block ) -> {
                if( block.notRequireItemRegistration() )
                    return;
                registry.register(block.getBlockItem());  
            } );

            modItems.forEach( ( key, item ) -> registry.register( (Item)item ) );
        }

        @SubscribeEvent
        public static void onBlocksRegistry(RegistryEvent.Register<Block> event) {
            init();
            IForgeRegistry<Block> registry = event.getRegistry();
            modBlocks.forEach( (key, block) -> registry.register( (Block)block ) );
        }

        @SubscribeEvent
        public static void onSoundsRegistry(RegistryEvent.Register<SoundEvent> event){

            IForgeRegistry<SoundEvent> registry = event.getRegistry();
            modSounds.forEach( (key, sound) -> registry.register( sound ) );
        }
    }


}
