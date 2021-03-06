package vrchatmods.litemod;

import java.io.File;
import java.lang.reflect.Field;

import vrchatmods.guis.GuiButtonClearChat;
import vrchatmods.litemod.overrides.GuiNewChatVRChat;
import vrchatmods.main.GameSettingsVR;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.src.GuiChatVRChat;
import net.minecraft.util.IChatComponent;

import com.mumfrey.liteloader.ChatFilter;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.RenderListener;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker.ReturnValue;
import com.mumfrey.liteloader.util.ModUtilities;
import com.mumfrey.liteloader.util.ObfuscationUtilities;

public class LiteModVRChat implements LiteMod, InitCompleteListener, RenderListener, ChatFilter {
	
	private static GameSettingsVR gameVRoptions;
	private GuiNewChatVRChat persistentChatGui;
	private Minecraft mc;
	
	public static GameSettingsVR getVRsettings() {
		return gameVRoptions;
	}
	
	@Override
	public String getName() {
		return "VR-ChatMods";
	}

	@Override
	public String getVersion() {
		return "1.5.2";
	}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame,
			boolean clock) {

		
	}

	@Override
	public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
		gameVRoptions = new GameSettingsVR(minecraft, minecraft.mcDataDir);
	}

	@Override
	public void onRender() {
	    mc = Minecraft.getMinecraft();

	    if ((mc.ingameGUI != null) && (mc.ingameGUI.getChatGUI() != null) && (!(mc.ingameGUI.getChatGUI() instanceof GuiNewChatVRChat)))
	    {
	      try
	      {
	        Field fChat = GuiIngame.class.getDeclaredField(ObfuscationUtilities.getObfuscatedFieldName("persistantChatGUI", "e", "field_73840_e"));
	        fChat.setAccessible(true);
	        if (this.persistentChatGui == null) this.persistentChatGui = new GuiNewChatVRChat(mc);
	        fChat.set(mc.ingameGUI, this.persistentChatGui);
	      }
	      catch (Exception ex)
	      {
	      }
	    }
        if (mc.currentScreen != null && mc.currentScreen instanceof GuiChat && !(mc.currentScreen instanceof GuiChatVRChat) && !(mc.currentScreen instanceof GuiSleepMP))
        {
            mc.currentScreen = new GuiChatVRChat((GuiChat)mc.currentScreen);
        }
		
	}

	@Override
	public void onRenderGui(GuiScreen currentScreen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetupCameraTransform() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onChat(IChatComponent chat, String message,
			ReturnValue<IChatComponent> newMessage) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void init(File configPath) {
		// TODO Auto-generated method stub
		gameVRoptions = new GameSettingsVR();
	}

	@Override
	public void upgradeSettings(String version, File configPath,
			File oldConfigPath) {
		// TODO Auto-generated method stub
		
	}


}
