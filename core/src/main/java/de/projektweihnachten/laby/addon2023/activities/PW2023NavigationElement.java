package de.projektweihnachten.laby.addon2023.activities;

import de.projektweihnachten.laby.addon2023.PW2023Addon;
import de.projektweihnachten.laby.addon2023.PW2023AddonConfig;
import de.projektweihnachten.laby.addon2023.hud.HudDataManager;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;

public class PW2023NavigationElement extends ScreenNavigationElement {

  private final Icon snowflake;

  public PW2023NavigationElement(Icon icon) {
    super(new PW2023Activity());
    snowflake = icon;
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("pw2023.activity.tab");
  }

  @Override
  public Icon getIcon() {
    return snowflake;
  }

  @Override
  public String getWidgetId() {
    return "pw2023-activity-wrapper";
  }

  @Override
  public boolean isVisible() {
    PW2023AddonConfig config = PW2023Addon.getInstance().configuration();
    return config.enabled().getOrDefault(true) && (config.getPermanav().getOrDefault(false) || RegionPermission.isPwserver());
  }

  public void reloadActivity() {
    Laby.labyAPI().minecraft().executeOnRenderThread(new Runnable() {
      @Override
      public void run() {
        ((PW2023Activity) getScreen()).reload();
      }
    });
  }
}
