package com.cavetale.mytems.entity.component;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;

/**
 * This class implements a common method for storing additional information
 * on an entity.
 * Currently, we employ Metadata for this purpose.
 */
@RequiredArgsConstructor
public final class EntityComponentTag {
    private final UUID uuid;
    private final Map<String, EntityComponent> componentMap = new IdentityHashMap<>();
    private static final String KEY = "mytems:entity_component_tag";

    /**
     * Get the tag or null.
     */
    public static EntityComponentTag get(Entity entity) {
        if (entity == null) return null;
        for (MetadataValue value : entity.getMetadata(KEY)) {
            if (value.getOwningPlugin() == mytemsPlugin() && value.value() instanceof EntityComponentTag tag) {
                return tag;
            }
        }
        return null;
    }

    public static boolean applyIf(Entity entity, Consumer<EntityComponentTag> callback) {
        if (entity == null) return false;
        final EntityComponentTag tag = get(entity);
        if (tag == null) {
            return false;
        } else {
            callback.accept(tag);
            return true;
        }
    }

    private void attach(Entity entity) {
        entity.setMetadata(KEY, new FixedMetadataValue(mytemsPlugin(), this));
    }

    /**
     * Get the tag or create one if necessary.
     */
    public static EntityComponentTag of(Entity entity) {
        EntityComponentTag result = get(entity);
        if (result != null) return result;
        result = new EntityComponentTag(entity.getUniqueId());
        result.attach(entity);
        return result;
    }

    public <T extends EntityComponent> T getComponent(Class<T> componentClass) {
        final Object o = componentMap.get(componentClass.getName());
        return componentClass.isInstance(o)
            ? componentClass.cast(o)
            : null;
    }

    public static <T extends EntityComponent> T getComponent(Entity entity, Class<T> componentClass) {
        final EntityComponentTag tag = get(entity);
        if (tag == null) return null;
        return tag.getComponent(componentClass);
    }

    public void setComponent(EntityComponent component) {
        componentMap.put(component.getClass().getName(), component);
    }

    public static void setComponent(Entity entity, EntityComponent component) {
        of(entity).setComponent(component);
    }

    public <T extends EntityComponent> T componentOf(Class<T> componentClass, Supplier<T> ctor) {
        Object o = componentMap.get(componentClass.getName());
        if (o != null && componentClass.isInstance(o)) return componentClass.cast(o);
        T result = ctor.get();
        componentMap.put(componentClass.getName(), result);
        return result;
    }

    public static <T extends EntityComponent> T componentOf(Entity entity, Class<T> componentClass, Supplier<T> ctor) {
        return of(entity).componentOf(componentClass, ctor);
    }

    public <T extends EntityComponent> boolean applyIf(Class<T> componentClass, Consumer<T> callback) {
        final T component = getComponent(componentClass);
        if (component == null) {
            return false;
        } else {
            callback.accept(component);
            return true;
        }
    }

    public static <T extends EntityComponent> boolean applyIf(Entity entity, Class<T> componentClass, Consumer<T> callback) {
        final EntityComponentTag tag = get(entity);
        if (tag == null) return false;
        return tag.applyIf(componentClass, callback);
    }
}
