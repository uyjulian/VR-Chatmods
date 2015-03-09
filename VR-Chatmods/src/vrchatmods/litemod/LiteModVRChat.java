package vrchatmods.litemod;

import java.lang.reflect.Field;

import vrchatmods.guis.GuiButtonClearChat;
import vrchatmods.litemod.overrides.GuiNewChatVRChat;
import vrchatmods.main.GameSettingsVR;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiChatVRChat;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.ScaledResolution;

import com.mumfrey.liteloader.ChatFilter;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.RenderListener;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.util.ModUtilities;

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
	public void init() {
		gameVRoptions = new GameSettingsVR();
		LexManos_sucks_like_a_dick();
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
	        Field fChat = GuiIngame.class.getDeclaredField(ModUtilities.getObfuscatedFieldName("persistantChatGUI", "e", "field_73840_e"));
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
	public void onRenderWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetupCameraTransform() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onChat(Packet3Chat chatPacket) {
		if (chatPacket.message == "") {
				
		}
		return true;
	}
	
	public void LexManos_sucks_like_a_dick() {
		 if (ClientBrandRetriever.getClientModName().contains("fml")) {
			 LiteLoader.logger.info("[VR-ChatMods] [WARNING] LexManos sucks bloody ass!");
			 LiteLoader.logger.info("[VR-ChatMods] [WARNING] This mod may not work because you are using Forge!");
			 LiteLoader.logger.info("[VR-ChatMods] [WARNING] No support will be given for people using Forge.");
		 }
		 else {
			 LiteLoader.logger.info("[VR-ChatMods] [INFO] Good job! You are not using Forge.");
		 }
		
	}


}
