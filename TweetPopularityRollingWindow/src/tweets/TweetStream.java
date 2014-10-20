package tweets;

import java.util.Calendar;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetStream {
	public static final int PriorityQueueLength = 10; 
	public final int N; // N is the last N minutes.

	private TwitterStream twitterStream; 
	private CustomStatusListener listener;  
	
	private Comparator<Tweet> comparator;
	private PriorityBlockingQueue<Tweet> pQueue; 
	
	private ScheduledExecutorService scheduledExecutorService; 
	private WorkerThread worker ; 

	public static final long secondsBetween(Calendar startDate, Calendar endDate) {
	    long end = endDate.getTimeInMillis();
	    long start = startDate.getTimeInMillis();
	    return Math.abs((end - start)/1000);
	}
	
	public TweetStream(int N) {
		this.N = N; 
		this.twitterStream = new TwitterStreamFactory().getInstance();
		
		this.comparator = new TweetComparator();
		this.pQueue = new PriorityBlockingQueue<>(TweetStream.PriorityQueueLength, this.comparator);
		
		this.listener = new CustomStatusListener(this.pQueue, this.N);
	    this.twitterStream.addListener(listener);
	    
	    this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
	    this.worker = new WorkerThread(this.pQueue, this.N);
	    
	    // Schedule the cleanup of the priority queue every 60 seconds. 
	    this.scheduledExecutorService.scheduleAtFixedRate(worker, 0, 60, TimeUnit.SECONDS); 
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
