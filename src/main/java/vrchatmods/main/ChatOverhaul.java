package vrchatmods.main;

import java.util.Iterator;

public class ChatOverhaul
{
    private static ChatOverhaul chatOverhaul = null;
    public ChatConfig settings = new ChatConfig(this);

    public static ChatOverhaul getInstance()
    {
        if (chatOverhaul == null)
        {
            chatOverhaul = new ChatOverhaul();
        }

        return chatOverhaul;
    }

    public String mouseClicked(String var1)
    {
        Iterator var2 = this.settings.ignorePrefix.iterator();
        String var3;

        while (var2.hasNext())
        {
            var3 = (String)var2.next();

            if (var1.startsWith(var3))
            {
                var1 = var1.substring(var3.length());
                break;
            }
        }

        var2 = this.settings.ignoreSuffix.iterator();

        while (var2.hasNext())
        {
            var3 = (String)var2.next();

            if (var1.endsWith(var3))
            {
                var1 = var1.substring(0, var1.length() - var3.length());
                break;
            }
        }

        return var1;
    }
}
