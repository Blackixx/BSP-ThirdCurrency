package org.black_ixx.bossshop.addon.thirdcurrency;


import java.util.ArrayList;
import java.util.List;

import org.black_ixx.bossshop.api.BossShopAddonConfigurable;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class ThirdCurrency extends BossShopAddonConfigurable {


    private List<CustomPoints> points;


    @Override
    public String getAddonName() {
        return "ThirdCurrency";
    }

    @Override
    public String getRequiredBossShopVersion() {
        return "1.9.5";
    }

    @Override
    public void enableAddon() {
        getServer().getPluginManager().registerEvents(new BSListener(this), this);

        getConfig().options().copyDefaults(true);
        getConfig().addDefault("1.Name", "token");
        getConfig().addDefault("1.PointsPlugin", "TokenEnchant");
        getConfig().addDefault("1.Message.NotEnoughPoints", "&cYou don't have enough Tokens!");
        getConfig().addDefault("1.Placeholder.DisplayPoints", "%token% Tokens");
        getConfig().addDefault("1.PointsDisplay.Enabled", false);

        List<String> list = new ArrayList<String>();
        list.add("1000000:6:2:%number% million");
        list.add("1000:3:2:%number%k");
        list.add("0:0:0:%number%");
        getConfig().addDefault("1.PointsDisplay.List", list);

        getAddonConfig().save();

        load();
    }

    public void load() {
        points = new ArrayList<CustomPoints>();
        for (String key : getConfig().getKeys(false)) {
            ConfigurationSection section = getConfig().getConfigurationSection(key);
            if (section != null) {
                if (section.getString("Name") != null) {
                    CustomPoints cp = new CustomPoints(section);
                    points.add(cp);
                }
            }
        }
    }

    @Override
    public void disableAddon() {
    }

    @Override
    public void bossShopFinishedLoading() {
    }

    @Override
    public void bossShopReloaded(CommandSender sender) {
        getAddonConfig().reload();
        load();
    }

    @Override
    public boolean saveConfigOnDisable() {
        return false;
    }


    public List<CustomPoints> getPoints() {
        return points;
    }


}
