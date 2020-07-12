package generator;
import java.util.Arrays;
public class Point implements Comparable<Point>{
	public int[] point;
	public int id;
	private int compareDim = 0;
	
	public static void main(String[] args) {
		int[] point = {1,2};
		Point p = new Point(point,2);
		System.out.println(p);
		
	}
	public Point(int[] point, int id) {
		this.point = point;
		this.id = id;
	}
	public String toString() {
		return  id + ": ("+Arrays.toString(point)+")";
	}
	public boolean isSmallerThan(Point p, int dimension) {
		if (dimension == 0) {
			if (this.point[0] < p.point[0]) {
				return true;
			}
			else if (this.point[0]==p.point[0] && this.point[1]<p.point[1]) {
				return true;
			}
			else if (this.point[0]==p.point[0] && this.point[1]==p.point[1] && this.id < p.id) {
				return true;
			}
			return false;
		}
		if (dimension == 1) {
			if (this.point[1]<p.point[1])
				return true;
			else if (this.point[1]==p.point[1] && this.point[0] < p.point[0])
				return true;
			else if (this.point[1]==p.point[1] && this.point[0]==p.point[0] && this.id < p.id)
				return true;
			return false;
		}
			
		return false;
	}
	public boolean isLargerThan(Point p, int dimension) {
		if (this.id != p.id && (! isSmallerThan(p, dimension)))
			return true;
		return false;
	}
	 public void setXPrior() {
		 compareDim = 0;
	 }
	 
	 public void setYPrior() {
		 compareDim = 1;
	 }
	 
	@Override
	public int compareTo(Point o) {
		int otherDim;
		if (compareDim == 0)
			otherDim = 1;
		else
			otherDim = 0;		
		if(this.point[compareDim]!=o.point[compareDim])
			return this.point[compareDim]-o.point[compareDim];
		else if (this.point[otherDim] != o.point[otherDim])
			return this.point[otherDim] - o.point[otherDim];
		else if (this.id != o.id)
			return this.id - o.id;
		else
			System.out.println("Warning, Same ID comparison");
			return 0;
	}
}
