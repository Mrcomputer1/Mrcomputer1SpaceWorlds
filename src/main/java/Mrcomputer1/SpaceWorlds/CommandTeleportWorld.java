package Mrcomputer1.SpaceWorlds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeleportWorld implements CommandExecutor {

	Mrcomputer1SpaceWorlds m1sw;
	
	public CommandTeleportWorld(Mrcomputer1SpaceWorlds m1sw) {
		this.m1sw = m1sw;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("mrcomputer1spaceworlds.commands.teleportworld")){
			return false;
		}
		
		Player p = (Player)sender;
		if(args.length == 0){
			String name = m1sw.getWorldName(p.getWorld());
			m1sw.teleportToSpaceWorld(p, name, 0);
		}else if(args.length == 1){
			String name = m1sw.getWorldName(p.getWorld());
			if(!m1sw.needCreateWorld(name, Integer.parseInt(args[0]))){
				m1sw.teleportToSpaceWorld(p, name, Integer.parseInt(args[0]));
			}else m1sw.sendChatMessage(p, "&4Teleport failed. Does not exist!");
		}else if(args.length == 2){
			if(!m1sw.needCreateWorld(args[1], Integer.parseInt(args[0]))){
				m1sw.teleportToSpaceWorld(p, args[1], Integer.parseInt(args[0]));
			}else m1sw.sendChatMessage(p, "&4Teleport failed. Does not exist!");
		}else{
			m1sw.sendChatMessage(p, "&4Teleport failed. Invalid syntax. Too many args?");
		}
		
		return true;
	}

}
