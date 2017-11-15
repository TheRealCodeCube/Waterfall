package io.github.codecube.creation;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.codecube.util.FileUtils;

public class CommandBuildObject implements CommandExecutor {
	public static final String OBJECT_EDITOR_WORLD_PREFIX = "objecteditor";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			World world = Bukkit.getWorld(OBJECT_EDITOR_WORLD_PREFIX + "0");
			if (world != null) {
				System.out.println("Deleting world...");
				if (!Bukkit.unloadWorld(world, true)) {
					player.sendMessage("You must be outside the editor to use this command!");
					return true;
				}
				File directory = world.getWorldFolder();
				FileUtils.deleteFolder(directory);
			}

			WorldCreator creator = new WorldCreator(OBJECT_EDITOR_WORLD_PREFIX + "0");
			creator.environment(Environment.NORMAL);
			creator.generateStructures(false);
			creator.generator(new GridWorldGenerator());

			world = creator.createWorld();
			world.setMonsterSpawnLimit(0);
			world.setAnimalSpawnLimit(0);

			player.teleport(world.getSpawnLocation());
		} else {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
		return true;
	}
}
