package tweets;

import java.util.Calendar;
import java.util.concurrent.PriorityBlockingQueue;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

public class CustomStatusListener implements twitter4j.StatusListener {
	private PriorityBlockingQueue<Tweet> pQueue; 
	private int N; 
	
	public CustomStatusListener(PriorityBlockingQueue<Tweet> pQueue, int N) {		
		this.pQueue = pQueue;   
		this.N = N; 
	}
	
	@Override
    public void onStatus(Status status) {
		
		/*
		 * Assuming we are not counting the tweets that are sent out by the authenticating user's account. 
		 * 
		 * status.getReteweetedStatus() -> Gives the Status object for the tweet that is retweeted, 
		 * not by the authenticating user.  
		 * 
		 */
		
		if (status.getRetweetedStatus() != null) {
    		Status originalStatus = status.getRetweetedStatus();
    		Tweet originalTweet = new Tweet(originalStatus); 
			
    		Calendar calendarTweet = Calendar.getInstance();
			Calendar presentTime = Calendar.getInstance();
    		calendarTweet.setTime(originalTweet.getStatus().getCreatedAt());
    		
    		/*
			 * Check if the Tweet should be added to the pQueue. 
			 * Conditions to being added: 
			 * 1. Should not be older than N * 60 seconds. (Last N minutes). 
			 * 2. Should not be already in the queue.  
			 */
			if (!(TweetStream.secondsBetween(calendarTweet, presentTime) > this.N * 60)) {
				if (!this.pQueue.contains(originalTweet)) {
					this.pQueue.add(originalTweet);
				} else {
					this.pQueue.remove(originalTweet); 
					this.pQueue.add(originalTweet); 
				}
			}
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