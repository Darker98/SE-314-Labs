/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    /**
     * add(vertex):
     * 		- if vertex not in graph, return true and modify graph
     * 		- if vertex in graph, return false
     * 
     * set(source, target, weight):
     * 		if weight > 0:
     * 			- new edge added between existing vertices
     * 			- new edge added when source or target not previously in graph
     * 			- existing edge updated, returning previous weight
     * 		if weight = 0:
     * 			- remove an existing edge, return previous weight
     * 			- remove non-existing edge, return 0
     * 
     * remove(vertex):
     * 		- if vertex exists, return true
     * 		- if vertex does not exist, return false
     * 		- all edges associated with vertex also removed
     * 
     * vertices():
     * 		- initialized to empty set
     * 		- returns all vertices
     * 
     * sources(target): 
     * 		- target has no incoming edges, return empty Map
     * 		- target has multiple incoming edges, return Map
     * 
     * targets(source):
     * 		- source has no outgoing edges, return empty Map
     * 		- source has multiple outgoing edges, return Map
     */
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    

    // Creating new graph
    @Test
    public void testNewGraphIsEmpty() {
        Graph<String> g = emptyInstance();
        assertTrue("Newly created graph should have no vertices", g.vertices().isEmpty());
    }

    // Adding vertices
    @Test
    public void testAddVertex() {
        Graph<String> g = emptyInstance();
        assertTrue("Adding a new vertex should return true", g.add("A"));
        assertTrue("Graph should contain vertex A", g.vertices().contains("A"));
    }

    // Preventing duplicates
    @Test
    public void testPreventDuplicateVertices() {
        Graph<String> g = emptyInstance();
        assertTrue(g.add("A"));
        assertFalse("Adding duplicate vertex should return false", g.add("A"));
        assertEquals("Graph should contain only one vertex", 1, g.vertices().size());
    }

    // Removing vertices
    @Test
    public void testRemoveExistingVertex() {
        Graph<String> g = emptyInstance();
        g.add("A");
        assertTrue("Removing existing vertex should return true", g.remove("A"));
        assertFalse("Graph should no longer contain removed vertex", g.vertices().contains("A"));
    }

    @Test
    public void testRemoveNonexistentVertex() {
        Graph<String> g = emptyInstance();
        assertFalse("Removing nonexistent vertex should return false", g.remove("X"));
    }

    // Adding, updating, and deleting edges
    @Test
    public void testAddEdge() {
        Graph<String> g = emptyInstance();
        int prev = g.set("A", "B", 5);

        assertEquals("No previous edge existed so return value should be 0", 0, prev);
        assertEquals("Edge weight should be stored in Map returned by targets()", Integer.valueOf(5), g.targets("A").get("B"));
    }

    @Test
    public void testUpdateEdge() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 3);
        int prev = g.set("A", "B", 7);

        assertEquals("Old weight should be returned", 3, prev);
        assertEquals("Edge weight must be updated to new value", Integer.valueOf(7), g.targets("A").get("B"));
    }

    @Test
    public void testDeleteEdgeByZeroWeight() {
        Graph<String> g = emptyInstance();
        g.set("A", "B", 10);
        int prev = g.set("A", "B", 0);

        assertEquals("Deleting an edge returns previous weight", 10, prev);
        assertFalse("Edge should be deleted", g.targets("A").containsKey("B"));
    }

    // Checking vertices list
    @Test
    public void testVerticesList() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");

        Set<String> vertices = g.vertices();
        assertEquals("Graph should contain two vertices", 2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
    }

    // Checking edge sources and targets
    @Test
    public void testSources() {
        Graph<String> g = emptyInstance();
        g.set("X", "A", 5);
        g.set("Y", "A", 7);

        Map<String, Integer> sources = g.sources("A");

        assertEquals("There should be 2 sources for A", 2, sources.size());
        assertEquals(Integer.valueOf(5), sources.get("X"));
        assertEquals(Integer.valueOf(7), sources.get("Y"));
    }

    @Test
    public void testTargets() {
        Graph<String> g = emptyInstance();
        g.set("A", "X", 3);
        g.set("A", "Y", 4);

        Map<String, Integer> targets = g.targets("A");

        assertEquals("There should be 2 targets", 2, targets.size());
        assertEquals(Integer.valueOf(3), targets.get("X"));
        assertEquals(Integer.valueOf(4), targets.get("Y"));
    }
    
}
