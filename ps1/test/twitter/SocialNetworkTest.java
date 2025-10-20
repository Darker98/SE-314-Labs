/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
//    @Test(expected=AssertionError.class)
//    public void testAssertionsEnabled() {
//        assert false; // make sure assertions are enabled with VM argument: -ea
//    }
    
    // --- Shared Timestamps ---
    private static final Instant time1 = Instant.parse("2020-01-01T10:00:00Z");
    private static final Instant time2 = Instant.parse("2020-01-01T11:00:00Z");
    private static final Instant time3 = Instant.parse("2020-01-01T12:00:00Z");

    // --- Tests for guessFollowsGraph() ---

    // Empty list of tweets
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    // Tweets without mentions
    @Test
    public void testTweetsWithoutMentions() {
        Tweet t1 = new Tweet(1, "Alice", "Just chilling", time1);
        Tweet t2 = new Tweet(2, "Bob", "Another day", time2);
        Map<String, Set<String>> graph = SocialNetwork.guessFollowsGraph(Arrays.asList(t1, t2));
        assertTrue("Tweets without mentions should not create graph entries", graph.isEmpty());
    }

    // Tweet with single mention
    @Test
    public void testSingleMention() {
        Tweet t1 = new Tweet(1, "Alice", "Hi @Bob!", time1);
        Map<String, Set<String>> graph = SocialNetwork.guessFollowsGraph(Arrays.asList(t1));

        assertTrue("Graph should contain Alice", graph.containsKey("alice"));
        assertTrue("Alice should follow Bob", graph.get("alice").contains("bob"));
    }

    // Tweet with multiple mentions
    @Test
    public void testMultipleMentions() {
        Tweet t1 = new Tweet(1, "Alice", "Hello @Bob and @Charlie!", time1);
        Map<String, Set<String>> graph = SocialNetwork.guessFollowsGraph(Arrays.asList(t1));

        Set<String> expected = new HashSet<String>(Arrays.asList("bob", "charlie"));
        assertEquals(expected, graph.get("alice"));
    }

    // Multiple tweets from one user
    @Test
    public void testMultipleTweetsFromSameUser() {
        Tweet t1 = new Tweet(1, "Alice", "Nice post @Bob", time1);
        Tweet t2 = new Tweet(2, "Alice", "Also hi @Charlie", time2);
        Map<String, Set<String>> graph = SocialNetwork.guessFollowsGraph(Arrays.asList(t1, t2));

        Set<String> expected = new HashSet<String>(Arrays.asList("bob", "charlie"));
        assertEquals(expected, graph.get("alice"));
    }

    // --- Tests for influencers() ---

    // Empty graph
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<String, Set<String>>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue("expected empty list", influencers.isEmpty());
    }

    // Single user without followers
    @Test
    public void testSingleUserWithoutFollowers() {
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        graph.put("alice", new HashSet<String>());
        List<String> influencers = SocialNetwork.influencers(graph);

        List<String> expected = Arrays.asList("alice");
        assertEquals(expected, influencers);
    }

    // Single influencer
    @Test
    public void testSingleInfluencer() {
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        graph.put("alice", new HashSet<String>(Arrays.asList("bob")));
        List<String> influencers = SocialNetwork.influencers(graph);

        assertEquals("Bob should come first (1 follower)", "bob", influencers.get(0));
    }

    // Multiple influencers
    @Test
    public void testMultipleInfluencers() {
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        graph.put("alice", new HashSet<String>(Arrays.asList("bob", "charlie")));
        graph.put("bob", new HashSet<String>(Arrays.asList("charlie")));
        graph.put("charlie", new HashSet<String>());

        List<String> influencers = SocialNetwork.influencers(graph);
        assertEquals("charlie should be first (2 followers)", "charlie", influencers.get(0));
        assertTrue(influencers.indexOf("bob") < influencers.indexOf("alice"));
    }

    // Tied influence
    @Test
    public void testTiedInfluence() {
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        graph.put("alice", new HashSet<String>(Arrays.asList("bob")));
        graph.put("charlie", new HashSet<String>(Arrays.asList("bob")));
        graph.put("bob", new HashSet<String>(Arrays.asList("alice")));

        List<String> influencers = SocialNetwork.influencers(graph);
        assertTrue("Both Alice and Bob should appear in the list",
                influencers.containsAll(Arrays.asList("alice", "bob")));
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
