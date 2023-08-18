package de.projektweihnachten.laby.addon2023;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class PW2023AddonConfig extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
  @SwitchSetting
  private final ConfigProperty<Boolean> gamemode = new ConfigProperty<>(true);
  @SwitchSetting
  private final ConfigProperty<Boolean> permanav = new ConfigProperty<>(false);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> showGamemode() {
    return this.gamemode;
  }

  public ConfigProperty<Boolean> getPermanav() {
    return permanav;
  }
}