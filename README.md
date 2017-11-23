# Mrcomputer1 Space Worlds
If you go above y:262 you get teleported to a space world.
If you go below y:-8 you get teleported to a underground world.

## Installation
1. Place Mrcomputer1SpaceWorlds-x.x.x-SNAPSHOT.jar into your plugins folder
2. Start and stop the server
3. Configure the plugin in config.yml (ignore worlds.yml it is an internal file)
4. Configure permissions.
5. Start the server

## Building

`mvn clean package javadoc:javadoc javadoc:jar` Builds plugin, javadoc and javadoc jar

`mvn clean package` Builds just the plugin

Plugin: target/Mrcomputer1SpaceWorlds-(version)-SNAPSHOT.jar

JavaDoc Jar: target/Mrcomputer1SpaceWorlds-(version)-SNAPSHOT-javadoc.jar

JavaDoc: target/apidocs

## Configuring
The config can be found in config.yml.

`maxWorldID: 5` You can go up five worlds.

`minWorldID: -5` You can go down five worlds.

`blacklist: [world_nether, world_the_end]` What worlds don't allow you to go up and down.

`messageFormat: "[Mrcomputer1SpaceWorlds] $1"` Message format. $1 is the message

`starsStartAt: 3` When do worlds start generating with stars.

## Commands

### /startplatform
Places a dirt block at your location.
**Permission**: mrcomputer1spaceworlds.commands.startplatform

### /whatworld
Tells you what world you are in.
**Permission**: mrcomputer1spaceworlds.commands.whatworld

## Permissions

### mrcomputer1spaceworlds.commands
Gives you permissions for all commands.
See _Commands_ for a list of commands and permission for them.

### mrcomputer1spaceworlds.teleport
Allows you to go up and down in all worlds.

## API

### Getting the API
`Mrcomputer1SpaceWorlds api = (Mrcomputer1SpaceWorlds) Bukkit.getPluginManager().getPlugin("Mrcomputer1SpaceWorlds");`

### void createSpaceWorld(String name, int id)
Creates a space world for the world _name_ at location _id_

### void teleportToSpaceWorld(Player p, String name, int id)
Teleports _p_ to the space world for _name_ at location _id_

### boolean needCreateWorld(String name, int id)
Checks if a world needs to be created

### int getWorldId(World w)
Gets the ID of the world.
0 if it is the primary world

### String getWorldName(World w)
Gets the world name.

### boolean canGoHigher(World w)
Checks if you can go higher

### boolean canGoLower(World w)
Checks if you can go lower

### boolean isWorldEnabled(World w)
Checks if this world has the plugin enabled

### boolean isUnderGround(World w)
Checks if the world is underground

### boolean isPrimaryWorld(World w)
Checks if the world is the primary world

### void sendChatMessage(Player p, String msg)
Sends a chat message (msg) as the plugin to the player (p).

### WorldCreator getWorldType(String name, int id)
If underground sets the world to NETHER. If space world sets the world to THE_END.
name should include the ID suffix.
