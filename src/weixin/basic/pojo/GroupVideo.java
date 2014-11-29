package weixin.basic.pojo;

public class GroupVideo {
   // 图文消息名称
	private String Title;
	private String Description;
	private String Media_id;
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
    public String getDescription() {
		return Description;
	}

	public void setDescription(String author) {
		Description= author;
	}
	public String getMedia_id() {
		return Media_id;
	}

	public void setMedia_id(String thumbMediaId) {
		Media_id = thumbMediaId;
	}
}
