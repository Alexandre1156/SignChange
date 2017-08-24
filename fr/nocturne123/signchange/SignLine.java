package fr.nocturne123.signchange;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import fr.nocturne123.signchange.button.GuiButtonClick;
import fr.nocturne123.signchange.button.GuiButtonColor;
import fr.nocturne123.signchange.button.GuiButtonIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class SignLine extends Gui {

	private int x, y, width, height, position;
	private boolean bold, italic, obfuscated, underlined, strikethrough;
	private GuiTextField textField;
	private Minecraft mc;
	private Style style;
	private ITextComponent text;
	private GuiButtonIcon boldBut, italicBut, obfBut, linedBut, strikeBut;
	private GuiButtonColor colorBut;
	private TextFormatting format;
	private ClickEvent click;
	private GuiButtonClick clickBut;
	
	public SignLine(int id, int x, int y, int screenWidth, int screenHeight, ITextComponent text, int position) {
		this.x = x;
		this.y = y;
		this.mc = Minecraft.func_71410_x();
		this.width = screenWidth;
		this.height = screenHeight;
		this.style = text.func_150256_b();
		this.text = text;
		this.position = position;
		
		if(style != null) {
			this.bold = style.func_150223_b();
			this.italic = style.func_150242_c();
			this.obfuscated = style.func_150233_f();
			this.underlined = style.func_150234_e();
			this.strikethrough = style.func_150236_d();
			this.format = style.func_150215_a() != null ? style.func_150215_a() : TextFormatting.BLACK;
			this.click = style.func_150235_h();
		} else {
			this.format = TextFormatting.WHITE;
			this.style = new Style();
		}
		
		this.textField = new GuiTextField(id, mc.field_71466_p, x - 100, y, 200, 20);
		this.textField.func_146205_d(true);
		this.textField.func_146195_b(false);
		this.textField.func_146203_f(32);
		this.textField.func_146180_a(text == null ? "" : text.func_150260_c());
		this.colorBut = new GuiButtonColor(5 + id, x - 130, y, format);
		this.boldBut = new GuiButtonIcon(9 + id, x - 155, y, 'B', this.bold);
		this.italicBut = new GuiButtonIcon(13 + id, x - 180, y, 'I', this.italic);
		this.obfBut = new GuiButtonIcon(17 + id, x - 205, y, 'O', this.obfuscated);
		this.linedBut = new GuiButtonIcon(21 + id, x - 230, y, 'L', this.underlined);
		this.strikeBut = new GuiButtonIcon(25 + id, x - 255, y, 'S', this.strikethrough);
		this.clickBut = new GuiButtonClick(29 + id, x + 110, y, 'C', this.click);
		this.setPosition(x, y);
	}
	
	public String getNBTComponent() {
		String text = "";
		text = "Text"+this.position+":\"{\\\"text\\\":\\\""+this.textField.func_146179_b()+"\\\"";
		if(this.colorBut.getColor() != TextFormatting.BLACK)
			text += ",\\\"color\\\":\\\""+this.colorBut.getColor().func_96297_d()+"\\\"";
		if(this.bold)
			text += ",\\\"bold\\\":\\\"true\\\"";
		if(this.italic)
			text += ",\\\"italic\\\":\\\"true\\\"";
		if(this.obfuscated)
			text += ",\\\"obfuscated\\\":\\\"true\\\"";
		if(this.strikethrough)
			text += ",\\\"strikethrough\\\":\\\"true\\\"";
		if(this.underlined)
			text += ",\\\"underlined\\\":\\\"true\\\"";
		if(this.click != null && this.click.func_150669_a() != null && this.click.func_150668_b() != null && !this.click.func_150668_b().isEmpty())
			text += ",\\\"clickEvent\\\":{\\\"action\\\":\\\""+this.click.func_150669_a().func_150673_b()+"\\\",\\\"value\\\":\\\""+this.click.func_150668_b()+"\\\"}";
		text += "}\"";
		return text;
	}
	
	public int getRGBColorInt() {
		return this.colorBut.getColorInt();
	}
	
	public String getUnformattedText() {
		return this.textField.func_146179_b();
	}
	
	public String getFormattedText() {
		return this.getTextComponent().func_150254_d();
	}
	
	public ITextComponent getTextComponent() {
		return new TextComponentString(this.textField.func_146179_b()).func_150255_a(this.style);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.textField.field_146209_f = x - 100;
		this.textField.field_146210_g = y;
		if(x - 255 < 0) {
			int newX = (x - 100) / 7;
			this.colorBut.setPosition(x - 130, y);
			this.boldBut.setPosition(newX * 4, y);
			this.italicBut.setPosition(newX * 3, y);
			this.obfBut.setPosition(newX * 2, y);
			this.linedBut.setPosition(newX, y);
			this.strikeBut.setPosition(0, y);
			this.clickBut.setPosition(x + 110, y);
		} else {
			this.colorBut.setPosition(x - 130, y);
			this.boldBut.setPosition(x - 155, y);
			this.italicBut.setPosition(x - 180, y);
			this.obfBut.setPosition(x - 205, y);
			this.linedBut.setPosition(x - 230, y);
			this.strikeBut.setPosition(x - 255, y);
			this.clickBut.setPosition(x + 110, y);
		}
	}
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		this.textField.func_146192_a(mouseX, mouseY, mouseButton);
		this.boldBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
		this.italicBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
		this.obfBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
		this.linedBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
		this.strikeBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
		this.clickBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
		this.clickBut.mouseClicked(mc, mouseX, mouseY, mouseButton);
	}
	
	public void updateStyle() {
		this.style.func_150227_a(this.bold = this.boldBut.isClicked());
		this.style.func_150238_a(this.format = this.colorBut.getColor());
		this.style.func_150217_b(this.italic = this.italicBut.isClicked());
		this.style.func_150237_e(this.obfuscated = this.obfBut.isClicked());
		this.style.func_150225_c(this.strikethrough = this.strikeBut.isClicked());
		this.style.func_150228_d(this.underlined = this.linedBut.isClicked());
		this.style.func_150241_a(this.click = this.clickBut.getOnClick());
	}
	
	public void updateCursorCounter() {
		this.textField.func_146178_a();
	}
	
	public void textboxKeyTyped(char typedChar, int keyCode) {
		this.textField.func_146201_a(typedChar, keyCode);
	}
	
	public void drawTextBox() {
		this.textField.func_146194_f();
	}
	
	public void setFocused(boolean value) {
		this.textField.func_146195_b(value);
	}
	
	public boolean isFocused() {
		return this.textField.func_146206_l();
	}
	
	public ArrayList<GuiButton> getButtons() {
		return Lists.newArrayList(this.boldBut, this.colorBut, this.italicBut, this.linedBut, this.obfBut, this.strikeBut, this.clickBut);
	}
	
}
