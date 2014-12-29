package ipsis.dyetopia.handler;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlockDesc;
import ipsis.dyetopia.manager.dyeableblocks.DyeableModInfo;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.util.ResourceLocation;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.ArrayList;

/**
        # simple dye : 0 = black, 15 = white
        # vanilla dye : 0 = white, 15 = black (eg carpet, wool)
        # vanilla offset : offset to vanilla  0 + x = white
        # custom : specify your own metadata map
        # fancy : block type changes per dye color

        # associate : change between colored blocks
        # origin : uncolored block
        */

public class DyeFileHandler {

    public ArrayList<DyeableModInfo> cfgArray;
    private ArrayList<String> modBlacklist;
    private ArrayList<String> blockBlacklist;

    private static final DyeFileHandler instance = new DyeFileHandler();
    public static final DyeFileHandler getInstance() { return instance; }

    private DyeFileHandler() {
        cfgArray = new ArrayList<DyeableModInfo>();
        modBlacklist = new ArrayList<String>();
        blockBlacklist = new ArrayList<String>();
    }

    public boolean isModBlacklisted(String mod) {
        return modBlacklist.contains(mod);
    }

    public boolean isBlockBlacklisted(String block) {
        return blockBlacklist.contains(block);
    }

    private static final String TAG_DYEBLOCK = "dyeblocks";
    private static final String TAG_MODID = "id";
    private static final String TAG_MODS = "mods";
    private static final String TAG_MAPS = "maps";
    private static final String TAG_REFNAME = "refname";
    private static final String TAG_ASSOC = "assoc";
    private static final String TAG_ORIGIN = "origin";
    private static final String TAG_OFFSET = "offset";
    private static final String TAG_SIMPLE = "simple";
    private static final String TAG_VANILLA = "vanilla";
    private static final String TAG_FULL_META = "fullmeta";
    private static final String TAG_FULL_BLOCK = "fullblock";
    private static final String TAG_NAME = "name";
    private static final String TAG_META = "meta";
    private static final String TAG_COLOR = "color";
    private static final String TAG_BLOCKNAME = "blockname";
    private static final String TAG_BLACKLIST = "blacklist";
    private static final String TAG_BLOCKS = "blocks";

    /** There are three files to load
     * Vanilla is a mod shipped file specifying the vanilla blocks
     * Modded is a mod shipped file specifying predefined modded blocks
     * CustomMap is a user created config file specifying extra modded blocks
     *
     */
    private static final String VANILLA_FILENAME = "dyetopia_vanilla.json";
    private static final String MODDED_FILENAME = "dyetopia_modded.json";
    private static final String USERCUSTOM_FILENAME = "dyetopia_custommaps.json";
    private static final String ASSET_PATH = "/assets/" + Reference.MOD_ID;

