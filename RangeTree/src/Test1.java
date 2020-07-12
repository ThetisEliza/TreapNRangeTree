import java.util.Arrays;

import generator.DataPointGenerator;
import generator.Point;
import generator.QueryGenerator;
import generator.Range;
import rangeTree.RangeTreeFC;
import rangeTree.RangeTreeNaive;
import rangeTree.RangeTreeSorted;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataPointGenerator dpg = new DataPointGenerator();
		Point[] points = dpg.generate_point_set(50);
		QueryGenerator qg = new  QueryGenerator();
		Range qr = qg.generate_a_query(30);
		
		
		System.out.println(qr);
		
//		System.out.println(Arrays.toString(points));
		System.out.println("\n*************************************************");
		System.out.println("Naive");
		RangeTreeNaive naive = new RangeTreeNaive();
		
		naive.construct(points);
//		System.out.println(naive.root+" "+naive.root.leftChild+" "+naive.root.rightChild);
		
		System.out.println(Arrays.toString(naive.query(qr)));;
		naive = null;
		System.gc();
		System.out.println("\n*************************************************");

		System.out.println("sorted");
		RangeTreeSorted sorted = new RangeTreeSorted();
		sorted.construct(points);
//		System.out.println(sorted.root+" "+sorted.root.leftChild+" "+sorted.root.rightChild);

		System.out.println(Arrays.toString(sorted.query(qr)));;
		sorted = null;
		System.gc();
		System.out.println("\n*************************************************");

		System.out.println("FC");
		RangeTreeFC fc = new RangeTreeFC();
		fc.construct(points);
//		System.out.println(fc.root+" "+fc.root.leftChild+" "+fc.root.rightChild);

		System.out.println(Arrays.toString(fc.query(qr)));;
		fc = null;		
		System.gc();
	}

}
