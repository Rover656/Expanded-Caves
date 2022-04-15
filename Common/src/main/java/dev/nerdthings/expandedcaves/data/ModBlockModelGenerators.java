package dev.nerdthings.expandedcaves.data;

import com.google.gson.JsonElement;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.blocks.rock.RockpileBlock;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.SpelothemBundle;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.TallStalagmiteBlock;
import dev.nerdthings.expandedcaves.mixin.BlockModelGeneratorsAccess;
import net.minecraft.core.Registry;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModBlockModelGenerators {
    private final Consumer<BlockStateGenerator> blockStateOutput;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

    private final Consumer<Item> skippedAutoModelsOutput;

    private static final ModelTemplate PEBBLE = new ModelTemplate(Optional.of(Constants.loc("block/pebble")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate SMALL_PILE = new ModelTemplate(Optional.of(Constants.loc("block/small_rockpile")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate BIG_PILE = new ModelTemplate(Optional.of(Constants.loc("block/big_rockpile")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);

    private static final ModelTemplate STALACTITE = new ModelTemplate(Optional.of(Constants.loc("block/stalactite")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate TALL_STALACTITE_LOWER = new ModelTemplate(Optional.of(Constants.loc("block/stalactite_tall_lower")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate TALL_STALACTITE_UPPER = new ModelTemplate(Optional.of(Constants.loc("block/stalactite_tall_upper")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate STALAGMITE = new ModelTemplate(Optional.of(Constants.loc("block/stalagmite")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate TALL_STALAGMITE_LOWER = new ModelTemplate(Optional.of(Constants.loc("block/stalagmite_tall_lower")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    private static final ModelTemplate TALL_STALAGMITE_UPPER = new ModelTemplate(Optional.of(Constants.loc("block/stalagmite_tall_upper")), Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);

    public ModBlockModelGenerators(Consumer<BlockStateGenerator> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput,
        Consumer<Item> skippedAutoModelsOutput) {
        this.blockStateOutput = blockStateOutput;
        this.modelOutput = modelOutput;
        this.skippedAutoModelsOutput = skippedAutoModelsOutput;
    }

    public void run() {
        // Process block families
        BlockFamilies.getAllFamilies().forEach(family -> {
            // Base block
            ResourceLocation baseModel = TexturedModel.CUBE.create(family.getBaseBlock(), this.modelOutput);

            // Register blocks
            this.blockStateOutput.accept(BlockModelGeneratorsAccess.createSimpleBlock(family.getBaseBlock(), baseModel));

            family.getVariants().forEach(((variant, block) -> {
                switch (variant) {
                    case STAIRS -> stairs(block, family.getBaseBlock());
                    case SLAB -> slab(block, family.getBaseBlock(), baseModel);
                    case WALL -> wall(block, family.getBaseBlock());
                    case PRESSURE_PLATE -> pressurePlate(block, family.getBaseBlock());
                    case BUTTON -> button(block, family.getBaseBlock());
                }
            }));
        });

        // Mushrooms
        createCrossBlock(ModBlocks.SWEETSHROOM);
        createCrossBlock(ModBlocks.GOLDISHROOM);
        createCrossBlock(ModBlocks.SHINYSHROOM);
        createCrossBlock(ModBlocks.LUMISHROOM);
        createCrossBlock(ModBlocks.FLUOSHROOM);
        createCrossBlock(ModBlocks.ROCKSHROOM);

        // Moss and Vines
        createCrossBlock(ModBlocks.MOSS_DRY);
        createCrossBlock(ModBlocks.MOSS_FIRE);
        createCrossBlock(ModBlocks.MOSS_FROZEN);
        createCrossBlock(ModBlocks.MOSS_HANGING_ROOTS);
        createCrossBlock(ModBlocks.MOSS_HUMID_GROUND);
        createCrossBlock(ModBlocks.MOSS_HUMID_CEILING);
        createCrossBlock(ModBlocks.CAVE_VINE);
        createCrossBlock(ModBlocks.CAVE_VINE_END);

        // Broken stone
        // TODO: Cracked textures for these.
//        createTrivialCube(ModBlocks.BROKEN_STONE);
//        createTrivialCube(ModBlocks.BROKEN_DEEPSLATE);
        copyBlock(ModBlocks.BROKEN_STONE, ModelLocationUtils.getModelLocation(Blocks.STONE));
        copyBlock(ModBlocks.BROKEN_DEEPSLATE, ModelLocationUtils.getModelLocation(Blocks.DEEPSLATE));

        // Misc blocks
        createTrivialCube(ModBlocks.MARLSTONE_POWDER);

        // Spelothems
        spelothem(ModBlocks.STONE_SPELOTHEMS);
        spelothem(ModBlocks.ANDESITE_SPELOTHEMS);
        spelothem(ModBlocks.DIORITE_SPELOTHEMS);
        spelothem(ModBlocks.GRANITE_SPELOTHEMS);
        spelothem(ModBlocks.TUFF_SPELOTHEMS);
        spelothem(ModBlocks.DEEPSLATE_SPELOTHEMS);
        spelothem(ModBlocks.SEDIMENT_STONE_SPELOTHEMS);
        spelothem(ModBlocks.LAVASTONE_SPELOTHEMS);
        spelothem(ModBlocks.PACKED_ICE_SPELOTHEMS);

        // Rockpiles
        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createRotatedVariant(ModBlocks.PEBBLE_FLINT, Constants.loc("block/rock_flint")));
        pebble(ModBlocks.STONE_PEBBLES);
        pebble(ModBlocks.ANDESITE_PEBBLES);
        pebble(ModBlocks.DIORITE_PEBBLES);
        pebble(ModBlocks.GRANITE_PEBBLES);
        pebble(ModBlocks.TUFF_PEBBLES);
        pebble(ModBlocks.DEEPSLATE_PEBBLES);
        pebble(ModBlocks.SEDIMENT_STONE_PEBBLES);
        pebble(ModBlocks.LAVASTONE_PEBBLES);

        // Ignore items that have their own models in files
        skippedAutoModelsOutput.accept(ModBlocks.PEBBLE_FLINT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.TREASURE_POT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.SMALL_TREASURE_POT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.TREASURE_AMPHORA.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.SMALL_QUARTZ_TREASURE_POT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.QUARTZ_TREASURE_AMPHORA.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.DECORATIVE_POT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.SMALL_DECORATIVE_POT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.DECORATIVE_AMPHORA.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.SMALL_QUARTZ_DECORATIVE_POT.asItem());
        skippedAutoModelsOutput.accept(ModBlocks.QUARTZ_DECORATIVE_AMPHORA.asItem());
    }

    // region Helpers all stolen from vanilla

    private void spelothem(SpelothemBundle bundle) {
        ResourceLocation texture = TextureMapping.getBlockTexture(bundle.referenceBlock);
        String id = "block/" + bundle.name;

        ResourceLocation stalactite = STALACTITE.create(Constants.loc(id + "_stalactite"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation tallStalactiteLower = TALL_STALACTITE_LOWER.create(Constants.loc(id + "_tall_stalactite_lower"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation tallStalactiteUpper = TALL_STALACTITE_UPPER.create(Constants.loc(id + "_tall_stalactite_upper"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation stalagmite = STALAGMITE.create(Constants.loc(id + "_stalagmite"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation tallStalagmiteLower = TALL_STALAGMITE_LOWER.create(Constants.loc(id + "_tall_stalagmite_lower"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation tallStalagmiteUpper = TALL_STALAGMITE_UPPER.create(Constants.loc(id + "_tall_stalagmite_upper"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);

        this.blockStateOutput.accept(shortSpelothem(bundle.stalactite, stalactite));
        this.blockStateOutput.accept(tallSpelothem(bundle.tallStalactite, tallStalactiteLower, tallStalactiteUpper));
        this.blockStateOutput.accept(shortSpelothem(bundle.stalagmite, stalagmite));
        this.blockStateOutput.accept(tallSpelothem(bundle.tallStalagmite, tallStalagmiteLower, tallStalagmiteUpper));
    }

    private MultiVariantGenerator shortSpelothem(Block block, ResourceLocation model) {
        return MultiVariantGenerator.multiVariant(block, BlockModelGeneratorsAccess.createRotatedVariants(model));
    }

    private MultiVariantGenerator tallSpelothem(Block block, ResourceLocation lower, ResourceLocation upper) {
        return MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(TallStalagmiteBlock.HALF)
                .select(DoubleBlockHalf.LOWER, List.of(BlockModelGeneratorsAccess.createRotatedVariants(lower)))
                .select(DoubleBlockHalf.UPPER, List.of(BlockModelGeneratorsAccess.createRotatedVariants(upper))));
    }

    private void pebble(RockpileBlock block) {
        ResourceLocation texture = TextureMapping.getBlockTexture(block.getBase());
        String blockId = "block/" + Registry.BLOCK.getKey(block).getPath();

        ResourceLocation pebbleModel = PEBBLE.create(Constants.loc(blockId + "_pebble"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation smallModel = SMALL_PILE.create(Constants.loc(blockId + "_small_pile"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);
        ResourceLocation bigModel = BIG_PILE.create(Constants.loc(blockId + "_big_pile"), TextureMapping.defaultTexture(texture).put(TextureSlot.PARTICLE, texture), this.modelOutput);

        this.blockStateOutput.accept(MultiVariantGenerator
            .multiVariant(block)
            .with(PropertyDispatch
                .property(RockpileBlock.PEBBLES)
                .select(1, List.of(BlockModelGeneratorsAccess.createRotatedVariants(pebbleModel)))
                .select(2, List.of(BlockModelGeneratorsAccess.createRotatedVariants(smallModel)))
                .select(3, List.of(BlockModelGeneratorsAccess.createRotatedVariants(bigModel)))));
    }

    private void stairs(Block stairs, Block baseBlock) {
        ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(stairs, allSides(baseBlock), this.modelOutput);
        ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, allSides(baseBlock), this.modelOutput);
        ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(stairs, allSides(baseBlock), this.modelOutput);

        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createStairs(stairs, inner, straight, outer));
        delegateItemModel(stairs, straight);
    }

    private void slab(Block block, Block baseBlock, ResourceLocation base) {
        ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(block, allSides(baseBlock), this.modelOutput);
        ResourceLocation top = ModelTemplates.SLAB_TOP.create(block, allSides(baseBlock), this.modelOutput);

        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createSlab(block, bottom, top, base));
        delegateItemModel(block, bottom);
    }

    private void wall(Block block, Block baseBlock) {
        TextureMapping textureMapping = (new TextureMapping()).put(TextureSlot.WALL, TextureMapping.getBlockTexture(baseBlock));
        ResourceLocation post = ModelTemplates.WALL_POST.create(block, textureMapping, this.modelOutput);
        ResourceLocation lowSide = ModelTemplates.WALL_LOW_SIDE.create(block, textureMapping, this.modelOutput);
        ResourceLocation tallSide = ModelTemplates.WALL_TALL_SIDE.create(block, textureMapping, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createWall(block, post, lowSide, tallSide));
        ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(block, textureMapping, this.modelOutput);
        delegateItemModel(block, inventory);
    }

    private void pressurePlate(Block block, Block baseBlock) {
        TextureMapping textureMapping = TextureMapping.defaultTexture(baseBlock);
        ResourceLocation up = ModelTemplates.PRESSURE_PLATE_UP.create(block, textureMapping, this.modelOutput);
        ResourceLocation down = ModelTemplates.PRESSURE_PLATE_DOWN.create(block, textureMapping, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createPressurePlate(block, up, down));
    }

    private void button(Block block, Block baseBlock) {
        ResourceLocation button = ModelTemplates.BUTTON.create(block, TextureMapping.defaultTexture(baseBlock), this.modelOutput);
        ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.create(block, TextureMapping.defaultTexture(baseBlock), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createButton(block, button, pressed));
        ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.create(block, TextureMapping.defaultTexture(baseBlock), this.modelOutput);
        delegateItemModel(block, inventory);
    }

    private TextureMapping allSides(Block block) {
        return (new TextureMapping())
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
            .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block));
    }

    private void createCrossBlock(Block block) {
        ResourceLocation resourcelocation = ModelTemplates.CROSS.create(block, TextureMapping.cross(block), this.modelOutput);
        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createSimpleBlock(block, resourcelocation));
    }

    private void copyBlock(Block block, ResourceLocation model) {
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)));
        this.modelOutput.accept(ModelLocationUtils.getModelLocation(block.asItem()), new DelegatedModel(model));
    }

    private void createTrivialCube(Block block) {
        createTrivialBlock(block, TexturedModel.CUBE);
    }

    private void createTrivialBlock(Block block, TexturedModel.Provider texturedModel) {
        this.blockStateOutput.accept(BlockModelGeneratorsAccess.createSimpleBlock(block, texturedModel.create(block, this.modelOutput)));
    }

    void delegateItemModel(Block block, ResourceLocation model) {
        this.modelOutput.accept(ModelLocationUtils.getModelLocation(block.asItem()), new DelegatedModel(model));
    }

    // endregion
}
