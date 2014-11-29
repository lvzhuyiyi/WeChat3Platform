package weixin.basic.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class Menu {
	private List<Button> button = new ArrayList<Button>();

	public List<Button> getButton() {
		return button;
	}

	public void setButton(List<Button> button) {
		this.button = button;
	}

}