package tweets;

import java.util.Comparator;

public class PriorityQueueNodeComparator implements Comparator<PriorityQueueNode> {
	
	@Override
	public int compare(PriorityQueueNode o1, PriorityQueueNode o2) {
		if (o1.getRetweetCount() > o2.getRetweetCount()) {
			return 1; 
		}
		if (o1.getRetweetCount() < o2.getRetweetCount()) {
			return -1; 
		}
		return 0; 
	}
}
