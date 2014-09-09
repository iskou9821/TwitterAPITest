package local.iskou9821.twittertest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFactoryUtil {
	public static Twitter getInstance() {
		return twitter;
	}
	
	private static TwitterFactory createFactory(String fileName) throws IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(fileName));
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder
			.setDebugEnabled("true".equals(p.getProperty("debug-enabled")))
			.setOAuthConsumerKey(p.getProperty("oauth.consumer-key"))
			.setOAuthConsumerSecret(p.getProperty("oauth.consumer-secret"))
			.setOAuthAccessToken(p.getProperty("oauth.access-token"))
			.setOAuthAccessTokenSecret(p.getProperty("oauth.access-token-secret"));
		
		return new TwitterFactory(builder.build());
	}
	
	private static Twitter twitter = null;
	static {
		try {
			twitter = createFactory("/var/conf/twitter-dev.properties").getInstance();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
