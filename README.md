PopularTweets
=============

Prints out the 10 most popular tweets in a rolling window of time. 

Please run the jar file using: 

    java -jar TweetPopularityRollingWindow.jar <Integer value for N>, e.g. 

    java -jar TweetPopularityRollingWindow.jar 5


The output format is: 

    <Retweet Count> : <Created At> : Username: <screen name of the user on twitter>, Text(<language of the tweet>): <Tweet text in whatever language>

Please note that the tweet text would be garbled if the console does not understand those languages. So you might get ? instead of text. The language of the tweet is written after Text. 

    e.g. Text(en). 

Indicates tweet text in English. 
