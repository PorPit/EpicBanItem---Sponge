package com.github.euonmyoji.epicbanitem.listener;

import com.github.euonmyoji.epicbanitem.EpicBanItem;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Chunk;

/**
 * @author yinyangshi
 */
public class ChunkListener {
//    @Listener
//    public void onGenerateChunk(GenerateChunkEvent event) {
//        testChunk(event.getTargetChunk());
//    }
//
//    @Listener
//    public void onLoadChunk(LoadChunkEvent event) {
//        if (EpicBanItem.plugin.getSettings().isListeningLoadingChunk) testChunk(event.getTargetChunk());
//    }

    private void testChunk(Chunk chunk) {
        Task.builder().async().execute(() -> {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 256; y++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState blockState = chunk.getBlock(x, y, z);
                    }
                }
            }
        }).name("EpicBanItem - Test the chunk:" + chunk.getUniqueId()).submit(EpicBanItem.plugin);
    }
}
