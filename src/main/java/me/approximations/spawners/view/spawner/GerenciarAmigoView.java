package me.approximations.spawners.view.spawner;

import lombok.val;
import me.approximations.spawners.Main;
import me.approximations.spawners.configuration.inventory.GerenciarAmigosInventory;
import me.approximations.spawners.model.Amigo;
import me.approximations.spawners.model.Spawner;
import me.approximations.spawners.util.ItemBuilder;
import me.approximations.spawners.util.TypeUtil;
import me.approximations.spawners.util.Utils;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewContext;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GerenciarAmigoView extends View {
    private final String SPAWNER_CONTEXT_KEY = "spawner";
    private final ItemStack canPlaceTrue;
    private final ItemStack canPlaceFalse;
    private final ItemStack canBreakTrue;
    private final ItemStack canBreakFalse;
    private final ItemStack canKillTrue;
    private final ItemStack canKillFalse;


    public GerenciarAmigoView() {
        super(GerenciarAmigosInventory.get(GerenciarAmigosInventory::size), GerenciarAmigosInventory.get(GerenciarAmigosInventory::name));
        setCancelOnClick(true);
        val voltarItem = GerenciarAmigosInventory.get(GerenciarAmigosInventory::voltarItem);
        val adicionarOn = GerenciarAmigosInventory.get(GerenciarAmigosInventory::adicionarOn);
        val adicionarOff = GerenciarAmigosInventory.get(GerenciarAmigosInventory::adicionarOff);
        val removerOn = GerenciarAmigosInventory.get(GerenciarAmigosInventory::removerOn);
        val removerOff = GerenciarAmigosInventory.get(GerenciarAmigosInventory::removerOff);
        val matarOn = GerenciarAmigosInventory.get(GerenciarAmigosInventory::matarOn);
        val matarOff = GerenciarAmigosInventory.get(GerenciarAmigosInventory::matarOff);

        this.canPlaceTrue = Utils.getItemFromConfig(adicionarOn);
        this.canPlaceFalse = Utils.getItemFromConfig(adicionarOff);
        this.canBreakTrue = Utils.getItemFromConfig(removerOn);
        this.canBreakFalse = Utils.getItemFromConfig(removerOff);
        this.canKillTrue = Utils.getItemFromConfig(matarOn);
        this.canKillFalse = Utils.getItemFromConfig(matarOff);

        slot(voltarItem.getInt("Slot"), Utils.getItemFromConfig(voltarItem)).onClick(click -> {
            click.open(AmigosView.class, click.getData());
        });
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        Spawner sp = context.get(SPAWNER_CONTEXT_KEY);
        Amigo amigo = getAmigo(context);
        context.slot(GerenciarAmigosInventory.get(GerenciarAmigosInventory::adicionarSlot)).onRender(render -> {
            render.setItem(amigo.isCanPlace() ? this.canPlaceTrue : this.canPlaceFalse);
        }).onClick(click -> {
            sp.setAmigoCanPlace(amigo, !amigo.isCanPlace());
            click.update();
        });

        context.slot(GerenciarAmigosInventory.get(GerenciarAmigosInventory::removerSlot)).onRender(render -> {
            render.setItem(amigo.isCanBreak() ? this.canBreakTrue : this.canBreakFalse);
        }).onClick(click -> {
            sp.setAmigoCanBreak(amigo, !amigo.isCanBreak());
            click.update();
        });

        context.slot(GerenciarAmigosInventory.get(GerenciarAmigosInventory::matarSlot)).onRender(render -> {
            render.setItem(amigo.isCanKill() ? this.canKillTrue : this.canKillFalse);
        }).onClick(click -> {
            sp.setAmigoCanKill(amigo, !amigo.isCanKill());
            click.update();
        });
    }

    public Amigo getAmigo(ViewContext context) {
        return context.get("amigo");
    }
}
