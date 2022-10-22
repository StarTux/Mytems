package com.cavetale.mytems.item.pocketmob;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.util.BlockColor;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Json;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Raider;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Steerable;
import org.bukkit.entity.Strider;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Colorable;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * The data stored in a PocketMob item.
 */
@Data @EqualsAndHashCode(callSuper = true)
public final class PocketMobTag extends MytemTag {
    public static final NamespacedKey KEY_MOB_TAG = NamespacedKey.fromString("pocketmob:mob_tag");
    public static final NamespacedKey KEY_MOB = NamespacedKey.fromString("pocketmob:mob");
    public static final TextColor COLOR_FG = TextColor.color(0xffa600); // gold
    public static final TextColor COLOR_BG = TextColor.color(0x3f274d); // pale purle
    protected String mobTag; // Legacy, Dirty
    protected String mob; // UnsafeValues#serializeEntity, Base64

    @Override
    public boolean isEmpty() {
        return mobTag == null && mob == null && super.isEmpty();
    }

    public void load(ItemStack itemStack, PocketMob pocketMob) {
        super.load(itemStack);
        if (!itemStack.hasItemMeta()) return;
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        mobTag = Tags.getString(tag, KEY_MOB_TAG);
        mob = Tags.getString(tag, KEY_MOB);
    }

    private static Component prop(String key, String value) {
        return Component.join(JoinConfiguration.noSeparators(),
                              Component.text(key + " ", COLOR_BG),
                              Component.text(value, COLOR_FG));
    }

    private static Component prop(String key, Component value) {
        return Component.join(JoinConfiguration.noSeparators(),
                              Component.text(key + " ", COLOR_BG),
                              value);
    }

    private static Component prop(String key, BlockColor blockColor) {
        return Component.join(JoinConfiguration.noSeparators(),
                              Component.text(key + " ", COLOR_BG),
                              Component.text(blockColor.niceName, blockColor.textColor));
    }

