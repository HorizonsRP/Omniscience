package net.lordofthecraft.omniscience.listener.entity;

import com.google.common.collect.ImmutableList;
import net.lordofthecraft.omniscience.api.entry.OEntry;
import net.lordofthecraft.omniscience.listener.OmniListener;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EventHitListener extends OmniListener {

    public EventHitListener() {
        super(ImmutableList.of("hit", "shot"));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (isEnabled("shot")
                && e.getDamager() instanceof Projectile
                && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
            OEntry.create().source(e.getDamager()).shot(e.getEntity()).save();
            return;
        }
        if (isEnabled("hit")
                && (e.getDamager() instanceof Player
                || e.getEntity() instanceof Player)) {
            OEntry.create().source(e.getDamager()).hit(e.getEntity()).save();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamagedByBlock(EntityDamageByBlockEvent e) {
        if (isEnabled("hit")) {
            OEntry.create().source(e.getDamager()).hit(e.getEntity()).save();
        }
    }
}
