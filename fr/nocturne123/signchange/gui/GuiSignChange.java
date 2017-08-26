package fr.nocturne123.signchange.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import fr.nocturne123.signchange.Reference;
import fr.nocturne123.signchange.SignChange;
import fr.nocturne123.signchange.SignLine;
import fr.nocturne123.signchange.button.GuiButtonColor;
import fr.nocturne123.signchange.button.GuiButtonIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class GuiSignChange extends GuiScreen {

	private final TileEntitySign te;
	private SignLine[] lines;
	private byte curentTextFieldFocus;
	private boolean isNew;
	private final ResourceLocation signTexture;

	public GuiSignChange(TileEntitySign teSign, boolean isNew) {
		this.te = teSign;
		this.lines = new SignLine[4];
		this.curentTextFieldFocus = 0;
		this.isNew = isNew;
		this.signTexture = new ResourceLocation(Reference.MOD_ID, "texture/sign.png");
	}

	@Override
	public void initGui() {
		this.te.setEditable(true);
		Keyboard.enableRepeatEvents(true);
		
		for (int i = 0; i <= 3; i++) {
			if(this.lines[i] == null) {
				this.lines[i] = new SignLine(i, this.width / 2, this.height / 3 + (25 * i), this.width, this.height, this.te.signText[i], i+1);
				for(GuiButton buts : this.lines[i].getButtons())
					this.addButton(buts);
			} else {
				this.lines[i].setPosition(this.width / 2, this.height / 3 + (25 * i));
				for(GuiButton buts : this.lines[i].getButtons())
					this.addButton(buts);
			}
		}
		this.lines[0].setFocused(this.curentTextFieldFocus == 0);
		this.addButton(new GuiButton(4, this.width / 2 - 100, this.height / 3 + 100, "Save"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			switch (button.id) {
			case 4:
				if(this.isNew) {
					for(int i = 0; i <= 3; i++) 
						this.te.signText[i] = this.lines[i].getTextComponent();
				}
				String text1 = this.lines[0].getNBTComponent();
				String text2 = this.lines[1].getNBTComponent();
				String text3 = this.lines[2].getNBTComponent();
				String text4 = this.lines[3].getNBTComponent();
				BlockPos signPos = this.te.getPos();
				SignChange.blockBlockUpdateMessage = 4;
				for(int i = 0; i <= 3; i++)
					this.mc.player.sendChatMessage("/blockdata "+signPos.getX()+" "+signPos.getY()+" "+signPos.getZ()+" {"
							+this.lines[i].getNBTComponent()+"}");
				this.mc.displayGuiScreen(null);
				break;
			}
		}
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		this.te.setEditable(true);
		if(this.isNew) {
			for(int i = 0; i <= 3; i++) {
				if(te.signText[i] == null)
					this.te.signText[i] = new TextComponentString("");
			}
			NetHandlerPlayClient nethandlerplayclient = this.mc.getConnection();
			if (nethandlerplayclient != null) 
				nethandlerplayclient.sendPacket(new CPacketUpdateSign(this.te.getPos(), this.te.signText));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		for (SignLine textField : this.lines) {
			if(textField != null)
				textField.drawTextBox();
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		GlStateManager.color(1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(signTexture);
		this.drawModalRectWithCustomSizedTexture(this.width / 2 - 49, this.height / 4 - 41, 0, 0, 512, 512, 512, 512);
		
		for(int i = 0; i <= 3; i++) {
			SignLine textField = this.lines[i];
			if(textField != null) {
				ITextComponent itextcomponent = new TextComponentString(textField.getFormattedText());
                List<ITextComponent> list = GuiUtilRenderComponents.splitText(itextcomponent, 90, this.fontRenderer, false, true);
                String s = list != null && !list.isEmpty() ? ((ITextComponent)list.get(0)).getFormattedText() : "";
				this.fontRenderer.drawString(s, (this.width / 2) - fontRenderer.getStringWidth(s) / 2, this.height / 4 - 37 + i * 10, this.lines[i].getRGBColorInt());
			}
		}
		//this.drawVerticalLine(this.width / 2, 0, this.height * 2, Color.red.getRGB());
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		switch (keyCode) {
		case 15: //ALT button
			if (curentTextFieldFocus >= 0 && curentTextFieldFocus <= 3) {
				if (curentTextFieldFocus == 0 || curentTextFieldFocus == 1 || curentTextFieldFocus == 2) {
					this.lines[curentTextFieldFocus].setFocused(false);
					curentTextFieldFocus++;
					this.lines[curentTextFieldFocus].setFocused(true);
				} else if (curentTextFieldFocus == 3) {
					this.lines[3].setFocused(false);
					curentTextFieldFocus = 0;
					this.lines[0].setFocused(true);
				}
			}
			break;
		case 208: //Down arrow
			if (curentTextFieldFocus >= 0 && curentTextFieldFocus <= 2) {
				this.lines[curentTextFieldFocus].setFocused(false);
				curentTextFieldFocus++;
				this.lines[curentTextFieldFocus].setFocused(true);
			}
			break;
		case 200: //Up arrow
			if (curentTextFieldFocus >= 1 && curentTextFieldFocus <= 3) {
				this.lines[curentTextFieldFocus].setFocused(false);
				curentTextFieldFocus--;
				this.lines[curentTextFieldFocus].setFocused(true);
			}
			break;
		}
		for (SignLine textField : this.lines)
			textField.textboxKeyTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		boolean hasClickOnButton = false;
		for(GuiButton button : this.buttonList) {
			if(button instanceof GuiButtonIcon) {
				if(((GuiButtonIcon) button).mouseClicked(mc, mouseX, mouseY, mouseButton))
					hasClickOnButton = true;
			} else if(button instanceof GuiButtonColor) {
				if(mouseButton == 1)
					((GuiButtonColor) button).onRightClick();
				if(button.isMouseOver())
					hasClickOnButton = true;
			}
		}
		if(!hasClickOnButton) {
			for (int i = 0; i <= 3; i++) {
				SignLine textField = this.lines[i];
				textField.mouseClicked(mouseX, mouseY, mouseButton);
				if(textField.isFocused())
					curentTextFieldFocus = Byte.valueOf(String.valueOf(i));
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void updateScreen() {
		if(this.lines != null) {
			if(this.lines[curentTextFieldFocus] != null)
				this.lines[curentTextFieldFocus].updateCursorCounter();
			for(SignLine line : this.lines) {
				if(line != null)
					line.updateStyle();
			}
		}
	}

}
