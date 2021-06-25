package org.josesilveiraa.pointsx.object;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter @Setter @AllArgsConstructor
public class Category {

    private String identification;
    private ItemStack itemStack;
    private int x;
    private int y;
    private ChestGui chestGui;

}
