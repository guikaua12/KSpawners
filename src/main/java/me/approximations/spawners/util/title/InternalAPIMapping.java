package me.approximations.spawners.util.title;

import lombok.AllArgsConstructor;
import lombok.val;
import me.approximations.spawners.Main;

@AllArgsConstructor
public enum InternalAPIMapping {

    REFLECTION(ReflectionTitleAPI.class, MinecraftVersion.v1_12),
    BUKKIT(BukkitTitleAPI.class, MinecraftVersion.v1_18);

    private final Class<? extends InternalTitleAPI> apiClass;
    private final MinecraftVersion maxVersion;

    public static InternalTitleAPI create() {
        val version = MinecraftVersion.get();
        for (val value : values()) {
            if (version.isLessThanOrEqualTo(value.maxVersion)) {
                try {
                    return value.apiClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    Main.getInstance().getLogger().warning("[TitleAPI] Erro ao tentar instanciar a api.");
                }
            }
        }
        return null;
    }
}