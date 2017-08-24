package fr.nocturne123.signchange.button;

import net.minecraft.client.Minecraft;

public class GuiButtonIcon extends GuiButtonClicked {

	protected Runnable rightClick, leftClick;
	
	public GuiButtonIcon(int buttonId, int x, int y, char buttonText, boolean isClicked) {
		super(buttonId, x, y, 20, 20, String.valueOf(buttonText), isClicked);
	}
	
	public boolean mouseClicked(Minecraft mc, int mouseX, int mouseY, int mouseButton) {
		if(this.isMouseOver()) {
			switch(mouseButton) {
			case 0: //Left click
				if(this.leftClick != null)
					this.leftClick.run();
				return true;
			case 1: //Right click
				if(this.rightClick != null)
					this.rightClick.run();
				return true;
			default:
				return false;
			}
		}
		return false;
	}

}
