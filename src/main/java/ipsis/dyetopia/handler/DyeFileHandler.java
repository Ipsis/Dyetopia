package ipsis.dyetopia.handler;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ipsis.dyetopia.manager.dyeableblocks.config.*;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;

import java.io.*;

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

    private static final DyeFileHandler instance = new DyeFileHandler();
    public static DyeFileHandler getInstance() { return instance; }

    private static final String TAG_MODID = "id";
    private static final String TAG_MODS = "mods";
    private static final String TAG_MAPS = "maps";
    private static final String TAG_REFNAME = "refname";
    private static final String TAG_ASSOC = "assoc";
    private static final String TAG_ORIGIN = "origin";
    private static final String TAG_SIMPLE = "simple";
    private static final String TAG_VANILLA = "vanilla";
    private static final String TAG_FULL_META = "fullmeta";
    private static final String TAG_FULL_BLOCK = "fullblock";
    private static final String TAG_NAME = "name";
    private static final String TAG_ATTR = "meta";
    private static final String TAG_COLOR = "color";
    private static final String TAG_BASENAME = "blockname";
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

    private ModObjectDesc readOrigin(JsonReader reader) throws IOException {

        int originAttr = -1;
        String originName = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(TAG_NAME))
                originName = reader.nextString();
            else if (name.equals(TAG_ATTR))
                originAttr = reader.nextInt();
            else
                reader.skipValue();
        }
        reader.endObject();

        return new ModObjectDesc(originName, originAttr);
    }

    private BlockDescBase readSimple(JsonReader reader) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {
                reader.skipValue();
        }
        reader.endObject();

        BlockDescSimple desc = new BlockDescSimple();
        desc.setVanillaOrder(false);
        return desc;
    }

    private BlockDescBase readVanilla(JsonReader reader) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {
                reader.skipValue();
        }
        reader.endObject();

        return new BlockDescSimple();
    }

    private BlockDescBase readFullMeta(JsonReader reader) throws IOException {

        BlockDescAttrMap desc = new BlockDescAttrMap();
        reader.beginArray();
        int i = 0;
        while (reader.hasNext()) {
            desc.setAttrMapEntry(DyeHelper.DyeType.getDye(i), reader.nextInt());
        }
        reader.endArray();
        return desc;
    }

    private BlockDescBase readFullBlock(JsonReader reader) throws IOException {

        BlockDescFull desc = new BlockDescFull();
        reader.beginArray();
        while (reader.hasNext()) {

            DyeHelper.DyeType dye = DyeHelper.DyeType.INVALID;
            String blockName = null;
            int blockAttr = -1;
            reader.beginObject();
            while (reader.hasNext()) {

                String name = reader.nextName();
                if (name.equals(TAG_COLOR))
                    dye = DyeHelper.DyeType.getDyeFromTag(reader.nextString());
                else if (name.equals(TAG_NAME))
                    blockName = reader.nextString();
                else if (name.equals(TAG_ATTR))
                    blockAttr = reader.nextInt();
                else
                    reader.skipValue();
            }
            reader.endObject();
            desc.addEntry(blockName, blockAttr, dye);
        }
        reader.endArray();
        return desc;
    }

    private void readMap(ModInfo modinfo, JsonReader reader) throws IOException {

        String name;
        BlockDescBase desc = null;
        String refname = null;
        String basename = null;
        boolean associative = false;
        ModObjectDesc origin = new ModObjectDesc();

        reader.beginObject();
        while (reader.hasNext()) {
            name = reader.nextName();
            if (name.equals(TAG_REFNAME))
                refname = reader.nextString();
            else if (name.equals(TAG_ASSOC))
                associative = reader.nextBoolean();
            else if (name.equals(TAG_ORIGIN))
                origin = readOrigin(reader);
            else if (name.equals(TAG_SIMPLE))
                desc = readSimple(reader);
            else if (name.equals(TAG_VANILLA))
                desc = readVanilla(reader);
            else if (name.equals(TAG_FULL_META))
                desc = readFullMeta(reader);
            else if (name.equals(TAG_FULL_BLOCK))
                desc = readFullBlock(reader);
            else if (name.equals(TAG_BASENAME))
                basename = reader.nextString();
            else
                reader.skipValue();
        }
        reader.endObject();

        if (desc == null)
            return;

        desc.refname = refname;
        desc.associative = associative;

        if (basename != null && !basename.equals("") && desc instanceof BlockDescSimple)
            ((BlockDescSimple) desc).setBaseName(basename);

        if (origin.isValid())
            desc.setOrigin(origin);

        modinfo.addMapping(desc);
    }

    private void readMod(JsonReader reader) throws IOException {

        ModInfo modinfo = new ModInfo();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(TAG_MODID)) {
                modinfo.setModid(reader.nextString());
            } else if (name.equals(TAG_MAPS)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    readMap(modinfo, reader);
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        if (modinfo.hasModid())
            DyeableBlocksConfigManager.getInstance().addMod(modinfo.getModid(), modinfo);
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
                    LogHelper.info("Blacklist mod " + mod);
                }
                reader.endArray();
            } else if (name.equals(TAG_BLOCKS)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    String blockname = reader.nextString();
                    LogHelper.info("Blacklist: block " + blockname);
                    DyeableBlocksConfigManager.getInstance().addBlockToBlacklist(blockname);
                }
                reader.endArray();
            } else if (name.equals(TAG_REFNAME)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    String refname = reader.nextString();
                    LogHelper.info("Blacklist: refname " + refname);
                    DyeableBlocksConfigManager.getInstance().addRefnameToBlacklist(refname);
                }
                reader.endArray();
            } else {
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
