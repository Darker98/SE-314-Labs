/* Copyright (c) 2015-2016 MIT 6.005 course staff */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph();
	}

	@Test
	public void testToStringEmptyGraph() {
		ConcreteEdgesGraph g = new ConcreteEdgesGraph();
		String s = g.toString();
		assertTrue(s.contains("[]"));
	}

	@Test
	public void testToStringVerticesAndEdges() {
		ConcreteEdgesGraph g = new ConcreteEdgesGraph();
		g.set("A", "B", 4);
		g.set("C", "A", 2);

		String s = g.toString();

		assertTrue(s.contains("A"));
		assertTrue(s.contains("B"));
		assertTrue(s.contains("C"));
		assertTrue(s.contains("4"));
		assertTrue(s.contains("2"));
	}

	@Test
	public void testEdgeConstructor() {
		Edge e = new Edge("A", "B", 5);

		assertEquals("A", e.getSource());
		assertEquals("B", e.getTarget());
		assertEquals(5, e.getWeight());
	}

	@Test
	public void testEdgeEquals() {
		Edge e1 = new Edge("A", "B", 5);
		Edge e2 = new Edge("A", "B", 5);

		assertEquals("Edges with same fields must be equal", e1, e2);
	}

	@Test
	public void testEdgeHashCode() {
		Edge e1 = new Edge("X", "Y", 9);
		Edge e2 = new Edge("X", "Y", 9);

		assertEquals(e1.hashCode(), e2.hashCode());
	}

	@Test
	public void testEdgeToString() {
		Edge e = new Edge("A", "B", 3);

		String s = e.toString();
		assertTrue(s.contains("A"));
		assertTrue(s.contains("B"));
		assertTrue(s.contains("3"));
	}
}
