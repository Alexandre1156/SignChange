package fr.nocturne123.signchange.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonClicked extends GuiButton {

	protected boolean clicked;
	protected boolean blocked;
	
	public GuiButtonClicked(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean isClicked) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.clicked = isClicked;
	}
	
	public GuiButtonClicked(int buttonID, int x, int y, String buttonText, boolean isClicked) {
		super(buttonID, x, y, buttonText);
		this.clicked = isClicked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if(this.isMouseOver() && !blocked) 
			this.clicked = !this.clicked;
		return super.mousePressed(mc, mouseX, mouseY);
	}
	
	@Override
	protected int getHoverState(boolean mouseOver) {
		int i = 1;
        if (!this.enabled)
            i = 0;
        else if (mouseOver || clicked)
            i = 2;
        return i;
	}
	
	public boolean isClicked() {
		return this.clicked;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
