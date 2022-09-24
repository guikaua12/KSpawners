package me.approximations.spawners.model;

import lombok.Builder;
import lombok.Data;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

@Data
@Builder
public class SpawnerWrapper {
    private String key;
    private boolean ativado;
    private ItemStack colocavelItem;
    private EntityType entityType;
    private String mobName;
    private ItemStack dropItem;
    private double dropPrice;
}
