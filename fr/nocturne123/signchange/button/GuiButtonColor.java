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
		int color = Minecraft.getMinecraft().fontRenderer.getColorCode(this.colorChar);
		float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        this.colorInt = MathHelper.rgb(f, f1, f2) | -16777216;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);
		
		this.drawRect(this.x + 2, this.y + 2, this.x + this.width - 2, this.y + this.height - 2, colorInt);
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if(this.isMouseOver()) {
			this.currentCycle++;
			if(this.currentCycle >= ChatFormatting.values().length)
				this.currentCycle = 0;
			while(!this.color.values()[this.currentCycle].isColor()) {
				this.currentCycle++;
				if(this.currentCycle >= ChatFormatting.values().length)
					this.currentCycle = 0;
			}
			this.color = TextFormatting.values()[currentCycle];
			this.colorChar = ObfuscationReflectionHelper.getPrivateValue(TextFormatting.class, color, 25);
			int color = mc.fontRenderer.getColorCode(this.colorChar);
			float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        this.colorInt = MathHelper.rgb(f, f1, f2) | -16777216;
		}
		return super.mousePressed(mc, mouseX, mouseY);
	}
	
	public void onRightClick() {
		if(this.isMouseOver()) {
			this.color = TextFormatting.BLACK;
			this.colorChar = ObfuscationReflectionHelper.getPrivateValue(TextFormatting.class, color, 25);
			int color = Minecraft.getMinecraft().fontRenderer.getColorCode(this.colorChar);
			float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        this.colorInt = MathHelper.rgb(f, f1, f2) | -16777216;
	        this.currentCycle = 0;
		}
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
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
