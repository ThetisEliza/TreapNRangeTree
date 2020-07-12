import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import generator.Config;
import generator.DataPointGenerator;
import generator.Point;
import generator.QueryGenerator;
import generator.Range;
import rangeTree.RangeTree;
import rangeTree.RangeTreeFC;
import rangeTree.RangeTreeNaive;
import rangeTree.RangeTreeSorted;


public class Main {
	private enum DataStructures{
		rtoNaive,
		rtoSorted,
		rtFC,
	}

	
	public static String[] testString = new String[] {"D:\\AADS\\constr\\", "D:\\AADS\\query1\\", "D:\\AADS\\query2\\"};
	
	public static int[] constrNums = new int[] {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
	
	public static int query1Num = 1000000;
	public static double[] query1perc = new double[] {0.01, 0.02, 0.05, 0.1, 0.2};
	
	
	
	public static void main(String[] args) {
		Main m = new Main();
//		m.customTest();
//		m.testConstrEff("D:\\tmp\\constr.csv");
		m.testQueryEff1("D:\\tmp\\query1.csv");
//		m.testQueryEff2("D:\\tmp\\query2.csv");
//		m.testConstrGenerator();
//		m.testQuery1Generator();
//		m.testQuery2Generator();

//		m.testConstr(DataStructures.rtoNaive, constrNums[1], null);  //300
//		m.testConstr(DataStructures.rtoSorted, constrNums[1], null); //140
//		m.testConstr(DataStructures.rtFC, constrNums[1], null);
		
//		m.testQuery1(DataStructures.rtoNaive, query1Num , query1perc);
//		m.testQuery1(DataStructures.rtFC, query1Num , query1perc);
	}
	
	public RangeTree constrRangeTree(DataStructures ds,  Point[] point_set) {
		long start = 0;
		long constrTime = 0;
		switch(ds) {
		case rtoNaive:
			RangeTreeNaive rto = new RangeTreeNaive();
			start = System.nanoTime();	
			rto.construct(point_set);;
			constrTime = (System.nanoTime()-start);
			System.out.println("Construe Time: "+ constrTime +" ns");
			return rto;
		case rtoSorted:
			RangeTreeSorted rto2 = new RangeTreeSorted();
			
			start = System.nanoTime();
			rto2.construct(point_set);;
			constrTime = (System.nanoTime()-start);
			System.out.println("Construe Time: "+ constrTime +" ns");
			
			return rto2;
		case rtFC:
			RangeTreeFC rtfc = new RangeTreeFC ();
			
			start = System.nanoTime();
			rtfc.construct(point_set);;
			constrTime = (System.nanoTime()-start);
			System.out.println("Construe Time: "+ constrTime +" ns");
			
			return rtfc;
		default:
			return null;
		}
	}
	
	
	public void customTest() {
		DataPointGenerator dgt = new DataPointGenerator();
		Point[] pointSet = dgt.generate_point_set(1000000);
		System.out.println("Dataset size:"+1000000);
		
		RangeTreeNaive rto = new RangeTreeNaive();
		RangeTreeSorted rto2 = new RangeTreeSorted();
		RangeTreeFC rtfc = new RangeTreeFC ();
		
		long start = 0;
		long end= 0;
		
		
	
		
		Range r = new Range(new int[] {10000, 50000}, 30000);
		
//		start = System.nanoTime();
//		rto.construct(pointSet);
//		end = (System.nanoTime()-start);
//		System.out.println("constrTime:"+end+"ns");
//		start = System.nanoTime();
//		for(int i=0;i<100;i++) {
//			
//			rto.query(r);
//			
//		}
//		end = (System.nanoTime()-start);
//		System.out.println("query Time:"+end/100+"ns");
//		
//		rto.finalize();
//		System.gc();

		start = System.nanoTime();
		rto2.construct(pointSet);
		end = (System.nanoTime()-start);
		System.out.println("constrTime:"+end+"ns");
		start = System.nanoTime();
		for(int i=0;i<100;i++) {
//			start = System.nanoTime();
			rto2.query(r);
		}
		end = (System.nanoTime()-start);
		System.out.println("query Time:"+end/100+"ns");
		rto2.finalize();
		System.gc();
	
		
		start = System.nanoTime();
		rtfc.construct(pointSet);
		end = (System.nanoTime()-start);
		System.out.println("constrTime:"+end+"ns");
		start = System.nanoTime();
		for(int i=0;i<100;i++) {
//			start = System.nanoTime();
			rtfc.query(r);
		}
		end = (System.nanoTime()-start);
		System.out.println("query Time:"+end/100+"ns");
		rtfc.finalize();
		System.gc();
	}

	public void testRt(int[] nums, DataStructures[] dss, double[] percentages, String fileName) {
		
		
		long results[][] = new long[3][nums.length+nums.length*percentages.length];
		String headers[] = new String[nums.length+nums.length*percentages.length];
		
		
		int groupIdx = 0;
		int columnIdx = 0;
		for(int num: nums) {
			DataPointGenerator dgt = new DataPointGenerator();
			Point[] pointSet = dgt.generate_point_set(num);
			
			System.out.println("Dataset size:"+num);
			
			int queriesNum = percentages.length;
			Range[][] loads = new Range[queriesNum][Config.Loads];
			for(int i=0;i<queriesNum;i++) {				
				Range[] queriesLoad = new Range[Config.Loads];
				for(int j=0;j<Config.Loads;j++) {
					queriesLoad[j] = QueryGenerator.generate_a_query((int)(Config.M*percentages[i]));
				}				
				loads[i] = queriesLoad;
			}		
			
			
			
			
			
			
			for(int mini = 0; mini< Config.AVG_EXP; mini++) {
				int treeIdx = 0;
				for(DataStructures ds: dss) {
					
					columnIdx = groupIdx+groupIdx*percentages.length;
					System.out.println(ds.name()+" test");
					long start = System.nanoTime();				
					RangeTree rt = this.constrRangeTree(ds, pointSet);				
					
					long constrTime = (System.nanoTime()-start);
					System.out.println("Construe Time: "+ constrTime +" us");
					
					headers[columnIdx] = "Set:"+String.valueOf(num);
					
//					if(results[treeIdx][columnIdx] == 0)
//						results[treeIdx][columnIdx] = constrTime;
//					else if(constrTime<results[treeIdx][columnIdx])
					
					if(mini>Config.STABLE_EPOCH) {
						if(Config.MIN_MODE == 1) {
							if(results[treeIdx][columnIdx] == 0)
								results[treeIdx][columnIdx] = constrTime;
							else if(constrTime<results[treeIdx][columnIdx])
								results[treeIdx][columnIdx] = constrTime;
						}
						else {
							results[treeIdx][columnIdx] += constrTime;
						}
						
						
						
					}
						
					
					
					columnIdx++;
					
					for(int i=0;i<loads.length;i++) {
						System.out.println("Query percentage:"+percentages[i]);
						
						long start2 = System.nanoTime();
						for(int j=0;j<loads[0].length;j++) {
							rt.query(loads[i][j]);
						}
						long queryTime = (System.nanoTime()-start2)/100;					
						System.out.println("Query Time: "+ queryTime +" ns");
						
						headers[columnIdx] = "Per:"+String.valueOf(percentages[i]);
						
						
//						results[treeIdx][columnIdx] = queryTime;
						

						if(mini>Config.STABLE_EPOCH) {
							if(Config.MIN_MODE == 1) {
								if(results[treeIdx][columnIdx] == 0)
									results[treeIdx][columnIdx] = queryTime;
								else if(queryTime<results[treeIdx][columnIdx])
									results[treeIdx][columnIdx] = queryTime;
							}
							else {
								results[treeIdx][columnIdx] += queryTime;
							}
							
							
							
						}
						
						columnIdx++;
						System.gc();
					}
					
					

					System.gc();
					System.out.println();
					treeIdx++;
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
					
				
			}
			
			groupIdx++;
			
		}
		
		
		
		this.wirteToText(fileName, headers, results);
		
		
		
		
	}
	
	public void testConstrEff(String fileName) {		
		int[] nums = new int[] {100, 200, 500, 800, 1000};
		for(int i=0;i<nums.length;i++)
			nums[i] *= 1000;
		this.testRt(nums, new DataStructures[] {DataStructures.rtoNaive, DataStructures.rtoSorted}, new double[] {}, fileName);
	}
	
	public void testQueryEff1(String fileName) {
		int[] nums = new int[] {Config.M/10};
		double[] percentages = new double[99];
		for(int i=1;i<100;i++) {
			percentages[i-1] = 0.01*i;
		}
		
		this.testRt(nums, DataStructures.values(), percentages, fileName);
	}
	
	public void testQueryEff2(String fileName) {
		int[] nums = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		for(int i=0;i<nums.length;i++)
			nums[i] = (int)(Math.pow(2, nums[i])*1000);
		double[] percentages = new double[] {0.05};
		this.testRt(nums, DataStructures.values(), percentages, fileName);
	}
		
	public void wirteToText(String fileName, String[] headers, long[][] results) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);//创建文本文件
			
			if(Config.MIN_MODE!=1) {
				for(int i=0;i<results.length;i++) {
					for(int j=0;j<results[0].length;j++) {
						results[i][j] /= (Config.AVG_EXP-Config.STABLE_EPOCH);
					}
				}
			}
			
			
			System.out.println(Arrays.toString(headers));
			System.out.println(Arrays.toString(results[0]));
			System.out.println(Arrays.toString(results[1]));
			System.out.println(Arrays.toString(results[2]));
			
		
			
			for(String h: headers) {
				fileWriter.write(h+",");//写入 \r\n换行
			}
			fileWriter.write("\r\n");
			for(int i=0;i<results.length;i++) {
				for (int j=0;j<results[0].length;j++) {					
					fileWriter.write(results[i][j]+",");					
				}
				fileWriter.write("\r\n");				
			}
			
			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
