package fr.nocturne123.signchange;

import fr.nocturne123.signchange.gui.GuiSignChange;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, acceptedMinecraftVersions = "1.12", clientSideOnly = true, name = Reference.MOD_NAME, updateJSON = "https://raw.githubusercontent.com/Alexandre1156/SignChange/master/Update.json")
public class SignChange {
    
	@Instance(Reference.MOD_ID)
	public static SignChange instance;
	public static int blockBlockUpdateMessage;
	
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onPlayerRightClick(RightClickBlock e) {
    	Block blockClicked = e.getWorld().getBlockState(e.getPos()).getBlock();
    	if((blockClicked == Blocks.STANDING_SIGN || blockClicked == Blocks.WALL_SIGN) && Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == Items.SIGN) {
    		TileEntity te = e.getWorld().getTileEntity(e.getPos());
    		if(te instanceof TileEntitySign) {
	    		Minecraft.getMinecraft().displayGuiScreen(new GuiSignChange((TileEntitySign) te, false));
	    		e.setCanceled(true);
    		}
    	}
    }
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
    	if(e.getGui() instanceof GuiEditSign) {
    		GuiEditSign gui = (GuiEditSign) e.getGui();
    		TileEntitySign teSign = ObfuscationReflectionHelper.getPrivateValue(GuiEditSign.class, gui, 0);
    		if(teSign != null)
    			e.setGui(new GuiSignChange(teSign, true));
    	}
    }
    
    @SubscribeEvent
    public void onMessageReceived(ClientChatReceivedEvent e) {
    	String commandSucessMessage = new TextComponentTranslation("commands.blockdata.success", new Object[0]).getUnformattedText();
    	String commandNotChangeMessage = new TextComponentTranslation("commands.blockdata.failed", new Object[0]).getUnformattedText();
    	if((e.getMessage().getUnformattedText().startsWith(commandSucessMessage) || e.getMessage().getUnformattedText().startsWith(commandNotChangeMessage)) && this.blockBlockUpdateMessage > 0) {
    		this.blockBlockUpdateMessage--;
    		e.setCanceled(true);
    	}
    }
}
