
package tweets;
import java.util.Comparator;

public class TweetTimeComparator implements Comparator<Tweet> {
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 * Compare tweets by the first seen time. 
	 */
	@Override
	public int compare(Tweet t1, Tweet t2) {
		return t1.getFirstSeenTime().compareTo(t2.getFirstSeenTime()); 
	}
		
}