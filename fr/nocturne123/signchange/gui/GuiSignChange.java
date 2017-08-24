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
	public void func_73866_w_() {
		this.te.func_145913_a(true);
		Keyboard.enableRepeatEvents(true);
		
		for (int i = 0; i <= 3; i++) {
			if(this.lines[i] == null) {
				this.lines[i] = new SignLine(i, this.field_146294_l / 2, this.field_146295_m / 3 + (25 * i), this.field_146294_l, this.field_146295_m, this.te.field_145915_a[i], i+1);
				for(GuiButton buts : this.lines[i].getButtons())
					this.func_189646_b(buts);
			} else {
				this.lines[i].setPosition(this.field_146294_l / 2, this.field_146295_m / 3 + (25 * i));
				for(GuiButton buts : this.lines[i].getButtons())
					this.func_189646_b(buts);
			}
		}
		this.lines[0].setFocused(this.curentTextFieldFocus == 0);
		this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 - 100, this.field_146295_m / 3 + 100, "Save"));
	}

	@Override
	protected void func_146284_a(GuiButton button) throws IOException {
		if (button.field_146124_l) {
			switch (button.field_146127_k) {
			case 4:
				String text1 = this.lines[0].getNBTComponent();
				String text2 = this.lines[1].getNBTComponent();
				String text3 = this.lines[2].getNBTComponent();
				String text4 = this.lines[3].getNBTComponent();
				BlockPos signPos = this.te.func_174877_v();
				SignChange.blockBlockUpdateMessage = 4;
				for(int i = 0; i <= 3; i++)
					this.field_146297_k.field_71439_g.func_71165_d("/blockdata "+signPos.func_177958_n()+" "+signPos.func_177956_o()+" "+signPos.func_177952_p()+" {"
							+this.lines[i].getNBTComponent()+"}");
				this.field_146297_k.func_147108_a(null);
				break;
			}
		}
	}
	
	@Override
	public void func_146281_b() {
		Keyboard.enableRepeatEvents(false);
		this.te.func_145913_a(true);
		
		if(this.isNew) {
			for(int i = 0; i <= 3; i++) {
				if(te.field_145915_a[i] == null)
					this.te.field_145915_a[i] = new TextComponentString("");
			}
			NetHandlerPlayClient nethandlerplayclient = this.field_146297_k.func_147114_u();
			if (nethandlerplayclient != null)
				nethandlerplayclient.func_147297_a(new CPacketUpdateSign(this.te.func_174877_v(), this.te.field_145915_a));
		}
	}

	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
		for (SignLine textField : this.lines) {
			if(textField != null)
				textField.drawTextBox();
		}
		
		super.func_73863_a(mouseX, mouseY, partialTicks);
		
		GlStateManager.func_179124_c(1f, 1f, 1f);
		Minecraft.func_71410_x().func_110434_K().func_110577_a(signTexture);
		this.func_146110_a(this.field_146294_l / 2 - 49, this.field_146295_m / 4 - 41, 0, 0, 128, 128, 128, 128);
		
		for(int i = 0; i <= 3; i++) {
			SignLine textField = this.lines[i];
			if(textField != null) {
				ITextComponent itextcomponent = new TextComponentString(textField.getFormattedText());
                List<ITextComponent> list = GuiUtilRenderComponents.func_178908_a(itextcomponent, 90, this.field_146289_q, false, true);
                String s = list != null && !list.isEmpty() ? ((ITextComponent)list.get(0)).func_150254_d() : "";
				this.field_146289_q.func_78276_b(s, (this.field_146294_l / 2) - field_146289_q.func_78256_a(s) / 2, this.field_146295_m / 4 - 37 + i * 10, this.lines[i].getRGBColorInt());
			}
		}
		//this.drawVerticalLine(this.width / 2, 0, this.height * 2, Color.red.getRGB());
	}

	@Override
	protected void func_73869_a(char typedChar, int keyCode) throws IOException {
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
	}

	@Override
	protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
		boolean hasClickOnButton = false;
		for(GuiButton button : this.field_146292_n) {
			if(button instanceof GuiButtonIcon) {
				if(((GuiButtonIcon) button).mouseClicked(field_146297_k, mouseX, mouseY, mouseButton))
					hasClickOnButton = true;
			} else if(button instanceof GuiButtonColor) {
				if(mouseButton == 1)
					((GuiButtonColor) button).onRightClick();
				if(button.func_146115_a())
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
		super.func_73864_a(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void func_73876_c() {
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
