package de.projektweihnachten.laby.addon2023;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.projektweihnachten.laby.addon2023.activities.RegionPermission;
import de.projektweihnachten.laby.addon2023.hud.HudDataManager;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.util.io.web.request.WebResolver;
import net.labymod.serverapi.protocol.payload.io.PayloadReader;

public class ServerCommunicatorListener {

  @Subscribe
  public void onDisconnect(ServerDisconnectEvent event) {
    HudDataManager.disconnect();
    RegionPermission.setPwserver(false);
    RegionPermission.setEditable(false);
    RegionPermission.setGamemode(false);
    RegionPermission.setGamemodeName(null);
    RegionPermission.setGamemodeUrl(null);
    RegionPermission.setGamemodeInfo(null);
    RegionPermission.setRegion(null, null, new JsonArray(),false);
  }

  @Subscribe
  public void onReceive(NetworkPayloadEvent event) {
    try {

      if (event.identifier().getNamespace().equals("labymod3") && event.identifier().getPath()
          .equals("pw2023_addon")) {
        PayloadReader reader = new PayloadReader(event.getPayload());
        String key = reader.readString();

        JsonObject payload = WebResolver.GSON.fromJson(reader.readString(), JsonObject.class);

        RegionPermission.setPwserver(true);

        if (key.equalsIgnoreCase("region")) {
          HudDataManager.setClaimPoints(payload.get("claimpoints").getAsInt());
          JsonObject data = payload.get("data").getAsJsonObject();
          RegionPermission.setRegion(jsonStringNull(data.get("name")),
              jsonStringNull(data.get("owner")), data.get("perms").getAsJsonArray(),data.get("editable").getAsBoolean());
          HudDataManager.setRegionName(RegionPermission.getRegionName());

        } else if (key.equalsIgnoreCase("server")) {
          HudDataManager.setOnlineGlobal(payload.get("global").getAsInt());
          HudDataManager.setOnlineWorld(payload.get("world").getAsInt());
          HudDataManager.setOnlineTeam(payload.get("team").getAsInt());

        } else if (key.equalsIgnoreCase("gamemode") && PW2023Addon.getInstance().configuration()
            .showGamemode().get()) {
          String gamemode = jsonStringNull(payload.get("gamemode_name"));
          boolean visible = payload.get("show_gamemode").getAsBoolean();

          LabyConnectSession session = PW2023Addon.getInstance().labyAPI().labyConnect()
              .getSession();

          if (session != null) {
            session.sendCurrentServer(
                PW2023Addon.getInstance().labyAPI().serverController().getCurrentServerData(),
                visible ? gamemode : null, false);
          }
        } else if (key.equalsIgnoreCase("gameinfo")) {
          RegionPermission.setGamemode(payload.get("isGamemode").getAsBoolean());
          RegionPermission.setGamemodeUrl(jsonStringNull(payload.get("image")));
          RegionPermission.setGamemodeName(jsonStringNull(payload.get("title")));
          RegionPermission.setGamemodeInfo(jsonStringNull(payload.get("info")));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String jsonStringNull(JsonElement element) {
    return element.isJsonNull() ? null : element.getAsString();
  }
}
