package dev.nerdthings.expandedcaves;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "expcaves";
	public static final String MOD_NAME = "Expanded Caves";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation loc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

}