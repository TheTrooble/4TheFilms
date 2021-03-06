package me.thetrooble.forTheFilms;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.thetrooble.forTheFilms.blocks.FlamingParent;
import me.thetrooble.forTheFilms.blocks.ScreenParent;
import me.thetrooble.forTheFilms.blocks.mBlockParent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.CustomBlock;
import java.util.HashMap;
public class ForTheFilms extends JavaPlugin implements Listener
{
	//Blocks
    public static CustomBlock GreenScreen;
    public static CustomBlock RedScreen;
    public static CustomBlock BlueScreen;
    public static CustomBlock GreenTracker;
    public static CustomBlock RedTracker;
    public static CustomBlock BlueTracker;
    public static CustomBlock TardisBottom;
    public static CustomBlock TardisTop;
    public static CustomBlock FlamingFilth;
    public static CustomBlock FlamingOrange;
    public static CustomBlock FlamingBlack;
    public static CustomBlock FlamingPlanks;
    public static CustomBlock FlamingLog;
    public static CustomBlock InvisibleBlock;
    
    //Textures
    public static Texture multiTexture;

	public static String invis = "http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png";
    FileConfiguration blockSettings = null;
    File blockSettingsFile = null;
    FileConfiguration characters = null;
    File charactersFile = null;
    public HashMap<String, Integer> entityID;
    public boolean useSpout;
    public Location loc1;
    public Location loc2;
    private VideoExecutor video;
    private TeleportExecutor teleport;
    private PlayerExecutor playerex;
    public void onEnable()
    {        
        log = Logger.getLogger("Minecraft"); 
        log.info("4TheFilms Enabled");
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(this,this);
        useSpout = false;
        Plugin spout = pluginManager.getPlugin("Spout"); 	
        video = new VideoExecutor(this);
        playerex = new PlayerExecutor(this);
        teleport = new TeleportExecutor();
        doConfig();
        setCommandExecutors();
       
    	
    	
    	if( spout != null ){
            if( !spout.isEnabled() ){
                pluginManager.enablePlugin(spout);}
                if(spout.isEnabled()){
                	log.info("Spout detected, enabling Spout features");
                    useSpout = true;
                    setupTextures();
                    setupBlocks();
                }
            }
        else 
        {
        	log.info("Spout not detected,  Spout features");
        }
    }
 
