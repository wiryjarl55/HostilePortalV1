package benjamin.boswell.hostileportal;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HostilePortal extends JavaPlugin implements Listener {
    private final Map<Entity, Boolean> canTeleportMap = new HashMap<>();
    private final Set<EntityType> hostileMobs = new HashSet<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        initializeHostileMobs();
    }

    private void initializeHostileMobs() {
        // Overworld Hostile Mobs
        hostileMobs.add(EntityType.ZOMBIE);
        hostileMobs.add(EntityType.SKELETON);
        hostileMobs.add(EntityType.CREEPER);
        hostileMobs.add(EntityType.SPIDER);
        hostileMobs.add(EntityType.CAVE_SPIDER);
        hostileMobs.add(EntityType.ENDERMAN);
        hostileMobs.add(EntityType.SILVERFISH);
        hostileMobs.add(EntityType.WITCH);
        hostileMobs.add(EntityType.SLIME);
        hostileMobs.add(EntityType.DROWNED); // Added drowned
        hostileMobs.add(EntityType.ELDER_GUARDIAN); // Added elder guardian
        hostileMobs.add(EntityType.GUARDIAN); // Added guardian
        hostileMobs.add(EntityType.PILLAGER); // Added pillager
        hostileMobs.add(EntityType.VINDICATOR); // Added vindicator
        hostileMobs.add(EntityType.EVOKER); // Added evoker

        // Nether Hostile Mobs
        hostileMobs.add(EntityType.ZOMBIFIED_PIGLIN);
        hostileMobs.add(EntityType.GHAST);
        hostileMobs.add(EntityType.MAGMA_CUBE);
        hostileMobs.add(EntityType.BLAZE);
        hostileMobs.add(EntityType.WITHER_SKELETON);
        hostileMobs.add(EntityType.HOGLIN);
        hostileMobs.add(EntityType.PIGLIN); // Aggressive in certain versions
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onEntityPortalEnter(EntityPortalEnterEvent event) {
        Entity entity = event.getEntity();

        // Check if the entity is a hostile mob
        if (isHostileMob(entity.getType())) {
            // Set a flag to indicate that the hostile mob should not teleport
            canTeleportMap.put(entity, false);
        } else {
            // For non-hostile mobs and players, set the flag to allow teleportation
            canTeleportMap.put(entity, true);
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        Entity entity = event.getEntity();

        // Check if the entity is a hostile mob and should not teleport
        if (isHostileMob(entity.getType()) && !canTeleportMap.getOrDefault(entity, true)) {
            event.setCancelled(true);
        }
    }

    private boolean isHostileMob(EntityType entityType) {
        return hostileMobs.contains(entityType);
    }
}