    private void readOrigin(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(TAG_NAME))
                desc.originName = reader.nextString();
            else if (name.equals(TAG_META))
                desc.originMeta = reader.nextInt();
            else
                reader.skipValue();
        }
        reader.endObject();
    }

    private void readSimple(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        desc.type = DyeableBlockDesc.MapType.SIMPLE;
        reader.beginObject();
        while (reader.hasNext()) {
                reader.skipValue();
        }
        reader.endObject();
    }

    private void readVanilla(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        desc.type = DyeableBlockDesc.MapType.VANILLA;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(TAG_OFFSET))
                desc.offset = reader.nextInt();
            else
                reader.skipValue();
        }
        reader.endObject();
    }

    private void readFullMeta(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        desc.type = DyeableBlockDesc.MapType.FULL_META;
        desc.initMetaMap();
        reader.beginArray();
        int i = 0;
        while (reader.hasNext()) {
            desc.setMetaMap(i++, reader.nextInt());
        }
        reader.endArray();
    }

    private void readFullBlock(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        desc.type = DyeableBlockDesc.MapType.FULL_BLOCK;
        desc.initBlockMap();

        reader.beginObject();
        while (reader.hasNext()) {

            reader.beginObject();
            while (reader.hasNext()) {
                DyeableBlockDesc.BlockMapDesc d = new DyeableBlockDesc.BlockMapDesc();

                String name = reader.nextName();

                if (name.equals(TAG_COLOR)) {
                    d.dye = DyeHelper.DyeType.getDyeFromTag(reader.nextString());
                } else if (name.equals(TAG_NAME)) {
                    d.name = reader.nextString();
                } else if (name.equals(TAG_META)) {
                    d.meta = reader.nextInt();
                } else {
                    reader.skipValue();
                }
                reader.endObject();

                if (d.dye != DyeHelper.DyeType.INVALID && d.name != null && d.meta != -1)
                    desc.blockMap[d.dye.ordinal()] = d;
            }
        }
        reader.endObject();
    }

    private void readMap(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        String name;
        reader.beginObject();
        while (reader.hasNext()) {
            name = reader.nextName();
            if (name.equals(TAG_REFNAME))
                desc.refname = reader.nextString();
            else if (name.equals(TAG_ASSOC))
                desc.associative = reader.nextBoolean();
            else if (name.equals(TAG_ORIGIN))
                readOrigin(desc, reader);
            else if (name.equals(TAG_SIMPLE))
                readSimple(desc, reader);
            else if (name.equals(TAG_VANILLA))
                readVanilla(desc, reader);
            else if (name.equals(TAG_FULL_META))
                readFullMeta(desc, reader);
            else if (name.equals(TAG_FULL_BLOCK))
                readFullBlock(desc, reader);
            else if (name.equals(TAG_BLOCKNAME))
                desc.blockName = reader.nextString();
            else
                reader.skipValue();
        }
        reader.endObject();
    }

    private void readMod(JsonReader reader) throws IOException {

        DyeableModInfo modInfo = new DyeableModInfo();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(TAG_MODID)) {
                modInfo.modid = reader.nextString();
            } else if (name.equals(TAG_MAPS)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    DyeableBlockDesc desc = new DyeableBlockDesc();
                    readMap(desc, reader);
                    if (desc.type != DyeableBlockDesc.MapType.INVALID)
                        modInfo.mappings.add(desc);
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        if (modInfo.modid != null && !modInfo.modid.equals(""))
            cfgArray.add(modInfo);
    }

    private void readMods(JsonReader reader) throws IOException {

        reader.beginArray();
        while (reader.hasNext())
            readMod(reader);
        reader.endArray();
    }

    private void readBlacklists(JsonReader reader) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(TAG_MODS)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    String mod = reader.nextString();
                    modBlacklist.add(mod);
                }
                reader.endArray();
            }
            else if (name.equals(TAG_BLOCKS)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    String blockname = reader.nextString();
                    blockBlacklist.add(blockname);
                }
                reader.endArray();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    public void load(File configDir) {

        InputStream inStream;

        inStream = getClass().getResourceAsStream(ASSET_PATH + "/" + VANILLA_FILENAME);
        if (inStream == null) {
            LogHelper.warn("Could not find " + VANILLA_FILENAME);
        } else {
            LogHelper.info("Loading " + ASSET_PATH + "/" + VANILLA_FILENAME);
            loadFromStream(inStream);
        }

        inStream = getClass().getResourceAsStream(ASSET_PATH + "/" + MODDED_FILENAME);
        if (inStream == null) {
            LogHelper.warn("Could not find " + MODDED_FILENAME);
        } else {
            LogHelper.info("Loading " + ASSET_PATH + "/" + MODDED_FILENAME);
            loadFromStream(inStream);
        }

        /* Load the user configuration last */
        loadUserConfig(configDir);

    }

    private void loadUserConfig(File configDir) {

        try {
            JsonReader reader = new JsonReader(new FileReader(configDir.toPath() + "/" + USERCUSTOM_FILENAME));
            LogHelper.info("Loading " + USERCUSTOM_FILENAME);
            load(reader);
        } catch (FileNotFoundException ignored) {
            /* Create a blank one for the user to edit */
            writeEmptyUserConfig(configDir);
        }
    }

    private void writeEmptyUserConfig(File configDir) {

        JsonWriter writer;
        try
        {
            writer = new JsonWriter(new FileWriter(configDir.toPath() + "/" + USERCUSTOM_FILENAME));
            writer.setIndent("    ");
            writer.beginObject();

            writer.name(TAG_BLACKLIST);
            writer.beginObject();
            writer.name(TAG_MODS);
            writer.beginArray();
            writer.endArray();
            writer.name(TAG_BLOCKS);
            writer.beginArray();
            writer.endArray();
            writer.endObject();

            writer.name(TAG_MODS);
            writer.beginArray();
            writer.endArray();

            writer.endObject();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadFromStream(InputStream in) {

        if (in == null)
            return;

        JsonReader reader = new JsonReader(new InputStreamReader(in));
        load(reader);
    }

    private void load(JsonReader reader) {

        if (reader == null)
            return;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(TAG_MODS)) {
                    readMods(reader);
                } else if (name.equals(TAG_BLACKLIST)) {
                    readBlacklists(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            reader.close();
        } catch (IllegalStateException expected) {
            expected.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
}
