package ru.stonlex.bukkit.gaming.item.menu;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.gaming.item.GamingItemCategory;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;
import ru.stonlex.bukkit.inventory.impl.BasePaginatedInventory;
import ru.stonlex.bukkit.vault.VaultPlayer;

public class GamingItemAutoGeneratedMenu extends BasePaginatedInventory {

    @Getter
    protected final GamingItemCategory gamingItemCategory;

    public GamingItemAutoGeneratedMenu(int inventoryRows,

                                       @NonNull String inventoryTitle,
                                       @NonNull GamingItemCategory gamingItemCategory) {

        super(inventoryTitle, inventoryRows);
        this.gamingItemCategory = gamingItemCategory;
    }

    @Override
    public void drawInventory(Player player) {
        GamingPlayer gamingPlayer = GamingPlayer.of(player);
        VaultPlayer vaultPlayer = gamingPlayer.toVault();

        gamingItemCategory.getLoadedItems().forEach(gamingItem ->
                addClickItemToMarkup(gamingItem.toBukkitItem(gamingPlayer), (player1, event) -> {

                    if (!vaultPlayer.hasMoney(gamingItem.getItemCost())) {
                        gamingPlayer.sendMessage(GamingSettingType.ITEM_PURCHASE_NO_MONEY_MESSAGE);

                        return;
                    }

                    if (vaultPlayer.hasMoney(gamingItem.getItemCost()) && !gamingPlayer.isItemBought(gamingItem)) {
                        gamingPlayer.sendMessage(GamingSettingType.ITEM_PURCHASE_MESSAGE, "%item%", gamingItem.getItemName());

                        vaultPlayer.takeMoney(gamingItem.getItemCost());
                        gamingPlayer.addBoughtItem(gamingItem);

                        updateInventory(player1);
                        return;
                    }

                    if (gamingPlayer.isItemBought(gamingItem) && !gamingPlayer.isItemSelected(gamingItem)) {
                        gamingPlayer.sendMessage(GamingSettingType.ITEM_SELECT_MESSAGE, "%item%", gamingItem.getItemName());

                        gamingPlayer.setSelectedItem(gamingItem);
                    }

                    updateInventory(player1);
        }));
    }
}