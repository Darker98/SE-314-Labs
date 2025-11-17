/* Copyright (c) 2015-2016 MIT 6.005 course staff */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph();
	}

	@Test
	public void testToStringEmptyGraph() {
		ConcreteVerticesGraph g = new ConcreteVerticesGraph();
		String s = g.toString();
		assertTrue("Empty graph must mention no vertices", s.contains(""));
	}

	@Test
	public void testToStringVerticesOnly() {
		ConcreteVerticesGraph g = new ConcreteVerticesGraph();
		g.add("A");
		g.add("B");
		String s = g.toString();

		assertTrue(s.contains("A"));
		assertTrue(s.contains("B"));
	}

	@Test
	public void testToStringWithEdges() {
		ConcreteVerticesGraph g = new ConcreteVerticesGraph();
		g.set("A", "B", 3);
		g.set("A", "C", 7);

		String s = g.toString();
		assertTrue(s.contains("A"));
		assertTrue(s.contains("B"));
		assertTrue(s.contains("C"));
		assertTrue(s.contains("3"));
		assertTrue(s.contains("7"));
	}

	@Test
	public void testVertexConstructor() {
		Vertex v = new Vertex("A");

		assertEquals("Label must match constructor argument", "A", v.getLabel());
		assertTrue("New vertex must have no outgoing edges", v.targets.isEmpty());
	}

	@Test
	public void testVertexAddEdge() {
		Vertex v = new Vertex("A");

		v.addTarget("B", 5);
		assertEquals(Integer.valueOf(5), v.targets.get("B"));
	}

	@Test
	public void testVertexUpdateEdge() {
		Vertex v = new Vertex("A");

		v.addTarget("B", 3);
		v.addTarget("B", 7);

		assertEquals(Integer.valueOf(7), v.targets.get("B"));
	}

	@Test
	public void testVertexRemoveEdge() {
		Vertex v = new Vertex("A");

		v.addTarget("B", 10);
		v.removeTarget("B");

		assertFalse(v.targets.containsKey("B"));
	}
}
