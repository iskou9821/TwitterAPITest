package local.iskou9821.twittertest.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({TwitterQueryResultDetailModel.class})
public class TwitterQueryResultModel {
	private int count;
	private Long pageTopId;
	private Long pageLastId;
	private Boolean lastPage;
	private TwitterQueryResultDetailModel[] details;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getPageTopId() {
		return pageTopId;
	}

	public void setPageTopId(Long pageTopId) {
		this.pageTopId = pageTopId;
	}

	public Long getPageLastId() {
		return pageLastId;
	}

	public void setPageLastId(Long pageLastId) {
		this.pageLastId = pageLastId;
	}

	public TwitterQueryResultDetailModel[] getDetails() {
		return details;
	}

	public void setDetails(TwitterQueryResultDetailModel[] details) {
		this.details = details;
	}

	public Boolean getLastPage() {
		return lastPage;
	}

	public void setLastPage(Boolean lastPage) {
		this.lastPage = lastPage;
	}
}
