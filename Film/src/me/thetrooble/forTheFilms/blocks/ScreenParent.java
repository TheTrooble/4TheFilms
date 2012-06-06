package me.thetrooble.forTheFilms.blocks;
 

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
public class ScreenParent extends GenericCubeCustomBlock
{
    public ScreenParent(Plugin plugin, Texture texture,String name, int LightLevel, boolean BlocksLight, int[] id)
    {
    	super(plugin, name,BlocksLight, new GenericCubeBlockDesign(plugin,texture, id));
        setLightLevel(LightLevel);
    }
}