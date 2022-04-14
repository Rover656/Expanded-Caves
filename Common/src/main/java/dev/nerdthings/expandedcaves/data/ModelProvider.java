package dev.nerdthings.expandedcaves.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import dev.nerdthings.expandedcaves.Constants;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModelProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public ModelProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(HashCache hashCache) throws IOException {
        Path path = this.generator.getOutputFolder();

        // Block states
        Map<Block, BlockStateGenerator> blockStates = Maps.newHashMap();
        Consumer<BlockStateGenerator> stateConsumer = (p_125120_) -> {
            Block block = p_125120_.getBlock();
            BlockStateGenerator blockstategenerator = blockStates.put(block, p_125120_);
            if (blockstategenerator != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + block);
            }
        };

        // Collect all models
        Map<ResourceLocation, Supplier<JsonElement>> models = Maps.newHashMap();
        Set<Item> ignoredItems = Sets.newHashSet();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> modelConsumer = (p_125123_, p_125124_) -> {
            Supplier<JsonElement> supplier = models.put(p_125123_, p_125124_);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + p_125123_);
            }
        };

        // Generate item models
        (new ModBlockModelGenerators(stateConsumer, modelConsumer, ignoredItems::add)).run();
        (new ItemModelGenerator(modelConsumer)).run();

        // Generate delegate models automatically.
        Registry.BLOCK.stream().filter(block -> Registry.BLOCK.getKey(block).getNamespace().equals(Constants.MOD_ID)).forEach((p_125128_) -> {
            Item item = Item.BY_BLOCK.get(p_125128_);
            if (item != null) {
                if (ignoredItems.contains(item)) {
                    return;
                }

                ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
                if (!models.containsKey(resourcelocation)) {
                    models.put(resourcelocation, new DelegatedModel(ModelLocationUtils.getModelLocation(p_125128_)));
                }
            }

        });

        // Save models and block states
        this.saveCollection(hashCache, path, blockStates, ModelProvider::createBlockStatePath);
        this.saveCollection(hashCache, path, models, ModelProvider::createModelPath);
    }

    private <T> void saveCollection(HashCache hashCache, Path path, Map<T, ? extends Supplier<JsonElement>> models, BiFunction<Path, T, Path> pathFunction) {
        models.forEach((id, json) -> {
            Path modelPath = pathFunction.apply(path, id);

            try {
                DataProvider.save(GSON, hashCache, json.get(), modelPath);
            } catch (Exception exception) {
                LOGGER.error("Couldn't save {}", modelPath, exception);
            }

        });
    }

    private static Path createBlockStatePath(Path p_125110_, Block p_125111_) {
        ResourceLocation resourcelocation = Registry.BLOCK.getKey(p_125111_);
        return p_125110_.resolve("assets/" + resourcelocation.getNamespace() + "/blockstates/" + resourcelocation.getPath() + ".json");
    }

    private static Path createModelPath(Path p_125113_, ResourceLocation p_125114_) {
        return p_125113_.resolve("assets/" + p_125114_.getNamespace() + "/models/" + p_125114_.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Common Model Provider";
    }
}
