package tweets;
// StringLengthComparator.java
import java.util.Comparator;

public class TweetComparator implements Comparator<Tweet>
{
    @Override
    public int compare(Tweet t1, Tweet t2)
    {
    	if (t1.getStatus().getRetweetCount() > t2.getStatus().getRetweetCount()) {
			return 1; 
		} else if (t1.getStatus().getRetweetCount() < t2.getStatus().getRetweetCount()) {
			return -1; 
		} else {
			return 0; 
		}
    }
}