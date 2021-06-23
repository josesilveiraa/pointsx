package org.josesilveiraa.points.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter @Setter @AllArgsConstructor
public class Category {

    private String identification;
    private ItemStack itemStack;
    private int slot;

}
