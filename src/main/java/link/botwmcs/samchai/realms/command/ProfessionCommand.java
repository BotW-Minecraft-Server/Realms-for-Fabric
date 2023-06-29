package link.botwmcs.samchai.realms.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import link.botwmcs.samchai.realms.util.PlayerDataHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ProfessionCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("profession")
//                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.literal("get")
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(context -> {
                                    Player player = EntityArgument.getPlayer(context, "target");
                                    String profession = PlayerDataHandler.getProfession(player);
                                    int level = PlayerDataHandler.getLevel(player);
                                    player.sendSystemMessage(Component.nullToEmpty("Profession: " + profession));
                                    player.sendSystemMessage(Component.nullToEmpty("Level: " + level));
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.literal("farmer")
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    Player player = EntityArgument.getPlayer(context, "target");
                                                    int level = IntegerArgumentType.getInteger(context, "level");
                                                    PlayerDataHandler.setProfession(player, "farmer", level);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("miner")
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    Player player = EntityArgument.getPlayer(context, "target");
                                                    int level = IntegerArgumentType.getInteger(context, "level");
                                                    PlayerDataHandler.setProfession(player, "miner", level);
                                                    return 1;
                                                })
                                        )
                                )
                                .then(Commands.literal("knight")
                                        .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    Player player = EntityArgument.getPlayer(context, "target");
                                                    int level = IntegerArgumentType.getInteger(context, "level");
                                                    PlayerDataHandler.setProfession(player, "knight", level);
                                                    return 1;
                                                })
                                        )
                                )

                        )
                )
        );
    }
}
