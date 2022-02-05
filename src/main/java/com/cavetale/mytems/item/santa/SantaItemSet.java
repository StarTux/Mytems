package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.EntityAttribute;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Getter
public final class SantaItemSet implements ItemSet {
    private static SantaItemSet instance = null;
    private final String name = "Santa Set";
    List<SetBonus> setBonuses;

    @Getter
    public static final class CookieBonus implements SetBonus {
        protected final int requiredItemCount = 2;
        protected final String name = "Cookie Bonus";
        protected final String description = "Eating cookies gives extra health";

        @Override
        public void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
            if (item.getType() == Material.COOKIE) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 120, 1, true, false, true));
            }
        }
    }

    @Getter
    public static final class MilkBonus implements SetBonus {
        protected final int requiredItemCount = 3;
        protected final String name = "Milk Bonus";
        protected final String description = "Drinking milk gives absorption";

        @Override
        public void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
            if (item.getType() == Material.MILK_BUCKET) {
                Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 120, 1, true, false, true));
                    });
            }
        }
    }

    @Getter
    public static final class BonusHealth implements SetBonus {
        protected final int requiredItemCount = 4;
        protected final String name = "Bonus Health";
        protected final String description = "Get 10% more health";
        protected final List<EntityAttribute> entityAttributes = Arrays
            .asList(new EntityAttribute(Attribute.GENERIC_MAX_HEALTH,
                                        UUID.fromString("60fbcfa6-eea5-4953-91a6-1e50905c9665"),
                                        "SantaHealth",
                                        0.1,
                                        Operation.ADD_SCALAR));

        @Override
        public List<EntityAttribute> getEntityAttributes(LivingEntity living) {
            return entityAttributes;
        }
    }

    public static SantaItemSet getInstance() {
        if (instance == null) instance = new SantaItemSet();
        return instance;
    }

    private SantaItemSet() {
        setBonuses = Arrays.asList(new CookieBonus(), new MilkBonus(), new BonusHealth());
    }
}
