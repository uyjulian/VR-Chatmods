package vrchatmods.litemod.overrides;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.src.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.StringUtils;

import org.lwjgl.opengl.GL11;

import vrchatmods.litemod.LiteModVRChat;
import vrchatmods.main.GameSettingsVR;

public class GuiNewChatVRChat extends GuiNewChat
{
    /** The Minecraft instance. */
    private final Minecraft mc;
    
    /** A list of messages previously sent through the chat GUI */
    private final List sentMessages = new ArrayList();

    /** Chat lines to be displayed in the chat box */
    public final List chatLines = new ArrayList();
    private final List field_96134_d = new ArrayList();
    private int field_73768_d = 0;
    private boolean field_73769_e = false;

    public GuiNewChatVRChat(Minecraft par1Minecraft)
    {
    	super(par1Minecraft);
        this.mc = par1Minecraft;
    }
    
    @Override
	public void drawChat(int par1) //TODO find out why new code doesn't work here
    {
        if (this.mc.gameSettings.chatVisibility != EnumChatVisibility.HIDDEN)
        {
            byte var2 = 10;
            boolean var3 = false;
            int var4 = 0;
            int var5 = this.chatLines.size();
            float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (var5 > 0)
            {
                if (this.getChatOpen())
                {
                    var2 = 20;
                    var3 = true;
                }

                int var7;
                int var8;
                int var9;

                for (var7 = 0; var7 + this.field_73768_d < this.chatLines.size() && var7 < var2; ++var7)
                {
                    ChatLine var10 = (ChatLine)this.chatLines.get(var7 + this.field_73768_d);

                    if (var10 != null)
                    {
                        var8 = par1 - var10.getUpdatedCounter();

                        if (var8 < 200 || var3)
                        {
                            double var11 = (double)var8 / 200.0D;
                            var11 = 1.0D - var11;
                            var11 *= 10.0D;

                            if (var11 < 0.0D)
                            {
                                var11 = 0.0D;
                            }

                            if (var11 > 1.0D)
                            {
                                var11 = 1.0D;
                            }

                            var11 *= var11;
                            var9 = (int)(255.0D * var11);

                            if (var3)
                            {
                                var9 = 255;
                            }

                            var9 = (int)((float)var9 * var6);
                            ++var4;

                            if (var9 > 3)
                            {
                                byte var13 = 3;
                                int var14 = -var7 * 9;
                                drawRect(var13, var14 - 1, var13 + 320 + 4, var14 + 8, var9 / 2 << 24);
                                GL11.glEnable(GL11.GL_BLEND);
                                String var15 = ""; //TODO: fix var10.getChatLineString();

                                if (!this.mc.gameSettings.chatColours)
                                {
                                    var15 = StringUtils.stripControlCodes(var15);
                                }

                                this.mc.fontRendererObj.drawStringWithShadow(var15, var13, var14, 16777215 + (var9 << 24));
                            }
                        }
                    }
                }

                if (var3)
                {
                    var7 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GL11.glTranslatef(0.0F, (float)var7, 0.0F);
                    int var16 = var5 * var7 + var5;
                    var8 = var4 * var7 + var4;
                    int var17 = this.field_73768_d * var8 / var5;
                    int var12 = var8 * var8 / var16;

                    if (var16 != var8)
                    {
                        var9 = var17 > 0 ? 170 : 96;
                        int var18 = this.field_73769_e ? 13382451 : 3355562;
                        drawRect(0, -var17, 2, -var17 - var12, var18 + (var9 << 24));
                        drawRect(2, -var17, 1, -var17 - var12, 13421772 + (var9 << 24));
                    }
                }
            }
        }
    }
    
    @Override
	public void clearChatMessages()
    {
        this.field_96134_d.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }
    
    /**
     * takes a String and prints it to chat
     */
    public void printChatMessage(String par1Str)
    {
        this.printChatMessageWithOptionalDeletion(par1Str, 0);
    }

