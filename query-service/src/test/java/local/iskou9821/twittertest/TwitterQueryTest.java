package local.iskou9821.twittertest;

import junit.framework.TestCase;
import local.iskou9821.twittertest.logic.TwitterQueryLogic;
import local.iskou9821.twittertest.rest.model.TwitterCsvQueryConfigModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryConfigModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultDetailModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultModel;
import local.iskou9821.twittertest.util.TwitterFactoryUtil;
import twitter4j.Twitter;

public class TwitterQueryTest extends TestCase {
	public void testCsvQuery() throws Exception {
		Twitter tw = TwitterFactoryUtil.getInstance();
		
		TwitterCsvQueryConfigModel m = new TwitterCsvQueryConfigModel();
		m.setCount(100);
		m.setQueryText("#njslyr");
		m.setHeaderText("hoge,hoge,hoge");
		m.setTweetParseTemplate("${item.id}");//,${item.createdAt?datetime},${item.user.name},\"${item.text}\"");
		
		System.out.println((new TwitterQueryLogic()).queryCsv(m, tw));
	}
	
	public void testQuery() throws Exception {
		Twitter tw = TwitterFactoryUtil.getInstance();
		
		TwitterQueryConfigModel m = new TwitterQueryConfigModel();
		m.setCount(10);
		m.setQueryText("#Tシャツの脱ぎ方教えて");
		
		TwitterQueryResultModel r = (new TwitterQueryLogic()).query(m, tw);
		System.out.println(r.getCount());
		for (TwitterQueryResultDetailModel d : r.getDetails()) {
			System.out.println(d);
		}
		m.setQueryMaxId(r.getDetails()[r.getDetails().length - 1].getId());
		
		r = (new TwitterQueryLogic()).query(m, tw);
		System.out.println(r.getCount());
		for (TwitterQueryResultDetailModel d : r.getDetails()) {
			System.out.println(d);
		}	
	}
}
