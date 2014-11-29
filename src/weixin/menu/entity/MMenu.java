package weixin.menu.entity;

import java.util.ArrayList;
import java.util.List;

public class MMenu {
	private List<MButton> button = new ArrayList<MButton>();

	public List<MButton> getButton() {
		return button;
	}

	public void setButton(List<MButton> button) {
		this.button = button;
	}

}
