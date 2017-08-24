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
				Minecraft.func_71410_x().func_147108_a(new GuiClick(GuiButtonClick.this, (GuiSignChange) Minecraft.func_71410_x().field_71462_r));
			}
		};
	}
	
	@Override
	public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
		return this.field_146124_l && this.field_146125_m && mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g;
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
