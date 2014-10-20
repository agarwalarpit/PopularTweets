package tweets;

import twitter4j.Status;

public class Tweet {
	private Status status; 
	
	public Tweet(Status s) {
		this.status = s; 
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
