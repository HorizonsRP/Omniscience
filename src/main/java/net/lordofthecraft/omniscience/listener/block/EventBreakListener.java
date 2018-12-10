package net.lordofthecraft.omniscience.listener.block;

import com.google.common.collect.ImmutableList;
import net.lordofthecraft.omniscience.Omniscience;
import net.lordofthecraft.omniscience.api.data.BlockTransaction;
import net.lordofthecraft.omniscience.api.entry.OEntry;
import net.lordofthecraft.omniscience.listener.OmniListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.UUID;

public class EventBreakListener extends OmniListener {

    public EventBreakListener() {
        super(ImmutableList.of("break"));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        OEntry.create().source(event.getPlayer()).brokeBlock(BlockTransaction.from(event.getBlock().getLocation(), event.getBlock().getState(), null)).save();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockExplode(BlockExplodeEvent event) {
        if (event.getBlock().hasMetadata("player-source")) {
            List<MetadataValue> metadataValues = event.getBlock().getMetadata("player-source");
            for (MetadataValue value : metadataValues) {
                if (value.getOwningPlugin() instanceof Omniscience) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(value.asString()));
                    if (player != null) {
                        event.blockList().forEach(block -> OEntry.create().source(player).brokeBlock(BlockTransaction.from(event.getBlock().getLocation(), block.getState(), null)).save());
                        return;
                    }
                }
            }
        } else {
            event.blockList().forEach(block -> OEntry.create().environment().brokeBlock(BlockTransaction.from(event.getBlock().getLocation(), block.getState(), null)).save());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBurn(BlockBurnEvent event) {
        OEntry.create().environment().brokeBlock(BlockTransaction.from(event.getBlock().getLocation(), event.getBlock().getState(), null)).save();
    }
}