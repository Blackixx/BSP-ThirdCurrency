package org.black_ixx.bossshop.addon.thirdcurrency;

import org.black_ixx.bossshop.events.BSCheckStringForFeaturesEvent;
import org.black_ixx.bossshop.events.BSRegisterTypesEvent;
import org.black_ixx.bossshop.events.BSTransformStringEvent;
import org.black_ixx.bossshop.misc.MathTools;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BSListener implements Listener {

    private ThirdCurrency plugin;

    public BSListener(ThirdCurrency plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void registerTypes(BSRegisterTypesEvent event) {
        if (plugin.getPoints() != null) {
            for (CustomPoints cp : plugin.getPoints()) {
                cp.register();
            }
        }
    }


    @EventHandler
    public void transformString(BSTransformStringEvent event) {
        Player p = event.getTarget();
        if (p != null) {

            for (CustomPoints cp : plugin.getPoints()) {
                double points = cp.getPointsManager().getPoints(p);
                String pointsdisplay = MathTools.displayNumber(points, cp.getSpecialDisplayFormatting(), !cp.getPointsManager().usesDoubleValues());
                String text = event.getText().replaceAll("%" + cp.getName() + "%", pointsdisplay);
                event.setText(text);
            }

        }
    }

    @EventHandler
    public void checkString(BSCheckStringForFeaturesEvent event) {
        String s = event.getText();

        for (CustomPoints cp : plugin.getPoints()) {
            if (s.contains("%" + cp.getName() + "%")) {
                event.approveFeature();
                break;
            }
        }
    }

}
