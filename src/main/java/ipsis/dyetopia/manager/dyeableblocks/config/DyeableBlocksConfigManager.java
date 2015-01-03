package ipsis.dyetopia.manager.dyeableblocks.config;


import ipsis.dyetopia.util.LogHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DyeableBlocksConfigManager {

    public HashMap<String, ModInfo> cfgMap;
    private ArrayList<String> modBlacklist;
    private ArrayList<String> blockBlacklist;
    private ArrayList<String> refnameBlacklist;

    private static DyeableBlocksConfigManager instance = new DyeableBlocksConfigManager();
    public static final DyeableBlocksConfigManager getInstance() { return instance; }

    private DyeableBlocksConfigManager() {
        cfgMap = new HashMap<String, ModInfo>();
        modBlacklist = new ArrayList<String>();
        blockBlacklist = new ArrayList<String>();
        refnameBlacklist = new ArrayList<String>();
    }

    public void addModToBlacklist(String modid) {
        if (!modBlacklist.contains(modid))
            modBlacklist.add(modid);
    }

    public void addBlockToBlacklist(String block) {
        if (!blockBlacklist.contains(block))
            blockBlacklist.add(block);
    }

    public void addRefnameToBlacklist(String refname) {
        if (!refnameBlacklist.contains(refname))
            refnameBlacklist.add(refname);
    }

    public boolean isBlockBlacklisted(String block) {
        return blockBlacklist.contains(block);
    }

    public boolean isRefnameBlacklisted(String modid, String refname) { return refnameBlacklist.contains(modid + ":" + refname); }

    public boolean isModBlacklisted(String modid) { return modBlacklist.contains(modid); }

    public boolean modExists(String modid) {
        return cfgMap.containsKey(modid);
    }

    public ModInfo getMod(String modid) {
        return cfgMap.get(modid);
    }

    public void addMod(String modid, ModInfo info) {
        if (!modExists(modid))
            cfgMap.put(modid, info);
        else
            LogHelper.warn("Duplicate configuration for " + modid);
    }

    public void applyBlacklists() {

        /* Mod blacklist */
        Iterator<Map.Entry<String, ModInfo>> iter = cfgMap.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry<String, ModInfo> entry = iter.next();
            if (isModBlacklisted(entry.getValue().modid)) {
                LogHelper.info("[MOD BLACKLIST] removing all blocks for " + entry.getValue().modid);
                iter.remove();
            }
        }

        /* Refname blacklist (modid:refname) */
        iter = cfgMap.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry<String, ModInfo> entry = iter.next();
            Iterator<BlockDescBase> iter2 = entry.getValue().getMappings().iterator();

            while (iter2.hasNext()) {

                BlockDescBase desc = iter2.next();
                if (DyeableBlocksConfigManager.getInstance().isRefnameBlacklisted(entry.getValue().modid, desc.refname)) {
                    LogHelper.info("[REFNAME BLACKLIST] removing " + entry.getValue().modid + ":" + desc.refname);
                    iter2.remove();
                }
            }
        }
    }
}
