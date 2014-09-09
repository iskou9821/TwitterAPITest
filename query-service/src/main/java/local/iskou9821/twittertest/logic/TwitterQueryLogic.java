package local.iskou9821.twittertest.logic;

import java.util.ArrayList;
import java.util.List;

import local.iskou9821.twittertest.rest.model.TwitterQueryConfigModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultDetailModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultModel;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;

public class TwitterQueryLogic {
	public TwitterQueryResultModel query(TwitterQueryConfigModel model, Twitter twitter) {
		if (model.getQueryText() == null ||model.getQueryText().equals("")) {
			return new TwitterQueryResultModel();
		}
		Query q = new Query(model.getQueryText());
		q.setCount(model.getCount());
		
		boolean maxIdExists = false;
		if (model.getQueryMaxId() != null && model.getQueryMaxId() != 0) {
			q.setMaxId(model.getQueryMaxId());
			if (!model.isIncludeMaxIdTweet()) {
				q.setCount(q.getCount() + 1);
			}
			maxIdExists = true;
		}
		if (model.getQueryMinId() != null && model.getQueryMinId() != 0) {
			q.setSinceId(model.getQueryMinId());
		}
		
		QueryResult r;
		try {
			r = twitter.search(q);
		} catch (Exception e) {
			throw new IllegalStateException("検索エラー", e);
		}
		TwitterQueryResultModel res = new TwitterQueryResultModel();
		res.setCount(r.getCount());
		res.setLastPage(!r.hasNext());
		
		List<TwitterQueryResultDetailModel> l = new ArrayList<>();
		int idx = 0;
		for (Status s : r.getTweets()) {
			if (maxIdExists && idx++ == 0 && !model.isIncludeMaxIdTweet()) continue;
			l.add(convert(s));
		}
		res.setDetails(l.toArray(new TwitterQueryResultDetailModel[0]));
		
		if (l.size() > 0) {
			res.setPageLastId(l.get(l.size() - 1).getId());
			res.setPageTopId(l.get(0).getId());
		}
		
		return res;
	}
	
	private TwitterQueryResultDetailModel convert(Status src) {
		TwitterQueryResultDetailModel d = new TwitterQueryResultDetailModel();
		d.setText(src.getText());
		d.setUserName(src.getUser().getName());
		d.setId(src.getId());
		return d;
	}
	
}
