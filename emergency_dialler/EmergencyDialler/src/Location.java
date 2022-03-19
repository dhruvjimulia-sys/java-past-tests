public class Location {
  private int xval = 0;
  private int yval = 0;

  public Location(int xval, int yval) {
    this.xval = xval;
    this.yval = yval;
  }

  public int getX() {
    return xval;
  }

  public int getY() {
    return yval;
  }

  public double distanceTo(Location l) {
    int xvaldiff = l.xval - this.xval;
    int yvaldiff = l.yval - this.yval;
    return Math.sqrt(xvaldiff * xvaldiff + yvaldiff * yvaldiff);
  }
}
