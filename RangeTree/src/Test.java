
import java.util.Arrays;

import generator.Bound;
import generator.DataPointGenerator;
import generator.Point;
import generator.QueryGenerator;
import generator.Range;
import rangeTree.RangeTreeFC;
import rangeTree.RangeTreeNaive;
import rangeTree.RangeTreeSorted;

public class Test {

	
	public static void main(String[] args) {
		
		Test test = new Test();
		test.construction_exp();
//		test.query_exp1();
//		test.query_exp2();		
	}
	
	
	
	
	public void construction_exp() {
		
		
		
		double[] l = new double[] {0.1, 0.2, 0.5, 0.8, 1};
		int len = l.length;
		
		int[] points_number= new int[len];
		
		for(int i=0;i<l.length;i++) {
			points_number[i] = (int)(l[i]*Math.pow(10, 6));
		}
		
		long[] Naive_Time = new long[len];
		long[] Sorted_Time = new long[len];
		long[] FC_Time = new long[len];
		
		long start = System.currentTimeMillis();
		for(int i=0;i<len;i++) {
			
			System.out.println("***************** Turn "+i+" ****************");
			System.out.println("Generate "+ points_number[i]+" Point Set...");
			DataPointGenerator dpg = new DataPointGenerator();
			Point[] points = dpg.generate_point_set(points_number[i]);
			
			System.out.println("Start Constructing Range Tree Org- Naive");
			RangeTreeNaive naive = new RangeTreeNaive();

			//Create naive object			
			
			start = System.currentTimeMillis();
			//Construct tree
			naive.construct(points);
			Naive_Time[i] = System.currentTimeMillis() - start;
			System.out.println("Naive Construction finished");
			naive = null;
			System.gc();
			
			System.out.println("Start Constructing Range Tree Org- Sorted");
			RangeTreeSorted sorted = new RangeTreeSorted();
			//Create sorted object			
			
			start = System.currentTimeMillis();
			//Construct tree
			sorted.construct(points);

			Sorted_Time[i] = System.currentTimeMillis() - start;
			System.out.println("Sorted Construction finished");
			sorted = null;
			System.gc();
			
			
			System.out.println("Start Constructing Range Tree Org- FC");

			//Create FC object			
			RangeTreeFC fc = new RangeTreeFC();

			start = System.currentTimeMillis();
			//Construct tree
			fc.construct(points);

			FC_Time[i] = System.currentTimeMillis() - start;
			System.out.println("FC Construction finished");
			fc = null;		
			System.gc();
		
		}
		
		System.out.println("Org Naive Time"+Arrays.toString(Naive_Time));
		System.out.println("Org Sorted Time"+Arrays.toString(Sorted_Time));
		System.out.println("FC Time"+Arrays.toString(FC_Time));
		
		
	}
	
	
	public void query_exp1() {
		
		double[] l = new double[] {0.01, 0.02, 0.05, 0.1, 0.2};
		int len = l.length;
		
		long[] Naive_Time = new long[len];
		long[] Sorted_Time = new long[len];
		long[] FC_Time = new long[len];
		
		long start = 0;
		//generate point set
		DataPointGenerator dpg = new DataPointGenerator();
		Point[] points = dpg.generate_point_set(Bound.bound);
		
		//create range tree
		
		System.out.println("Construct Tree-naive...");
		RangeTreeNaive naive = new RangeTreeNaive();
		
		naive.construct(points);
		//
		System.out.println("Construct Tree-sorted...");
		RangeTreeSorted sorted = new RangeTreeSorted();
		sorted.construct(points);
		//
		System.out.println("Construct Tree-FC...");
		RangeTreeFC fc = new RangeTreeFC();
		fc.construct(points);
		//
		
		for(int i=0;i<len;i++) {
			System.out.println("***************** Turn "+i+" ****************");
			
			long naive_records = 0;
			long sorted_records = 0;
			long fc_records = 0;
			
			for(int j=0;j<100;j++) {
				//query range
				QueryGenerator qg = new  QueryGenerator();
				Range qr = qg.generate_a_query((int)l[i]*Bound.bound);
				start = System.currentTimeMillis();
				//query
				System.out.println(naive.query(qr).length);
				naive_records += (System.currentTimeMillis()-start);
				
				
				start = System.currentTimeMillis();
				//query
				System.out.println(sorted.query(qr).length);

				sorted_records += (System.currentTimeMillis()-start);
				
				
				start = System.currentTimeMillis();
				//query
				System.out.println(fc.query(qr).length);

				fc_records += (System.currentTimeMillis()-start);
				
				
				
			}
			
			Naive_Time[i] = naive_records;
			Sorted_Time[i] = sorted_records;
			FC_Time[i] = fc_records;
			
		}
		
		System.out.println("Org Naive Time"+Arrays.toString(Naive_Time));
		System.out.println("Org Sorted Time"+Arrays.toString(Sorted_Time));
		System.out.println("FC Time"+Arrays.toString(FC_Time));
		
		
	}



	public void query_exp2() {
		
		long query_range_length = (int)(0.05*Math.pow(10, 6));
		
		int[] l = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int len = l.length;
		
		long[] Naive_Time = new long[len];
		long[] Sorted_Time = new long[len];
		long[] FC_Time = new long[len];
		
		long start = 0;

		
		for(int i=0;i<len;i++) {
			System.out.println("***************** Turn "+i+" ****************");
			
			//generate set
			
			

			System.out.println("Construct Tree-naive...");
			//
			System.out.println("Construct Tree-sorted...");
			//
			System.out.println("Construct Tree-FC...");
			//		
				
			
			
			
			long naive_records = 0;
			long sorted_records = 0;
			long fc_records = 0;
			
			for(int j=0;j<100;j++) {
				//query range
				
				start = System.currentTimeMillis();
				//query
				naive_records += (System.currentTimeMillis()-start);
				
				
				start = System.currentTimeMillis();
				//query
				sorted_records += (System.currentTimeMillis()-start);
				
				
				start = System.currentTimeMillis();
				//query
				fc_records += (System.currentTimeMillis()-start);
				
				
				
			}
			
			Naive_Time[i] = naive_records;
			Sorted_Time[i] = sorted_records;
			FC_Time[i] = fc_records;
			
		}
		
		System.out.println("Org Naive Time"+Arrays.toString(Naive_Time));
		System.out.println("Org Sorted Time"+Arrays.toString(Sorted_Time));
		System.out.println("FC Time"+Arrays.toString(FC_Time));
		
		
	}	
	
}
