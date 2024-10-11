# Player Promote Plugin

Paper/Purpur 1.21.1 player self promotion plugin for luckperms.

You **must** have **luckperms installed** to use this plugin!

Players can promote themselves from default to player (or other group, configurable!) with /agree

Tell them to use it at the end of your server rules if they agree for example.

# Permissions

```playerpromote.use``` allows a player to use /agree

# Configuration

```
# Player Message Configuration:
# Plugin Title:
PlayerTitle: "§2§l[§r§2Player Promote§2§l]§r "
# Message to show to new players, set this to DISABLED to stop this message from appearing at all:
NewPlayerMessage: "This is the first time you have joined. Please read the rules at spawn and you can promote yourself to player once you have agreed to the rules."
# Message to show to the player when they have successfully been promoted:
PromoteMessage: "You have been promoted to the Player group! GLHF!"
# Message to show to the player when they are already in a higher group then the default group:
AlreadyPromotedMessage: "You are already in the group Player, or higher."

# Groups:
# Inital group the player will join in (this should be left at the default unless you have changed it):
DefaultGroup: "default"
# The group you would like the player to be promoted to:
PromoteToGroup: "group.player"
```
