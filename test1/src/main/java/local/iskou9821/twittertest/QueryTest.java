package local.iskou9821.twittertest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class QueryTest {
	private static Logger log = LoggerFactory.getLogger(QueryTest.class);

	public static void main(String[] args) {
		Twitter tw = TwitterFactory.getSingleton();
		
		Query q = new Query("#njslyr");
		q.setCount(20);
		
		try {
			QueryResult r = tw.search(q);
			log.info("tweets:" + r.getTweets().size());
			for (Status s : r.getTweets()) {
				log.info(s.getUser().getName() + "/" + s.getText());
			}
		} catch (TwitterException e) {
			log.error("ERROR!", e);
		}
	}
}
