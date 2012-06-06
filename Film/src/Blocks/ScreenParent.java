package Blocks;
 

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import FilmPlugin.FilmPlugin.ForTheFilms; 
public class ScreenParent extends GenericCubeCustomBlock
{
    public ScreenParent(Plugin plugin, Texture texture,String name, int LightLevel, boolean BlocksLight, int[] id)
    {
    	super(ForTheFilms.getInstance(), name,BlocksLight, new GenericCubeBlockDesign(plugin,texture, id));
        setLightLevel(LightLevel);
    }
}