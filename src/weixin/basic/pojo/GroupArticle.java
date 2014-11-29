package weixin.basic.pojo;

public class GroupArticle {
	// 图文消息名称
	private String Title;
	private String Author;
	private String Thumb_media_id;
	private String Content_source_url;
	private String Content;
	private String Digest;
	private int Show_cover_pic;

	public GroupArticle() {

	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getThumb_media_id() {
		return Thumb_media_id;
	}

	public void setThumb_media_id(String thumbMediaId) {
		Thumb_media_id = thumbMediaId;
	}

	public String getContent_source_url() {
		return Content_source_url;
	}

	public void setContent_source_url(String contentSourceUrl) {
		Content_source_url = contentSourceUrl;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getDigest() {
		return Digest;
	}

	public void setDigest(String digest) {
		Digest = digest;
	}

	public int getShow_cover_pic() {
		return Show_cover_pic;
	}

	public void setShowCoverPic(int showCoverPic) {
		Show_cover_pic = showCoverPic;
	}
}
