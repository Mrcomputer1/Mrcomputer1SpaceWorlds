package Mrcomputer1.SpaceWorlds;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit plugin class and API.
 * 
 * @author Mrcomputer1
 */
public class Mrcomputer1SpaceWorlds extends JavaPlugin implements Listener{
	
	// name, id
	
	private FileConfiguration fc = new YamlConfiguration();
	
	/**
	 * Save the worlds, used internally.
	 */
	public void saveWorlds(){
		try {
			fc.save(new File(getDataFolder(), "worlds.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * INTERNAL USE ONLY
	 */
	@Override
	public void onEnable() {
		saveDefaultConfig();
		File f = new File(getDataFolder(), "worlds.yml");
		if(f.exists()){
			try {
				fc.load(f);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}else{
			try {
				fc.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Bukkit.getPluginManager().registerEvents(this, this);
		for(World w : Bukkit.getWorlds()){
			if(!fc.contains(w.getName())){
				fc.set(w.getName() + ".id", 0);
				fc.set(w.getName() + ".name", w.getName());
			}
		}
		saveWorlds();
		Bukkit.getPluginCommand("startplatform").setExecutor(new CommandStartPlatform());
		Bukkit.getPluginCommand("teleportworld").setExecutor(new CommandTeleportWorld(this));
		Bukkit.getPluginCommand("whatworld").setExecutor(new CommandWhatWorld(this));
	}
	
	/**
	 * Creates a world.
	 * 
	 * @param name Primary world's name.
	 * @param id ID of world. 0 is primary.
	 * @return The World
	 */
	public World createSpaceWorld(String name, int id){
		String worldName;
		if(id == 0){
			worldName = name;
		}else{
			worldName = name + "-" + id;
		}
		fc.set(worldName + ".id", id);
		fc.set(worldName + ".name", name);
		saveWorlds();WorldCreator wc = getWorldType(worldName, id);
		if(id >= getConfig().getInt("starsStartAt")){
			wc = wc.generator(new SpaceWorldGenerator());
		}else{
			wc = wc.generator(new EmptyWorldGenerator());
		}
		return Bukkit.createWorld(wc);
	}
	/**
	 * Get the world type.
	 * 
	 * @param name World name with ID suffix
	 * @param id World ID
	 * @return The WorldCreator
	 */
	public WorldCreator getWorldType(String name, int id){
		if(id < 0){
			return WorldCreator.name(name).environment(Environment.NETHER);
		}else{
			return WorldCreator.name(name).environment(Environment.THE_END);
		}
	}
	/**
	 * Teleports a player to a world
	 * 
	 * @param p Player
	 * @param name Primary World Name
	 * @param id ID
	 */
	public void teleportToSpaceWorld(Player p, String name, int id){
		String worldName;
		if(id == 0){
			worldName = name;
		}else{
			worldName = name + "-" + id;
		}
		World w = Bukkit.getWorld(worldName);
		Location loc = p.getLocation();
		loc.setY(w.getHighestBlockYAt(loc));
		loc.setWorld(w);
		p.teleport(loc);
	}
	
	/**
	 * Does a world need creating?
	 * 
	 * @param name Primary World Name
	 * @param id ID
	 * @return Does it need creating?
	 */
	public boolean needCreateWorld(String name, int id){
		if(id == 0){
			return !fc.contains(name);
		}
		return !fc.contains(name + "-" + id);
	}
	/**
	 * Gets the world ID
	 * 
	 * @param w World
	 * @return The ID
	 */
	public int getWorldID(World w){
		// Get the world object in config.yml worlds:
		return fc.getInt(w.getName() + ".id");
	}
	/**
	 * Gets the primary worlds name
	 * 
	 * @param w World
	 * @return The name
	 */
	public String getWorldName(World w){
		return fc.getString(w.getName() + ".name");
	}
	/**
	 * Can you go higher?
	 * 
	 * @param w World
	 * @return Can you?
	 */
	public boolean canGoHigher(World w){
		int id = getWorldID(w);
		return getConfig().getInt("maxWorldID") != id;
	}
	/**
	 * Can you go lower?
	 * 
	 * @param w World
	 * @return Can you?
	 */
	public boolean canGoLower(World w){
		int id = getWorldID(w);
		return getConfig().getInt("minWorldID") != id;
	}
	/**
	 * Is the world enabled?
	 * 
	 * @param w World
	 * @return Is it?
	 */
	public boolean isWorldEnabled(World w){
		String name = getWorldName(w);
		return !getConfig().getStringList("blacklist").contains(name);
	}
	/**
	 * Is it a primary world?
	 * 
	 * @param w World
	 * @return Is it?
	 */
	public boolean isPrimaryWorld(World w){
		if(getWorldID(w) == 0){
			return true;
		}
		return false;
	}
	/**
	 * Is it a under ground world?
	 * 
	 * @param w World
	 * @return Is it?
	 */
	public boolean isUnderGround(World w){
		if(getWorldID(w) < 0){
			return true;
		}
		return false;
	}
	/**
	 * Send a chat message as the plugin
	 * 
	 * @param p Player
	 * @param msg Message
	 */
	public void sendChatMessage(Player p, String msg){
		String message = getConfig().getString("messageFormat").replace("$1", msg);
		message = ChatColor.translateAlternateColorCodes('&', message);
		p.sendMessage(message);
	}
	
	/**
	 * DO NOT USE
	 * 
	 * @param e no reason
	 */
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(!isWorldEnabled(e.getTo().getWorld())) return;
		if(!e.getPlayer().hasPermission("mrcomputer1spaceworlds.teleport")) return;
		if(e.getTo().getY() >= 262){
			if(canGoHigher(e.getTo().getWorld())){
				sendChatMessage(e.getPlayer(), "Teleporting you up a world...");
				String name = getWorldName(e.getTo().getWorld());
				int id = getWorldID(e.getTo().getWorld()) + 1;
				if(needCreateWorld(name, id)){
					createSpaceWorld(name, id);
				}
				teleportToSpaceWorld(e.getPlayer(), name, id);
				sendChatMessage(e.getPlayer(), "Teleported up a world!");
			}
		}
		if(e.getTo().getY() <= -8){
			if(canGoLower(e.getTo().getWorld())){
				sendChatMessage(e.getPlayer(), "Teleporting you down a world...");
				String name = getWorldName(e.getTo().getWorld());
				int id = getWorldID(e.getTo().getWorld()) - 1;
				if(needCreateWorld(name, id)){
					createSpaceWorld(name, id);
				}
				teleportToSpaceWorld(e.getPlayer(), name, id);
				sendChatMessage(e.getPlayer(), "Teleported down a world!");
			}
		}
	}
	
}
