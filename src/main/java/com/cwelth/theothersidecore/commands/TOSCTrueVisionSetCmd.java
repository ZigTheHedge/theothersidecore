package com.cwelth.theothersidecore.commands;

import com.cwelth.theothersidecore.ModMain;
import com.cwelth.theothersidecore.network.SyncCaps;
import com.cwelth.theothersidecore.player.ITrueVisionPlayer;
import com.cwelth.theothersidecore.player.TrueVisionProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TOSCTrueVisionSetCmd extends CommandBase {
    private String usage = "tosc [player] [tvon|tvoff]";

    @Override
    public String getName() {
        return "tosc";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length > 2) throw new WrongUsageException(usage, new Object[0]);
        if(args.length == 0)
        {
            if(sender instanceof EntityPlayerMP) {
                EntityPlayerMP entityPlayer = ((EntityPlayerMP) sender);
                ITrueVisionPlayer player = entityPlayer.getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
                if(player.isTrueVisionActive())
                    sender.sendMessage(new TextComponentString("True Vision module is installed."));
                else
                    sender.sendMessage(new TextComponentString("True Vision module is NOT installed."));
            } else
                throw new WrongUsageException("Sender is not a player", new Object[0]);
        } else if(args.length == 1)
        {
            if(args[0].equals("tvoff") || args[0].equals("tvon"))
            {
                if(sender instanceof EntityPlayerMP) {
                    EntityPlayerMP entityPlayer = ((EntityPlayerMP) sender);
                    ITrueVisionPlayer player = entityPlayer.getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
                    if(args[0].equals("tvoff")) {
                        if (player.isTrueVisionActive()) {
                            player.setTrueVisionActive(false);
                            ModMain.network.sendTo(new SyncCaps(player.writeToNBT(), entityPlayer.getEntityId()), entityPlayer);
                            sender.sendMessage(new TextComponentString("True Vision module is deactivated."));
                        } else
                            sender.sendMessage(new TextComponentString("True Vision module is NOT installed, so cannot be deactivated."));
                    } else if(args[0].equals("tvon"))
                    {
                        if (player.isTrueVisionActive()) {
                            sender.sendMessage(new TextComponentString("True Vision module is already installed."));
                        } else {
                            player.setTrueVisionActive(true);
                            ModMain.network.sendTo(new SyncCaps(player.writeToNBT(), entityPlayer.getEntityId()), entityPlayer);
                            sender.sendMessage(new TextComponentString("True Vision module installed successfully."));
                        }
                    }
                } else
                    throw new WrongUsageException("Sender is not a player", new Object[0]);
            } else {
                EntityPlayerMP entityPlayer = getPlayer(server, sender, args[0]);
                if(entityPlayer != null)
                {
                    ITrueVisionPlayer player = entityPlayer.getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
                    if(player.isTrueVisionActive())
                        sender.sendMessage(new TextComponentString("True Vision module is installed."));
                    else
                        sender.sendMessage(new TextComponentString("True Vision module is NOT installed."));

                } else
                    throw new WrongUsageException("No such player", new Object[0]);

            }
        } else {
            EntityPlayerMP entityPlayer = getPlayer(server, sender, args[0]);
            if(entityPlayer != null) {
                if (args[1].equals("tvoff") || args[1].equals("tvon")) {
                    ITrueVisionPlayer player = entityPlayer.getCapability(TrueVisionProvider.TRUE_VISION_PLAYER_CAPABILITY, null);
                    if (args[1].equals("tvoff")) {
                        if (player.isTrueVisionActive()) {
                            player.setTrueVisionActive(false);
                            ModMain.network.sendTo(new SyncCaps(player.writeToNBT(), entityPlayer.getEntityId()), entityPlayer);
                            sender.sendMessage(new TextComponentString("True Vision module is deactivated."));
                        } else
                            sender.sendMessage(new TextComponentString("True Vision module is NOT installed, so cannot be deactivated."));
                    } else if (args[1].equals("tvon")) {
                        if (player.isTrueVisionActive()) {
                            sender.sendMessage(new TextComponentString("True Vision module is already installed."));
                        } else {
                            player.setTrueVisionActive(true);
                            ModMain.network.sendTo(new SyncCaps(player.writeToNBT(), entityPlayer.getEntityId()), entityPlayer);
                            sender.sendMessage(new TextComponentString("True Vision module installed successfully."));
                        }
                    }
                } else
                    throw new WrongUsageException(usage, new Object[0]);
            } else
                throw new WrongUsageException("No such player", new Object[0]);
        }
    }


    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1)
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        else if(args.length == 2)
            return Arrays.asList(new String[]{"tvon", "tvoff"});
        else
            return Collections.<String>emptyList();
    }
}
