package tweets;

import java.util.Calendar;

import twitter4j.Status;

public class Tweet {
	
	private Status status; 
	private int diffRetweetCount; 
	private Calendar firstSeenTime; 

	public Calendar getFirstSeenTime () {
		return this.firstSeenTime; 
	}
	
	public int getDiffRetweetCount() {
		return diffRetweetCount;
	}
	
	public int getRetweetCount() {
		return this.status.getRetweetCount(); 
	}

	public void setDiffRetweetCount(int diffRetweetCount) {
		this.diffRetweetCount = diffRetweetCount;
	}

	public void setLastseen(Calendar lastseen) {
		this.firstSeenTime = lastseen;
	}

	public long getKey() {
		return this.status.getId(); 
	}
	
	public boolean isOld(Calendar presentTime, int nMinutes) {
		if (secondsBetween(this.firstSeenTime, presentTime) > nMinutes*60 ) {
			return true; 
		} else {
			return false; 
		}
	}
	
	public static final long secondsBetween(Calendar startDate, Calendar endDate) {
	    long end = endDate.getTimeInMillis();
	    long start = startDate.getTimeInMillis();
	    return Math.abs((end - start)/1000);
	}
	
	public Tweet(Status s) {
		this.status = s;
		this.diffRetweetCount = 0;
		this.firstSeenTime = Calendar.getInstance(); 
	}
	
	public Status getStatus() {
		return this.status; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Tweet)) {
			return false; 
		}
		Tweet tweetObj = (Tweet) obj; 
		
		return (this.status.getId() == tweetObj.getStatus().getId()); 
	}
}
