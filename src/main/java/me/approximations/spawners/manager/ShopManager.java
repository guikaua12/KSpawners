package me.approximations.spawners.manager;

import lombok.Getter;
import me.approximations.spawners.model.SpawnerShop;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {
    @Getter
    private static final List<SpawnerShop> spawnerShopList = new ArrayList<>();
}
