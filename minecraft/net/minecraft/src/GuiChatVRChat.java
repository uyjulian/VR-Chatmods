package net.minecraft.src;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import vrchatmods.guis.GuiButtonClearChat;
import vrchatmods.litemod.LiteModVRChat;
import vrchatmods.litemod.overrides.GuiTextFieldVRChat;
import vrchatmods.main.ChatOverhaul;
import vrchatmods.main.GameSettingsVR;

public class GuiChatVRChat extends GuiChat
{
    private String field_73898_b = "";

    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    private int sentHistoryCursor = -1;
    private boolean field_73897_d = false;
    private boolean field_73905_m = false;
    private int field_73903_n = 0;
    private List field_73904_o = new ArrayList();

    /** used to pass around the URI to various dialogues and to the host os */
    private URI clickedURI = null;

    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    private String defaultInputFieldText = "";

    public GuiChatVRChat() {}
    

    public GuiChatVRChat(GuiChat var1)
    {
        this.defaultInputFieldText = var1.inputField.getText();
        this.setWorldAndResolution(var1.mc, var1.width, var1.height);
    }

    public GuiChatVRChat(String par1Str)
    {
        this.defaultInputFieldText = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        GameSettingsVR var1 = LiteModVRChat.getVRsettings();

        if (GameSettingsVR.chatClear)
        {
            ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var3 = var2.getScaledWidth();
            int var4 = var2.getScaledHeight();
            this.buttonList.add(new GuiButtonClearChat(2, var3 - var3 + 10, var4 - 36));
        }

        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextFieldVRChat(this.fontRenderer, 4, this.height - 12, this.width - 4, 12);
        GameSettingsVR var5 = LiteModVRChat.getVRsettings();
        this.inputField.setMaxStringLength(GameSettingsVR.chatTextFieldMax);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        String var3 = "";
        Boolean var4 = Boolean.valueOf(false);
        String var5 = "";
        this.field_73905_m = false;

        if (par2 == 15)
        {
            this.completePlayerName();
        }
        else
        {
            this.field_73897_d = false;
        }

        if (par2 == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else
        {
            GameSettingsVR var6;

            if (par2 == 28)
            {
                String var7 = this.inputField.getText().trim();

                if (var7.length() > 0)
                {
                    this.mc.ingameGUI.getChatGUI().addToSentMessages(var7);

                    if (!this.mc.handleClientCommand(var7))
                    {
                        var6 = LiteModVRChat.getVRsettings();

                        if (GameSettingsVR.highLight)
                        {
                            var6 = LiteModVRChat.getVRsettings();
                            GameSettingsVR.myMessage = var7;
                        }

                        if (var7.length() >= 101)
                        {
                            int var8;

                            if (var7.startsWith("/"))
                            {
                                var3 = var7.substring(1, var7.indexOf(" "));
                                var4 = Boolean.valueOf(true);

                                if (var3.contains("msg"))
                                {
                                    var8 = var7.indexOf(" ", 4);
                                    var5 = var7.substring(var8, var7.indexOf(" ", var7.indexOf(" ", var8 + 2)));
                                    var3 = var3 + var5;
                                }
                            }

                            var8 = var7.length() / 90;
                            int var9 = 0;
                            int var10;

                            for (var10 = 0; var9 != var8; ++var9)
                            {
                                int var11 = var9 * 90;
                                String var12 = var7.substring(var11, var11 + 90);

                                if (var4.booleanValue() && var9 > 0)
                                {
                                    this.mc.thePlayer.sendChatMessage("/" + var3 + " " + var12);
                                }
                                else
                                {
                                    this.mc.thePlayer.sendChatMessage(var12);
                                }

                                var10 += 90;
                            }

                            if (var9 == var8)
                            {
                                String var14 = var7.substring(var10, var7.length());

                                if (var4.booleanValue() && var9 > 0)
                                {
                                    this.mc.thePlayer.sendChatMessage("/" + var3 + " " + var14);
                                }
                                else
                                {
                                    this.mc.thePlayer.sendChatMessage(var14);
                                }
                            }
                        }
                        else
                        {
                            this.mc.thePlayer.sendChatMessage(var7);
                        }
                    }
                }

                var6 = LiteModVRChat.getVRsettings();

                if (!GameSettingsVR.chatKeepOpen)
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                else
                {
                    Keyboard.enableRepeatEvents(true);
                    this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
                    GameSettingsVR var13 = LiteModVRChat.getVRsettings();
                    this.inputField.setMaxStringLength(GameSettingsVR.chatTextFieldMax);
                    this.inputField.setEnableBackgroundDrawing(false);
                    this.inputField.setFocused(true);
                    this.inputField.setText(this.defaultInputFieldText);
                    this.inputField.setCanLoseFocus(false);
                }

                var6 = LiteModVRChat.getVRsettings();

                if (GameSettingsVR.chatEnterClose && var7 == "")
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
            }
            else if (par2 == 200)
            {
                this.getSentHistory(-1);
            }
            else if (par2 == 208)
            {
                this.getSentHistory(1);
            }
            else if (par2 == 201)
            {
                this.mc.ingameGUI.getChatGUI().scroll(19);
            }
            else if (par2 == 209)
            {
                this.mc.ingameGUI.getChatGUI().scroll(-19);
            }
            else
            {
                this.inputField.textboxKeyTyped(par1, par2);
                var6 = LiteModVRChat.getVRsettings();

                if (GameSettingsVR.chatBgMentioned.booleanValue())
                {
                    var6 = LiteModVRChat.getVRsettings();

                    if (GameSettingsVR.highLightMentioned)
                    {
                        var6 = LiteModVRChat.getVRsettings();
                        GameSettingsVR.highLightMentioned = false;
                    }
                }
            }
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton var1)
    {
        GameSettingsVR var2 = LiteModVRChat.getVRsettings();

        if (GameSettingsVR.chatClear)
        {
            if (!var1.enabled)
            {
                return;
            }

            if (var1.id == 2)
            {
                this.mc.ingameGUI.getChatGUI().clearChatMessages();
                this.mc.ingameGUI.getChatGUI().printChatMessage("** Chat has been cleared **");
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0)
        {
            if (var1 > 1)
            {
                var1 = 1;
            }

            if (var1 < -1)
            {
                var1 = -1;
            }

            if (!isShiftKeyDown())
            {
                GameSettingsVR var2 = LiteModVRChat.getVRsettings();
                var1 *= GameSettingsVR.chatScroll;
            }

            this.mc.ingameGUI.getChatGUI().scroll(var1);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0 && this.mc.gameSettings.chatLinks)
        {
            ChatClickData var4 = this.mc.ingameGUI.getChatGUI().func_73766_a(Mouse.getX(), Mouse.getY());

            if (var4 != null)
            {
                URI var5 = var4.getURI();

                if (var5 != null)
                {
                    if (this.mc.gameSettings.chatLinksPrompt)
                    {
                        this.clickedURI = var5;
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var4.getClickedUrl(), 0,false));
                    }
                    else
                    {
                        this.func_73896_a(var5);
                    }

                    return;
                }

                if (ChatOverhaul.getInstance().settings.copyWordsEnabled && var4.getClickedUrl() != null)
                {
                    this.inputField.writeText(ChatOverhaul.getInstance().mouseClicked(var4.getClickedUrl()));
                    return;
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
        

    }

    private void func_73896_a(URI par1URI)
    {
        try
        {
            Class var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            var2.getMethod("browse", new Class[] {URI.class}).invoke(var3, new Object[] {par1URI});
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    private void func_73893_a(String par1Str, String par2Str)
    {
        if (par1Str.contains("/"))
        {
            par1Str = par1Str.substring(1);
        }

        if (par1Str.length() >= 1)
        {
            this.mc.thePlayer.sendQueue.addToSendQueue(new Packet203AutoComplete(par1Str));
            this.field_73905_m = true;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        GameSettingsVR var4 = LiteModVRChat.getVRsettings();
        GameSettingsVR var5;
        int var6;
        int var7;
        int var8;

        if (GameSettingsVR.chatBgMentioned.booleanValue())
        {
            var6 = this.height - 14;
            var8 = this.width - 2;
            var7 = this.height - 2;
            var5 = LiteModVRChat.getVRsettings();
            drawRect(2, var6, var8, var7, GameSettingsVR.getColorWithOpacity(Boolean.valueOf(GameSettingsVR.highLightMentioned)));
        }
        else
        {
            var6 = this.height - 14;
            var8 = this.width - 2;
            var7 = this.height - 2;
            var5 = LiteModVRChat.getVRsettings();
            drawRect(2, var6, var8, var7, GameSettingsVR.getColorWithOpacity(Boolean.valueOf(false)));
        }

        super.drawScreen(par1, par2, par3);

    }


    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
