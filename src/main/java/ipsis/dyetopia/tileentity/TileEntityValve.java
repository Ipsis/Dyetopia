package ipsis.dyetopia.tileentity;

import ipsis.dyetopia.util.TankType;
 import net.minecraft.nbt.NBTTagCompound;
 import net.minecraft.network.NetworkManager;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
 import net.minecraftforge.fluids.Fluid;
 import net.minecraftforge.fluids.FluidStack;
 import net.minecraftforge.fluids.FluidTankInfo;
 import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityValve extends TileEntityMultiBlockBase implements IFluidHandler{

     private Color color;

     public TileEntityValve() {

         this.color = Color.NONE;
     }

     public Color getColor() { return this.color; }

     public void setColor(Color color) {
         this.color = color; }

     private TankType getTankFromColor() {

         switch (this.color) {
             case RED:
                 return TankType.RED;
             case YELLOW:
                 return TankType.YELLOW;
             case BLUE:
                 return TankType.BLUE;
             case WHITE:
                 return TankType.WHITE;
             case PURE:
                 return TankType.PURE;
             default:
                 return null;
         }
     }

     /****************
      * IFluidHandler
      * This just passes through to the master block (if it exists)
      */
     @Override
     public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

         TileEntityMultiBlockMaster m = this.getMasterTE();
         if (m != null && m instanceof ITankHandler && getTankFromColor() != null)
             return ((ITankHandler)m).fill(getTankFromColor(), from, resource, doFill);

         return 0;
     }

     @Override
     public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

         TileEntityMultiBlockMaster m = this.getMasterTE();
         if (m != null && m instanceof ITankHandler && getTankFromColor() != null)
             return ((ITankHandler)m).drain(getTankFromColor(), from, resource, doDrain);

         return null;
     }

     @Override
     public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

         TileEntityMultiBlockMaster m = this.getMasterTE();
         if (m != null && m instanceof ITankHandler && getTankFromColor() != null)
             return ((ITankHandler) m).drain(getTankFromColor(), from, maxDrain, doDrain);

         return null;
     }

     @Override
     public boolean canFill(ForgeDirection from, Fluid fluid) {

         TileEntityMultiBlockMaster m = this.getMasterTE();
         if (m != null && m instanceof ITankHandler && getTankFromColor() != null)
             return ((ITankHandler)m).canFill(getTankFromColor(), from, fluid);

         return false;
     }

     @Override
     public boolean canDrain(ForgeDirection from, Fluid fluid) {

         TileEntityMultiBlockMaster m = this.getMasterTE();
         if (m != null && m instanceof ITankHandler && getTankFromColor() != null)
             return ((ITankHandler)m).canDrain(getTankFromColor(), from, fluid);

         return false;
     }

     @Override
     public FluidTankInfo[] getTankInfo(ForgeDirection from) {

         TileEntityMultiBlockMaster m = this.getMasterTE();
         if (m != null && m instanceof ITankHandler) {
             FluidTankInfo[] t = ((ITankHandler) m).getTankInfo(from);
             return t;
         }

         return null;
     }

     /**
      * NBT and description packet
      */
     @Override
     public void writeToNBT(NBTTagCompound nbttagcompound) {
         super.writeToNBT(nbttagcompound);

         nbttagcompound.setInteger("Color", this.color.ordinal());
     }

     @Override
     public void readFromNBT(NBTTagCompound nbttagcompound) {
         super.readFromNBT(nbttagcompound);

         color = Color.getColor(nbttagcompound.getInteger("Color"));
     }

     @Override
     public Packet getDescriptionPacket() {

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         writeToNBT(nbttagcompound);
         return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
     }

     @Override
     public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

         readFromNBT(pkt.func_148857_g());
         worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
     }

     public static enum Color {
         NONE,
         RED,
         YELLOW,
         BLUE,
         WHITE,
         PURE;

         public static Color VALID_COLORS[] = { NONE, RED, YELLOW, BLUE, WHITE, PURE };

         public static Color getColor(int id) {
             if (id < 0 || id >= VALID_COLORS.length)
                 return NONE;

             return VALID_COLORS[id];
         }
     }
 }
