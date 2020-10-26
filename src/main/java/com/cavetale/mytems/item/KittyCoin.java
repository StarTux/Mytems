package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import java.util.Base64;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class KittyCoin implements Mytem {
    public static final Mytems KEY = Mytems.KITTY_COIN;
    private final MytemsPlugin plugin;
    @Getter private BaseComponent[] displayName;
    private ItemStack prototype;

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        @SuppressWarnings("LineLength")
        final byte[] bytes = Base64.getDecoder().decode("H4sIAAAAAAAAAMWUTc+jVBSAma/Y6SQujG7Nm65MOrEtLX1538RFC6VAC5QWKGCMucDl88Jt+WgLk/kHszAxxvkLJu79K/4RExcupGZmNKNLk1me7+ece87tEsQj4hkLSmDAvIhwRhDdTzvEw8gjPkujDLo58Mv7AwI1zL8NIfC6xKMSBF3iIy8qruoO8VgGKSS+e9GDlzIHvfuvX/QcjLzefZlX8HkvKgGK3LdSlXkwR23i1u4DVLSqosyjBJZhjqsgfKfFjl8VLij/4ehihPPefS+4Zn/eK9t6rbSKyrK+YXCU9V5+807be/mUeLzGOewQBPGYeP1vujdJ3+K9Ef8XvhzUf/NtoQdhelOGUfEflMQPH44MlDfgRoK5G4KsfA/r+w+HVeKbQ3WFKuB7UD9+OCh4cVFVRCd4E0CQf/keWJf4ZFM5LcG8SpKoNACqYNEhPj7jHHkpyBOY37cX1U2uu/qt225BG9LdJRVCyjmD+dsj6jDgBNtW4DPioeBdN5f+5Y/fXv3Kaa9/93569QX+vA3b5PgA8zKCxVOic2Woclh0W+cHHeLJX6UfmLAWh2A/Qu54GzrmLFLixUhh3YusJbXEzi4SqxZCNq8d0j44S0O0GWEqxNLEir3UIq2RspQThVVJWVMpZWlN5HgbKloYS3FA2jGHhOgcubx4slNU2DoVOns9UiKRtUwbeUuusHdCm18+2CQVerxR24aIXNM4uKmRCPFh6GYGWjPi0DbDoWe2tlqYtszaboiU1n4rZEbtXJnS1s7Ppuv67h++VAn2FLLGYmhnauWkxnA93iLIb0duqp+sWETy3kiteNbI6Ta0l2JkswGpaG6tLKWLzLqkpQWNROpDOxYoiRSRws4jay+cFU0npVQi7dQI5bbvdlZt/+pI2Yl3vjn8qkM83UVBBq4zf/izb2yqLDcuwkwYn/DojtTq8+1sqQ0sOqQXlB1cXHowiTnB7MtgrPs0zhg9i8zpEdWDzRhzwuTIr4Mth/kLk+/XWnCqWWlbSZbpn5Db1BsHa3d+yg63gLbFXbM7Ok15VknpdnNGCUmaaW2kClmq/ejgqPacp8UAAPnirXHKwZUYMZwoL5eHZbFFpORZ2J+yrne89GNrv9guSIGu8iYTKdSMVmvHbxYy7k/GFsuVaJlZC8A1wzU/xbToa426CJxROs3TxXSeY33CLxhysaW2mKb3QaPTtjqdLgV+ekxZDmgDYPpi4fnHwTxueGgUOaUvYn29BOTQqRXOrO60dFGrSpbo56rMRvLUwP0zOCTWWDmJIAtuEU/NVzO+ZCLDczw1pfC+XEjaQEVMbHtWn8lV0jmu3MMghvRRVo+q26TOGae0iLhAFSZzRh5OaW5cndhVPzIXu/1qq5VzPGB0XnC5lZOIWihtbk87YEPRHA83c9Oij3f1wCe3IWCKW4bXJeZucvHd1XmVWpFkxm5RuH6UjyftExg8l1c2qtYlr4tJ4TKn8qiZ5oKa7kJhvKZkZiewOZ0ejgUQufmpSA5Nxmg13qmQWiCr72gZFzTqPlGK6WStroL+2bT79n4MKH3Oz8aU2qfEvp3pQ+WSSf5hv1nV63KwK/qSHsaWMzjSahPf0grC83IYrHElQzObxfQKbhI63Sj9i1f3m5OLvyKuHwTxhMFVVj4g/gRs9qQq9wcAAA==");
        prototype = ItemStack.deserializeBytes(bytes);
        displayName = TextComponent.fromLegacyText("" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.ITALIC + "Kitty Coin");
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }
}
