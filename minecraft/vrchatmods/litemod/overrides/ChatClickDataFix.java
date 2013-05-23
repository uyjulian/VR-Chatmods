package vrchatmods.litemod.overrides;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.src.ChatClickData;
import net.minecraft.src.ChatLine;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.StringUtils;

public class ChatClickDataFix extends ChatClickData
{
    public static final Pattern pattern = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,3})(/\\S*)?$");
    private final FontRenderer fontR;
    private final String line;
    private final int field_78312_d;
    private final int field_78313_e;
    private final String field_78310_f;

    /** The URL which was clicked on. */
    private final String clickedUrl;

    public ChatClickDataFix(FontRenderer var1, ChatLine var2, ChatLine var3, int var4, int var5)
    {
    	super(var1, var2, var4, var5);
        String var6 = var2.getChatLineString();

        if (var6.contains("http") && var3 != null && var2.getUpdatedCounter() == var3.getUpdatedCounter())
        {
            var6 = var6 + var3.getChatLineString().substring(3);
        }

        this.fontR = var1;
        this.line = var6;
        this.field_78312_d = var4;
        this.field_78313_e = var5;
        this.field_78310_f = var1.trimStringToWidth(var6, var4);
        this.clickedUrl = this.findClickedUrl();
    }

    /**
     * Gets the URL which was clicked on.
     */
    public String getClickedUrl()
    {
        return this.clickedUrl;
    }

    /**
     * computes the URI from the clicked chat data object
     */
    public URI getURI()
    {
        String var1 = this.getClickedUrl();

        if (var1 == null)
        {
            return null;
        }
        else
        {
            Matcher var2 = pattern.matcher(var1);

            if (var2.matches())
            {
                try
                {
                    String var3 = var2.group(0);

                    if (var2.group(1) == null)
                    {
                        var3 = "http://" + var3;
                    }

                    return new URI(var3);
                }
                catch (URISyntaxException var4)
                {
                    Logger.getLogger("Minecraft").log(Level.SEVERE, "Couldn\'t create URI from chat", var4);
                }
            }

            return null;
        }
    }

    private String findClickedUrl()
    {
        int var1 = this.field_78310_f.lastIndexOf(" ", this.field_78310_f.length()) + 1;

        if (var1 < 0)
        {
            var1 = 0;
        }

        int var2 = this.line.indexOf(" ", var1);

        if (var2 < 0)
        {
            var2 = this.line.length();
        }

        return StringUtils.stripControlCodes(this.line.substring(var1, var2));
    }
}
