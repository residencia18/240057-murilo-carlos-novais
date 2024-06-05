package com.example.atividades.atividade06;

import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void testDistanceTo_SamePoint() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        assertEquals(0.0, p1.distanceTo(p2), 0.0001);
    }

    @Test
    public void testDistanceTo_DifferentPoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        assertEquals(5.0, p1.distanceTo(p2), 0.0001);
    }

    @Test
    public void testDistanceTo_PositiveAndNegativeCoordinates() {
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(1, 1);
        assertEquals(Math.sqrt(8), p1.distanceTo(p2), 0.0001);
    }

    @Test
    public void testDistanceTo_ZeroDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        assertEquals(0.0, p1.distanceTo(p2), 0.0001);
    }

    @Test
    public void testDistanceTo_LargeCoordinates() {
        Point p1 = new Point(1e9, 1e9);
        Point p2 = new Point(1e9 + 3, 1e9 + 4);
        assertEquals(5.0, p1.distanceTo(p2), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistanceTo_NullPoint() {
        Point p1 = new Point(3, 4);
        p1.distanceTo(null);
    }
}
