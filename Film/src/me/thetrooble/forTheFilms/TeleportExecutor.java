package me.thetrooble.forTheFilms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportExecutor implements CommandExecutor {
	Player player1;
	Player player2;
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase(); 
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }
        
     	if(args.length>1){
     		player1 = Bukkit.getServer().getPlayer(args[0]);
     		player2 = Bukkit.getServer().getPlayer(args[1]);
     	}
     	else{
     		player1 = (Player)sender;
     		player2 = Bukkit.getServer().getPlayer(args[0]);
     	}
     	Location loc1 = player1.getLocation();
     	Location loc2 = player2.getLocation();
     	
     	
	 if (commandName.equals("tp")||commandName.equals("tele")||commandName.equals("teleport")){
     	player1.teleport(loc2);
     	return true;
     }
     if (commandName.equals("tphere")||commandName.equals("tpme")||commandName.equals("tph")){
     	player2.teleport(loc1);
     	return true;
     }
     if (commandName.equals("swap")){
     	player1.teleport(loc2);
     	player2.teleport(loc1);
     	return true;
     }
     if (commandName.equals("swapboom")||commandName.equals("boomswap")){
     	player1.getWorld().createExplosion(loc1,0);
     	player2.getWorld().createExplosion(loc2,0);
     	player1.teleport(loc2);
     	player2.teleport(loc1);
     	return true;
     }
     if (commandName.equals("tpboom")||commandName.equals("boomtp")||commandName.equals("teleboom")){
     	player1.getWorld().createExplosion(loc1,0);
     	player2.getWorld().createExplosion(loc2,0);
     	player1.teleport(loc2);
     	return true;
     }
     return false;
	}
}
