package fr.nocturne123.signchange.button;

import fr.nocturne123.signchange.gui.GuiClick;
import fr.nocturne123.signchange.gui.GuiSignChange;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.event.ClickEvent;

public class GuiButtonClick extends GuiButtonIcon {

	private ClickEvent click;
	
	public GuiButtonClick(int buttonId, int x, int y, char buttonText, ClickEvent onClick) {
		super(buttonId, x, y, buttonText, onClick != null);
		this.click = onClick;
		this.rightClick = new Runnable() {
			@Override
			public void run() {
				GuiButtonClick.this.clicked = false;
				GuiButtonClick.this.click = null;
			}
		};
		this.leftClick = new Runnable() {
			@Override
			public void run() {
				Minecraft.getMinecraft().displayGuiScreen(new GuiClick(GuiButtonClick.this, (GuiSignChange) Minecraft.getMinecraft().currentScreen));
			}
		};
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
		
	public ClickEvent getOnClick() {
		return click;
	}
	
	public void setOnClick(ClickEvent onClick) {
		this.click = onClick;
		if(click != null)
			this.clicked = true;
		else
			this.clicked = false;
	}
	
}
