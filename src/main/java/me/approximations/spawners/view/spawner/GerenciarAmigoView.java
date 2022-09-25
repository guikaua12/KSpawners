package me.approximations.spawners.view.spawner;

import me.approximations.spawners.Main;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.TypeUtil;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GerenciarAmigoView extends View {
    private final ItemStack canPlaceFalse;
    private final ItemStack canPlaceTrue;
    private final ItemStack canBreakTrue;
    private final ItemStack canBreakFalse;
    private final ItemStack canKillTrue;
    private final ItemStack canKillFalse;
    public GerenciarAmigoView() {
        super(3, "Gerenciar permissões");
        setCancelOnClick(true);

        this.canPlaceTrue = new ItemBuilder(TypeUtil.convertFromLegacy("INK_SACK", 10)).setName("§aPermissão para adicionar spawners.").setLore("§fClique para ativar").wrap();
        this.canPlaceFalse = new ItemBuilder(TypeUtil.convertFromLegacy("INK_SACK", 8)).setName("§7Permissão para adicionar spawners.").setLore("§fClique para desativar").wrap();
        this.canBreakTrue = new ItemBuilder(TypeUtil.convertFromLegacy("INK_SACK", 10)).setName("§aPermissão para remover spawners.").setLore("§fClique para desativar").wrap();
        this.canBreakFalse = new ItemBuilder(TypeUtil.convertFromLegacy("INK_SACK", 8)).setName("§7Permissão para remover spawners.").setLore("§fClique para ativar").wrap();
        this.canKillTrue = new ItemBuilder(TypeUtil.convertFromLegacy("INK_SACK", 10)).setName("§aPermissão para matar mobs do spawner.").setLore("§fClique para desativar").wrap();
        this.canKillFalse = new ItemBuilder(TypeUtil.convertFromLegacy("INK_SACK", 8)).setName("§7Permissão para matar mobs do spawner.").setLore("§fClique para ativar").wrap();

        slot(16, new ItemBuilder(Material.ARROW)
                .setName("§cVoltar")
                .wrap()
        ).onClick(click -> {
            click.open(AmigosView.class, click.getData());
        });
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        context.update();
    }

    @Override
    protected void onUpdate(@NotNull ViewContext context) {
        Spawner sp = getSpawner(context);
        Amigo amigo = getAmigo(context);
        context.slot(10).onRender(render -> {
            render.setItem(amigo.isCanPlace() ? this.canPlaceTrue : this.canPlaceFalse);
        }).onClick(click -> {
            sp.setAmigoCanPlace(amigo, !amigo.isCanPlace());
            click.update();
        });

        context.slot(11).onRender(render -> {
            render.setItem(amigo.isCanBreak() ? this.canBreakTrue : this.canBreakFalse);
        }).onClick(click -> {
            sp.setAmigoCanBreak(amigo, !amigo.isCanBreak());
            click.update();
        });

        context.slot(12).onRender(render -> {
            render.setItem(amigo.isCanKill() ? this.canKillTrue : this.canKillFalse);
        }).onClick(click -> {
            sp.setAmigoCanKill(amigo, !amigo.isCanKill());
            click.update();
        });
    }

    public Spawner getSpawner(ViewContext context) {
        Spawner sp = context.get("spawner");
        return Main.getInstance().getSpawnerManager().getSpawner(sp.getLocation());
    }

    public Amigo getAmigo(ViewContext context) {
        return context.get("amigo");
    }
}
