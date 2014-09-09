package local.iskou9821.twittertest.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.MoreObjects;

@XmlRootElement
public class TwitterQueryResultDetailModel {
	private String userName;
	private String text;
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(getClass())
			.add("userName", userName)
			.add("text", text)
			.add("id", id)
			.toString();
	}
}
