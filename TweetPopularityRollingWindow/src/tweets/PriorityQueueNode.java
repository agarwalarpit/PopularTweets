package tweets;


public class PriorityQueueNode {
	private Long ID; 
	private int retweetCount; 
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PriorityQueueNode)) {
			return false; 
		}
		PriorityQueueNode nodeObj = (PriorityQueueNode) obj; 
		
		return (this.ID == nodeObj.getTweetID()); 
	}
	
	public int getRetweetCount() {
		return this.retweetCount; 
	}
	
	public long getTweetID() {
		return this.ID; 
	}
	
	public PriorityQueueNode(Tweet t, int retweetCount) {
		// TODO Auto-generated constructor stub
		this.ID = t.getKey(); 
		this.retweetCount = retweetCount;
	}
	
	public PriorityQueueNode(long key, int retweetCount) {
		this.ID = key; 
		this.retweetCount = retweetCount; 
	}
}
