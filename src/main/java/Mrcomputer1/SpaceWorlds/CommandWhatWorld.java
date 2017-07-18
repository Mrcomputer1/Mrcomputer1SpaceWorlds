package Mrcomputer1.SpaceWorlds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /whatworld
 * Internal Use Only. No docs.
 * 
 * @author Mrcomputer1
 */
public class CommandWhatWorld implements CommandExecutor {

	Mrcomputer1SpaceWorlds chw;
	
	public CommandWhatWorld(Mrcomputer1SpaceWorlds chw) {
		this.chw = chw;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("mrcomputer1spaceworlds.commands.whatworld")){
			return false;
		}
		Player p = (Player)sender;
		String name = chw.getWorldName(p.getLocation().getWorld());
		int id = chw.getWorldID(p.getLocation().getWorld());
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[Mrcomputer1SpaceWorlds] You are in " + name + " at level " + id + "!"));
		return true;
	}

}
