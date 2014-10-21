PopularTweets
=============

Prints out the 10 most retweeted tweets in a rolling window of time. 

Please run the jar file using: 

    java -jar TweetPopularityRollingWindow.jar <Integer value for N>, e.g. 

    java -jar TweetPopularityRollingWindow.jar 5


The output format is: 

    Rank : <Rank between 1-10> Re-Tweet Count: <Number of times retweeted>, ID: <Tweet ID>

-------

Data Structure: 

    PriorityQueues (comparable element: Retweet Count in the interval). 
    Hashtable <key=TweetID, value=TweetList> 
    
    TweetList - DataStructure to hold all the tweets of a particular tweet ID
    
Code complexity: 

    For every incoming tweet, there is a constant time operation to remove stale tweets, and their references. 
    O(K) for every incoming tweet, where K is of max-order 2. 
    
    Space: O(n) : Worst case
    Where n is the number of tweets received in an nMinute minute period. 
    
    nMinute: The size of the rolling window. 
    


