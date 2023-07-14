package de.projektweihnachten.laby.addon2023.activities;

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
}
