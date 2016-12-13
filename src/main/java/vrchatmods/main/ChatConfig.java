package vrchatmods.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class ChatConfig
{
    public boolean chatLoggerEnabled = false;
    public File logFile = null;
    public String logFormat = "EEE, d MMM yyyy HH:mm:ss \'-\' $line\'\n\'";
    public boolean copyWordsEnabled = true;
    public ArrayList ignorePrefix = new ArrayList();
    public ArrayList ignoreSuffix = new ArrayList();
    public int chatHistoryLength = 1000;
    public int chatScrollSpeed = 19;
    public ChatOverhaul chatOverhaul = null;

    public ChatConfig(ChatOverhaul var1)
    {
        this.chatOverhaul = var1;
        this.readConfig();
    }

    private void readConfig()
    {
    	//TODO: Minecraft.getAppDir("minecraft")
        File var1 = new File("", "mods" + File.separator + "grooohm");
        File var2 = new File(var1, "chat.conf");
        String var3 = "";
        String var4 = "";

        if (!var1.exists())
        {
            var1.mkdirs();
        }

        try
        {
            if (var1.exists())
            {
                if (var2.exists())
                {
                    BufferedReader var5 = new BufferedReader(new FileReader(var2));
                    String var6;

                    while ((var6 = var5.readLine()) != null)
                    {
                        if (!var6.startsWith("#") && !var6.equals(""))
                        {
                            if (var6.startsWith("\t\t"))
                            {
                                if (var4.equals("ignoreSuffix"))
                                {
                                    this.ignoreSuffix.add(var6.substring(2));
                                }

                                if (var4.equals("ignorePrefix"))
                                {
                                    this.ignorePrefix.add(var6.substring(2));
                                }
                            }
                            else
                            {
                                String[] var7;

                                if (var6.startsWith("\t"))
                                {
                                    var7 = var6.split(":", 2);
                                    var7[0] = var7[0].trim();

                                    if (var7.length == 2 && !var7[1].trim().equals(""))
                                    {
                                        var7[1] = var7[1].trim();

                                        if (var7[0].equalsIgnoreCase("enabled"))
                                        {
                                            if (var3.equals("copyWordsFromChat"))
                                            {
                                                this.copyWordsEnabled = Boolean.parseBoolean(var7[1]);
                                            }
                                            else if (var3.equals("chatLogger"))
                                            {
                                                this.chatLoggerEnabled = Boolean.parseBoolean(var7[1]);
                                            }
                                        }

                                        if (var3.equals("chatLogger"))
                                        {
                                            if (var7[0].equalsIgnoreCase("format"))
                                            {
                                                this.logFormat = "";
                                            }

                                            if (var7[0].equalsIgnoreCase("destination"))
                                            {
                                                this.logFile = new File(var7[1]);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (var6.trim().equalsIgnoreCase("ignoreSuffix:"))
                                        {
                                            var4 = "ignoreSuffix";
                                        }

                                        if (var6.trim().equalsIgnoreCase("ignorePrefix:"))
                                        {
                                            var4 = "ignorePrefix";
                                        }
                                    }
                                }
                                else
                                {
                                    var7 = var6.split(":", 2);
                                    var7[0] = var7[0].trim();

                                    if (var7.length == 2 && !var7[1].trim().equals(""))
                                    {
                                        var7[1] = var7[1].trim();

                                        if (var7[0].equalsIgnoreCase("chatHistoryLength"))
                                        {
                                            this.chatHistoryLength = Integer.parseInt(var7[1]);
                                        }

                                        if (var7[0].equalsIgnoreCase("chatScrollSpeed"))
                                        {
                                            this.chatScrollSpeed = Integer.parseInt(var7[1]);
                                        }
                                    }
                                    else
                                    {
                                        if (var6.equalsIgnoreCase("copyWordsFromChat:"))
                                        {
                                            var3 = "copyWordsFromChat";
                                        }

                                        if (var6.equalsIgnoreCase("chatLogger:"))
                                        {
                                            var3 = "chatLogger";
                                        }
                                    }
                                }
                            }
                        }
                    }

                    var5.close();
                }
                else
                {
                    this.logFile = new File(var1, "chat.log");
                    PrintWriter var9 = new PrintWriter(var2);
                    var9.println("#Config file for Grooohm\'s chat mod");
                    var9.println("copyWordsFromChat:");
                    var9.println("\tignorePrefix:");
                    var9.println("\t\t#");
                    var9.println("\t\t\u00f8");
                    var9.println("\t\t+");
                    var9.println("\t\t*");
                    var9.println("\tignoreSuffix:");
                    var9.println("\t\t:");
                    var9.println("\t\t.");
                    var9.println("\t\t,");
                    var9.println("chatLogger:");
                    var9.println("\tenabled: false");
                    var9.println("chatHistoryLength: 2000");
                    var9.println("chatScrollSpeed: 19");
                    var9.close();
                    this.readConfig();
                }
            }
        }
        catch (IOException var8)
        {
            var8.printStackTrace();
        }
    }
}
