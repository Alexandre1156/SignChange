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
	public void initGui() {
		//this.addButton(new GuiButtonClicked(0, this.width / 3 - 200, this.height / 4, "Open URL", clickBut.getOnClick() != null && clickBut.getOnClick().getAction() == Action.OPEN_URL));
		GuiButtonClicked but = this.addButton(new GuiButtonClicked(1, this.width / 3 + 5, this.height / 4, "Run command", true));
		but.setBlocked(true);
		//this.addButton(new GuiButtonClicked(2, this.width / 3 + 210, this.height / 4, "Suggest command", clickBut.getOnClick() != null && clickBut.getOnClick().getAction() == Action.SUGGEST_COMMAND));
		this.addButton(new GuiButton(3, this.width / 2 - 100, this.height - 50, "Save"));
		this.textField = new GuiTextField(4, fontRenderer, this.width / 2 - 100, this.height / 4 * 2, 200, 20);
		this.textField.setCanLoseFocus(false);
		this.textField.setFocused(true);
		this.textField.setText(clickBut.getOnClick() != null ? clickBut.getOnClick().getValue() : "");
		Keyboard.enableRepeatEvents(true);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.textField.textboxKeyTyped(typedChar, keyCode);
		if(keyCode == 1) {
			Minecraft.getMinecraft().displayGuiScreen(guiSign);
			clickBut.setOnClick(clickBut.getOnClick() != null ? clickBut.getOnClick() : null);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.textField.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void updateScreen() {
		this.textField.updateCursorCounter();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.textField.drawTextBox();
		this.drawCenteredString(fontRenderer, TextFormatting.BOLD+"When player right click on the sign...", this.width / 2, this.height / 4 - 20, Color.white.getRGB());
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.enabled) {
			switch(button.id) {
//			case 0:
//			case 1:
//			case 2:
//				this.desactiveOtherButton(button.id);
//				break;
			case 3:
//				Action action = this.getActionByButtonActived();
				String value = this.textField.getText();
				if(/*action != null && */value != null && !value.isEmpty())
					this.clickBut.setOnClick(new ClickEvent(Action.RUN_COMMAND, this.textField.getText()));
				else
					this.clickBut.setOnClick(null);
				Minecraft.getMinecraft().displayGuiScreen(guiSign);
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
