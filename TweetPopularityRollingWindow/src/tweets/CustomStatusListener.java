package tweets;

import java.util.concurrent.BlockingQueue;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

public class CustomStatusListener implements twitter4j.StatusListener {
	private BlockingQueue<Tweet> tweetQueue; 
	
	public CustomStatusListener(BlockingQueue<Tweet> tweetQueue) {
		this.tweetQueue = tweetQueue; 
	}
	
	@Override
    public void onStatus(Status status) {
		
		/*
		 * We are not counting the tweets that are sent out by the authenticating user's account.  
		 * status.getReteweetedStatus() -> Gives the Status object for the tweet that is retweeted, 
		 * not by the authenticating user.  
		 * 
		 */
		
		if (status.getRetweetedStatus() != null) {
			Status originalStatus = status.getRetweetedStatus();
    		Tweet originalTweet = new Tweet(originalStatus);
    		this.tweetQueue.add(originalTweet); 
		} 
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}