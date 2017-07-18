package Mrcomputer1.SpaceWorlds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * /startplatform
 * Internal use only. No docs.
 * 
 * @author Mrcomputer1
 */
public class CommandStartPlatform implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("mrcomputer1spaceworlds.commands.startplatform")){
			return false;
		}
		Player p = (Player)sender;
		p.getLocation().getBlock().setType(Material.DIRT);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[Mrcomputer1SpaceWorlds] Placed a dirt block at your location"));
		return true;
	}

}
