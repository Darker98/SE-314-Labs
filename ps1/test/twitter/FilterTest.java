/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
//    @Test(expected=AssertionError.class)
//    public void testAssertionsEnabled() {
//        assert false; // make sure assertions are enabled with VM argument: -ea
//    }
//    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    @Test
    public void testWrittenBySingle() {
        Tweet t1 = new Tweet(1, "alice", "hi", Instant.parse("2023-02-10T10:00:00Z"));
        Tweet t2 = new Tweet(2, "bob", "yo", Instant.parse("2023-02-10T11:00:00Z"));

        List<Tweet> result = Filter.writtenBy(Arrays.asList(t1, t2), "Alice");

        List<Tweet> expected = Arrays.asList(t1);
        assertEquals(expected, result);
    }
    
    @Test
    public void testInTimespan() {
        Instant start = Instant.parse("2023-02-10T00:00:00Z");
        Instant end   = Instant.parse("2023-02-11T00:00:00Z");
        Timespan span = new Timespan(start, end);

        Tweet inside = new Tweet(1, "a", "hi", Instant.parse("2023-02-10T10:00:00Z"));
        Tweet outside = new Tweet(2, "b", "bye", Instant.parse("2023-02-12T10:00:00Z"));

        List<Tweet> result = Filter.inTimespan(Arrays.asList(inside, outside), span);

        List<Tweet> expected = Arrays.asList(inside);
        assertEquals(expected, result);
    }

    @Test
    public void testContainingSingleWord() {
        Tweet t1 = new Tweet(1, "a", "I love this project", Instant.parse("2023-02-10T10:00:00Z"));
        Tweet t2 = new Tweet(2, "b", "Nothing special", Instant.parse("2023-02-10T11:00:00Z"));

        List<Tweet> result = Filter.containing(Arrays.asList(t1, t2), Arrays.asList("love"));
        List<Tweet> expected = Arrays.asList(t1);
        assertEquals(expected, result);
    }

    @Test
    public void testContainingCaseInsensitive() {
        Tweet t1 = new Tweet(1, "a", "I LoVe this", Instant.parse("2023-02-10T10:00:00Z"));
        Tweet t2 = new Tweet(2, "b", "I dislike it", Instant.parse("2023-02-10T11:00:00Z"));

        List<Tweet> result = Filter.containing(Arrays.asList(t1, t2), Arrays.asList("love"));
        List<Tweet> expected = Arrays.asList(t1);
        assertEquals(expected, result);
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
