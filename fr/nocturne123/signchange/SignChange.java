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

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, acceptedMinecraftVersions = "1.12", clientSideOnly = true, name = Reference.MOD_NAME)
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
    	Block blockClicked = e.getWorld().func_180495_p(e.getPos()).func_177230_c();
    	if((blockClicked == Blocks.field_150472_an || blockClicked == Blocks.field_150444_as) && Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() == Items.field_151155_ap) {
    		TileEntity te = e.getWorld().func_175625_s(e.getPos());
    		if(te instanceof TileEntitySign) {
	    		Minecraft.func_71410_x().func_147108_a(new GuiSignChange((TileEntitySign) te, false));
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
    	String commandSucessMessage = new TextComponentTranslation("commands.blockdata.success", new Object[0]).func_150260_c();
    	String commandNotChangeMessage = new TextComponentTranslation("commands.blockdata.failed", new Object[0]).func_150260_c();
    	if((e.getMessage().func_150260_c().startsWith(commandSucessMessage) || e.getMessage().func_150260_c().startsWith(commandNotChangeMessage)) && this.blockBlockUpdateMessage > 0) {
    		this.blockBlockUpdateMessage--;
    		e.setCanceled(true);
    	}
    }
}
