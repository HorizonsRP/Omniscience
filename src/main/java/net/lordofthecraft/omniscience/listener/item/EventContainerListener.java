package net.lordofthecraft.omniscience.listener.item;

import com.google.common.collect.ImmutableList;
import net.lordofthecraft.omniscience.api.entry.OEntry;
import net.lordofthecraft.omniscience.listener.OmniListener;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class EventContainerListener extends OmniListener {

    public EventContainerListener() {
        super(ImmutableList.of("open", "close"));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (isEnabled("open")
                && event.getInventory().getHolder() instanceof Container) {
            OEntry.create().source(event.getPlayer()).opened((Container) event.getInventory().getHolder()).save();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (isEnabled("close")
                && event.getInventory().getHolder() instanceof Container) {
            OEntry.create().source(event.getPlayer()).closed((Container) event.getInventory().getHolder()).save();
        }
    }
}
