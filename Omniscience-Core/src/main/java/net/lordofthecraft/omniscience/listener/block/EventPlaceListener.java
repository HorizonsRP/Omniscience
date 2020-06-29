package net.lordofthecraft.omniscience.listener.block;

import com.google.common.collect.ImmutableList;
import net.lordofthecraft.omniscience.api.data.InventoryTransaction;
import net.lordofthecraft.omniscience.api.data.LocationTransaction;
import net.lordofthecraft.omniscience.api.entry.OEntry;
import net.lordofthecraft.omniscience.listener.OmniListener;
import net.lordofthecraft.omniscience.listener.item.EventInventoryListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Lectern;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class EventPlaceListener extends OmniListener {

    public EventPlaceListener() {
        super(ImmutableList.of("place"));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        // Escape out because we don't want to log sign placements twice.
        if (event.getBlock().getState() instanceof Sign) {
            return;
        }

        if (event.getBlockPlaced().getType().equals(Material.LECTERN) && event.getBlockPlaced().getState() instanceof Lectern &&
            (event.getItemInHand().getType().equals(Material.WRITTEN_BOOK) || event.getItemInHand().getType().equals(Material.WRITABLE_BOOK)) &&
            isEnabled("deposit")) {
            Lectern lectern = (Lectern) event.getBlockPlaced().getState();
            EventInventoryListener.saveLecternTransaction(event.getPlayer(), event.getItemInHand(), lectern, InventoryTransaction.ActionType.DEPOSIT);
            return;
        }
        OEntry.create().source(event.getPlayer()).placedBlock(new LocationTransaction<>(event.getBlock().getLocation(), event.getBlockReplacedState(), event.getBlock().getState())).save();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        event.getReplacedBlockStates().stream()
                .filter(state -> !blockLocationsAreEqual(event.getBlock().getLocation(), state.getLocation()))
                .forEach(state ->
                        OEntry.create().source(event.getPlayer()).placedBlock(new LocationTransaction<>(state.getBlock().getLocation(), state, state.getBlock().getState())).save()
                );
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onSignChange(SignChangeEvent event) {
        Sign sign = (Sign) event.getBlock().getState();
        for (int i = 0; i <= 3; i++) {
            sign.setLine(i, event.getLine(i));
        }
        OEntry.create().source(event.getPlayer()).placedBlock(new LocationTransaction<>(event.getBlock().getLocation(), null, sign)).save();
    }

    private boolean blockLocationsAreEqual(Location locA, Location locB) {
        return locA.getBlockX() == locB.getBlockX() && locA.getBlockY() == locB.getBlockY() && locA.getBlockZ() == locB.getBlockZ();
    }
}
