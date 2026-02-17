package practiceproblems.design;

import java.util.*;

/**
 * https://leetcode.com/problems/design-twitter/discuss/82825/Java-OO-Design-with-most-efficient-function-getNewsFeed
 *
 **/
public class Twitter {
    private static int timeStamp = 0;
    private Map<Integer, User> userMap;

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        userMap = new HashMap<>();
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        userMap.computeIfAbsent(userId, x -> new User(userId)).post(tweetId);
    }

    // Best part of this.
    // first get all tweets lists from one user including itself and all people it followed.
    // Second add all heads into a max heap. Every time we poll a tweet with
    // the largest time stamp from the heap, then we add its next tweet into the heap.
    // So after adding all heads we only need to add 9 tweets at most into this
    // heap before we get the 10 most recent tweet.
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new LinkedList<>();

        if (!userMap.containsKey(userId)) {
            return result;
        }

        Set<Integer> users = userMap.get(userId).followers;
        PriorityQueue<Tweet> q = new PriorityQueue<>(users.size(), (a, b) -> (b.time - a.time));
        for (int user : users) {
            Tweet t = userMap.get(user).tweetHead;
            // very important! If we add null to the head we are screwed.
            if (t != null) {
                q.add(t);
            }
        }

        while (!q.isEmpty() && result.size() < 10) {
            Tweet t = q.poll();
            result.add(t.id);
            if (t.next != null) {
                q.add(t.next);
            }
        }

        return result;

    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        userMap.computeIfAbsent(followerId, x -> new User(followerId));
        userMap.computeIfAbsent(followeeId, x -> new User(followeeId));
        userMap.get(followerId).follow(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId) || followerId == followeeId) {
            return;
        }
        userMap.get(followerId).unfollow(followeeId);
    }

    // Tweet link to next Tweet so that we can save a lot of time
    // when we execute getNewsFeed(userId)
    private class Tweet {
        public int id;
        public int time;
        public Tweet next;

        public Tweet(int id) {
            this.id = id;
            time = timeStamp++;
            next = null;
        }
    }

    public class User {
        public int id;
        public Set<Integer> followers;
        public Tweet tweetHead;

        public User(int id) {
            this.id = id;
            followers = new HashSet<>();
            follow(id); // first follow yourself
            tweetHead = null;
        }

        public void follow(int id) {
            followers.add(id);
        }

        public void unfollow(int id) {
            followers.remove(id);
        }

        // everytime user post a new tweet, add it to the head of tweet list.
        public void post(int id) {
            Tweet t = new Tweet(id);
            t.next = tweetHead;
            tweetHead = t;
        }
    }
}

class TwitterEff {

    private int timeStamp;
    // Map: UserId -> List of [timestamp, tweetId]
    private Map<Integer, List<int[]>> tweetMap;
    // Map: FollowerId -> Set of FolloweeIds
    private Map<Integer, Set<Integer>> followMap;

    public TwitterEff() {
        timeStamp = 0;
        tweetMap = new HashMap<>();
        followMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        // Use timeStamp++ (positive).
        // Newer tweets will have LARGER numbers.
        tweetMap.computeIfAbsent(userId, k -> new ArrayList<>())
                .add(new int[]{timeStamp++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();

        // OPTIMIZATION: Use Max Heap (b[0] - a[0]) to get the LARGEST timestamp (newest) first.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        // 1. Get all people the user follows
        Set<Integer> followees = followMap.getOrDefault(userId, new HashSet<>());

        // 2. We must also include the user's own tweets (Twitter logic: you see your own posts)
        // We create a temporary checking list so we don't dirty the actual followMap data
        // or rely on side-effects.
        Set<Integer> usersToCheck = new HashSet<>(followees);
        usersToCheck.add(userId);

        // 3. Initialize the Heap
        for (int followeeId : usersToCheck) {
            List<int[]> tweets = tweetMap.get(followeeId);
            // Verify the user actually has tweets before accessing
            if (tweets != null && !tweets.isEmpty()) {
                //This logic is the core of the "Merge $k$ Sorted Lists" algorithm.Why tweets.size() - 1?
                // When we postTweet, we append to an ArrayList.
                // This means the last element (at index size() - 1) is always the most recent tweet (highest timestamp).
                // Since a News Feed wants the newest stuff first, we must start looking at the very end of every user's list.
                int index = tweets.size() - 1;
                int[] tweet = tweets.get(index);

                // Tuple: {timestamp, tweetId, followeeId, indexInTheirList}
                maxHeap.offer(new int[]{tweet[0], tweet[1], followeeId, index});
            }
        }

        // 4. Merge K Sorted Lists logic
        while (!maxHeap.isEmpty() && res.size() < 10) {
            int[] curr = maxHeap.poll(); // Pulls the newest tweet
            res.add(curr[1]); // Add tweetId to result

            // Check if this specific user has older tweets to process
            int index = curr[3];
            int ownerId = curr[2];

            if (index > 0) {
                int[] prevTweet = tweetMap.get(ownerId).get(index - 1);
                // Add the next newest tweet from the SAME user into the heap
                maxHeap.offer(new int[]{prevTweet[0], prevTweet[1], ownerId, index - 1});
            }
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        followMap.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
        }
    }
}