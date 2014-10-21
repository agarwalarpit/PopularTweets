package tweets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TweetList {
	
	private int totalRetweets; 
	private ArrayList<Tweet> tweetList; 
	private int nMinutes; 
	
	public TweetList(int N) {
		this.tweetList = new ArrayList<Tweet>();
		this.nMinutes = N; 
	}
	
	public TweetList(int N, Tweet tweet) {
		this.tweetList = new ArrayList<Tweet>();
		this.tweetList.add(tweet); 
		this.nMinutes = N; 
	}
	
	public void addTweet(Tweet tweet) {
		removeOld(); 
		this.tweetList.add(tweet); 
	}
	
	public int getTweetListSize() {
		return this.tweetList.size(); 
	}
	
	public int getTotalRetweetCount() {
		removeOld();
		Collections.sort(this.tweetList, new TweetTimeComparator());
		this.totalRetweets = 0; 
		
		for (int i = 1; i < this.tweetList.size(); i++) {
			this.totalRetweets += this.tweetList.get(i).getRetweetCount() - this.tweetList.get(i-1).getRetweetCount(); 
		}
		
		return this.totalRetweets; 
	}
	
	public int removeOld() {
		ArrayList<Tweet> newList = new ArrayList<>(); 
		Calendar presentTime = Calendar.getInstance();
		int initialSize = this.tweetList.size(); 
		
		for (int i = 0; i < initialSize; i++) {
			if (!this.tweetList.get(i).isOld(presentTime, nMinutes)) {
				newList.add(this.tweetList.get(i)); 
			}
		}
		
		this.tweetList.clear();
		this.tweetList.addAll(newList);
		
		return initialSize - newList.size();
	}
}
