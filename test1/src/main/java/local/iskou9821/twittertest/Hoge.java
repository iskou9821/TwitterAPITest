package local.iskou9821.twittertest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class Hoge {
	private static Logger log = LoggerFactory.getLogger(Hoge.class);

	public static void main(String[] args) {
		Twitter tw = new TwitterFactory().getInstance();
		
		try {
			User user = tw.verifyCredentials();
			
			log.info("name:" +user.getName());
			
		} catch (TwitterException e) {
			log.error("ERROR!", e);
		}
	}
}
