package dev.nerdthings.expandedcaves.data;

import com.google.gson.JsonElement;
import dev.nerdthings.expandedcaves.Constants;
import dev.nerdthings.expandedcaves.common.blocks.ModBlocks;
import dev.nerdthings.expandedcaves.common.blocks.rock.RockpileBlock;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.SpelothemBundle;
import dev.nerdthings.expandedcaves.common.blocks.spelothem.TallStalagmiteBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;

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
            this.blockStateOutput.accept(createSimpleBlock(family.getBaseBlock(), baseModel));

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
        this.blockStateOutput.accept(createRotatedVariant(ModBlocks.PEBBLE_FLINT, Constants.loc("block/rock_flint")));
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
        return MultiVariantGenerator.multiVariant(block, createRotatedVariants(model));
    }

    private MultiVariantGenerator tallSpelothem(Block block, ResourceLocation lower, ResourceLocation upper) {
        return MultiVariantGenerator.multiVariant(block)
            .with(PropertyDispatch.property(TallStalagmiteBlock.HALF)
                .select(DoubleBlockHalf.LOWER, List.of(createRotatedVariants(lower)))
                .select(DoubleBlockHalf.UPPER, List.of(createRotatedVariants(upper))));
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
                .select(1, List.of(createRotatedVariants(pebbleModel)))
                .select(2, List.of(createRotatedVariants(smallModel)))
                .select(3, List.of(createRotatedVariants(bigModel)))));
    }

    private void stairs(Block stairs, Block baseBlock) {
        ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(stairs, allSides(baseBlock), this.modelOutput);
        ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, allSides(baseBlock), this.modelOutput);
        ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(stairs, allSides(baseBlock), this.modelOutput);

        this.blockStateOutput.accept(createStairs(stairs, inner, straight, outer));
        delegateItemModel(stairs, straight);
    }

    private void slab(Block block, Block baseBlock, ResourceLocation base) {
        ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(block, allSides(baseBlock), this.modelOutput);
        ResourceLocation top = ModelTemplates.SLAB_TOP.create(block, allSides(baseBlock), this.modelOutput);

        this.blockStateOutput.accept(createSlab(block, bottom, top, base));
        delegateItemModel(block, bottom);
    }

    private void wall(Block block, Block baseBlock) {
        TextureMapping textureMapping = (new TextureMapping()).put(TextureSlot.WALL, TextureMapping.getBlockTexture(baseBlock));
        ResourceLocation post = ModelTemplates.WALL_POST.create(block, textureMapping, this.modelOutput);
        ResourceLocation lowSide = ModelTemplates.WALL_LOW_SIDE.create(block, textureMapping, this.modelOutput);
        ResourceLocation tallSide = ModelTemplates.WALL_TALL_SIDE.create(block, textureMapping, this.modelOutput);
        this.blockStateOutput.accept(createWall(block, post, lowSide, tallSide));
        ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.create(block, textureMapping, this.modelOutput);
        delegateItemModel(block, inventory);
    }

    // TODO: Implement accessor mixins and tidy this all to fuck?
    static BlockStateGenerator createWall(Block block, ResourceLocation post, ResourceLocation lowSide, ResourceLocation tallSide) {
        return MultiPartGenerator.multiPart(block).with(Condition.condition().term(BlockStateProperties.UP, true), Variant.variant().with(VariantProperties.MODEL, post)).with(Condition.condition().term(BlockStateProperties.NORTH_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, lowSide).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.EAST_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, lowSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.SOUTH_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, lowSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.WEST_WALL, WallSide.LOW), Variant.variant().with(VariantProperties.MODEL, lowSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.NORTH_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, tallSide).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.EAST_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, tallSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.SOUTH_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, tallSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).with(Condition.condition().term(BlockStateProperties.WEST_WALL, WallSide.TALL), Variant.variant().with(VariantProperties.MODEL, tallSide).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true));
    }

    private void pressurePlate(Block block, Block baseBlock) {
        TextureMapping textureMapping = TextureMapping.defaultTexture(baseBlock);
        ResourceLocation up = ModelTemplates.PRESSURE_PLATE_UP.create(block, textureMapping, this.modelOutput);
        ResourceLocation down = ModelTemplates.PRESSURE_PLATE_DOWN.create(block, textureMapping, this.modelOutput);
        this.blockStateOutput.accept(createPressurePlate(block, up, down));
    }

    static BlockStateGenerator createPressurePlate(Block p_124942_, ResourceLocation p_124943_, ResourceLocation p_124944_) {
        return MultiVariantGenerator.multiVariant(p_124942_).with(createBooleanModelDispatch(BlockStateProperties.POWERED, p_124944_, p_124943_));
    }

    private static PropertyDispatch createBooleanModelDispatch(BooleanProperty p_124623_, ResourceLocation p_124624_, ResourceLocation p_124625_) {
        return PropertyDispatch.property(p_124623_).select(true, Variant.variant().with(VariantProperties.MODEL, p_124624_)).select(false, Variant.variant().with(VariantProperties.MODEL, p_124625_));
    }

    private void button(Block block, Block baseBlock) {
        ResourceLocation button = ModelTemplates.BUTTON.create(block, TextureMapping.defaultTexture(baseBlock), this.modelOutput);
        ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.create(block, TextureMapping.defaultTexture(baseBlock), this.modelOutput);
        this.blockStateOutput.accept(createButton(block, button, pressed));
        ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.create(block, TextureMapping.defaultTexture(baseBlock), this.modelOutput);
        delegateItemModel(block, inventory);
    }

    static BlockStateGenerator createButton(Block block, ResourceLocation modelDepressed, ResourceLocation modelPressed) {
        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.POWERED).select(false, Variant.variant().with(VariantProperties.MODEL, modelDepressed)).select(true, Variant.variant().with(VariantProperties.MODEL, modelPressed))).with(PropertyDispatch.properties(BlockStateProperties.ATTACH_FACE, BlockStateProperties.HORIZONTAL_FACING).select(AttachFace.FLOOR, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(AttachFace.FLOOR, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)).select(AttachFace.FLOOR, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(AttachFace.FLOOR, Direction.NORTH, Variant.variant()).select(AttachFace.WALL, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.WALL, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.WALL, Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.WALL, Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(AttachFace.CEILING, Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(AttachFace.CEILING, Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(AttachFace.CEILING, Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(AttachFace.CEILING, Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)));
    }

    private TextureMapping allSides(Block block) {
        return (new TextureMapping())
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
            .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block));
    }

    private MultiVariantGenerator createStairs(Block block, ResourceLocation inner, ResourceLocation straight, ResourceLocation outer) {
        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE).select(
            Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight)).select(Direction.WEST, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.BOTTOM, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer)).select(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer)).select(Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner)).select(Direction.WEST, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner)).select(Direction.NORTH, Half.BOTTOM, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straight).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outer).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.EAST, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Half.TOP, StairsShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, inner).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.UV_LOCK, true)));
    }

    private BlockStateGenerator createSlab(Block p_124929_, ResourceLocation p_124930_, ResourceLocation p_124931_, ResourceLocation p_124932_) {
        return MultiVariantGenerator
            .multiVariant(p_124929_)
            .with(PropertyDispatch
                .property(BlockStateProperties.SLAB_TYPE)
                .select(SlabType.BOTTOM, Variant.variant().with(VariantProperties.MODEL, p_124930_))
                .select(SlabType.TOP, Variant.variant().with(VariantProperties.MODEL, p_124931_))
                .select(SlabType.DOUBLE, Variant.variant().with(VariantProperties.MODEL, p_124932_)));
    }

    private void createCrossBlock(Block block) {
        ResourceLocation resourcelocation = ModelTemplates.CROSS.create(block, TextureMapping.cross(block), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    private static MultiVariantGenerator createRotatedVariant(Block block, ResourceLocation model) {
        return MultiVariantGenerator.multiVariant(block, createRotatedVariants(model));
    }

    private static Variant[] createRotatedVariants(ResourceLocation model) {
        return new Variant[]{Variant.variant().with(VariantProperties.MODEL, model), Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)};
    }

    private void copyBlock(Block block, ResourceLocation model) {
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)));
        this.modelOutput.accept(ModelLocationUtils.getModelLocation(block.asItem()), new DelegatedModel(model));
    }

    private void createTrivialCube(Block block) {
        createTrivialBlock(block, TexturedModel.CUBE);
    }

    private void createTrivialBlock(Block block, TexturedModel.Provider texturedModel) {
        this.blockStateOutput.accept(createSimpleBlock(block, texturedModel.create(block, this.modelOutput)));
    }

    private MultiVariantGenerator createSimpleBlock(Block block, ResourceLocation model) {
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model));
    }

    void delegateItemModel(Block block, ResourceLocation model) {
        this.modelOutput.accept(ModelLocationUtils.getModelLocation(block.asItem()), new DelegatedModel(model));
    }

    // endregion
}
