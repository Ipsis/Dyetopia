package com.ipsis.dyetopia.tileentity;

import cofh.util.BlockHelper;
import cofh.util.position.BlockPosition;
import com.ipsis.dyetopia.block.BlockDYTMultiBlock;
import com.ipsis.dyetopia.manager.MultiBlockPatternManager;
import com.ipsis.dyetopia.util.multiblock.MultiBlockPattern;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMultiBlockMaster extends TileEntityMultiBlockBase {

    private int tickCount;
    private static final int CHECK_VALID_TICKS = 40;
    private MultiBlockPattern pattern = MultiBlockPatternManager.getPattern(MultiBlockPatternManager.Type.SQUEEZER);
    private boolean structureValid;

    public TileEntityMultiBlockMaster() {
        super(true);
        this.structureValid = false;
    }

    /**
     * Processing
     */
    private boolean validateStructure() {

        for (int slice = 0; slice < pattern.getSlices(); slice++) {
            for (int row = 0; row < pattern.getRows(); row++) {
                for (int col = 0; col < pattern.getCols(); col++) {

                    BlockPosition p = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.getDirectionFacing());
                    p.y += (slice - 1);
                    p.moveBackwards(row);

                    /* This needs to be flipped as the pattern is looking AT the forward facing side */
                    if (col == 0)
                        p.moveRight(1);
                    else if (col == 2)
                        p.moveLeft(1);

                    ItemStack itemStack = pattern.getItemStackAt(slice, row, col);
                    Block b = this.worldObj.getBlock(p.x, p.y, p.z);

                    if (itemStack != null && !BlockHelper.isEqual(b, Block.getBlockFromItem(itemStack.getItem())))
                        return false;
                }
            }
        }

        return true;
    }

    private void updateStructure(boolean doForm) {

        for (int slice = 0; slice < pattern.getSlices(); slice++) {
            for (int row = 0 ; row < pattern.getRows(); row++) {
                for (int col = 0; col < pattern.getCols(); col++) {

                    BlockPosition p = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.getDirectionFacing());
                    p.y += (slice - 1);
                    p.moveBackwards(row);

                    if (col == 0)
                        p.moveRight(1);
                    else if (col == 2)
                        p.moveLeft(1);

                    Block b = this.worldObj.getBlock(p.x, p.y, p.z);
                    if (b instanceof BlockDYTMultiBlock) {
                        TileEntity te = this.worldObj.getTileEntity(p.x, p.y, p.z);
                        if (te instanceof TileEntityMultiBlockBase) {
                            if (doForm)
                                ((TileEntityMultiBlockBase)te).setMaster(this);
                            else
                                ((TileEntityMultiBlockBase)te).setMaster(null);
                            this.worldObj.markBlockForUpdate(p.x, p.y, p.z);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity() {

        if (worldObj.isRemote)
            return;

        this.tickCount++;

        if ((this.tickCount % CHECK_VALID_TICKS) == 0) {
            boolean isNowValid = validateStructure();
            if (this.structureValid != isNowValid) {
                updateStructure(isNowValid);
                this.structureValid = isNowValid;
            }
        }
    }

    @Override
    public void breakStructure() {
        if (this.structureValid) {
            updateStructure(false);
            this.structureValid = false;
        }
    }
}
