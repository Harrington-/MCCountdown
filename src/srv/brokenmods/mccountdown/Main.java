package srv.brokenmods.mccountdown;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public static boolean MCCountdownInProgress = false;
	public static boolean MCCountdownForceStop = false;
	@Override
	public void onEnable() {
		
	}
	@Override
	public void onDisable() {
		
	}
	
	
	
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (command.getName().equalsIgnoreCase("countdown")) {
        	String arg1 = args[0];
        	String arg2 = "";
        	
        	if(args.length > 1)
        		arg2 = args[1];
        	
        	if(arg1.compareToIgnoreCase("stop") >- 1)
        	{
        		MCCountdownForceStop = true;
        		return true;
        	}
        	int initialTime;
        	try
        	{
            	if(arg2.compareToIgnoreCase("minutes") > -1)
            	{
            		initialTime = Integer.parseInt(arg1) * 60;
            	}
            	else
            	{
            		initialTime = Integer.parseInt(arg1);
            	}
        	}
        	catch(Exception e)
        	{
        		sender.sendMessage(ChatColor.DARK_RED + "Argument must be an integer value. No Decimals or commas.");
        		return false;
        	}
        	if(MCCountdownInProgress)
        	{
				sender.sendMessage(ChatColor.DARK_RED + "Another countdown is in progress, please wait until the current one expires.");
        	}
        	else Bukkit.getScheduler().runTaskTimer(this, new Runnable()
        			{
        				int time = initialTime;
        				boolean initiated = false;
        				@Override
        				public void run()
        				{
        					if(this.time < 0)
        					{
        						return;
        					}
        					if(MCCountdownForceStop)
        					{
        						this.time = -1;
        						MCCountdownInProgress = false;
        		        		MCCountdownForceStop = false;
        						this.initiated = false;
            					for(final Player player : Bukkit.getOnlinePlayers())
            					{
            						player.sendMessage(ChatColor.WHITE +"" + ChatColor.BOLD + "Countdown Terminated.");
            					}
        						return;
        					}
        					if(this.time == initialTime && !this.initiated)
        					{
            					for(final Player player : Bukkit.getOnlinePlayers())
            					{
            						if(this.time % 60 == 0)
            						{
                						player.sendMessage(ChatColor.BLUE +"" + ChatColor.BOLD + (this.time / 60) + " Minute Countdown Initiated.");
            						}
            						else
            						{
            							player.sendMessage(ChatColor.BLUE +"" + ChatColor.BOLD + this.time + " Second Countdown Initiated.");
            						}
            					}
            					this.time += 1;
            					this.initiated = true;
            					MCCountdownInProgress = true;
            					return;
        					}

        					if(this.time >= 300 && this.time % 60 == 0) 
        					{
	        					for(final Player player : Bukkit.getOnlinePlayers())
	        					{
	        						player.sendMessage(ChatColor.WHITE +""+ ChatColor.BOLD + (this.time / 60) + " minutes remaining...");
	        					}
        					}
        					else if(this.time > 60 && this.time < 300 && this.time % 60 == 0) 
        					{
	        					for(final Player player : Bukkit.getOnlinePlayers())
	        					{
	        						player.sendMessage(ChatColor.WHITE +""+ ChatColor.BOLD + (this.time / 60) + " minutes remaining...");
	        					}
        					}
        					else if(this.time > 30 && this.time <= 60 && this.time % 10 == 0)
        					{
	        					for(final Player player : Bukkit.getOnlinePlayers())
	        					{
	        						player.sendMessage(ChatColor.GOLD +""+ ChatColor.BOLD + this.time + " seconds remaining...");
	        					}
        					}
        					else if(this.time > 10 && this.time <= 30 && this.time % 5 == 0)
        					{
	        					for(final Player player : Bukkit.getOnlinePlayers())
	        					{
	        						player.sendMessage(ChatColor.YELLOW +""+ ChatColor.BOLD + this.time + " seconds remaining...");
	        					}
        					}
        					else if(this.time <= 10 && this.time > 0)
        					{

	        					for(final Player player : Bukkit.getOnlinePlayers())
	        					{
	        						player.sendMessage(ChatColor.RED +""+ ChatColor.BOLD + this.time + " seconds remaining...");
	        					}
        					}
        					else if(this.time == 0) {
            					for(final Player player : Bukkit.getOnlinePlayers())
            					{
            						player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "GO!!!");
            					}
            					MCCountdownInProgress = false;
            					this.time--;
        						return;
        					}

        					this.time--;
        					return;
        				}
        			}, 0L, 20L);
        	
            return true;
        }
        return false;

    }
}
