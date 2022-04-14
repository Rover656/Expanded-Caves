package dev.nerdthings.expandedcaves.data;

import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.blocks.moss.MossBlock;
import dev.nerdthings.expandedcaves.common.blocks.mushroom.CustomMushroomBlock;
import dev.nerdthings.expandedcaves.common.blocks.rock.RockpileBlock;
import dev.nerdthings.expandedcaves.common.blocks.vine.CaveVineBlock;
import dev.nerdthings.expandedcaves.common.blocks.vine.CaveVineEndBlock;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SandBlock;

import java.util.function.Predicate;

public class ModBlockTagProvider extends BlockTagsProvider {

    private Predicate<Block> MOD_BLOCK = (b) -> Constants.MOD_ID.equals(Registry.BLOCK.getKey(b).getNamespace());

    // Filter instant mined blocks
    private Predicate<Block> NOT_INSTAMINE = (b) -> !(b instanceof CustomMushroomBlock || b instanceof MossBlock || b instanceof CaveVineBlock
        || b instanceof CaveVineEndBlock || b instanceof RockpileBlock // TODO: Exclude PotBlock?
    );

    public ModBlockTagProvider(DataGenerator $$0) {
        super($$0);
    }

    @Override
    protected void addTags() {
        // Mining tags
        // TODO: Automated way because fuck doing this by hand
        //        tag(BlockTags.MINEABLE_WITH_PICKAXE).add();

        // Base stones
        tag(BlockTags.BASE_STONE_OVERWORLD).add(ModBlocks.SEDIMENT_STONE, ModBlocks.LAVASTONE, ModBlocks.DIRTSTONE, ModBlocks.COBBLED_DIRTSTONE);

        tag(BlockTags.SLABS).add(ModBlocks.SEDIMENT_STONE_SLAB, ModBlocks.LAVASTONE_SLAB, ModBlocks.POLISHED_LAVASTONE_SLAB, ModBlocks.DIRTSTONE_SLAB,
            ModBlocks.COBBLED_DIRTSTONE_SLAB, ModBlocks.MARLSTONE_SLAB, ModBlocks.BRICKS_ICE_SLAB, ModBlocks.BRICKS_SNOW_SLAB);

        tag(BlockTags.STAIRS).add(ModBlocks.SEDIMENT_STONE_STAIRS, ModBlocks.LAVASTONE_STAIRS, ModBlocks.POLISHED_LAVASTONE_STAIRS, ModBlocks.DIRTSTONE_STAIRS,
            ModBlocks.COBBLED_DIRTSTONE_STAIRS, ModBlocks.MARLSTONE_STAIRS, ModBlocks.BRICKS_ICE_STAIRS, ModBlocks.BRICKS_SNOW_STAIRS);

        tag(BlockTags.WALLS).add(ModBlocks.SEDIMENT_STONE_WALL, ModBlocks.LAVASTONE_WALL, ModBlocks.POLISHED_LAVASTONE_WALL, ModBlocks.DIRTSTONE_WALL,
            ModBlocks.COBBLED_DIRTSTONE_WALL, ModBlocks.MARLSTONE_WALL, ModBlocks.BRICKS_ICE_WALL, ModBlocks.BRICKS_SNOW_WALL);

        tag(BlockTags.BUTTONS).add(ModBlocks.SEDIMENT_STONE_BUTTON, ModBlocks.LAVASTONE_BUTTON, ModBlocks.POLISHED_LAVASTONE_BUTTON, ModBlocks.DIRTSTONE_BUTTON,
            ModBlocks.COBBLED_DIRTSTONE_BUTTON, ModBlocks.MARLSTONE_BUTTON, ModBlocks.BRICKS_ICE_BUTTON, ModBlocks.BRICKS_SNOW_BUTTON);

        tag(BlockTags.STONE_PRESSURE_PLATES).add(ModBlocks.SEDIMENT_STONE_PRESSURE_PLATE, ModBlocks.LAVASTONE_PRESSURE_PLATE,
            ModBlocks.POLISHED_LAVASTONE_PRESSURE_PLATE, ModBlocks.DIRTSTONE_PRESSURE_PLATE, ModBlocks.COBBLED_DIRTSTONE_PRESSURE_PLATE,
            ModBlocks.MARLSTONE_PRESSURE_PLATE, ModBlocks.BRICKS_ICE_PRESSURE_PLATE, ModBlocks.BRICKS_SNOW_PRESSURE_PLATE);

        miningTags();
    }

    private void miningTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(getBlocks(NOT_INSTAMINE.and(b -> !(b instanceof SandBlock))));
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(getBlocks(b -> b instanceof SandBlock));
    }

    private Block[] getBlocks(Predicate<Block> filter) {
        return registry.stream().filter(MOD_BLOCK.and(filter)).toArray(Block[]::new);
    }

    @Override
    public String getName() {
        return null;
    }
}
