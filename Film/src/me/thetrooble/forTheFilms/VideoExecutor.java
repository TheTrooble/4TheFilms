package me.thetrooble.forTheFilms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VideoExecutor implements CommandExecutor {
	public VideoExecutor(ForTheFilms plugin){
		this.plugin=plugin;
	}
	private ForTheFilms plugin;
	public static Player camera = null;
	public Sign sign;
	public String proj = "Movie";
	public Location marker;
	public Material preScene;
	public byte preSceneData;
	public static Player getCam(){
		return camera;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase(); 
 
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }
        if (commandName.equals("setcam")){
        	if(camera!=null){
        		camera.sendMessage(args[0] + "has replaced you as the camera");
        	}
        camera=Bukkit.getServer().getPlayer(args[0]);
        camera.sendMessage("You are the new camera");
        return true;
        }
        if (commandName.equals("setproject")){
        proj=args[0];
        plugin.getServer().broadcastMessage("Project set to " + args[0]);
        return true;
        }
        if (commandName.equals("scene")) {
            Location loc = camera.getLocation();
            World w = loc.getWorld();
            float direction = loc.getYaw();
            direction=(direction*-1);
            marker = new Location(w,loc.getX(),loc.getY() + 1,loc.getZ()+1);
            byte facing=0x0;
            if(direction>315||direction<45){
            marker = new Location(w,loc.getX(),loc.getY() + 1,loc.getZ()+1);
            facing=0x8;
            }
            if(direction>75&&direction<135)
            {
            marker = new Location(w,loc.getX()+1,loc.getY() + 1,loc.getZ());
            facing=0x4;
            }
            if(direction>135&&direction<225)
            {
            marker = new Location(w,loc.getX(),loc.getY() + 1,loc.getZ()-1);
            facing=0x0;
            }
            if(direction>225&&direction<315)
            {
            marker = new Location(w,loc.getX()-1,loc.getY() + 1,loc.getZ());
            facing=0xC;
            }

			Block b = w.getBlockAt(marker);
			preScene = b.getType();
			preSceneData=b.getData();
            b.setTypeId(63);
            b = w.getBlockAt(marker);
            sign = (Sign)b.getState();
            sign.setLine(0,proj);
            
            if (args.length>0){
            sign.setLine(1, "Scene " + args[0]);
            }
            if(args.length>1){
            sign.setLine(2, "Take " + args[1]);
            }
            if (args.length>2){
            sign.setLine(3,args[2]);
            }
            sign.setRawData(facing);
            sign.update();
            return true;
        }
        if (commandName.equals("mark")||commandName.equals("action")){
            Block b = camera.getWorld().getBlockAt(marker);
            b.setType(preScene);
            b.setData(preSceneData);
            return true;
        }
    	return false;
	}
}
