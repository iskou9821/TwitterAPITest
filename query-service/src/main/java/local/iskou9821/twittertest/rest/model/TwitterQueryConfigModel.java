package local.iskou9821.twittertest.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TwitterQueryConfigModel {
	private String queryText;
	private int count = 15;
	private Long queryMaxId = null;
	private Long queryMinId = null;
	private boolean includeMaxIdTweet = false;
	
	public boolean isIncludeMaxIdTweet() {
		return includeMaxIdTweet;
	}

	public void setIncludeMaxIdTweet(boolean includeMaxIdTweet) {
		this.includeMaxIdTweet = includeMaxIdTweet;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getQueryMinId() {
		return queryMinId;
	}

	public void setQueryMinId(Long queryMinId) {
		this.queryMinId = queryMinId;
	}

	public Long getQueryMaxId() {
		return queryMaxId;
	}

	public void setQueryMaxId(Long queryMaxId) {
		this.queryMaxId = queryMaxId;
	}
}
