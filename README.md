#Dyetopia

Fraking Flowers!

#What Is This?

Squeeze colored dyes out vanilla and modded items and mix them to create pure dye.

You can then either
* Use the painter to feed in colored blocks and recolor them
** eg. wool, carpet, stained clay and supported modded blocks
* Use the dye gun to recolor blocks on-the-fly
** Think of a "Wand Of Equal Trade" from Thaumcraft, but with color.

The mods currently supported are:
* Chisel
* Forestry
* MFR
* Railcraft
* Tinkers Construct
* Thermal Expansion
* Extra Utilities

* [Minecraft Forum Thread](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/wip-mods/2262586-dyetopia-fraking-flowers)
* [CurseForge Project Page](http://minecraft.curseforge.com/mc-mods/226037-dyetopia)
* [Website](http://www.zen121381.zen.co.uk/dyetopia/index.shtml)

#Dependencies
This mod requires CoFHLib.
[http://www.curse.com/mc-mods/minecraft/220333-cofhlib]

#Credits
I rely heavily on CoFHLib for help with the GUI code and some other bits and pieces.
[https://github.com/CoFH/CoFHLib]

Railcraft for ideas on how to sync the GUI update messages for tanks.
[https://github.com/CovertJaguar/Railcraft]

#Code From Other Project
This mod uses a small number of classes from other mods.
## CoFH
[https://github.com/CoFH/ThermalFoundation]
The following classes are based off:

* BlockFluidDYT -> BlockFluidCoFHBase

[https://github.com/CoFH/CoFHCore]

The following classes are based off:

* TabEnergy -> TabEnergy from CoFHCore
* TabInfo -> TabInfo from CoFHCore

The following classed are copied:

* IconRegistry
* TabScrolledText

I think I based the fluid textures on the ThermalFoundation ones, but I'm no longer sure!

## Pahimar
[EE3 Repository](https://github.com/pahimar/Equivalent-Exchange-3)

The following classed are copied:

* LogHelper.java

##Tonius
[NEI-Integration](https://github.com/Tonius/NEI-Integration/src/main/java/tonius/neiintegration/RecipeHandlerBase.java)

A tiny piece of code but an important one. How to change the mouse x,y into recipe x,y for use in NEI recipe handlers!
