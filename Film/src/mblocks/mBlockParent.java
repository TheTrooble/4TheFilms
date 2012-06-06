package mblocks;
 

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

public class mBlockParent extends GenericCubeCustomBlock
{
    public mBlockParent(Plugin plugin, Texture texture,String name, int[] id)
    {
    	super(plugin, name, new GenericCubeBlockDesign(plugin,texture, id));
    }
}