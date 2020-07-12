package generator;

public class Range{
	public int x_min;
	public int x_max;
	public int y_min;
	public int y_max;
	public Range(int[] q, int s) {
		x_min = q[0];
		x_max = x_min + s;
		
		y_min = q[1];
		y_max = y_min + s;
	}
	public String toString() {
		return "[" + x_min +", " + x_max + "] x [" + y_min + ", " + y_max + "]";
	}
}