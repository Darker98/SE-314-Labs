/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    /** Abstraction function:
     *		AF(vertices, edges) = a directed, weighted graph where:
     *			- vertices = all vertex objects
     *			- edges = there is an edge from source to target with weight w
     *
     *	Representation invariant:
     *  	- vertices != null
     *  	- edges != null
     *  	- all vertices in edges are in vertices set
     *  	- all weights > 0
     *  
     *	Safety from rep exposure:
     *   	- vertices and edges are kept private
     *   	- no aliasing errors allowed by returning immutable objects
     */
     
    
    public ConcreteVerticesGraph() {
        // The graph starts empty
        checkRep();
    }
    
    // Enforce rep invariant
    private void checkRep() {
        Vertex.checkRep(vertices);
    }
    
    public boolean containsLabel(String label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return true;
            }
        }
        return false;
    }
    
    public Vertex getVertex(String label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }
    
    @Override public boolean add(String vertex) {
        Objects.requireNonNull(vertex);

        if (containsLabel(vertex))
            return false;

        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);

        if (!containsLabel(source)) add(source);
        if (!containsLabel(target)) add(target);

        Vertex src = getVertex(source);
        int previous = src.targets.getOrDefault(target, 0);

        if (weight == 0) {
            src.targets.remove(target);
        } else {
            src.targets.put(target, weight);
        }

        checkRep();
        return previous;
    }
    
    @Override public boolean remove(String vertex) {
        Objects.requireNonNull(vertex);

        Vertex toRemove = getVertex(vertex);
        if (toRemove == null) return false;

        vertices.remove(toRemove);

        for (Vertex v : vertices) {
            v.targets.remove(vertex);  // remove incoming edges
        }

        checkRep();
        return true;
    }
    
    @Override public Set<String> vertices() {
        Set<String> result = new HashSet<>();
        for (Vertex v : vertices)
            result.add(v.label);
        return Collections.unmodifiableSet(result);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();

        for (Vertex v : vertices) {
            Integer w = v.targets.get(target);
            if (w != null) result.put(v.label, w);
        }

        return Collections.unmodifiableMap(result);
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Vertex v = getVertex(source);
        if (v == null) return Collections.emptyMap();

        return Collections.unmodifiableMap(new HashMap<>(v.targets));
    }
    
    // TODO toString()
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    final String label;
    final Map<String, Integer> targets = new HashMap<>();
    
    // Abstraction function:
    //   A vertex labelled 'label' with outgoing edges defined by targets
    
    // Representation invariant:
    //   - label != null
    //	 - targets != null
    //	 - all weights > 0
    
    // Safety from rep exposure:
    //   - targets map never exposed outside
    
    // Constructor
    Vertex(String label) {
        this.label = Objects.requireNonNull(label);
    }
    
    // checkRep
    public static void checkRep(List<Vertex> vertices) {
        // No null vertices
        for (Vertex v : vertices) {
            if (v == null) {
                throw new AssertionError("Vertex list contains null entry.");
            }
        }

        // No duplicate labels
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (vertices.get(i).getLabel().equals(vertices.get(j).getLabel())) {
                    throw new AssertionError("Duplicate vertex label found: " + vertices.get(i).getLabel());
                }
            }
        }
    }
    
    // methods
    public String getLabel() {
    	return label;
    }
    
    // toString()
    @Override
    public String toString() {
        return label + " -> " + targets.toString();
    }
    
}
