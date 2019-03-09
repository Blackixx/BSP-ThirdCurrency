package org.black_ixx.bossshop.addon.thirdcurrency;


import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.prices.BSPriceTypeNumber;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.black_ixx.bossshop.misc.MathTools;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class BSPriceTypeThirdCurrencyVariable extends BSPriceTypeNumber {

    private CustomPoints cp;

    public BSPriceTypeThirdCurrencyVariable(CustomPoints points) {
        this.cp = points;
        updateNames();
    }


    public Object createObject(Object o, boolean force_final_state) {
        return InputReader.getDouble(o, -1);
    }

    public boolean validityCheck(String item_name, Object o) {
        if ((Double) o != -1) {
            return true;
        }
        ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The price object needs to be a valid number. Example: '7' or '12'.");
        return false;
    }

    public void enableType() {
    }


    @Override
    public boolean hasPrice(Player p, BSBuy buy, Object price, ClickType clickType, int multiplier, boolean messageOnFailure) {
        double points = ClassManager.manager.getMultiplierHandler().calculatePriceWithMultiplier(p, buy, clickType, (Double) price) * multiplier;
        if (cp.getPointsManager().getPoints(p) < points) {
            String message = cp.getMessageNotEnoughPoints();
            if (message != null && messageOnFailure) {
                p.sendMessage(ClassManager.manager.getStringManager().transform(message, buy, buy.getShop(), null, p));
            }
            return false;
        }
        return true;
    }

    @Override
    public String takePrice(Player p, BSBuy buy, Object price, ClickType clickType, int multiplier) {
        double points = ClassManager.manager.getMultiplierHandler().calculatePriceWithMultiplier(p, buy, clickType, (Double) price) * multiplier;

        cp.getPointsManager().takePoints(p, points);
        return getDisplayBalance(p, buy, price, clickType);
    }

    @Override
    public String getDisplayBalance(Player p, BSBuy buy, Object price, ClickType clickType) {
        double balance_points = cp.getPointsManager().getPoints(p);
        return cp.getPlaceholderPoints().replace("%" + cp.getName() + "%", MathTools.displayNumber(balance_points, cp.getSpecialDisplayFormatting(), !cp.getPointsManager().usesDoubleValues()));
    }

    @Override
    public String getDisplayPrice(Player p, BSBuy buy, Object price, ClickType clickType) {
        return ClassManager.manager.getMultiplierHandler().calculatePriceDisplayWithMultiplier(p, buy, clickType, (Double) price, cp.getPlaceholderPoints().replace("%" + cp.getName() + "%", "%number%"), cp.getSpecialDisplayFormatting(), true);
    }


    @Override
    public String[] createNames() {
        if (cp == null) {
            return new String[]{"thirdcurrency", "points2", "point2"};
        } else {
            return new String[]{cp.getName()};
        }
    }

    public boolean supportsMultipliers() {
        return true;
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return true;
    }

    @Override
    public boolean isIntegerValue() {
        return false;
    }


}
