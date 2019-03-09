package org.black_ixx.bossshop.addon.thirdcurrency;

import java.util.List;

import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.features.PointsManager;
import org.black_ixx.bossshop.managers.features.PointsManager.PointsPlugin;
import org.bukkit.configuration.ConfigurationSection;

public class CustomPoints {

    private PointsPlugin pointsplugin;
    private PointsManager manager;
    private BSPriceTypeThirdCurrencyVariable pricetype;
    private BSRewardTypeThirdCurrencyVariable rewardtype;

    private String name;
    private String messageNotEnough, messageDisplay;

    private boolean specialDisplay;
    private List<String> specialDisplayFormatting;


    public CustomPoints(ConfigurationSection section) {
        name = section.getString("Name");
        pointsplugin = ClassManager.manager.getConfigHandler().findPointsPlugin(section.getString("PointsPlugin"));
        messageNotEnough = section.getString("Message.NotEnoughPoints");
        messageDisplay = section.getString("Placeholder.DisplayPoints");
        specialDisplay = section.getBoolean("PointsDisplay.Enabled");
        specialDisplayFormatting = section.getStringList("PointsDisplay.List");
        manager = new PointsManager(pointsplugin);

        pricetype = new BSPriceTypeThirdCurrencyVariable(this);
        rewardtype = new BSRewardTypeThirdCurrencyVariable(this);
    }


    public void register() {
        pricetype.register();
        rewardtype.register();
    }


    public String getName() {
        return name;
    }


    public PointsPlugin getPointsPlugin() {
        return pointsplugin;
    }

    public PointsManager getPointsManager() {
        return manager;
    }

    public BSPriceTypeThirdCurrencyVariable getPriceType() {
        return pricetype;
    }

    public BSRewardTypeThirdCurrencyVariable getRewardType() {
        return rewardtype;
    }

    public String getMessageNotEnoughPoints() {
        return messageNotEnough;
    }

    public String getPlaceholderPoints() {
        return messageDisplay;
    }

    public boolean getSpecialDisplayEnabled() {
        return specialDisplay;
    }

    public List<String> getSpecialDisplayFormatting() {
        return specialDisplayFormatting;
    }

}
