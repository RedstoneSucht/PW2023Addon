package de.projektweihnachten.laby.addon2023.activities;

import de.projektweihnachten.laby.addon2023.PW2023Addon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

@AutoActivity
@Link("pw2023-activity.lss")
public class PW2023Activity extends SimpleActivity {

  @Override
  public void initialize(Parent parent) {
    super.initialize(parent);

    if (!RegionPermission.isPwserver()) {
      ComponentWidget error = ComponentWidget.i18n("pw2023.activity.messages.notpwserver");
      error.addId("error-notpwserver");
      this.document().addChild(error);
    } else if (RegionPermission.isGamemode()) {
      ComponentWidget title = ComponentWidget.i18n(
          "pw2023.activity.messages.unknowngamemode.title");
      ComponentWidget info = ComponentWidget.i18n("pw2023.activity.messages.unknowngamemode.info");

      if (RegionPermission.getGamemodeInfo() != null
          && RegionPermission.getGamemodeName() != null) {
        title = ComponentWidget.text(RegionPermission.getGamemodeName());
        info = ComponentWidget.text(RegionPermission.getGamemodeInfo());
      } else {
        title.addId("error-gamemode-title");
        info.addId("error-gamemode-info");
      }

      if (RegionPermission.getGamemodeUrl() != null) {
        IconWidget image = new IconWidget(Icon.url(RegionPermission.getGamemodeUrl()));

        image.addId("gamemode-image");
        title.addId("gamemode-title-image");
        info.addId("gamemode-info-image");

        this.document().addChild(image);
      } else {
        title.addId("gamemode-title");
        info.addId("gamemode-info");
      }

      this.document().addChild(title);
      this.document().addChild(info);
    } else {
      ComponentWidget title = ComponentWidget.i18n(
          RegionPermission.isEditable() ? "pw2023.activity.title.settings"
              : "pw2023.activity.title.info");
      title.addId("perms-title");
      this.document().addChild(title);

      if (!RegionPermission.isEditable()) {
        ComponentWidget warning = ComponentWidget.i18n("pw2023.activity.title.warning");
        warning.addId("perms-warning");
        this.document().addChild(warning);
      }
      ComponentWidget region;

      if (RegionPermission.getRegionName() == null) {
        region = ComponentWidget.component(
            Component.translatable("pw2023.activity.messages.name", NamedTextColor.DARK_PURPLE)
                .append(
                    Component.translatable("pw2023.activity.messages.noname", NamedTextColor.RED)));
      } else {
        region = ComponentWidget.component(
            Component.translatable("pw2023.activity.messages.name", NamedTextColor.DARK_PURPLE)
                .append(Component.text(RegionPermission.getRegionName(), NamedTextColor.YELLOW)));
      }
      region.addId("region-name");

      this.document().addChild(region);

      ComponentWidget owner;

      if (RegionPermission.getRegionOwner() == null) {
        owner = ComponentWidget.component(
            Component.translatable("pw2023.activity.messages.owner", NamedTextColor.DARK_PURPLE)
                .append(Component.translatable("pw2023.activity.messages.noowner",
                    NamedTextColor.RED)));
      } else {
        owner = ComponentWidget.component(
            Component.translatable("pw2023.activity.messages.owner", NamedTextColor.DARK_PURPLE)
                .append(Component.text(RegionPermission.getRegionOwner(), NamedTextColor.YELLOW)));
      }
      owner.addId("region-owner");

      this.document().addChild(owner);

      VerticalListWidget<Widget> permsSettings = new VerticalListWidget<>();
      permsSettings.addId("perms-settings");

      ComponentWidget permissionsText = ComponentWidget.i18n("pw2023.activity.messages.perms");
      permissionsText.addId("perms-other-team-info");
      permsSettings.addChild(permissionsText);

      int i = 0;

      for (RegionPermission p : RegionPermission.getPerms()) {
        HorizontalListWidget horizontalListWidget = new HorizontalListWidget();
        horizontalListWidget.addId("perms-settings-entry");

        SwitchWidget switchWidget = SwitchWidget.create(new Switchable() {
          @Override
          public void switchValue(boolean value) {
            final String command = p.getCommandID() + " " + value;
            try {
              PW2023Addon.getInstance().labyAPI().minecraft().chatExecutor().chat(command, false);
            } catch (NullPointerException ignored) {
            }
            PW2023Addon.getInstance().logger().debug(command);
          }
        });

        switchWidget.setValue(p.getValue());

        if (!RegionPermission.isEditable() || !p.isAllowed()) {
          switchWidget.addId("non-interactable");
        }

        switchWidget.addId("perms-switch");
        horizontalListWidget.addEntry(switchWidget);

        horizontalListWidget.addEntry(ComponentWidget.text(p.getName()));

        permsSettings.addChild(horizontalListWidget);
        i++;
      }

      if (i == 0) {
        ComponentWidget noperms = ComponentWidget.i18n("pw2023.activity.messages.noperms");
        noperms.addId("perms-noperms-info");
        permsSettings.addChild(noperms);
      }

      ScrollWidget scrollWidget = new ScrollWidget(permsSettings);
      scrollWidget.addId("perms-scroll");

      this.document().addChild(permsSettings);
    }
  }
}
