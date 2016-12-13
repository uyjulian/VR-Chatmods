package vrchatmods.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import net.minecraft.client.Minecraft;

public class GameSettingsVR
{
    protected Minecraft mc;
    private File optionsFile;
    public static int chatBuffer;
    public static int chatScroll;
    public static boolean chatClear;
    public static boolean chatKeepOpen;
    public static int chatTextFieldMax;
    public static boolean highLight;
    public static boolean highLightSound;
    public static boolean chatKeepLog;
    public static boolean chatEnterClose;
    public static boolean chatLogToFile;
    public static String highLightColor;
    public static String _highLightColor;
    public static String myMessage;
    public static boolean highLightMentioned;
    public static String chatBgColor;
    public static Boolean chatBgMentioned;
    public static String chatBgMentionedColor;
    public static int chatBgOpacity = 7;
    public static GuiChatVRHighlight[] Highlights;
    public static GuiChatVRHighlight[] Ignores;
    public static ArrayList highLightList;
    public static ArrayList ignoreList;
    private static Hashtable colors = new Hashtable();
    private static Hashtable colorsChat = new Hashtable();

    public GameSettingsVR(Minecraft var1, File var2)
    {
        chatBuffer = 1000;
        chatScroll = 5;
        chatClear = true;
        chatTextFieldMax = 500;
        chatKeepOpen = true;
        highLight = true;
        highLightSound = true;
        chatKeepLog = true;
        chatEnterClose = true;
        chatLogToFile = true;
        highLightColor = "red";
        highLightList = new ArrayList();
        ignoreList = new ArrayList();
        colors.put("white", "FFFFFFF");
        colors.put("lightGray", "FC0C0C0");
        colors.put("gray", "F808080");
        colors.put("darkGray", "F404040");
        colors.put("black", "F000000");
        colors.put("red", "FFF0000");
        colors.put("pink", "FFFAFAF");
        colors.put("orange", "FFFC800");
        colors.put("yellow", "FFFFF00");
        colors.put("green", "F00FF00");
        colors.put("magenta", "FFF00FF");
        colors.put("cyan", "F00FFFF");
        colors.put("blue", "F0000FF");
        colorsChat.put("black", "0");
        colorsChat.put("darkBlue", "1");
        colorsChat.put("darkGreen", "2");
        colorsChat.put("darkAqua", "3");
        colorsChat.put("darkRed", "4");
        colorsChat.put("darkGrey", "8");
        colorsChat.put("orange", "6");
        colorsChat.put("grey", "7");
        colorsChat.put("purple", "5");
        colorsChat.put("indigo", "9");
        colorsChat.put("brightGreen", "a");
        colorsChat.put("aqua", "b");
        colorsChat.put("red", "c");
        colorsChat.put("pink", "d");
        colorsChat.put("yellow", "e");
        colorsChat.put("white", "f");
        chatBgColor = "black";
        chatBgMentioned = Boolean.valueOf(true);
        chatBgMentionedColor = "red";
        chatBgOpacity = 7;
        highLightMentioned = false;
        this.mc = var1;
        this.optionsFile = new File(var2, "optionsVR.txt");
        this.loadOptions();
    }

    public GameSettingsVR()
    {
        chatBuffer = 1000;
        chatScroll = 5;
        chatClear = true;
        chatTextFieldMax = 500;
        chatKeepOpen = true;
        highLight = true;
        highLightSound = true;
        chatKeepLog = true;
        chatEnterClose = true;
        chatLogToFile = true;
        highLightColor = "red";
        highLightList = new ArrayList();
        ignoreList = new ArrayList();
        colors.put("white", "FFFFFFF");
        colors.put("lightGray", "FC0C0C0");
        colors.put("gray", "F808080");
        colors.put("darkGray", "F404040");
        colors.put("black", "F000000");
        colors.put("red", "FFF0000");
        colors.put("pink", "FFFAFAF");
        colors.put("orange", "FFFC800");
        colors.put("yellow", "FFFFF00");
        colors.put("green", "F00FF00");
        colors.put("magenta", "FFF00FF");
        colors.put("cyan", "F00FFFF");
        colors.put("blue", "F0000FF");
        colorsChat.put("black", "0");
        colorsChat.put("darkBlue", "1");
        colorsChat.put("darkGreen", "2");
        colorsChat.put("darkAqua", "3");
        colorsChat.put("darkRed", "4");
        colorsChat.put("darkGrey", "8");
        colorsChat.put("orange", "6");
        colorsChat.put("grey", "7");
        colorsChat.put("purple", "5");
        colorsChat.put("indigo", "9");
        colorsChat.put("brightGreen", "a");
        colorsChat.put("aqua", "b");
        colorsChat.put("red", "c");
        colorsChat.put("pink", "d");
        colorsChat.put("yellow", "e");
        colorsChat.put("white", "f");
        chatBgColor = "black";
        chatBgMentioned = Boolean.valueOf(true);
        chatBgMentionedColor = "red";
        chatBgOpacity = 7;
        highLightMentioned = false;
    }

