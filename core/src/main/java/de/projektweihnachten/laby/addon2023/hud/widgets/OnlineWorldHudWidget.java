package de.projektweihnachten.laby.addon2023.hud.widgets;

import de.projektweihnachten.laby.addon2023.hud.HudDataManager;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;

public class OnlineWorldHudWidget extends TextHudWidget<TextHudWidgetConfig> {

  private static OnlineWorldHudWidget instance = null;
  private TextLine line;
  private int data = -1;

  public OnlineWorldHudWidget(String id, Icon hudIcon) {
    super(id);
    instance = this;
    setIcon(hudIcon);
    bindCategory(HudDataManager.getCategory());
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);
    this.line = super.createLine(Component.translatable("pw2023.hudWidget.online_world.name"),
        "???");
  }

  @Override
  public boolean isVisibleInGame() {
    return data >= 0;
  }

  public static void setData(int value) {
    instance.data = value;
    instance.line.updateAndFlush(instance.data < 0 ? "???" : value);
  }
}
