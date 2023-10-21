package de.projektweihnachten.laby.addon2023;

import de.projektweihnachten.laby.addon2023.activities.PW2023NavigationElement;
import de.projektweihnachten.laby.addon2023.hud.HudDataManager;
import de.projektweihnachten.laby.addon2023.hud.widgets.*;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class PW2023Addon extends LabyAddon<PW2023AddonConfig> {

  private final Icon hudIcon = Icon.texture(
          ResourceLocation.create("pw2023", "themes/vanilla/textures/settings/hud/snowflake64.png"))
      .resolution(64, 64);

  private static PW2023Addon instance = null;

  public static PW2023Addon getInstance() {
    return instance;
  }

  private PW2023NavigationElement nav =null;

  public PW2023NavigationElement getNav() {
    return nav;
  }

  @Override
  protected void enable() {
    instance = this;
    this.registerSettingCategory();

    this.labyAPI().eventBus().registerListener(new ServerCommunicatorListener());

    nav = new PW2023NavigationElement(hudIcon);
    this.labyAPI().navigationService().register(nav);

    this.labyAPI().hudWidgetRegistry().categoryRegistry().register(HudDataManager.getCategory());

    this.labyAPI().hudWidgetRegistry().register(new ClaimpointsHudWidget("claimpoints", hudIcon));
    this.labyAPI().hudWidgetRegistry().register(new OnlineTeamHudWidget("online_team", hudIcon));
    this.labyAPI().hudWidgetRegistry()
        .register(new OnlineGlobalHudWidget("online_global", hudIcon));
    this.labyAPI().hudWidgetRegistry().register(new OnlineWorldHudWidget("online_world", hudIcon));
    this.labyAPI().hudWidgetRegistry().register(new RegionNameHudWidget("regionname", hudIcon));

    this.logger().info("Enabled the Addon");
  }

  @Override
  protected Class<PW2023AddonConfig> configurationClass() {
    return PW2023AddonConfig.class;
  }

}
