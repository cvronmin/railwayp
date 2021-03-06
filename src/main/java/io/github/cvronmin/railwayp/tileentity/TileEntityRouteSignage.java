package io.github.cvronmin.railwayp.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.cvronmin.railwayp.util.TextComponentUtil;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityRouteSignage extends TileEntityBanner{
	//public final ITextComponent[] stationText = new ITextComponent[] {new TextComponentString(""), new TextComponentString("")};
	//public final ITextComponent[] nextText = new ITextComponent[] {new TextComponentString(""), new TextComponentString("")};
    private NBTTagList stations;
    private List<Station> stationList;
    private int baseColor;
    private boolean field_175119_g;
    /** A list of all patterns stored on this banner. */
    private List<EnumUnifiedBannerPattern> patternList;
    /** A list of all the color values stored on this banner. */
    private List<Integer> colorList;
    private int routeColor;
	private String routeColorEncoded;
    /** 0 = Left, 1 = Right*/
    private byte direction;
    /** This is a String representation of this banners pattern and color lists, used for texture caching. */
    private String patternResourceLocation;
    private void decodeColor(){
    	if(routeColorEncoded.length() <= 6)
    	if(!routeColorEncoded.startsWith("0x")){
    		try{
    			routeColor = Integer.decode("0x" + routeColorEncoded);
    		}
    		catch(Exception e){
    			routeColor = 0;
    		}
    	}
    	else{
    		try{
    			routeColor = Integer.decode(routeColorEncoded);
    		}
    		catch(Exception e){
    			routeColor = 0;
    		}
    	}
    }

    public void setItemValues(ItemStack stack)
    {

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10))
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("BlockEntityTag");
            if (nbttagcompound.hasKey("Stations"))
            {
                this.stations = (NBTTagList)nbttagcompound.getTagList("Stations", 10).copy();
            }
            if(nbttagcompound.hasKey("LineColor", 8)){
            	routeColorEncoded = nbttagcompound.getString("LineColor");
            	decodeColor();
            }
            if (nbttagcompound.hasKey("Direction", 1)) {
				direction = nbttagcompound.getByte("Direction");
			}
            /*for (int i = 0; i < 2; ++i)
            {
            	if(nbttagcompound.hasKey("StationText" + (i + 1))){
                String s = nbttagcompound.getString("StationText" + (i + 1));


                    ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);
                    this.stationText[i] = ichatcomponent;

            	}
            }
            for (int i = 0; i < 2; ++i)
            {
            	if(nbttagcompound.hasKey("NextText" + (i + 1))){
                String s = nbttagcompound.getString("NextText" + (i + 1));


                    ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);
                    this.nextText[i] = ichatcomponent;

            	}
            }*/
        }
        this.stationList = null;
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = "";
        this.field_175119_g = true;
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        if((routeColor >= 0x0 && routeColor < 0x1000000)){
        	compound.setString("LineColor", Integer.toHexString(this.routeColor));
        }
        if(direction >= 0 && direction <= 2){
        	compound.setByte("Direction", direction);
        }
        if (stations != null)
        {
        	compound.setTag("Stations", stations);
        }
        /*for (int i = 0; i < 2; ++i)
        {
            String s = ITextComponent.Serializer.componentToJson(this.stationText[i]);
            compound.setString("StationText" + (i + 1), s);
        }
        for (int i = 0; i < 2; ++i)
        {
            String s = ITextComponent.Serializer.componentToJson(this.nextText[i]);
            compound.setString("NextText" + (i + 1), s);
        }*/
		return compound;
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
    	routeColorEncoded = compound.getString("LineColor");
    	decodeColor();
        this.direction = compound.getByte("Direction");
        this.stations = compound.getTagList("Stations", 10);
        /*for (int i = 0; i < 2; ++i)
        {
            String s = compound.getString("StationText" + (i + 1));
                ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);
                this.stationText[i] = ichatcomponent;
        }
        for (int i = 0; i < 2; ++i)
        {
            String s = compound.getString("NextText" + (i + 1));
                ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);
                this.nextText[i] = ichatcomponent;
        }*/
        this.stationList = null;
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = null;
        this.field_175119_g = true;
    }

    /**
     * Allows for a specialized description packet to be created. This is often used to sync tile entity data from the
     * server to the client easily. For example this is used by signs to synchronise the text to be displayed.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new SPacketUpdateTileEntity(this.pos, 6, nbttagcompound);
    }

    /**
     * Retrieves the list of patterns for this tile entity. The banner data will be initialized/refreshed before this
     * happens.
     */
    @SideOnly(Side.CLIENT)
    public List getPatternList()
    {
        this.initializeBannerData();
        return this.patternList;
    }
    @SideOnly(Side.CLIENT)
    public List<Station> getStationList()
    {
        this.initializeBannerData();
        return this.stationList;
    }
    public static int getStations(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");
        return nbttagcompound != null && nbttagcompound.hasKey("Stations") ? nbttagcompound.getTagList("Stations", 10).tagCount() : 0;
    }
    /**
     * Retrieves the list of colors for this tile entity. The banner data will be initialized/refreshed before this
     * happens.
     */
    @SideOnly(Side.CLIENT)
    public List getColorList()
    {
        this.initializeBannerData();
        return this.colorList;
    }

    @SideOnly(Side.CLIENT)
    public String func_175116_e()
    {
        this.initializeBannerData();
        return this.patternResourceLocation;
    }

    /**
     * Removes all the banner related data from a provided instance of ItemStack.
     *  
     * @param stack The instance of an ItemStack which will have the relevant nbt tags removed.
     */
    public static void removeBannerData(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");

        if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9))
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);

            if (nbttaglist.tagCount() > 0)
            {
                nbttaglist.removeTag(nbttaglist.tagCount() - 1);

                if (nbttaglist.hasNoTags())
                {
                    stack.getTagCompound().removeTag("BlockEntityTag");

                    if (stack.getTagCompound().hasNoTags())
                    {
                        stack.setTagCompound((NBTTagCompound)null);
                    }
                }
            }
        }
    }

    /**
     * Establishes all of the basic properties for the banner. This will also apply the data from the tile entities nbt
     * tag compounds.
     */
    @SideOnly(Side.CLIENT)
    private void initializeBannerData()
    {
        if (this.stationList == null || this.patternList == null || this.colorList == null || this.patternResourceLocation == null)
        {
            if (!this.field_175119_g)
            {
                this.patternResourceLocation = "";
            }
            else
            {
            	this.stationList = Lists.newArrayList();
                this.patternList = Lists.newArrayList();
                this.colorList = Lists.newArrayList();
                this.patternList.add(EnumUnifiedBannerPattern.BASE);
                this.colorList.add(EnumDyeColor.byDyeDamage(15).getMapColor().colorValue);
                this.patternResourceLocation = "b" + this.baseColor;
                if (this.checkGoodBanner()) {
                    EnumUnifiedBannerPattern enumbannerpattern = EnumUnifiedBannerPattern.LONGRIBBON;

                    if (enumbannerpattern != null)
                    {
                        this.patternList.add(enumbannerpattern);
                        this.colorList.add(this.routeColor);
                        this.patternResourceLocation = this.patternResourceLocation + enumbannerpattern.getPatternID() + this.routeColor;
                    }
                    for (int i = 0; i < stations.tagCount(); i++) {
                        NBTTagCompound nbttagcompound = this.stations.getCompoundTagAt(i);
                        stationList.add(Station.getStationFormCompound(nbttagcompound));
					}
                    /*enumbannerpattern = EnumUnifiedBannerPattern.SPOINT;

                    if (enumbannerpattern != null)
                    {
                        this.patternList.add(enumbannerpattern);
                        this.colorList.add(0);
                        this.patternResourceLocation = this.patternResourceLocation + enumbannerpattern.getPatternID() + 0;
                    }
                    enumbannerpattern = direction == 0  ? EnumUnifiedBannerPattern.LAL : (direction == 2 ? EnumUnifiedBannerPattern.LAR : null);
                    if (enumbannerpattern != null)
                    {
                        this.patternList.add(enumbannerpattern);
                        this.colorList.add(0);
                        this.patternResourceLocation = this.patternResourceLocation + enumbannerpattern.getPatternID() + 0;
                    }*/
				}
            }
        }
    }
    public boolean checkGoodBanner(){
    	boolean flag1 = routeColor >= 0x0 && routeColor < 0x1000000;
    	boolean flag2 = direction >= 0 && direction <= 2;
    	boolean flag3 = stations.tagCount() >= 2;
    	return flag1 && flag2 && flag3;
    }

    public byte getDirection(){
    	return this.direction;
    }

    public int getRouteColor() {
		return routeColor;
	}

	public static class Station{
		public final ITextComponent[] stationName = new ITextComponent[]{new TextComponentString(""),new TextComponentString("")};
		private boolean iAmHere;
		private List<RailLine> interchangeLines = new ArrayList<>();
		public Station(Station station){
			stationName[0] = station.stationName[0];
			stationName[1] = station.stationName[1];
			iAmHere = station.iAmHere;
			interchangeLines = Lists.newArrayList(station.getInterchangeLines());
		}
		public Station(){
			
		}
		public boolean isInterchangeStation() {
			return !getInterchangeLines().isEmpty();
		}
		public boolean amIHere(){
			return iAmHere;
		}
		public Station IAmHere(){
			iAmHere = true;
			return this;
		}
		public Station IAmNotHere(){
			iAmHere = false;
			return this;
		}
		public Station setIfIAmHere(boolean flag){
			iAmHere = flag;
			return this;
		}
		public static Station getStationFormCompound(NBTTagCompound compound){
			Station station = new Station();
			NBTTagList ss = compound.getTagList("Name", 8);
			station.setStationName(ss.tagCount() > 0 ? ss.getStringTagAt(0) : "", ss.tagCount() > 1 ? ss.getStringTagAt(1) : "");
			station.setIfIAmHere(compound.getBoolean("Here"));
			NBTTagList icls = compound.getTagList("InterChangeLines",10);
			for (int i = 0; i < icls.tagCount();i++){
				station.getInterchangeLines().add(new RailLine().readFromCompound(icls.getCompoundTagAt(i)));
			}
			NBTTagCompound ls = compound.getCompoundTag("InterChangeLine");
			if(ls != null && !ls.hasNoTags()){
				station.getInterchangeLines().add(new RailLine().readFromCompound(ls));
			}
			return station;
		}

		public NBTTagCompound writeStationToCompound() {
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagList ss = new NBTTagList();
			ss.appendTag(new NBTTagString(TextComponentUtil.getPureText(stationName[0])));
			ss.appendTag(new NBTTagString(TextComponentUtil.getPureText(stationName[1])));
			compound.setTag("Name", ss);
			compound.setBoolean("Here", iAmHere);

			NBTTagList list = new NBTTagList();
			for (RailLine line : getInterchangeLines()) {
				list.appendTag(line.writeToCompound());
			}
			compound.setTag("InterChangeLines", list);
			return compound;
		}
		public static NBTTagList writeStationsToTagList(List<Station> list){
			NBTTagList compound = new NBTTagList();
			for (Station station : list) {
				compound.appendTag(station.writeStationToCompound());
			}
			return compound;
		}
		public static List<Station> readStationsFromTagList(NBTTagList nbtTagList){
			List<Station> list = Lists.newArrayList();
			for (int i = 0; i < nbtTagList.tagCount(); i++) {
				list.add(getStationFormCompound(nbtTagList.getCompoundTagAt(i)));
			}
			return list;
		}
		public Station setStationName(String name1, String name2){
			this.stationName[0] = new TextComponentString(name1);
			this.stationName[1] = new TextComponentString(name2);
			return this;
		}

		public List<RailLine> getInterchangeLines() {
			return interchangeLines;
		}
	}

	public static class RailLine{
		private int lineColor;
		private final ITextComponent[] lineName = new ITextComponent[]{new TextComponentString(""),new TextComponentString("")};
		public RailLine(){}
		public RailLine(RailLine src){
			lineName[0] = src.lineName[0];
			lineName[1] = src.lineName[1];
			lineColor = src.lineColor;
		}
		public RailLine setLineName(String name1, String name2){
			this.lineName[0] = new TextComponentString(name1);
			this.lineName[1] = new TextComponentString(name2);
			return this;
		}
		public RailLine setLineColor(int color){
			lineColor = color;
			return this;
		}
		public int getLineColor(){
			return lineColor;
		}
		public RailLine setLineColor(String color){
			return setLineColor(decodeColor(color));
		}
		private static int decodeColor(String colorEncoded){
			if(colorEncoded.length() <= 6)
				if(!colorEncoded.startsWith("0x")){
					try{
						return Integer.decode("0x" + colorEncoded);
					}
					catch(Exception e){
						return 0;
					}
				}
				else{
					try{
						return Integer.decode(colorEncoded);
					}
					catch(Exception e){
						return 0;
					}
				}
			return 0;
		}

		public NBTTagCompound writeToCompound(){
			NBTTagCompound ls = new NBTTagCompound();
			NBTTagList list = new NBTTagList();
			list.appendTag(new NBTTagString(TextComponentUtil.getPureText(lineName[0])));
			list.appendTag(new NBTTagString(TextComponentUtil.getPureText(lineName[1])));
			ls.setTag("Name", list);
			ls.setString("Color", Integer.toHexString(lineColor));
			return ls;
		}

		public RailLine readFromCompound(NBTTagCompound compound){
			NBTTagList list = compound.getTagList("Name", 8);
			setLineName(list.tagCount() > 0 ? list.getStringTagAt(0) : "", list.tagCount() > 1 ? list.getStringTagAt(1) : "");
			setLineColor(compound.getString("Color"));
			return this;
		}

		public ITextComponent[] getLineName() {
			return lineName;
		}
	}

	public void setData(byte dir, String color, List<Station> stations2) {
    	this.direction = dir;
    	this.routeColorEncoded = color;
    	this.stationList = stations2;
    	NBTTagList list=new NBTTagList();
    	for (Station station : stations2) {
			list.appendTag(station.writeStationToCompound());
		}
    	this.stations = list;
    	decodeColor();
	}
}
