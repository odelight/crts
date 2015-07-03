package gotox.crts.model;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class TestCrtsLine {
	
	@Test
	public void testPointContainment(){
		CrtsLine l = new CrtsLine(new Point(10,10), new Point(10,20));
		assertTrue(l.contains(new Point(10,15)));
	}
	
	@Test
	public void testPointContainmentCollinearPoint(){
		CrtsLine l = new CrtsLine(new Point(10,10), new Point(10,20));
		assertFalse(l.contains(new Point(10,25)));
	}
	
	@Test
	public void testPointContainmentNonCollinearPoint(){
		CrtsLine l = new CrtsLine(new Point(10,10), new Point(10,20));
		assertFalse(l.contains(new Point(15,15)));
	}

}
