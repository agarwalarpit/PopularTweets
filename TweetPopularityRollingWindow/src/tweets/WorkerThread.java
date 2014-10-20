package tweets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.PriorityBlockingQueue;
 
public class WorkerThread implements Runnable{
 
	private PriorityBlockingQueue<Tweet> pQueue;
	private int N; 
	
    public WorkerThread(PriorityBlockingQueue<Tweet> pQueue, int N){
        this.pQueue = pQueue;
        this.N = N; 
    }
 
    @Override
    public void run() {
    	/*
		 * Operations to be done every minute: 
		 * 1. Remove all the stale Tweets from the priority queue.
		 * 2. Keep only the top 10 Tweets in the queue. 
		 * 3. Print all the elements of the priority queue. 
		 * 
		 */

		Calendar presentTime = Calendar.getInstance(); 
		Calendar tweetTime = Calendar.getInstance();
		ArrayList<Tweet> toRemoveTweets = new ArrayList<>();

		// Find the Tweets that are stale. 
		for (Tweet tweet : pQueue) {
			tweetTime.setTime(tweet.getStatus().getCreatedAt());
			long timeElapsed = TweetStream.secondsBetween(tweetTime, presentTime);
			if (timeElapsed >= 60 * this.N) {
				toRemoveTweets.add(tweet); 
			}
		}
		
		if (toRemoveTweets.size() > 0) {
			System.out.println("Going to remove " + toRemoveTweets.size() + " stale elements.");
		}
		
		// Remove all the stale Tweets from the Queue. 
		for (Tweet tweet : toRemoveTweets) {
			this.pQueue.remove(tweet); 
		}
		
		// Keep only the top 10 Tweets. 
		while (this.pQueue.size() > TweetStream.PriorityQueueLength) {
			this.pQueue.remove();  
		}
		
		System.out.println("There are " + this.pQueue.size() + " number of tweets in the priority queue");
		// Print all the Tweets in the queue, when this thread is executed. 
		for (Tweet tweet : pQueue) {
			System.out.print(
				tweet.getStatus().getRetweetCount() + 
				": " + 
				tweet.getStatus().getCreatedAt() + 
				": "
			);
			
			System.out.print("Username: " + tweet.getStatus().getUser().getScreenName());
			
			if (tweet.getStatus().getLang().equals(Locale.ENGLISH.toString())) {
				System.out.println(", Text(" + tweet.getStatus().getLang() + "): " + tweet.getStatus().getText()); 
			} else {
				// System.out.println("Not printed because text not English");
				System.out.println(", Text(" + tweet.getStatus().getLang() + "): " + tweet.getStatus().getText());
			}
		}
    }
}