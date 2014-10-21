package tweets;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
public class WorkerThread extends Thread implements Runnable{
 
	private BlockingQueue<Tweet> tweetQueue; 
	
	private PriorityBlockingQueue<PriorityQueueNode> pQueue;
	private Hashtable<Long, TweetList> hashtable; 
	private int N;
	
	private ScheduledExecutorService scheduledExecutorService;
	private PrintEntries printEntries; 
	
    public WorkerThread(BlockingQueue<Tweet> tweetQueue, int N){
    	this.tweetQueue = tweetQueue;  
    	this.N = N; 
    	this.hashtable = new Hashtable<Long, TweetList>(); 
    	
    	Comparator<PriorityQueueNode> tweetComparator = new PriorityQueueNodeComparator();  
    	this.pQueue = new PriorityBlockingQueue<>(TweetStream.PriorityQueueLength, tweetComparator);
    	
	    this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
	    this.printEntries = new PrintEntries(this.pQueue); 
	    
	    /*
	     * Every minute print the top 10 most retweeted tweets. 
	     */
	    this.scheduledExecutorService.scheduleAtFixedRate(this.printEntries, 0, 60, TimeUnit.SECONDS);
    }
 
    @Override
    public void run() {
    	while (true) {
    		Tweet tweet = this.tweetQueue.poll();
    		if (tweet == null) {
				continue;
			}
    		
			Long key = tweet.getStatus().getId();
			
			/*
			 * Synchronized over hashtable. 
			 */
			synchronized (this.hashtable) {
				if (!this.hashtable.containsKey(key)) {
	    			TweetList tweetList = new TweetList(N, tweet); 
					this.hashtable.put(key, tweetList); 
				} else {
					TweetList tweetList = this.hashtable.get(key);
					int retweetCount = tweetList.getTotalRetweetCount(); // This removes the stale entries. 
					tweetList.addTweet(tweet);
					
					/*
					 * Synchronized over priority queue. 
					 */
					synchronized (this.pQueue) {
						
						/*
						 * Recycle the priority queue. Thus, removing the stale elements. 
						 */
				    	Comparator<PriorityQueueNode> tweetComparator = new PriorityQueueNodeComparator();  
						PriorityBlockingQueue<PriorityQueueNode> newPQueue = new PriorityBlockingQueue<>(TweetStream.PriorityQueueLength, tweetComparator); 
						for (PriorityQueueNode priorityQueueNode : pQueue) {
							long tweetID = priorityQueueNode.getTweetID();
							TweetList tempTweetList = this.hashtable.get(tweetID);
							tempTweetList.removeOld();
							newPQueue.add(new PriorityQueueNode(tweetID, tempTweetList.getTotalRetweetCount())); 
						}
						this.pQueue.clear(); 
						this.pQueue.addAll(newPQueue); 
						
						/*
						 * Placing the new incoming Tweet in the priority queue (if). 
						 */
						PriorityQueueNode pQueueNode = new PriorityQueueNode(key, retweetCount); 
						if (this.pQueue.contains(pQueueNode)) {
							this.pQueue.remove(pQueueNode); 
						} 
						
						this.pQueue.add(pQueueNode);
						
						if (this.pQueue.size() > TweetStream.PriorityQueueLength) {
							this.pQueue.poll(); 
						}
					}
				}
			}
		}
    }
}