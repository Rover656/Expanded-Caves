package dev.nerdthings.expandedcaves.common.config;

public class ConfigDefaults {
    public enum Ores {
        BROKEN_STONE(24, 12),
        BROKEN_DEEPSLATE(24, 12),
        SEDIMENT_STONE(54, 8),
        LAVASTONE(48, 12),
        DIRTSTONE(64, 5),
        MARLSTONE(48, 5),
        PACKED_ICE(64, 5);

        // TODO: Maybe enabled/disabled flags here too

        public final int veinSize;
        public final int count;

        Ores(int veinSize, int count) {
            this.veinSize = veinSize;
            this.count = count;
        }

        public String getEnabledComment() {
            return "Whether this feature generates.";
        }

        public String getVeinSizeComment() {
            return String.format("The maximum size of each vein. (default = %d)", veinSize);
        }

        public String getCountComment() {
            return String.format("The maximum number of this feature per chunk. (default = %d)", count);
        }
    }

    public enum Patches {
        FLINT_PEBBLE(12),
        PEBBLES(12),
        SURFACE_PEBBLES(4),
        ROCKPILES(12),
        STALAGMITES(16),
        STALACTITES(16),

        SWEETSHROOM(32),
        GOLDISHROOM(16),
        SHINYSHROOM(24),
        LUMISHROOM(32),
        FLUOSHROOM(32),
        ROCKSHROOM(12),

        DRY_MOSS(54),
        FIRE_MOSS(24),
        FROZEN_MOSS(32),
        HANGING_ROOTS(32),
        HUMID_MOSS(32),

        CAVE_VINE(54);

        public final int count;

        Patches(int count) {
            this.count = count;
        }

        // TODO: Maybe enabled/disabled flags here too

        public String getEnabledComment() {
            return "Whether this feature generates.";
        }

        public String getCountComment() {
            return String.format("The maximum number of " + this.name().toLowerCase().replaceAll("_", " ") + " per chunk. (default = %d)", count);
        }
    }
}
