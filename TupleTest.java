package gobang;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class TupleTest {
  private Tuple tuple;

  @Before
  public void setUp() {
    tuple = new Tuple(6, 9);
  }

  @Test
  public void testGetX() {
    assertEquals(6, tuple.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(9, tuple.getY());
  }

  @Test
  public void testEquals() {
    Tuple anotherTuple = new Tuple(6, 9);
    assertEquals(anotherTuple, tuple);
  }

  @Test
  public void testHashCode() {
    assertEquals(69, tuple.hashCode());    
  }
}
