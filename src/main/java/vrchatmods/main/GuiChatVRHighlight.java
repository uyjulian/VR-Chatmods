package vrchatmods.main;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.IntHashMap;

public class GuiChatVRHighlight
{
    public static List highLightArray = new ArrayList();
    public static IntHashMap hash = new IntHashMap();
    public String highLightDescription;
    public int highLightCode;

    public GuiChatVRHighlight(String var1, int var2)
    {
        this.highLightDescription = var1;
        this.highLightCode = var2;
        highLightArray.add(this);
        hash.addKey(var2, this);
    }
}
