package me.approximations.spawners.view.spawnersShop;

import me.approximations.spawners.model.User;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

public class TOP10View extends PaginatedView<User> {
    public TOP10View() {
        super(4, "TOP 10");
        setCancelOnClick(true);
        setLayout("XXXXXXXXX",
                  "XOOOOOOOX",
                  "XXXOOOXXX",
                  "XXXXXXXXX");
    }

//    @Override
//    protected void onItemRender(PaginatedViewSlotContext<User> render, ViewItem item, User value) {
//        OfflinePlayer player = Bukkit.getOfflinePlayer(value.getNome());
//        item.withItem(new ItemBuilder(Utils.getHead(player))
//                .setName("§a"+player.getName()+"§7["+(UserDao.getInstance().users.indexOf(value)+1)+"°]")
//                .setLore("§fSpawners comprados: §7"+ NumberUtils.format(value.getSpawnersComprados(), false))
//                .toItemStack()
//        );
//    }
//
//    @Override
//    protected void onRender(ViewContext context) {
//        context.paginated().setSource(UserDao.getInstance().users);
//        context.slot(31).onRender(render -> {
//            render.setItem(new ItemBuilder(Material.ARROW)
//                    .setName("§cVoltar")
//                    .toItemStack());
//        }).onClick(click -> {
//            click.open(SpawnersView.class);
//        });
//    }
}
