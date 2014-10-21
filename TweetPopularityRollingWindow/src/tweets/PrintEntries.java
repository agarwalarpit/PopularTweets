package tweets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

public class PrintEntries implements Runnable {
	private PriorityBlockingQueue<PriorityQueueNode> pQueue; 
	
	public PrintEntries(PriorityBlockingQueue<PriorityQueueNode> pQueue) {
		this.pQueue = pQueue; 
	}

	@Override
	public void run() {

		/*
		 * Clear screen, works on linux console. 2J is for clear screen. 
		 */
		System.out.print("\033[2J\033[1;1H");
		
		/*
		 * The synchronized block makes sure that pQueue is not being updated while the items 
		 * are being printed. 
		 */
		
		System.out.println("\nPrinting the most retweeted tweets (rank/ID)----"); 

		synchronized (this.pQueue) {
			ArrayList<PriorityQueueNode> pqArrayList = new ArrayList<>();
			for (PriorityQueueNode node : this.pQueue) {
				pqArrayList.add(node); 
			}
			
			Collections.sort(pqArrayList, new PriorityQueueNodeComparator());
						
			for (int i = pqArrayList.size() - 1; i >= 0; i--) {
				System.out.println("Rank: " + (pqArrayList.size() - i) + ", Re-Tweet Count: " + pqArrayList.get(i).getRetweetCount() + ", ID: " + pqArrayList.get(i).getTweetID()); 
			}
		}
	}
}
