package com.ipsis.dyetopia.reference;

public class Reference {

    public static final String MOD_ID = "dyetopia";
    public static final String MOD_NAME = "Dyetopia";
    public static final String MOD_VERSION = "@VERSION@";

    public static final String CLIENT_PROXY_CLASS = "com.ipsis.dyetopia.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.ipsis.dyetopia.proxy.ServerProxy";

    /* Textures */
    public static final String TEXTURE_BASE = Reference.MOD_ID.toLowerCase() + ":textures";
    public static final String GUI_TEXTURE_BASE = TEXTURE_BASE + "/gui/";
    public static final String GUI_PROGRESS_TEXTURE = GUI_TEXTURE_BASE + "progress.png";

}