    public void onDisable()
    {
        log.info("4TheFilms disabled.");
        useSpout = false;
    }
    public void doConfig(){
    	getConfig().addDefault("General.Blocks.Flaming.Planks",false);
    	getConfig().addDefault("General.Blocks.Flaming.Log",false);
    	getConfig().addDefault("General.Blocks.Flaming.Orange",false);
    	getConfig().addDefault("General.Blocks.Flaming.Black",false);
    	getConfig().addDefault("General.Blocks.Flaming.Filth",false);
    	getConfig().addDefault("General.Blocks.Tardis",true);
    	getConfig().addDefault("General.Blocks.Screens.Red",true);
    	getConfig().addDefault("General.Blocks.Screens.Blue",true);
    	getConfig().addDefault("General.Blocks.Screens.Green",true);
    	getConfig().addDefault("General.Blocks.Screens.RedTracker",true);
    	getConfig().addDefault("General.Blocks.Screens.BlueTracker",true);
    	getConfig().addDefault("General.Blocks.Screens.GreenTracker",true);
    	getConfig().options().copyDefaults(true);
    	this.saveConfig();
    	reloadBlockSettings();
    	blockSettings.addDefault("Blocks.Screens.Green.LightLevel",15);
    	blockSettings.addDefault("Blocks.Screens.Green.InhibitsLight",false);
    	blockSettings.addDefault("Blocks.Screens.Red.LightLevel",0);
    	blockSettings.addDefault("Blocks.Screens.Red.InhibitsLight",false);
    	blockSettings.addDefault("Blocks.Screens.Blue.LightLevel",0);
    	blockSettings.addDefault("Blocks.Screens.Blue.InhibitsLight",true);
    	blockSettings.options().copyDefaults(true);//thanks to AlbireoX
    	saveBlockSettings();
    	reloadCharacters();
    	characters.addDefault("Characters.Expendable.Name", "Expendable");
    	characters.addDefault("Characters.Expendable.Skin", "http://www.minecraftskins.info/startrekred.png");
    	characters.addDefault("Characters.Herobrine.Name", "Herobrine");
    	characters.addDefault("Characters.Herobrine.Skin", "http://www.minecraftskins.info/herobrine.png");
    	characters.addDefault("Characters.Steve.Name", "Steve");
    	characters.addDefault("Characters.Steve.Skin","http://www.minecraft.net/images/char.png");
    	characters.addDefault("Characters.Batman.Name", "The God Damn Batman");
    	characters.addDefault("Characters.Batman.Skin", "http://www.minecraftskins.info/batman.png");
    	characters.options().copyDefaults(true);
    	saveCharacters();
    }
    public void setCommandExecutors(){
    	getCommand("action").setExecutor(video);
    	getCommand("mark").setExecutor(video);
    	getCommand("setcam").setExecutor(video);
    	getCommand("setproject").setExecutor(video);
    	getCommand("scene").setExecutor(video);
    	
    	getCommand("tp").setExecutor(teleport);
    	getCommand("teleport").setExecutor(teleport);
    	getCommand("tphere").setExecutor(teleport);
    	getCommand("tpme").setExecutor(teleport);
    	getCommand("tph").setExecutor(teleport);
    	getCommand("swap").setExecutor(teleport);
    	getCommand("boomswap").setExecutor(teleport);
    	getCommand("swapboom").setExecutor(teleport);
    	getCommand("tpboom").setExecutor(teleport);
    	getCommand("boomtp").setExecutor(teleport);
    	getCommand("teleboom").setExecutor(teleport);
    	
    	getCommand("setskin").setExecutor(playerex);
    	getCommand("resetskin").setExecutor(playerex);
    	getCommand("stealskin").setExecutor(playerex);
    	getCommand("setcape").setExecutor(playerex);
    	getCommand("resetcape").setExecutor(playerex);
    	getCommand("invisible").setExecutor(playerex);
    	getCommand("resetplayer").setExecutor(playerex);
    	getCommand("setname").setExecutor(playerex);
    	getCommand("resetname").setExecutor(playerex);
    	getCommand("hidename").setExecutor(playerex);
    	getCommand("setchar").setExecutor(playerex);
    	getCommand("setcharname").setExecutor(playerex);
    	getCommand("setcharskin").setExecutor(playerex);
    	getCommand("setcharcape").setExecutor(playerex);
    	getCommand("delcharname").setExecutor(playerex);
    	getCommand("delcharskin").setExecutor(playerex);
    	getCommand("delcharcape").setExecutor(playerex);
    	getCommand("steve").setExecutor(playerex);
    	getCommand("hidecape").setExecutor(playerex);
    	
    }
    public void reloadBlockSettings() {
    	if (blockSettingsFile == null){
    		blockSettingsFile = new File(getDataFolder(), "blockSettings.yml");
    	}
    	blockSettings = YamlConfiguration.loadConfiguration(blockSettingsFile);
    	InputStream defConfigStream = getResource("blockSettings.yml");
    	if (defConfigStream != null) {
    		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    		blockSettings.setDefaults(defConfig);
    	}  	
    }
    
   public FileConfiguration getBlockSettings() {
	   if (blockSettings == null){
		   reloadBlockSettings();
	   }
	   return blockSettings;
   }
    
