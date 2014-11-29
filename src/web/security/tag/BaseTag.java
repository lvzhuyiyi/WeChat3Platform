package web.security.tag;

import javax.servlet.jsp.tagext.TagSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class BaseTag extends TagSupport {

     protected void print(String str) throws JspException {
        try {
        	//PageContext是JspContext子类
            pageContext.getOut().print(str);
        }
        catch(java.io.IOException e) {
            throw new JspException(e.getMessage());
        }
    }

    
}
