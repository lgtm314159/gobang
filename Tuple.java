package gobang;

public class Tuple {
  private int x;
  private int y;

  public Tuple(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Tuple) {
      Tuple tuple = (Tuple) obj;
      if(tuple.getX() == x) {
        return tuple.getY() == y;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return x * 10 + y;
  }
}
