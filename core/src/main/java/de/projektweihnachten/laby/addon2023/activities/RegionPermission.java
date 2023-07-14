package de.projektweihnachten.laby.addon2023.activities;

import static de.projektweihnachten.laby.addon2023.ServerCommunicatorListener.jsonStringNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class RegionPermission {

  private static boolean editable = false;
  private static boolean gamemode = false;
  private static boolean pwserver = false;

  private final String commandID;
  private final String name;


  private static String gamemodeName = null;
  private static String gamemodeInfo = null;
  private static String gamemodeUrl = null;


  private static String regionName = null;
  private static String regionOwner = null;
  private static ArrayList<RegionPermission> perms = new ArrayList<RegionPermission>();

  public RegionPermission(String commandID, String name) {
    this.commandID = commandID;
    this.name = name;

  }

  public static boolean isEditable() {
    return editable;
  }

  public static void setEditable(boolean editable) {
    RegionPermission.editable = editable;
  }

  public static void setGamemode(boolean gamemode) {
    RegionPermission.gamemode = gamemode;
  }

  public static boolean isGamemode() {
    return gamemode;
  }

  public static boolean isPwserver() {
    return pwserver;
  }

  public static void setPwserver(boolean pwserver) {
    RegionPermission.pwserver = pwserver;
  }

  public static String getGamemodeName() {
    return gamemodeName;
  }

  public static void setGamemodeName(String gamemodeName) {
    RegionPermission.gamemodeName = gamemodeName;
  }

  public static String getGamemodeInfo() {
    return gamemodeInfo;
  }

  public static void setGamemodeInfo(String gamemodeInfo) {
    RegionPermission.gamemodeInfo = gamemodeInfo;
  }

  public static String getGamemodeUrl() {
    return gamemodeUrl;
  }

  public static void setGamemodeUrl(String gamemodeUrl) {
    RegionPermission.gamemodeUrl = gamemodeUrl;
  }

  public static ArrayList<RegionPermission> getPerms() {
    return perms;
  }

  public static String getRegionName() {
    return regionName;
  }

  public static String getRegionOwner() {
    return regionOwner;
  }

  public String getCommandID() {
    return commandID;
  }

  public String getName() {
    return name;
  }

  public static void setRegion(String regionName, String regionOwner, JsonArray permissions) {
    RegionPermission.regionName = regionName;
    RegionPermission.regionOwner = regionOwner;

    ArrayList<RegionPermission> perms = new ArrayList<>();

    for (JsonElement p : permissions) {
      try {
        if (p.isJsonObject()) {
          JsonObject d = p.getAsJsonObject();
          perms.add(
              new RegionPermission(jsonStringNull(d.get("name")), jsonStringNull(d.get("owner"))));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    RegionPermission.perms = perms;
  }
}
