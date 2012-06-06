package Blocks;
 

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

public class FlamingParent extends GenericCubeCustomBlock
{
    public FlamingParent(Plugin plugin, Texture texture,String name,int arg[])
    {
        super(plugin,name, 87,new GenericCubeBlockDesign(plugin,texture,arg));
        isOpaque();
        setLightLevel(0);
    }
}