    /**
     * This method stores this tag in the item and updates its lore
     * accordingly.
     */
    public void store(ItemStack itemStack, PocketMob pocketMob) {
        super.store(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        if (mobTag != null) {
            Tags.set(tag, KEY_MOB_TAG, mobTag);
        }
        List<Component> text = new ArrayList<>();
        text.add(null);
        if (mob != null) {
            Tags.set(tag, KEY_MOB, mob);
            Entity entity = deserializeMob(Bukkit.getWorlds().get(0));
            if (entity != null) {
                Component customName = entity.customName();
                text.set(0, customName);
            }
            if (entity instanceof Mob mobEntity) {
                Component customName = mobEntity.customName();
                if (customName != null && !Component.empty().equals(customName)) {
                    text.add(prop("Name", customName));
                }
                String health = "" + (int) Math.ceil(mobEntity.getHealth());
                String maxHealth = "" + (int) Math.ceil(mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                text.add(prop("Health", Component.join(JoinConfiguration.noSeparators(),
                                                       Component.text(Unicode.HEART.string + health, NamedTextColor.RED),
                                                       Component.text("/", COLOR_BG),
                                                       Component.text(maxHealth, NamedTextColor.RED))));
                if (mobEntity.getRemoveWhenFarAway() || !mobEntity.isPersistent()) {
                    text.add(prop("Warning", Component.text("May Despawn!", TextColor.color(0xFF0000))));
                }
            }
            List<String> nameComponents = new ArrayList<>();
            String finalNameComponent = Text.toCamelCase(entity.getType());
            if (entity instanceof Ageable ageable) {
                if (!ageable.isAdult()) nameComponents.add("Baby");
            }
            if (entity instanceof Creeper creeper) {
                if (creeper.isPowered()) {
                    nameComponents.add(0, "Powered");
                }
            } else if (entity instanceof Wither wither) {
                if (wither.isCharged()) {
                    nameComponents.add(0, "Charged");
                }
            } else if (entity instanceof AbstractHorse ahorse) {
                if (ahorse instanceof Horse horse) {
                    nameComponents.add(Text.toCamelCase(horse.getColor()));
                    nameComponents.add(Text.toCamelCase(horse.getStyle()));
                }
                if (ahorse instanceof Llama llama) {
                    nameComponents.add(Text.toCamelCase(llama.getColor()));
                    text.add(prop("Strength", "" + llama.getStrength()));
                }
                text.add(prop("Jump", "" + (int) Math.round(100.0 * ahorse.getJumpStrength())));
                text.add(prop("Speed", "" + (int) Math.round(100.0 * ahorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue())));
            } else if (entity instanceof Colorable colorable) { // Sheep, Shulker
                DyeColor dyeColor = colorable.getColor();
                if (dyeColor != null) {
                    BlockColor blockColor = BlockColor.of(dyeColor);
                    text.add(prop("Color", blockColor));
                    nameComponents.add(0, blockColor.niceName);
                }
            } else if (entity instanceof Cat cat) {
                nameComponents.add(Text.toCamelCase(cat.getCatType()));
                text.add(prop("Collar", BlockColor.of(cat.getCollarColor())));
            } else if (entity instanceof Parrot parrot) {
                nameComponents.add(Text.toCamelCase(parrot.getVariant()));
            } else if (entity instanceof Frog frog) {
                nameComponents.add(Text.toCamelCase(frog.getVariant()));
            } else if (entity instanceof Wolf wolf) {
                if (wolf.isAngry()) {
                    nameComponents.add(0, "Angry");
                }
                text.add(prop("Collar", BlockColor.of(wolf.getCollarColor())));
            } else if (entity instanceof Bee bee) {
                if (bee.getAnger() > 0) {
                    nameComponents.add(0, "Angry");
                }
            } else if (entity instanceof PufferFish pufferfish) {
                if (pufferfish.getPuffState() > 0) {
                    nameComponents.add(0, "Inflated");
                }
            } else if (entity instanceof Fox fox) {
                nameComponents.add(Text.toCamelCase(fox.getFoxType()));
                if (fox.isSleeping()) {
                    nameComponents.add(0, "Sleeping");
                }
            } else if (entity instanceof Rabbit rabbit) {
                nameComponents.add(Text.toCamelCase(rabbit.getRabbitType()));
                if (rabbit.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY) {
                    finalNameComponent = null;
                }
            } else if (entity instanceof Steerable steerable) { // Pig, Strider
                if (steerable instanceof Strider strider) {
                    if (strider.isShivering()) {
                        nameComponents.add(0, "Shivering");
                    }
                }
                if (steerable.hasSaddle()) {
                    nameComponents.add(0, "Saddled");
                }
            } else if (entity instanceof TropicalFish tropicalFish) {
                nameComponents.add(Text.toCamelCase(tropicalFish.getBodyColor()));
                finalNameComponent = Text.toCamelCase(tropicalFish.getPattern());
            } else if (entity instanceof Villager villager) {
                if (villager.getProfession() != Villager.Profession.NONE) {
                    finalNameComponent = Text.toCamelCase(villager.getProfession());
                }
                nameComponents.add(Text.toCamelCase(villager.getVillagerType()));
                text.add(prop("Level", "" + villager.getVillagerLevel()));
            } else if (entity instanceof ZombieVillager zombieVillager) {
                finalNameComponent = Text.toCamelCase(zombieVillager.getVillagerProfession());
                nameComponents.add(Text.toCamelCase(zombieVillager.getVillagerType()));
            } else if (entity instanceof Slime slime) {
                text.add(prop("Size", "" + slime.getSize()));
            } else if (entity instanceof Raider raider) {
                if (raider.isPatrolLeader()) {
                    nameComponents.add("Chief");
                }
                if (raider instanceof Spellcaster spellcaster) {
                    if (spellcaster.getSpell() != Spellcaster.Spell.NONE) {
                        text.add(prop("Spell", Text.toCamelCase(spellcaster.getSpell())));
                    }
                }
            }
            if (entity instanceof Tameable tameable) {
                if (tameable.isTamed()) {
                    nameComponents.add(0, "Tamed");
                }
            }
            if (entity.isGlowing()) {
                nameComponents.add(0, "Glowing");
            }
            if (finalNameComponent != null) {
                nameComponents.add(finalNameComponent);
            }
            if (text.get(0) == null) {
                text.set(0, Component.text(String.join(" ", nameComponents), COLOR_FG));
            } else {
                text.add(1, Component.text(String.join(" ", nameComponents), COLOR_BG));
            }
        } else {
            Map<String, Object> map = parseMobTag();
            Object o;
            o = map.get("Health");
            if (o instanceof Number) {
                text.add(Component.join(JoinConfiguration.noSeparators(),
                                        Component.text("Health ", COLOR_BG),
                                        Component.text(((Number) o).intValue(), COLOR_FG)));
            }
        }
        Items.text(meta, text);
        itemStack.setItemMeta(meta);
    }

    @SuppressWarnings("deprecation")
    public Entity deserializeMob(World world) {
        if (mob == null) return null;
        final byte[] byteArray = Base64.getDecoder().decode(mob);
        return Bukkit.getUnsafe().deserializeEntity(byteArray, world, false);
    }

    @SuppressWarnings("deprecation")
    public void serializeMob(Entity entity) {
        final byte[] byteArray = Bukkit.getUnsafe().serializeEntity(entity);
        mob = Base64.getEncoder().encodeToString(byteArray);
    }

    @Deprecated
    public Map<String, Object> parseMobTag() {
        if (mobTag == null) return new HashMap<>();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) Json.deserialize(mobTag, Map.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public boolean isDismissable() {
        return false;
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof PocketMobTag that
            && Objects.equals(this.mobTag, that.mobTag)
            && Objects.equals(this.mob, that.mob);
    }
}