   public void saveBlockSettings(){
	   try {
		   blockSettings.save(blockSettingsFile);
	   } catch (IOException ex) {
		   Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + blockSettingsFile, ex);
	   }
   }
   
   public void reloadCharacters() {
  	if (charactersFile == null){
  		charactersFile = new File(getDataFolder(), "characters.yml");
  	}
  	characters = YamlConfiguration.loadConfiguration(charactersFile);
  	InputStream defConfigStream = getResource("characters.yml");
  	if (defConfigStream != null) {
  		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
  		characters.setDefaults(defConfig);
  	}  	
  }
  
   public FileConfiguration getcharacters() {
	   if (characters == null){
		   reloadCharacters();
	   }
	   return characters;
 }
  
   public void saveCharacters(){
	   try {
		   characters.save(charactersFile);
	   } catch (IOException ex) {
		   Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + charactersFile, ex);
	   }
 }
   
   public void setupTextures() {
    	multiTexture = new Texture(this, "http://dl.dropbox.com/s/aaord2ibg0awtmg/terrain.png",256,256,16);
    }
    
    public void setupBlocks() {
    	//Green Screen
    	if(getConfig().getBoolean("General.Blocks.Screens.Green") && GreenScreen==null)
        GreenScreen = new ScreenParent(this,multiTexture,"Green Screen",blockSettings.getInt("Blocks.Screens.Green.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"), new int[]{16,16,16,16,16,16}); 
    	//Green Tracker thanks to agentEE7
    	if(getConfig().getBoolean("General.Blocks.Screens.GreenTracker") && GreenTracker==null)
    	GreenTracker = new ScreenParent(this,multiTexture,"Green Tracker",blockSettings.getInt("Blocks.Screens.Green.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{19,19,19,19,19,19}); 
    	//Red Screen
    	if(getConfig().getBoolean("General.Blocks.Screens.Red") && RedScreen==null)
    	RedScreen = new ScreenParent(this,multiTexture,"Red Screen",blockSettings.getInt("Blocks.Screens.Red.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{17,17,17,17,17,17}); 
    	//Red Tracker
    	if(getConfig().getBoolean("General.Blocks.Screens.RedTracker") && RedTracker==null)
    	RedTracker = new ScreenParent(this,multiTexture,"Red Tracker",blockSettings.getInt("Blocks.Screens.Red.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{20,20,20,20,20,20}); 
    	//Blue Screen
    	if(getConfig().getBoolean("General.Blocks.Screens.Blue") && BlueScreen==null)
        BlueScreen = new ScreenParent(this,multiTexture,"Blue Screen",blockSettings.getInt("Blocks.Screens.Blue.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{18,18,18,18,18,18});
    	//Blue Tracker
    	if(getConfig().getBoolean("General.Blocks.Screens.BlueScreen") && BlueTracker==null)
    	BlueTracker = new ScreenParent(this,multiTexture,"Blue Tracker",blockSettings.getInt("Blocks.Screens.Blue.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{21,21,21,21,21,21});
        
    	//Tardis
        if(getConfig().getBoolean("General.Blocks.Tardis")&&TardisBottom==null){
        TardisBottom= new mBlockParent(this,multiTexture,"Tardis Bottom", new int[]{0,1,1,1,1,0});
        TardisTop= new mBlockParent(this,multiTexture,"Tardis Top", new int[]{0,3,2,3,3,0});
        }       
        
        //Flaming Blocks
        	if(getConfig().getBoolean("General.Blocks.Flaming.Filth")&&FlamingFilth==null)
        FlamingFilth= new FlamingParent(this,multiTexture,"Flaming Filth",new int[]{4,4,4,4,4,4});
        	if(getConfig().getBoolean("General.Blocks.Flaming.Orange")&&FlamingOrange==null)
        FlamingOrange= new FlamingParent(this,multiTexture,"Flaming Orange",new int[]{5,5,5,5,5,5});
        	if(getConfig().getBoolean("General.Blocks.Flaming.Black")&&FlamingBlack==null)
        FlamingBlack= new FlamingParent(this,multiTexture,"Flaming Black",new int[]{6,6,6,6,6,6});
        	if(getConfig().getBoolean("General.Blocks.Flaming.Planks")&&FlamingPlanks==null)
        FlamingPlanks= new FlamingParent(this,multiTexture,"Flaming Planks",new int[]{7,7,7,7,7,7});
        	if(getConfig().getBoolean("General.Blocks.Flaming.Log")&&FlamingLog==null)
        FlamingLog= new FlamingParent(this,multiTexture,"Flaming Log",new int[]{9,8,8,8,8,9});
        	if(InvisibleBlock==null)
        InvisibleBlock = new ScreenParent(this,multiTexture,"Invisiblock",0,false,new int[]{10,10,10,10,10,10});
    }
		//Commands
	
		//Commands Override
	@Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase(); 
 
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }
                
        //Fling, VERY expirimental
        if (commandName.equals("fling")){
        	Player player;
        	Vector vec;
        	int mul;
        	if(args.length>1){
        		player = Bukkit.getServer().getPlayer(args[0]);
        		mul = Integer.valueOf(args[1]);
        	}
        	else if(args.length>0){
        		player = (Player)sender;
        		mul = Integer.valueOf(args[0]);
        	}
        	else{
        		player = (Player)sender;
        		mul=1;
        	}
        	vec = player.getLocation().getDirection();
        	vec.multiply(mul);
       		player.setVelocity(vec);
        }
        
        //Time Commands
               
        if (commandName.equals("day")){
        	Player player = (Player) sender;
            player.getWorld().setTime(0);
            return true;
            }
        if (commandName.equals("night")){
        	Player player = (Player) sender;
            player.getWorld().setTime(14000);
            return true;
            }

        if (commandName.equals("hungry")){
        	Player player = (Player) sender;
            player.setFoodLevel(1);
            return true;
        }
        if (commandName.equals("sethunger")){
        	Player player;
        	if(args.length<2){
        	player = (Player) sender;
        	player.setFoodLevel(Integer.valueOf(args[0]));
        	}
        	else{
        	player = Bukkit.getServer().getPlayer(args[0]);
        	player.setFoodLevel(Integer.valueOf(args[1]));
        	}       	
        }
        if (commandName.equals("sethealth")||commandName.equals("sethp")){
        	Player player;
        	if(args.length<2){
        	player = (Player) sender;
        	player.setHealth(Integer.valueOf(args[0]));
        	}
        	else{
        	player = Bukkit.getServer().getPlayer(args[0]);
        	player.setHealth(Integer.valueOf(args[1]));
        	}       	
        }
        //Weather Commands
        
        if (commandName.equals("sun")){
        	Player player = (Player) sender;
            player.getWorld().setStorm(false);
            return true;
            }
        if (commandName.equals("rain")){
        	final Player player = (Player) sender;
        	if(args.length<1)
            player.getWorld().setStorm(true);
        	else{
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            	   public void run() {
            		   player.getWorld().setStorm(true);
            	   }
            	}, Long.valueOf(args[0]) );
        	}
            return true;
            }
        if (commandName.equals("sunnyday")){
        	Player player = (Player) sender;
            player.getWorld().setTime(0);
            player.getWorld().setStorm(false);
            return true;
        }
        //Explosion NEEDS WORK
       
        if (commandName.equals("boom")){
        	Player player;
        	if(args.length<1){
        	player = (Player) sender;
        	}
        	else{
        	player = Bukkit.getServer().getPlayer(args[0]);
        	}
        	player.getWorld().createExplosion(player.getLocation(),0);
            return true;
        }
    
        //Reload EITHER DEPRICATE OR IMPROVE
        
        if (commandName.equals("filmreload")){
        	reloadConfig();
        	if(useSpout){
        		setupBlocks();//Any blocks without a check for null will double generate
        		setupTextures();//whereas any blocks WITH a check for null will not change 
        	}
        	return true;
        }
        if (commandName.equals("vanish")){
        	Player player;
        	if(args.length<1){
        	player = (Player) sender;
        	}
        	else{
        	player = Bukkit.getServer().getPlayer(args[0]);
        	}
        	for(Player other : Bukkit.getServer().getOnlinePlayers()){
        		other.hidePlayer(player);
        	}
        	player.getWorld().createExplosion(player.getLocation(),0);
            return true;
        }
        if (commandName.equals("appear")){
        	Player player;
        	if(args.length<1){
        	player = (Player) sender;
        	}
        	else{
        	player = Bukkit.getServer().getPlayer(args[0]);
        	}
        	for(Player other : Bukkit.getServer().getOnlinePlayers()){
        		other.showPlayer(player);
        	}
        	player.getWorld().createExplosion(player.getLocation(),0);
            return true;
        }
        if (commandName.equals("worker")){
        	Player cam = VideoExecutor.getCam();
        	Player player = null;
        	if(args.length>0){
        		player = Bukkit.getServer().getPlayer(args[0]);
        	}
        	else{
        		player = (Player)sender;
        	}
        	cam.hidePlayer(player);
        }
        if (commandName.equals("mode")){
        	Player player = null;
        	GameMode modefrom;
        	GameMode modeto = null;
        	if(args.length==0){
        	player = (Player) sender;
        	modefrom = player.getGameMode();
        	if(modefrom.equals(GameMode.SURVIVAL))
        		modeto = GameMode.CREATIVE;
        	else
        		modeto = GameMode.SURVIVAL;
        	}
        	if(args.length==1){
        		if(Bukkit.getServer().getPlayer(args[0])!=null){
        			player = Bukkit.getServer().getPlayer(args[0]);
        			modefrom = player.getGameMode();
                	if(modefrom.equals(GameMode.SURVIVAL))
                		modeto = GameMode.CREATIVE;
                	else
                		modeto = GameMode.SURVIVAL;
        		}else{
        			player = (Player)sender;
        			if(args[0].toLowerCase().equals("survival")||args[1].equals("1"))
        				modeto = GameMode.SURVIVAL;
        			else
        				modeto = GameMode.CREATIVE;
        		}
        		}
        	if(args.length>1){
        		player = Bukkit.getServer().getPlayer(args[0]);
        		if(args[1].toLowerCase().equals("survival")||args[1].equals("1"))
    				modeto = GameMode.SURVIVAL;
        		else
        			modeto = GameMode.CREATIVE;
        	}
        	player.setGameMode(modeto);
        	return true;
        }
    return false;
    }
    Logger log; 
}
