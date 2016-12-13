package vrchatmods.guis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiButtonClearChat extends GuiButton
{

    public GuiButtonClearChat(int var1, int var2, int var3)
    {
        super(var1, var2, var3, 20, 20, "");
    }

    /**
     * Draws this button to the screen.
     */
    @Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
    	if (this.visible)
        {
            par1Minecraft.getTextureManager().bindTexture(new ResourceLocation("/gui/gui.png"));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = 106;

            if (var4)
            {
                var5 += this.height;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, var5, this.width, this.height);
        }
    }
}
