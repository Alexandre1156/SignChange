package fr.nocturne123.signchange.button;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class GuiButtonColor extends GuiButton {

	private TextFormatting color;
	private int currentCycle;
	private char colorChar;
	private int colorInt;
	
	public GuiButtonColor(int buttonId, int x, int y, TextFormatting defaultColor) {
		super(buttonId, x, y, 20, 20, "");
		this.color = defaultColor;
		this.currentCycle = 0;
		this.colorChar = ObfuscationReflectionHelper.getPrivateValue(TextFormatting.class, defaultColor, 25);
		int color = Minecraft.func_71410_x().field_71466_p.func_175064_b(this.colorChar);
		float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        this.colorInt = MathHelper.func_180183_b(f, f1, f2) | -16777216;
	}
	
	@Override
	public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.func_191745_a(mc, mouseX, mouseY, partialTicks);
		
		this.func_73734_a(this.field_146128_h + 2, this.field_146129_i + 2, this.field_146128_h + this.field_146120_f - 2, this.field_146129_i + this.field_146121_g - 2, colorInt);
	}
	
	@Override
	public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
		if(this.func_146115_a()) {
			this.currentCycle++;
			if(this.currentCycle >= ChatFormatting.values().length)
				this.currentCycle = 0;
			while(!this.color.values()[this.currentCycle].func_96302_c()) {
				this.currentCycle++;
				if(this.currentCycle >= ChatFormatting.values().length)
					this.currentCycle = 0;
			}
			this.color = TextFormatting.values()[currentCycle];
			this.colorChar = ObfuscationReflectionHelper.getPrivateValue(TextFormatting.class, color, 25);
			int color = mc.field_71466_p.func_175064_b(this.colorChar);
			float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        this.colorInt = MathHelper.func_180183_b(f, f1, f2) | -16777216;
		}
		return super.func_146116_c(mc, mouseX, mouseY);
	}
	
	public void onRightClick() {
		if(this.func_146115_a()) {
			this.color = TextFormatting.WHITE;
			this.colorChar = ObfuscationReflectionHelper.getPrivateValue(TextFormatting.class, color, 25);
			int color = Minecraft.func_71410_x().field_71466_p.func_175064_b(this.colorChar);
			float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        this.colorInt = MathHelper.func_180183_b(f, f1, f2) | -16777216;
	        this.currentCycle = 0;
		}
	}
	
	public void setPosition(int x, int y) {
		this.field_146128_h = x;
		this.field_146129_i = y;
	}
	
	public TextFormatting getColor() {
		return color;
	}
	
	public char getColorChar() {
		return colorChar;
	}
	
	public int getColorInt() {
		return colorInt;
	}
	
	public int getCurrentCycle() {
		return currentCycle;
	}

}
