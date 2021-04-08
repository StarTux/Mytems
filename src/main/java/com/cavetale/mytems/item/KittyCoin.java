package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.Base64;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public final class KittyCoin implements Mytem {
    @Getter private final Mytems key;
    @Getter private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        @SuppressWarnings("LineLength")
        final byte[] bytes = Base64.getDecoder().decode("H4sIAAAAAAAAAMVUO6+jRhhlH1Hu3q3TpIiuXEXyKgYb2/hKW2CwAduAjQEDURQNMDzM8/KG1f6DTZFui/yBSOnzM9Lmj0RKkSJ4N/uuk1SjOd+Z75yj+WauEeQB8pgGBVBhlvtJjCDXX14h930b+SLyY2hlwClu0xC0MPveg8C+Rh4UwL1GPrf9/AJfIQ8FEEHkx2cD2BQZGNx++2xgJqE9uC2yEj4Z+AUIfWtw64Aw77dlbMMs7Dvbb6G8yPwAFl6WlK73Fk1Mp8wtULxHtJIwyQa3A/fS/smg6AX73dYvivaGSvx48Py7t+jg+SPk4S7J4BVyifjyU3v/NP03/GWgfedPgjaE0U3h+fmN9YlL5Lf/zxkobnYgtiOQBTcRzCwPxEX+zeD5k2cfa785b/fU79MyS0M4eC/EB4F++E8DfTAKoxpk6Ud2rpHrY1CGoVjHMHszrVcUqGDvBvbVfZakMCt8mD9Cri7nygzm1/3U3LtCPlNBWMJ7P8E6cTlqg4ITFloTyTM10ufoxOVlvRa6w5jvuClPu6hA1VuOIn2L3VRGFOaGEgacT844iutEhpsYkRAJsnc2LtyzgoqyF4m0jupygOsdhwuR3hhHLqd80uXiZWuOjdRkVFHvdV/32dC6ZoQ2s87f8YTUGE89m1VbQ92ElqamVqS+1mWl1j4p//CkELIS1te617X84vWSSz6iofghduGrrUlxruiTPmAl1KKTajd512MXYakZqWcrWkc2NS0N7VDZjIq/8nFc+HykYMbJ8HmaHxvMOtCjTdBnnQp9Tr1boUJneCLNd4YsRfw56PRIwXlm4+lncirIeqt3RmScVqhxdmueDnCDVqacm7zy5hz6lUW3zuHp0yvk0dF3Y3C5t/u/hNt61cTUaAHC0TbxiYN7MDugtSuuDrYj9VxZo6RawdCoZif+qBw5qlwN19GdPWGk2fnA1E0enPCSp8f1hBQxwZnfhcwkSdEqOZ3NHK3xfedArxTWi6jBazjjo7lsoHfk7CDMNK5pigM0SXrEaKRUdadDgO8KNvE3i3Cy5BrGHNl0bYIKo0QGDrtw6IlZk/rT8MgXHouvLJXVcMwkhxpHUrMqW+fEpG5xVfJRXlqk2XorLk8zbJfx20Z1xd1+06InzOOAQ1Tj6anIAXHuSn+/XBGnZbOpHeeuZWLIoTtJIlCf4CmNYPBUKQ/T3DouLD31NnVeeeWcdoe0kjgNpYk1zqxsfJ5Y1Rjs5Tv7NGflbegCWlRlkrfSyWjRVmOFGJL+9k6btWWXnpW0PjgdcPtHjdZFWBlHKYazkGaKoeToKnHyZr2eGrOEvO3WrFytsQVlU3arYMMC5jlxN3anRdVNVKIdycu5uMxwwyllPWAtWlcS9EwH+zmVrBhmI2Nj6cDNgVK7qxOYRWsO30MBxXQQkrguqa7FMrv1uiEXbpfNIeNO3RjlpnNsZB01RTf1PSHqMI+XuLstQlRxFnk1a7Vho6TBOtjzWuEcdcEkoolSj9FmqIb7wy6WsaOI3mHD2dj1mqGJbgwSBpmPBTHLeJrICixKrXB1HCSVXIQLWxrtcXauDbsUM+3KZxkuZ9x4l26soOra7U5/iiCPkfuc3X81D4lf//rjxe9r+eWf9s8vvk6+6n8f5DMqKePiHvI3CFevWKQHAAA=");
        prototype = ItemStack.deserializeBytes(bytes);
        displayName = Component.text("Kitty Coin").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        List<Component> lore = meta.lore();
        lore.set(lore.size() - 1, Component.text(""));
        lore.add(Component.text("/warp Bazaar").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("/warp WitchLair").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("/warp DwarvenVillage").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("/warp CloudVillage").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
