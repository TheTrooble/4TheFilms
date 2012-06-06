package FilmPlugin.FilmPlugin;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import mblocks.mBlockParent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.block.design.Texture;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;
import java.util.HashMap;
import Blocks.FlamingParent;
import Blocks.ScreenParent;
public class ForTheFilms extends JavaPlugin implements Listener
{
	
	// {{ Blocks
	//does it work?
	public static CustomBlock nothing;
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
    // }}
    
    // {{ Textures
    public static Texture greenScreenTexture;
    public static Texture redScreenTexture;
    public static Texture blueScreenTexture;
    public static Texture multiTexture;
    // }}
    
    private FileConfiguration blockSettings = null;
    private File blockSettingsFile = null;
    private static ForTheFilms instance;
    public HashMap<String, Integer> entityID;
    public boolean useSpout;
    public Player camera = null;
    public Sign sign;
    public String proj = "Movie";
    public Location marker;
    public Material preScene;
    public byte preSceneData;
    public Location loc1;
    public Location loc2;
	// {{ onEnable and disable
	
    public void onEnable()
    {        
        log = Logger.getLogger("Minecraft"); 
        log.info("4TheFilms Enabled");
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(this,this);
        useSpout = false;
        Plugin spout = pluginManager.getPlugin("Spout"); 	
        
               
        
        
        
        getConfig().addDefault("General.Blocks.Flaming.Planks",false);
    	getConfig().addDefault("General.Blocks.Flaming.Log",false);
    	getConfig().addDefault("General.Blocks.Flaming.Orange",false);
    	getConfig().addDefault("General.Blocks.Flaming.Black",false);
    	getConfig().addDefault("General.Blocks.Flaming.Filth",false);
    	getConfig().addDefault("General.Blocks.Tardis",true);
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
   
   
   
   
    // {{ setups Blocks and Textures
    public void setupTextures() {
    	multiTexture = new Texture(this, "http://dl.dropbox.com/s/aaord2ibg0awtmg/terrain.png",256,256,16);
    }
    
    public void setupBlocks() {
    	if(GreenScreen==null){
        GreenScreen = new ScreenParent(this,multiTexture,"Green Screen",blockSettings.getInt("Blocks.Screens.Green.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"), new int[]{16,16,16,16,16,16}); 
        GreenTracker = new ScreenParent(this,multiTexture,"Green Tracker",blockSettings.getInt("Blocks.Screens.Green.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{19,19,19,19,19,19}); 
        RedScreen = new ScreenParent(this,multiTexture,"Red Screen",blockSettings.getInt("Blocks.Screens.Red.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{17,17,17,17,17,17}); 
        RedTracker = new ScreenParent(this,multiTexture,"Red Tracker",blockSettings.getInt("Blocks.Screens.Red.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{20,20,20,20,20,20}); 
        BlueScreen = new ScreenParent(this,multiTexture,"Blue Screen",blockSettings.getInt("Blocks.Screens.Blue.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{18,18,18,18,18,18});
        BlueTracker = new ScreenParent(this,multiTexture,"Blue Tracker",blockSettings.getInt("Blocks.Screens.Blue.LightLevel"), blockSettings.getBoolean("Blocks.Screens.Green.InhibitsLight"),new int[]{21,21,21,21,21,21});
    	}
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
        InvisibleBlock = new mBlockParent(this,multiTexture,"InvisiBlock",new int[]{10,10,10,10,10,10});
    }
    // }}
	public static ForTheFilms getInstance()
	{
		return instance;
	}   

		// {{ Commands
	
		// {{ Commands Override
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase(); 
 
        if (!(sender instanceof Player)) {
            sender.sendMessage("/" + commandName + " can only be run from in game.");
            return true;
        }
        // }}      
        
        // {{ Marker Sign
        
        
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
        getServer().broadcastMessage("Project set to " + args[0]);
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
        if (commandName.equals("mark")){
            Block b = camera.getWorld().getBlockAt(marker);
            b.setType(preScene);
            b.setData(preSceneData);
            return true;
            }
        // }}
        if (commandName.equals("tp")){
        	Player player1;
        	Player player2;
        	if(args.length>1){
        		player1 = Bukkit.getServer().getPlayer(args[0]);
        		player2 = Bukkit.getServer().getPlayer(args[1]);
        	}
        	else{
        		player1 = (Player)sender;
        		player2 = Bukkit.getServer().getPlayer(args[0]);
        	}
        	Location loc = player2.getLocation();
        	player1.teleport(loc);
        	return true;
        }
        if (commandName.equals("tpboom")){
        	Player player1;
        	Player player2;
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
        	player1.getWorld().createExplosion(loc1,0);
        	player2.getWorld().createExplosion(loc2,0);
        	player1.teleport(loc2);
        	return true;
        }
        
        // {{ Time Commands
        
        
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
        // }}
        if (commandName.equals("hungry")){
        	Player player = (Player) sender;
            player.setFoodLevel(1);
            return true;
            }
        // {{ Weather Commands

        
        if (commandName.equals("sun")){
        	Player player = (Player) sender;
            player.getWorld().setStorm(false);
            return true;
            }
        if (commandName.equals("rain")){
        	Player player = (Player) sender;
            player.getWorld().setStorm(true);
            return true;
            }
        // }}
        
        // {{ Explosion NEEDS WORK

        
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
        if (commandName.equals("vanish")){
        	Player player;
        	if(args.length<1){
        	player = (Player) sender;
        	}
        	else{
        	player = Bukkit.getServer().getPlayer(args[0]);
        	}
        	SpoutPlayer splayer = (SpoutPlayer) player;
        	splayer.setSkin("http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png");
    		splayer.setCape("http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png");
    		splayer.hideTitle();
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
        	SpoutPlayer splayer = (SpoutPlayer) player;
        	splayer.resetSkin();
    		splayer.resetCape();
    		splayer.hideTitle();
        	player.getWorld().createExplosion(player.getLocation(),0);
            return true;
        }
  
        // }}
    
        // {{ Reload EITHER DEPRICATE OR IMPROVE
        
        if (commandName.equals("filmreload")){
        	reloadConfig();
        	if(useSpout){
        		setupBlocks();
        		setupTextures();
        	}
        	return true;
        }
    
        // }}

        // {{ Spout Commands
        
        if (useSpout){   
        	
        // {{ Skin Commands
        	
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
        // }}
        	
        // {{ Cape Commands
        	
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
        // }}
        	if (commandName.equals("invisible")){
        		if(args.length>0){
        		Player player = Bukkit.getServer().getPlayer(args[0]);
        		SpoutPlayer splayer = (SpoutPlayer) player;
        		splayer.setSkin("http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png");
        		splayer.setCape("http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png");
        		splayer.hideTitle();
        	}
        	else{
        		Player player = (Player) sender;
        		SpoutPlayer splayer = (SpoutPlayer) player;
        		splayer.setSkin("http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png");
        		splayer.setCape("http://dl.dropbox.com/s/fktmeor9m9rd19w/invisiskin.png");
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
        }
    return false;
    }
    // }}
    // }}
    Logger log;     
}