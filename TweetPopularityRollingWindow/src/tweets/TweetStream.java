package tweets;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetStream {
	public static final int PriorityQueueLength = 10; 
	public final int N; // N is the last N minutes.
	private BlockingQueue<Tweet> tweetQueue; 

	private TwitterStream twitterStream; 
	private CustomStatusListener listener;  
		
	private WorkerThread worker ; 

	public TweetStream(int N) {
		this.N = N; 
		this.twitterStream = new TwitterStreamFactory().getInstance();
		
		// Create a "large enough" blocking queue for the incoming Tweets. 
		this.tweetQueue = new ArrayBlockingQueue<>(1024); 
		
		this.listener = new CustomStatusListener(this.tweetQueue);
	    this.twitterStream.addListener(listener);
	    
	    this.worker = new WorkerThread(this.tweetQueue, this.N);
	    this.worker.start(); 
	}
	
	public static void main(String[] args) {
		System.out.println("Run by: java -jar TweetPopularityRollingWindow.jar <Integer value for N>, e.g. java -jar TweetPopularityRollingWindow.jar 5");
		System.out.println("Please set the variables: N - Last N minutes of the stream, of which we analyze the twitter data.");
		int lastNMinutes = 10;
		
		if (args.length >= 1) {
			lastNMinutes = Integer.parseInt(args[0]);
			System.out.println("N set to last " + lastNMinutes + " minutes.");
		} else {
			System.out.println("N not set. Default N = 10 minutes.");
		}
		
		TweetStream tweetStream = new TweetStream(lastNMinutes);		
		tweetStream.twitterStream.sample();
	}
}
