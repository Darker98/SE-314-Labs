/* Copyright (c) 2015-2016 MIT 6.005 course staff */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    /*
     * Abstraction function:
     *   AF(vertices, edges) = a weighted directed graph whose vertex set is exactly
     *     the strings in `vertices`, and whose edges are the (source,target,weight)
     *     triples contained in `edges`.
     *
     * Representation invariant:
     *   - vertices != null
     *   - edges != null
     *   - every edge e in edges satisfies:
     *        e.getSource() ∈ vertices
     *        e.getTarget() ∈ vertices
     *        e.getWeight() > 0
     *   - no two edges have the same (source, target)
     *
     * Safety from rep exposure:
     *   - vertices and edges are private and final
     *   - vertices() returns a defensive copy (unmodifiable set)
     *   - sources() and targets() return new maps (not internal)
     *   - Edge is immutable
     */

    /** Construct an empty graph. */
    public ConcreteEdgesGraph() {
        checkRep();
    }

    /** Check the representation invariant. */
    private void checkRep() {
        assert vertices != null;
        assert edges != null;

        Set<String> seenPairs = new HashSet<>();

        for (Edge e : edges) {
            assert vertices.contains(e.getSource());
            assert vertices.contains(e.getTarget());
            assert e.getWeight() > 0;

            // ensure unique (source,target)
            String pair = e.getSource() + "->" + e.getTarget();
            assert !seenPairs.contains(pair) : "duplicate edge!";
            seenPairs.add(pair);
        }
    }

    @Override
    public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {

        if (!vertices.contains(source)) vertices.add(source);
        if (!vertices.contains(target)) vertices.add(target);

        // Search for existing edge
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                int prev = e.getWeight();

                if (weight == 0) {
                    edges.remove(i);
                } else {
                    edges.set(i, new Edge(source, target, weight));
                }

                checkRep();
                return prev;
            }
        }

        // Edge did not exist
        if (weight != 0) {
            edges.add(new Edge(source, target, weight));
        }

        checkRep();
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) return false;

        vertices.remove(vertex);

        // Remove all edges touching the vertex
        edges.removeIf(e ->
                e.getSource().equals(vertex) || e.getTarget().equals(vertex)
        );

        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(new HashSet<>(vertices));
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) {
                result.put(e.getSource(), e.getWeight());
            }
        }
        return result; 
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> result = new HashMap<>();
        for (Edge e : edges) {
            if (e.getSource().equals(source)) {
                result.put(e.getTarget(), e.getWeight());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vertices: " + vertices + "\nEdges:\n");
        for (Edge e : edges) {
            sb.append("  ").append(e).append("\n");
        }
        return sb.toString();
    }
}



/**
 * Immutable class representing a weighted directed edge.
 */
class Edge {

    private final String source;
    private final String target;
    private final int weight;

    /*
     * Abstraction function:
     *   AF(source, target, weight) = a directed edge from `source` to `target`
     *   with positive integer weight `weight`.
     *
     * Representation invariant:
     *   - source != null
     *   - target != null
     *   - weight > 0
     *
     * Safety from rep exposure:
     *   - All fields are private and final.
     *   - Strings are immutable.
     *   - Object is immutable
     */

    /** Create an edge. */
    public Edge(String source, String target, int weight) {
        if (source == null || target == null)
            throw new IllegalArgumentException("null vertex");
        if (weight <= 0)
            throw new IllegalArgumentException("weight must be > 0");

        this.source = source;
        this.target = target;
        this.weight = weight;

        checkRep();
    }

    /** Check rep invariant. */
    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight > 0;
    }

    public String getSource() { return source; }

    public String getTarget() { return target; }

    public int getWeight() { return weight; }

    @Override
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}