    public void loadOptions()
    {
        try
        {
            if (!this.optionsFile.exists())
            {
                return;
            }

            BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
            int var2 = 0;
            int var3 = 0;
            String var4 = "";

            while ((var4 = var1.readLine()) != null)
            {
                try
                {
                    String[] var5 = var4.split(":");

                    if (var5[0].equals("chatBuffer") && var5.length <= 4)
                    {
                        chatBuffer = Integer.parseInt(var5[1]);
                    }

                    if (var5[0].equals("chatScroll") && var5.length <= 2)
                    {
                        chatScroll = Integer.parseInt(var5[1]);
                    }

                    if (var5[0].equals("chatClear"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        chatClear = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("chatKeepOpen"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        chatKeepOpen = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("chatTextFieldMax") && var5.length <= 4)
                    {
                        chatTextFieldMax = Integer.parseInt(var5[1]);
                    }

                    if (var5[0].equals("highLight"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        highLight = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("highLightSound"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        highLightSound = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("chatKeepLog"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        chatKeepLog = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("chatEnterClose"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        chatEnterClose = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("chatLogToFile"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        chatLogToFile = Boolean.parseBoolean(var5[1]);
                    }

                    if (var5[0].equals("highLightColor") && var5.length <= 2)
                    {
                        highLightColor = var5[1];
                        _highLightColor = getColorByString(var5[1]);
                    }

                    if (var5[0].equals("chatBgColor"))
                    {
                        chatBgColor = var5[1];
                    }

                    if (var5[0].equals("chatBgMentionedShow"))
                    {
                        if (!FunctionsVR.isBoolean(var5[1]))
                        {
                            throw new Exception("");
                        }

                        chatBgMentioned = Boolean.valueOf(Boolean.parseBoolean(var5[1]));
                    }

                    if (var5[0].equals("chatBgMentionedColor"))
                    {
                        chatBgMentionedColor = var5[1];
                    }

                    if (var5[0].equals("chatBgOpacity") && var5.length < 2)
                    {
                        chatBgOpacity = Integer.parseInt(var5[1]);
                    }

                    if (var5[0].equals("highLight_" + var2))
                    {
                        highLightList.add(var2, var5[1]);
                        ++var2;
                    }

                    if (var5[0].equals("ignore_" + var3))
                    {
                        ignoreList.add(var3, var5[1]);
                        ++var3;
                    }
                }
                catch (Exception var6)
                {
                    System.out.println("Skipping bad option: " + var4);
                }
            }

            var1.close();
        }
        catch (Exception var7)
        {
            System.out.println("Failed to load options");
            var7.printStackTrace();
        }
    }

    public static String getColorHex(String var0)
    {
        String var1 = (String)colors.get(var0);
        return var1 == "" ? "F000000" : var1;
    }

    public static String getColorString(String var0)
    {
        String var1 = (String)colors.get(var0);
        return var1 == "" ? "black" : var1;
    }

    public static int getColorWithOpacity(Boolean var0)
    {
        String var1;

        if (var0.booleanValue())
        {
            var1 = "0x" + chatBgOpacity + getColorHex(chatBgMentionedColor);
        }
        else
        {
            var1 = "0x" + chatBgOpacity + getColorHex(chatBgColor);
        }

        int var2 = Integer.decode(var1).intValue();
        return var2;
    }

    public static String getColorByString(String var0)
    {
        String var1 = (String)colorsChat.get(var0);
        return var1 == "" ? "0" : var1;
    }


    public void saveOptions()
    {
        try
        {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFile));
            var1.println("chatBuffer:" + chatBuffer);
            var1.println("chatScroll:" + chatScroll);
            var1.println("chatClear:" + chatClear);
            var1.println("chatKeepOpen:" + chatKeepOpen);
            var1.println("chatTextFieldMax:" + chatTextFieldMax);
            var1.println("highLight:" + highLight);
            var1.println("highLightSound:" + highLightSound);
            var1.println("chatKeepLog:" + chatKeepLog);
            var1.println("chatEnterClose:" + chatEnterClose);
            var1.println("chatLogToFile:" + chatLogToFile);
            var1.println("highLightColor:" + highLightColor);
            var1.println((new StringBuilder()).append("chatBgColor:").append(chatBgColor));
            var1.println("chatBgMentionedShow:" + chatBgMentioned);
            var1.println((new StringBuilder()).append("chatBgMentionedColor:").append(chatBgMentionedColor));
            var1.println("chatBgOpacity:" + chatBgOpacity);
            int var2;

            for (var2 = 0; var2 < highLightList.size(); ++var2)
            {
                var1.println((new StringBuilder()).append("highLight_").append(var2).append(":").append(highLightList.get(var2).toString()));
            }

            for (var2 = 0; var2 < ignoreList.size(); ++var2)
            {
                var1.println("ignore_" + Ignores[var2].highLightDescription + ":" + Ignores[var2].highLightCode);
            }

            var1.close();
        }
        catch (Exception var3)
        {
            System.out.println("Failed to save options");
            var3.printStackTrace();
        }
    }
}
