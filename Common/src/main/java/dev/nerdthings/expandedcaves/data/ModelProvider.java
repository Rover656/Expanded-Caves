package dev.nerdthings.expandedcaves.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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

        // Collect all models
        Map<ResourceLocation, Supplier<JsonElement>> models = Maps.newHashMap();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> itemConsumer = (p_125123_, p_125124_) -> {
            Supplier<JsonElement> supplier = models.put(p_125123_, p_125124_);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + p_125123_);
            }
        };

        // Generate item models
        (new ItemModelGenerator(itemConsumer)).run();

        // Save models and block states
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

    private static Path createModelPath(Path p_125113_, ResourceLocation p_125114_) {
        return p_125113_.resolve("assets/" + p_125114_.getNamespace() + "/models/" + p_125114_.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Common Model Provider";
    }
}
