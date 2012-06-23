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
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase(); 
 
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }
	if (commandName.equals("setskin")){
		String skin;
		SpoutPlayer splayer;
    	if(args.length>1){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		splayer = (SpoutPlayer) player;
    		skin =args[1];
    	}
    	else{
    		Player player = (Player) sender;
    		splayer = (SpoutPlayer) player;
    		skin = args[0];
    	}
    	try{
    		splayer.setSkin(skin);}
    	catch(UnsupportedOperationException e){     	
    		log.info(splayer.getName()+"entered an invalid URL, all skin URL's MUST end in .png");
    	}
    	return true;
	}     	
	if (commandName.equals("resetskin")){
    	if(args.length>0){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.resetSkin();
    	}
    	else{
    		Player player = (Player) sender;
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.resetSkin();
    	}
    	return true;
	}
	if (commandName.equals("stealskin")){
		if(args.length>1){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.setSkin("http://s3.amazonaws.com/MinecraftSkins/"+args[1]+".png");
    	}
    	else{
    		Player player = (Player) sender;
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.setSkin("http://s3.amazonaws.com/MinecraftSkins/"+args[0]+".png");
    	}
    	return true;
	}

	//Cape Commands
	
	if (commandName.equals("setcape")){
		String cape;
		SpoutPlayer splayer;
    	if(args.length>1){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		splayer = (SpoutPlayer) player;
    		cape = args[1];
    	}
    	else{
    		Player player = (Player) sender;
    		splayer = (SpoutPlayer) player;
    		cape = args[0];
    	}try{
    		splayer.setCape(cape);}
    	catch(UnsupportedOperationException e){     	
    		log.info(splayer.getName()+"entered an invalid URL, all skin URL's MUST end in .png");
    	}
    	return true;
	}
	if (commandName.equals("resetcape")){
    	if(args.length>0){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.resetCape();
    	}
    	else{
    		Player player = (Player) sender;
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.resetCape();
    	}
    	return true;
	}

	if (commandName.equals("invisible")){
		Player player;
		if(args.length>0){
		player = Bukkit.getServer().getPlayer(args[0]);
		SpoutPlayer splayer = (SpoutPlayer) player;
		splayer.setSkin(invis);
		splayer.setCape(invis);
		splayer.hideTitle();
	}
	else{
		player = (Player) sender;
		SpoutPlayer splayer = (SpoutPlayer) player;
		splayer.setSkin(invis);
		splayer.setCape(invis);
		splayer.hideTitle();
	}
		return true;
	}
	if (commandName.equals("resetplayer")){
		if(args.length>0){
		Player player = Bukkit.getServer().getPlayer(args[0]);
		SpoutPlayer splayer = (SpoutPlayer) player;
		splayer.resetSkin();
		splayer.resetCape();
		splayer.resetTitle();
	}
	else{
		Player player = (Player) sender;
		SpoutPlayer splayer = (SpoutPlayer) player;
		splayer.resetSkin();
		splayer.resetCape();
		splayer.resetTitle();
	}
		return true;
	}
// {{ Name Commands

	
	if (commandName.equals("setname")){
    	if(args.length>1){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.setTitle(args[1]);
    	}
    	else{
    		Player player = (Player) sender;
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.setTitle(args[0]);
    	}
    	return true;
	}
	if (commandName.equals("resetname")){
    	if(args.length>0){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.resetTitle();
    	}
    	else{
    		Player player = (Player) sender;
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.resetTitle();
    	}
    	return true;
	}
	if (commandName.equals("hidename")){
    	if(args.length>0){
    		Player player = Bukkit.getServer().getPlayer(args[0]);
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.hideTitle();
    	}
    	else{
    		Player player = (Player) sender;
    		SpoutPlayer splayer = (SpoutPlayer) player;
    		splayer.hideTitle();
    	}
    	return true;
	}
	if (commandName.equals("setchar")){
		Player player;
    	if(args.length>1){
    		player = Bukkit.getServer().getPlayer(args[0]);
    	}
    	else{
    		player = (Player) sender;
    	}
		SpoutPlayer splayer = (SpoutPlayer)player;
		if(plugin.characters.getString("Characters." + args[0] + ".Name")!=null){
			if(plugin.characters.getString("Characters." + args[0] + ".Name").equals("none"))
			{
				splayer.hideTitle();
			}else{
				splayer.setTitle(plugin.characters.getString("Characters." + args[0] + ".Name"));
			}
		}
		if(plugin.characters.getString("Characters." + args[0] + ".Skin")!=null)
			splayer.setSkin(plugin.characters.getString("Characters." + args[0] + ".Skin"));
		if(plugin.characters.getString("Characters." + args[0] + ".Cape")!=null)
			splayer.setCape(plugin.characters.getString("Characters." + args[0] + ".Cape"));
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
		Player player;
    	if(args.length>1){
    		player = Bukkit.getServer().getPlayer(args[0]);
    	}
    	else{
    		player = (Player) sender;
    	}
		SpoutPlayer splayer = (SpoutPlayer)player;
		splayer.setCape(invis);
		splayer.setTitle("Steve");
		splayer.setSkin("http://www.minecraft.net/images/char.png");
	}
	if (commandName.equals("hidecape")){
		Player player;
    	if(args.length>1){
    		player = Bukkit.getServer().getPlayer(args[0]);
    	}
    	else{
    		player = (Player) sender;
    	}
		SpoutPlayer splayer = (SpoutPlayer)player;
		splayer.setCape(invis);
	}        		
	return false;
}
    Logger log; 
}
