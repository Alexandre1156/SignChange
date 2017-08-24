package fr.nocturne123.signchange.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import fr.nocturne123.signchange.button.GuiButtonClick;
import fr.nocturne123.signchange.button.GuiButtonClicked;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;

public class GuiClick extends GuiScreen {

	private GuiButtonClick clickBut;
	private GuiSignChange guiSign;
	private GuiTextField textField;
	
	public GuiClick(GuiButtonClick clickBut, GuiSignChange parentGui) {
		this.clickBut = clickBut;
		this.guiSign = parentGui;
	}
	
	@Override
	public void func_73866_w_() {
		//this.addButton(new GuiButtonClicked(0, this.width / 3 - 200, this.height / 4, "Open URL", clickBut.getOnClick() != null && clickBut.getOnClick().getAction() == Action.OPEN_URL));
		GuiButtonClicked but = this.func_189646_b(new GuiButtonClicked(1, this.field_146294_l / 3 + 5, this.field_146295_m / 4, "Run command", true));
		but.setBlocked(true);
		//this.addButton(new GuiButtonClicked(2, this.width / 3 + 210, this.height / 4, "Suggest command", clickBut.getOnClick() != null && clickBut.getOnClick().getAction() == Action.SUGGEST_COMMAND));
		this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 - 100, this.field_146295_m - 50, "Save"));
		this.textField = new GuiTextField(4, field_146289_q, this.field_146294_l / 2 - 100, this.field_146295_m / 4 * 2, 200, 20);
		this.textField.func_146205_d(false);
		this.textField.func_146195_b(true);
		this.textField.func_146180_a(clickBut.getOnClick() != null ? clickBut.getOnClick().func_150668_b() : "");
		Keyboard.enableRepeatEvents(true);
	}
	
	@Override
	protected void func_73869_a(char typedChar, int keyCode) throws IOException {
		this.textField.func_146201_a(typedChar, keyCode);
		if(keyCode == 1) {
			Minecraft.func_71410_x().func_147108_a(guiSign);
			clickBut.setOnClick(clickBut.getOnClick() != null ? clickBut.getOnClick() : null);
		}
	}
	
	@Override
	protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.textField.func_146192_a(mouseX, mouseY, mouseButton);
		super.func_73864_a(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void func_73876_c() {
		this.textField.func_146178_a();
	}
	
	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
		this.func_146276_q_();
		super.func_73863_a(mouseX, mouseY, partialTicks);
		this.textField.func_146194_f();
		this.func_73732_a(field_146289_q, TextFormatting.BOLD+"When player right click on the sign...", this.field_146294_l / 2, this.field_146295_m / 4 - 20, Color.white.getRGB());
	}
	
	@Override
	protected void func_146284_a(GuiButton button) throws IOException {
		if(button.field_146124_l) {
			switch(button.field_146127_k) {
//			case 0:
//			case 1:
//			case 2:
//				this.desactiveOtherButton(button.id);
//				break;
			case 3:
//				Action action = this.getActionByButtonActived();
				String value = this.textField.func_146179_b();
				if(/*action != null && */value != null && !value.isEmpty())
					this.clickBut.setOnClick(new ClickEvent(Action.RUN_COMMAND, this.textField.func_146179_b()));
				else
					this.clickBut.setOnClick(null);
				Minecraft.func_71410_x().func_147108_a(guiSign);
				Keyboard.enableRepeatEvents(false);
				break;
			}
		}
	}
	
//	private Action getActionByButtonActived() {
//		Action action = null;
//		for(GuiButton but : this.buttonList) {
//			if(but instanceof GuiButtonClicked && ((GuiButtonClicked) but).clicked) {
//				switch(but.id) {
//				case 0:
//					action = Action.OPEN_URL;
//					break;
//				case 1:
//					action = Action.RUN_COMMAND;
//					break;
//				case 2:
//					action = Action.SUGGEST_COMMAND;
//					break;
//				}
//				break;
//			}
//		}
//		return action;
//	}
	
//	private void desactiveOtherButton(int currentIDActived) {
//		for(GuiButton but : this.buttonList) {
//			if(but instanceof GuiButtonClicked && but.id != currentIDActived)
//				((GuiButtonClicked) but).clicked = false;
//		}
//	}
	
}
