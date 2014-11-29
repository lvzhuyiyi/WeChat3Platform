package weixin.template.entity;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity(name = "textTemplate")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "pUser" })
public class TextTemplate extends BaseTemplate {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
