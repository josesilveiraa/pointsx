package org.josesilveiraa.pointsx.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter @Setter @AllArgsConstructor
public class ShopItem {

    private final ItemStack itemStack;
    private final int x;
    private final int y;

}
