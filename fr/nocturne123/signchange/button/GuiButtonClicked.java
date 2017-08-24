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
	public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
		if(this.func_146115_a() && !blocked) 
			this.clicked = !this.clicked;
		return super.func_146116_c(mc, mouseX, mouseY);
	}
	
	@Override
	protected int func_146114_a(boolean mouseOver) {
		int i = 1;
        if (!this.field_146124_l)
            i = 0;
        else if (mouseOver || clicked)
            i = 2;
        return i;
	}
	
	public boolean isClicked() {
		return this.clicked;
	}
	
	public void setPosition(int x, int y) {
		this.field_146128_h = x;
		this.field_146129_i = y;
	}

}
