package me.approximations.spawners.view.spawner;


import com.google.common.collect.ImmutableMap;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.SpawnersConfig;
import me.approximations.spawners.configuration.inventory.MainInventory;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.model.SpawnerWrapper;
import me.approximations.spawners.util.NumberUtils;
import me.approximations.spawners.util.Utils;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.configuration.ConfigurationSection;

public class MainView extends View {
    private final String SPAWNER_CONTEXT_KEY = "spawner";

    public MainView() {
        super(MainInventory.get(MainInventory::size), MainInventory.get(MainInventory::name));
        setCancelOnClick(true);
        ConfigurationSection dropsItem = MainInventory.get(MainInventory::dropsItem);
        ConfigurationSection amigosItem = MainInventory.get(MainInventory::amigosItem);

        slot(dropsItem.getInt("Slot"), Utils.getItemFromConfig(dropsItem))
                .onClick(click -> click.open(DropsView.class, ImmutableMap.of(SPAWNER_CONTEXT_KEY, click.get(SPAWNER_CONTEXT_KEY))));

        slot(amigosItem.getInt("Slot"), Utils.getItemFromConfig(amigosItem))
                .onClick(click -> click.open(AmigosView.class, ImmutableMap.of(SPAWNER_CONTEXT_KEY, click.get(SPAWNER_CONTEXT_KEY))));
    }

    @Override
    protected void onRender(ViewContext context) {
        Spawner sp = context.get(SPAWNER_CONTEXT_KEY);
        SpawnerWrapper sw = Main.getInstance().getSpawnerManager().getSpawnerWrapper(sp.getSpawnerWrapperKey());
        ConfigurationSection spawnersSection = SpawnersConfig.get(SpawnersConfig::getSpawners).getConfigurationSection(sw.getKey());
        ConfigurationSection infoItem = MainInventory.get(MainInventory::infoItem);
        String head_url = spawnersSection.getString("MenuHead");

        context.slot(infoItem.getInt("Slot"), Utils.getItemFromConfig(infoItem, ImmutableMap.of(
                "{mobName}", sw.getMobName(),
                "{quantia}", NumberUtils.format(sp.getQuantia(), false),
                "{drops}", NumberUtils.format(sp.getDrops(), false),
                "{valor_total}", NumberUtils.format(sp.getDrops() * sw.getDropPrice(), false),
                "{dono}", sp.getDono()
                ), head_url));

//        context.slot(15, new ItemBuilder(Material.BARRIER)
//                .setName("§cRemover spawner")
//                .wrap()
//        ).onClick(click -> {
//            Player player = click.getPlayer();
//            Spawner sp2 = getSpawner(click);
//            Location spl = sp2.getLocation();
//            if(!player.hasPermission("spawners.admin")) {
//                if(!player.getName().equalsIgnoreCase(sp.getDono())) {
//                    Amigo amigo = sp2.getAmigoByName(player.getName());
//                    if(amigo == null) {
//                        player.sendMessage("§cVocê não tem permissão para remover geradores.");
//                        return;
//                    }
//                    if(!amigo.isCanBreak()) {
//                        player.sendMessage("§cVocê não tem permissão para remover geradores.");
//                        return;
//                    }
//                }
//            }
//
//            String nome = sp2.getNome();
//            Double quantia = sp2.getQuantia();
//            SpawnerManager.giveSpawner(player, quantia, nome);
//            Block block = spl.getBlock();
//            block.setType(Material.AIR);
//            click.close();
//
//
//
//
//
//            for (Entity entity : block.getWorld().getNearbyEntities(block.getLocation(), 5.0, 5.0, 5.0)) {
//                if(!entity.getType().equals(EntityType.PLAYER)) {
//                    if(entity.hasMetadata("LOCATION") && LocationSerializer.getInstance().encode(block.getLocation()).equalsIgnoreCase(entity.getMetadata("LOCATION").get(0).asString())) {
//                        entity.remove();
//                        if(entity.getPassenger() != null) {
//                            entity.getPassenger().remove();
//                        }
//                    }
//                }
//            }
//        });

    }

}