    /**
     * prints the String to Chat. If the ID is not 0, deletes an existing Chat Line of that ID from the GUI
     */
    public void printChatMessageWithOptionalDeletion(String par1Str, int par2)
    {
        boolean var3 = this.getChatOpen();
        boolean var4 = true;
        boolean var5 = false;

        if (par2 != 0)
        {
            this.deleteChatLine(par2);
        }

        Iterator var6 = this.mc.fontRendererObj.listFormattedStringToWidth(par1Str, 320).iterator();
        GameSettingsVR var7;

        while (var6.hasNext())
        {
            String var8 = (String)var6.next();

            if (var3 && this.field_73768_d > 0)
            {
                this.field_73769_e = true;
                this.scroll(1);
            }

            if (!var4)
            {
                var8 = " " + var8;
            }

            GameSettingsVR var9 = LiteModVRChat.getVRsettings();

            if (GameSettingsVR.myMessage == null)
            {
                var9 = LiteModVRChat.getVRsettings();
                GameSettingsVR.myMessage = "";
            }

            var9 = LiteModVRChat.getVRsettings();

            if (GameSettingsVR.highLight)
            {
                var7 = LiteModVRChat.getVRsettings();

                if (var8.contains(GameSettingsVR.myMessage) && var8.contains(this.mc.getSession().getUsername()))
                {
                    var5 = true;
                }
                else
                {
                    var5 = false;
                }

                if (!var5)
                {
                    var9 = LiteModVRChat.getVRsettings();
                    StringBuffer var10;

                    for (Iterator var11 = GameSettingsVR.highLightList.iterator(); var11.hasNext(); var8 = var10.toString())
                    {
                        String var12 = (String)var11.next();
                        var10 = new StringBuffer();
                        String var13 = "";
                        int var14 = 0;
                        int var15 = 0;
                        Pattern var16 = Pattern.compile(var12, 2);
                        Matcher var17;

                        for (var17 = var16.matcher(var8); var17.find(); ++var15)
                        {
                            var9 = LiteModVRChat.getVRsettings();

                            if (GameSettingsVR.chatBgMentioned.booleanValue())
                            {
                                var9 = LiteModVRChat.getVRsettings();
                                GameSettingsVR.highLightMentioned = true;
                            }

                            String var19;
                            int var18;

                            if (var15 == 0)
                            {
                                var18 = var17.start();
                                String var20 = var8.substring(0, var18);
                                var14 = var18;
                                int var21 = var20.lastIndexOf("\u00a7");

                                if (var21 != -1)
                                {
                                    var19 = var20.substring(var21, var21 + 2);
                                }
                                else
                                {
                                    var19 = "\u00a7f";
                                }

                                if (var13 == "")
                                {
                                    var13 = var19;
                                }
                            }
                            else
                            {
                                var18 = var17.start();
                                var19 = var8.substring(var14, var18);
                                int var24 = var19.lastIndexOf("\u00a7");

                                if (var24 != -1)
                                {
                                    var13 = var19.substring(var24, var24 + 2);
                                }
                            }

                            StringBuilder var23 = (new StringBuilder()).append("\u00a7");
                            GameSettingsVR var25 = LiteModVRChat.getVRsettings();
                            var17.appendReplacement(var10, var23.append(GameSettingsVR._highLightColor).append(var12).append(var13).toString());
                            var9 = LiteModVRChat.getVRsettings();
                            //TODO: fix
//                            if (GameSettingsVR.highLightSound)
//                            {
//                                this.mc.getSoundHandler().playSound("random.orb", 1.0F, 1.0F);
//                                this.mc.getSoundHandler().playSound("random.orb", 1.0F, 1.0F);
//                            }
                        }

                        var17.appendTail(var10);
                    }
                }
            }

            var4 = false;
            this.chatLines.add(0, new ChatLine(this.mc.ingameGUI.getUpdateCounter(), new ChatComponentText(var8), par2));
        }

        while (true)
        {
            int var22 = this.chatLines.size();
            var7 = LiteModVRChat.getVRsettings();

            if (var22 <= GameSettingsVR.chatBuffer)
            {
                return;
            }

            this.chatLines.remove(this.chatLines.size() - 1);
        }
    }
    
//
//    public ChatClickDataFix func_73766_a(int par1, int par2)
//    {
//        if (!this.getChatOpen())
//        {
//            return null;
//        }
//        else
//        {
//            ScaledResolution var3 = new ScaledResolution(this.mc);
//            int var4 = var3.getScaleFactor();
//            int var5 = par1 / var4 - 3;
//            int var6 = par2 / var4 - 40;
//
//            if (var5 >= 0 && var6 >= 0)
//            {
//                int var7 = Math.min(20, this.chatLines.size());
//
//                if (var5 <= 320 && var6 < this.mc.fontRendererObj.FONT_HEIGHT * var7 + var7)
//                {
//                    int var8 = var6 / this.mc.fontRendererObj.FONT_HEIGHT + this.field_73768_d;
//                    ChatLine var9 = (ChatLine)this.chatLines.get(var8);
//                    ChatLine var10;
//
//                    if (var8 != 0)
//                    {
//                        var10 = (ChatLine)this.chatLines.get(var8 - 1);
//                    }
//                    else
//                    {
//                        var10 = null;
//                    }
//
//                    return new ChatClickDataFix(this.mc.fontRendererObj, var9, var10, var5, var6 - (var8 - this.field_73768_d) * this.mc.fontRendererObj.FONT_HEIGHT + var8);
//                }
//                else
//                {
//                    return null;
//                }
//            }
//            else
//            {
//                return null;
//            }
//        }
//    }

    /**
     * @return {@code true} if the chat GUI is open
     */
    @Override
	public boolean getChatOpen()
    {
        return this.mc.currentScreen instanceof GuiChatVRChat;
    }

}
