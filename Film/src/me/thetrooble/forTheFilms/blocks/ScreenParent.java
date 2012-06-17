package me.thetrooble.forTheFilms.blocks;
 

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
public class ScreenParent extends GenericCubeCustomBlock implements Listener
{
    public ScreenParent(Plugin plugin, Texture texture,String name, int LightLevel, boolean BlocksLight, int[] id)
    {
    	super(plugin, name,BlocksLight, new GenericCubeBlockDesign(plugin,texture, id));
        setLightLevel(LightLevel);
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
    	if (command.equals("switch")){
    		this.setName("Test");
    	}
		return false;
    	
    }
}