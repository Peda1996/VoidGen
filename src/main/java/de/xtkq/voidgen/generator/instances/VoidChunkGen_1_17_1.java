package de.xtkq.voidgen.generator.instances;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import de.xtkq.voidgen.VoidGen;
import de.xtkq.voidgen.generator.annotations.VoidChunkGenInfo;
import de.xtkq.voidgen.generator.interfaces.ChunkGen;
import de.xtkq.voidgen.generator.settings.ChunkGenSettings;
import de.xtkq.voidgen.utils.StringUtils;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@VoidChunkGenInfo(versions = {"1.17.1", "1.18", "1.18.1" ,"1.20", "1.20.1"})
public class VoidChunkGen_1_17_1 extends ChunkGen {

    public VoidChunkGen_1_17_1(JavaPlugin javaPlugin, String paramIdentifier) {
        super(javaPlugin);
        Gson gson = new Gson();

        if (StringUtils.isBlank(paramIdentifier)) {
            this.chunkGenSettings = new ChunkGenSettings();
            this.javaPlugin.getLogger().info("Generator settings have not been set. Using default values:");
        } else {
            try {
                this.chunkGenSettings = gson.fromJson(paramIdentifier, ChunkGenSettings.class);
            } catch (JsonSyntaxException jse) {
                this.chunkGenSettings = new ChunkGenSettings();
                this.javaPlugin.getLogger().info("Generator settings \"" + paramIdentifier + "\" syntax is not valid. Using default values:");
            }
        }
        // Posting the currently used chunkGenSettings to console.
        this.javaPlugin.getLogger().info(gson.toJson(chunkGenSettings));
    }

    @Override
    public BiomeProvider getDefaultBiomeProvider(WorldInfo worldInfo) {
        if (Objects.isNull(this.chunkGenSettings.getBiome())) {
            return null;
        } else {
            return new VoidBiomeProvider(this.chunkGenSettings.getBiome());
        }
    }

    private static class VoidBiomeProvider extends BiomeProvider {
        private final Biome biome;

        public VoidBiomeProvider(Biome paramBiome) {
            this.biome = paramBiome;
        }

        @Override
        public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
            return this.biome;
        }

        @Override
        public List<Biome> getBiomes(WorldInfo worldInfo) {
            return Collections.singletonList(this.biome);
        }
    }
}