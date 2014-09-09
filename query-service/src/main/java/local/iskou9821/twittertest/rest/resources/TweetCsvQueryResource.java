package local.iskou9821.twittertest.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import local.iskou9821.twittertest.logic.TwitterQueryLogic;
import local.iskou9821.twittertest.rest.model.TwitterCsvQueryConfigModel;
import local.iskou9821.twittertest.util.TwitterFactoryUtil;

@Path("/tweetCsvQuery")
public class TweetCsvQueryResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String getCsv(TwitterCsvQueryConfigModel model) {
		return (new TwitterQueryLogic()).queryCsv(model, TwitterFactoryUtil.getInstance());
	}
	
}
