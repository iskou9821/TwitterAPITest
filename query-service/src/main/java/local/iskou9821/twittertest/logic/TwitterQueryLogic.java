package local.iskou9821.twittertest.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.iskou9821.twittertest.rest.model.TwitterCsvQueryConfigModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryConfigModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultDetailModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultModel;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TwitterQueryLogic {
	public String queryCsv(TwitterCsvQueryConfigModel model, Twitter twitter) {
		if (model.getQueryText() == null ||model.getQueryText().equals("")) {
			return "";
		}
		Query q = new Query(model.getQueryText());
		if (model.getCount() > 50) {
			q.setCount(50);
		} else {
			q.setCount(model.getCount());
		}
		
		int maxCnt = model.getCount();
		int currCnt = 0;
		
		try {
			QueryResult r = twitter.search(q);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println(model.getHeaderText());
			Map<Long, Long> fetchedIds = new HashMap<>();
			Template tm = getTemplate("tweetParseTemplate", model.getTweetParseTemplate());
			while (maxCnt > currCnt) {
				for (Status s : r.getTweets()) {
					if (fetchedIds.get(s.getId()) == null) {
						pw.println(parseTweet(s, tm));
						fetchedIds.put(s.getId(), s.getId());
						currCnt++;
					}
					if (currCnt >= maxCnt) break;
				}
				if (!r.hasNext()) break;
				r = twitter.search(r.nextQuery());
			}
			return sw.toString();
		} catch (TwitterException e) {
			throw new IllegalStateException("検索エラー", e);
		}
		
	}
	
	private Template getTemplate(String templateName, String templateBody) {
		StringTemplateLoader sl = new StringTemplateLoader();
		sl.putTemplate(templateName, templateBody);
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(sl);
		cfg.setNumberFormat("0");
		cfg.setDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return cfg.getTemplate(templateName);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}		
	}
	
	private String parseTweet(Status src, Template tm) {
		Map<Object, Object> data = new HashMap<>();
		data.put("item", src);
		StringWriter sw = new StringWriter();
		try {
			tm.process(data, sw);
		} catch (TemplateException | IOException e) {
			throw new IllegalStateException(e);
		}
		return sw.toString();
	}
	
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
