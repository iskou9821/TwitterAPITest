package local.iskou9821.twittertest.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.core.util.Base64;

import local.iskou9821.twittertest.logic.TwitterQueryLogic;
import local.iskou9821.twittertest.rest.model.TwitterQueryConfigModel;
import local.iskou9821.twittertest.rest.model.TwitterQueryResultModel;
import local.iskou9821.twittertest.util.TwitterFactoryUtil;

@Path("/tweetModels")
public class TwitterQueryResource {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public TwitterQueryResultModel query(@QueryParam("query-text")String queryText,
										@QueryParam("query-count")Integer queryCount,
										@QueryParam("query-max-id")Long queryMaxId,
										@QueryParam("query-min-id")Long queryMinId,
										@QueryParam("include-max-id")Boolean includeMaxIdTweet) {
		String s;
		if (Base64.isBase64(queryText)) {
			s = new String(Base64.decode(queryText));
		} else {
			s = queryText;
		}
		TwitterQueryConfigModel conf = new TwitterQueryConfigModel();
		conf.setCount(queryCount == null ? 10 : queryCount);
		conf.setQueryText(s);
		conf.setQueryMaxId(queryMaxId);
		conf.setQueryMinId(queryMinId);
		conf.setIncludeMaxIdTweet(includeMaxIdTweet == null ? false : includeMaxIdTweet);
		return (new TwitterQueryLogic()).query(conf, TwitterFactoryUtil.getInstance());
	}
	
}
