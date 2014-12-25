package ipsis.dyetopia.handler;

import com.google.gson.stream.JsonReader;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlockDesc;
import ipsis.dyetopia.util.DyeHelper;

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

    public static ArrayList<DyeableBlockDesc> cfgArray = new ArrayList<DyeableBlockDesc>();

    private static final String TAG_DYEBLOCK = "dyeblocks";
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


    private static boolean doesFileExist(String file) {

        /** EE3 Serialization Helper dataFileExist */

        //if (FMLCommonHandler.instance().getMinecraftServerInstance() == null)
        //    return false;

        File configFile = new File(file);
        if (configFile.exists() && configFile.isFile())
            return true;

        return false;
    }

    private static void readOrigin(DyeableBlockDesc desc, JsonReader reader) throws IOException {

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

    private static void readSimple(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        desc.type = DyeableBlockDesc.MapType.SIMPLE;
        reader.beginObject();
        while (reader.hasNext()) {
                reader.skipValue();
        }
        reader.endObject();
    }

    private static void readVanilla(DyeableBlockDesc desc, JsonReader reader) throws IOException {

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

    private static void readFullMeta(DyeableBlockDesc desc, JsonReader reader) throws IOException {

        desc.type = DyeableBlockDesc.MapType.FULL_META;
        desc.initMetaMap();
        reader.beginArray();
        int i = 0;
        while (reader.hasNext()) {
            desc.setMetaMap(i++, reader.nextInt());
        }
        reader.endArray();
    }

    private static void readFullBlock(DyeableBlockDesc desc, JsonReader reader) throws IOException {

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

    public static void load(String jsonFile) {

        if (!doesFileExist(jsonFile))
            return;

        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(jsonFile));
            reader.beginObject();
            String name = reader.nextName();
            if (name.equals(TAG_DYEBLOCK)) {
                reader.beginArray();
                while (reader.hasNext()) {

                    DyeableBlockDesc desc = new DyeableBlockDesc();
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

                    if (desc.type != DyeableBlockDesc.MapType.INVALID)
                        cfgArray.add(desc);
                }
                reader.endArray();
            }

            reader.endObject();
            reader.close();

        } catch (FileNotFoundException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
}
