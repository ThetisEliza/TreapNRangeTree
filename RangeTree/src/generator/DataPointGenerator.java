package generator;
import java.util.Random;
import java.util.Arrays;

public class DataPointGenerator {
	private Random r = new Random();

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataPointGenerator object = new DataPointGenerator();
		Point[] point_set = object.generate_point_set(10);
		System.out.println(Arrays.toString(point_set));
//		System.out.println(Arrays.toString(object.generate_a_point(3, 5)));
		
	}
	
	
	public int[] generate_a_point(int coord_min, int coord_max) {
		if (coord_min > coord_max)
			return null;
		int[] point = new int[2];
		for(int i=0;i<point.length;i++)
			point[i] = coord_min + r.nextInt(coord_max-coord_min+1);
		return point;
	}
	
	public Point[] generate_point_set(int n) {
		Point[] point_set = new Point[n];
		for(int i=0; i<n; i++) {
			Point p = new Point(generate_a_point(1,Config.M),i);
			point_set[i] = p;
		}
		return point_set;
	}
	

	
}
