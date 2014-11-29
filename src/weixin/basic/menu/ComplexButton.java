package weixin.basic.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 复杂按钮（父按钮）
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class ComplexButton extends Button {
	private List<Button> sub_button = new ArrayList<Button>();

	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}

}