package local.iskou9821.twittertest.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TwitterCsvQueryConfigModel {
	private String queryText;
	private int count;
	private String headerText;
	private String tweetParseTemplate;
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
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	public String getTweetParseTemplate() {
		return tweetParseTemplate;
	}
	public void setTweetParseTemplate(String tweetParseTemplate) {
		this.tweetParseTemplate = tweetParseTemplate;
	}	
}
