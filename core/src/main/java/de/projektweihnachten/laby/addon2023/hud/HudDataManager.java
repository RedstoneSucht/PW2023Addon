package de.projektweihnachten.laby.addon2023.hud;

import de.projektweihnachten.laby.addon2023.PW2023Addon;
import de.projektweihnachten.laby.addon2023.hud.widgets.*;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;

public class HudDataManager {

  private static final HudWidgetCategory category = new HudWidgetCategory("pw2023");

  public static void setRegionName(String regionName) {
    RegionNameHudWidget.setData(regionName);
  }

  public static HudWidgetCategory getCategory() {
    return category;
  }

  public static void setClaimPoints(int claimPoints) {
    ClaimpointsHudWidget.setData(claimPoints);
  }

  public static void setOnlineGlobal(int onlineGlobal) {
    OnlineGlobalHudWidget.setData(onlineGlobal);
  }

  public static void setOnlineWorld(int onlineWorld) {
    OnlineWorldHudWidget.setData(onlineWorld);
  }

  public static void setOnlineTeam(int onlineTeam) {
    OnlineTeamHudWidget.setData(onlineTeam);
  }

  public static void disconnect() {
    PW2023Addon.getInstance().logger().debug("Disconnect, resetting PW2023-HUD!");
    setRegionName(null);
    setClaimPoints(-1);
    setOnlineGlobal(-1);
    setOnlineWorld(-1);
    setOnlineTeam(-1);
  }
}
