package me.thetrooble.forTheFilms;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PlayerExecutor implements CommandExecutor {
	public PlayerExecutor(ForTheFilms plugin){
		this.plugin=plugin;
	}
	private ForTheFilms plugin;
	public static String invis = "http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png";
	
	Player player;
	SpoutPlayer splayer;
	String URL;	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase(); 
 
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }
        
        
        if(args.length>1){
        	if(Bukkit.getServer().getPlayer(args[0])!=null)
    		player = Bukkit.getServer().getPlayer(args[0]);
        	else
        	player = (Player)sender;
    		splayer = (SpoutPlayer) player;
    		URL =args[1];
    	}
    	else{
    		Player player = (Player) sender;
    		splayer = (SpoutPlayer) player;
    		URL = args[0];
    	}        
        
        
	if (commandName.equals("setskin")){	
    	try{
    		splayer.setSkin(URL);}
    	catch(UnsupportedOperationException e){     	
    		log.info(splayer.getName()+"entered an invalid URL, all skin URL's MUST end in .png");
    	}
    	return true;
	}     	
	if (commandName.equals("resetskin")){
		splayer.resetSkin();
    	return true;
	}
	if (commandName.equals("stealskin")){
		try{
    	splayer.setSkin("http://s3.amazonaws.com/MinecraftSkins/"+URL+".png");
    	}catch(UnsupportedOperationException e){     	
    		log.info(splayer.getName()+"entered an invalid URL, please ensure the player name is valid");
    	}
    	return true;
	}
	if (commandName.equals("setcape")){
		try{
    		splayer.setCape(URL);
    	}catch(UnsupportedOperationException e){     	
    		log.info(splayer.getName()+"entered an invalid URL, all cape URL's MUST end in .png");
    	}
    	return true;
	}
	if (commandName.equals("resetcape")){
		splayer.resetCape();
    	return true;
	}

	if (commandName.equals("invisible")){
		splayer.setSkin(invis);
		splayer.setCape(invis);
		splayer.hideTitle();
		return true;
	}
	if (commandName.equals("resetplayer")){
		splayer.resetSkin();
		splayer.resetCape();
		splayer.resetTitle();
		return true;
	}
	if (commandName.equals("setname")){
    	splayer.setTitle(URL);
    	return true;
	}
	if (commandName.equals("resetname")){
    	splayer.resetTitle();
    	return true;
	}
	if (commandName.equals("hidename")){
    	splayer.hideTitle();
    	return true;
	}
	if (commandName.equals("setchar")){
		if(plugin.characters.getString("Characters." + URL + ".Name")!=null){
			if(plugin.characters.getString("Characters." + URL + ".Name").equals("none"))
			{
				splayer.hideTitle();
			}else{
				splayer.setTitle(plugin.characters.getString("Characters." + URL + ".Name"));
			}
		}
		if(plugin.characters.getString("Characters." + URL + ".Skin")!=null)
			splayer.setSkin(plugin.characters.getString("Characters." + URL + ".Skin"));
		if(plugin.characters.getString("Characters." + URL + ".Cape")!=null)
			splayer.setCape(plugin.characters.getString("Characters." + URL + ".Cape"));
		else
			splayer.setCape(invis);
		return true;
	}
	if (commandName.equals("setcharname"))
	{
		plugin.reloadCharacters();
		plugin.characters.set("Characters." + args[0] + ".Name", args[1]);
		plugin.saveCharacters();
		return true;
	}
	if (commandName.equals("setcharskin"))
	{
		plugin.reloadCharacters();
		plugin.characters.set("Characters." + args[0] + ".Skin", args[1]);
		plugin.saveCharacters();
		return true;
	}
	if (commandName.equals("setcharcape"))
	{
		plugin.reloadCharacters();
		plugin.characters.set("Characters." + args[0] + ".Cape", args[1]);
		plugin.saveCharacters();
		return true;
	}
	if (commandName.equals("delcharname"))
	{
		plugin.reloadCharacters();
		plugin.characters.set("Characters." + args[0] + ".Name", null);
		plugin.saveCharacters();
		return true;
	}
	if (commandName.equals("delcharskin"))
	{
		plugin.reloadCharacters();
		plugin.characters.set("Characters." + args[0] + ".Skin", null);
		plugin.saveCharacters();
		return true;
	}
	if (commandName.equals("delcharcape"))
	{
		plugin.reloadCharacters();
		plugin.characters.set("Characters." + args[0] + ".Cape", null);
		plugin.saveCharacters();
		return true;
	}
	if (commandName.equals("steve")){
		splayer.setCape(invis);
		splayer.setTitle("Steve");
		splayer.setSkin("http://www.minecraft.net/images/char.png");
	}
	if (commandName.equals("hidecape")){
		splayer.setCape(invis);
	}        		
	return false;
}
    Logger log; 
}
