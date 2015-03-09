package vrchatmods.guis;

import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;

import org.lwjgl.input.Keyboard;

import vrchatmods.litemod.LiteModVRChat;
import vrchatmods.main.GameSettingsVR;

public class GuiChatVR extends GuiScreen
{
    private GuiScreen parentScreen;
    protected String screenTitle = "Chat Settings";
    private final GameSettings options;
    private int buttonId;
    private GuiTextField chatBufferTextField;
    private GuiTextField chatScrollTextField;

    public GuiChatVR(GuiOptions var1, GameSettings var2)
    {
        this.parentScreen = var1;
        this.options = var2;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int var1 = this.height / 4 + 48;
        StringTranslate var2 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 135 + 12, var2.translateKey("gui.done")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 159 + 12, var2.translateKey("gui.cancel")));
        this.screenTitle = "VR Chat Settings";
        this.chatBufferTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 145, 200, 20);
        this.chatBufferTextField.setMaxStringLength(4);
        this.chatBufferTextField.setFocused(true);
        this.chatBufferTextField.setText(Integer.toString(GameSettingsVR.chatBuffer));
        this.chatScrollTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 185, 200, 20);
        this.chatScrollTextField.setMaxStringLength(2);
        this.chatScrollTextField.setFocused(false);
        this.chatScrollTextField.setText(Integer.toString(GameSettingsVR.chatScroll));
        ((GuiButton)this.buttonList.get(0)).enabled = this.chatBufferTextField.getText().length() > 0 && this.chatScrollTextField.getText().length() > 0;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton var1)
    {
        if (var1.enabled)
        {
            if (var1.id == 1)
            {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (var1.id == 0)
            {
                GameSettingsVR.chatBuffer = Integer.parseInt(this.chatBufferTextField.getText());
                GameSettingsVR.chatScroll = Integer.parseInt(this.chatScrollTextField.getText());
                LiteModVRChat.getVRsettings().saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int var1, int var2, int var3)
    {
        super.mouseClicked(var1, var2, var3);
        this.chatBufferTextField.mouseClicked(var1, var2, var3);
        this.chatScrollTextField.mouseClicked(var1, var2, var3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char var1, int var2)
    {
        this.chatBufferTextField.textboxKeyTyped(var1, var2);
        this.chatScrollTextField.textboxKeyTyped(var1, var2);

        if (var1 == 9)
        {
            if (this.chatBufferTextField.isFocused())
            {
                this.chatBufferTextField.setFocused(false);
                this.chatScrollTextField.setFocused(true);
            }
            else
            {
                this.chatBufferTextField.setFocused(true);
                this.chatScrollTextField.setFocused(false);
            }
        }

        if (var1 == 28)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        ((GuiButton)this.buttonList.get(0)).enabled = this.chatBufferTextField.getText().length() > 0 && this.chatScrollTextField.getText().length() > 0;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "www.Cubecraft.co.uk - Survival - PVP - Creative - Join now!", this.width / 2, 20, 16776960);
        this.drawCenteredString(this.fontRenderer, "survive.cubecraft.co.uk", this.width / 2, 40, 10526880);
        this.drawCenteredString(this.fontRenderer, "pvp.cubecraft.co.uk", this.width / 2, 50, 10526880);
        this.drawCenteredString(this.fontRenderer, "build.cubecraft.co.uk", this.width / 2, 60, 10526880);
        this.drawCenteredString(this.fontRenderer, "VR Chat Settings", this.width / 2, 110, 16777215);
        this.drawString(this.fontRenderer, "Chat history length (normal 100):", this.width / 2 - 100, 130, 10526880);
        this.drawString(this.fontRenderer, "Chat scroll speed (normal 7):", this.width / 2 - 100, 170, 10526880);
        this.chatBufferTextField.drawTextBox();
        this.chatScrollTextField.drawTextBox();
        super.drawScreen(var1, var2, var3);
    }
}
