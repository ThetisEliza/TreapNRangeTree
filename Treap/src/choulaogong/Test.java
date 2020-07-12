package choulaogong;
import java.util.Random;

/**
 * This class is to execute all the experiments. When doing the experiments, just create a test instance and 
 * invoke the test method, in which two parameters should be given, the refreshing frequency and whether show the operating speed.
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */


public class Test {
	int all = (int)(Math.pow(10, 7));
	
	public static void main(String[] args) {		
		
		Test t = new Test();
		while (true) {
//			t.insertTest(10, true);
			t.deletionTest(10, true);
//			t.searchTest(10, true);
//			t.mixTest(10, true);
		}
		
	}
	
	
	public void insertTest(int freq, boolean speed) {
		double[] lengths = {0.1, 0.2, 0.5, 0.8, 1};
		for(int i=0;i<lengths.length;i++) {
			System.out.println();
			System.out.println("insert test, length:"+lengths[i]);
			Operation[] ops = this.generateSequence(0, 0, lengths[i], 6);			
			this.doubleDSTest(ops, freq, speed);			
		}		
	}
	
	
	public void deletionTest(int freq, boolean speed) {
		double[] deletePercs = {0.1, 0.5, 1, 5, 10};
		for(int i=0;i<deletePercs.length;i++) {
			System.out.println();
			System.out.println("delete test, perc:"+deletePercs[i]);
			Operation[] ops = this.generateSequence(deletePercs[i], 0, 1, 6);
			this.doubleDSTest(ops, freq, speed);
			
		}
	}

	public void searchTest(int freq, boolean speed) {
		double[] searchPercs = {0.1, 0.5, 1, 5, 10};
		for(int i=0;i<searchPercs.length;i++) {
			System.out.println();
			System.out.println("search test, perc:"+searchPercs[i]);
			Operation[] ops = this.generateSequence(0, searchPercs[i], 1, 6);
			this.doubleDSTest(ops, freq, speed);
			
			
		}
	}
	
	public void mixTest(int freq, boolean speed) {
		double[] lengths = {0.1, 0.2, 0.5, 0.8, 1};
		for(int i=0;i<lengths.length;i++) {
			System.out.println();
			System.out.println("mix test, length:"+lengths[i]);
			Operation[] ops = this.generateSequence(5, 5, lengths[i], 6);
			this.doubleDSTest(ops, freq, speed);
		}
	}
	
	
	public void SpeedTest(int freq, boolean speed) {
		Operation[] ops = null;
		System.out.println("insert");
		ops = this.generateSequence(0, 0, 1, 6);
		this.doubleDSTest(ops, freq, speed);
		
		System.out.println();
		System.out.println("delete");
		ops = this.generateSequence(10, 0, 1, 6);
		this.doubleDSTest(ops, freq, speed);
		
		System.out.println();
		System.out.println("search");
		ops = this.generateSequence(0, 10, 1, 6);
		this.doubleDSTest(ops, freq, speed);
		
		System.out.println();
		System.out.println("mix");
		ops = this.generateSequence(5, 5, 1, 6);
		this.doubleDSTest(ops, freq, speed);
			
	}
	
	private void doubleDSTest(Operation[] ops, int freq, boolean speed) {		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		DataStructure da = new DynamicArray();
		DataStructure treap = new Treap();		
		DataStructure map = new JavaHashMap();
		this.sequenceTest(treap, ops, freq, speed);
//		this.sequenceTest(da, ops, freq, speed);
		this.sequenceTest(map, ops, freq, speed);
//		da = null;
		treap = null;
		System.gc();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Operation[] generateSequence(double deletePerc, double searchPerc, double length, int power) {
		Random r = new Random();
		DataGenerator d = new DataGenerator();
		int all = (int)(length*Math.pow(10, power));
		Operation[] ops = new Operation[all];
		for (int i=0;i<all;i++) {
			double dice = r.nextDouble();
			if(dice<deletePerc/100)
				ops[i] = d.gen_deletion();
			else if(dice<(searchPerc+deletePerc)/100)
				ops[i] = d.gen_search();
			else
				ops[i] = d.gen_insertion();
		}
		return ops;		
	}
	
	
	
	
	public void sequenceTest(DataStructure d, Operation[] ops, int outputFrequence, boolean speedShow) {
		
		
		
		long start = System.currentTimeMillis();
		Timer t = new Timer();
		int i = 0;
		t.start();
		for(Operation op: ops) {
			i ++;
			t.monitorPara = i;
			if(outputFrequence != 0) {
				double perc = ((double)i) / ops.length;
	            int percent = (int)(perc * 100);
	            if(i % ( ops.length/outputFrequence) == 0) {

	            	System.out.println("Speed: "+((double)t.speed)/10+" ops per 10ms");
	            	
	            	String str = "";
	            	for(int j=0;j<100;j++) {
	            		if(j<percent-1)
	            			str+="=";
	            		else if(j==percent-1)
	            			str+=">";
	            		else
	            			str+="-";
	            	}
	                str += String.format("Percentage:%4.0f%%", (perc * 100));
	                System.out.println(str);
	            }
			}
            
			
			if(op.op==Op.Ins)
				d.insert(op.e);
			else if (op.op == Op.Src)
				d.search(op.key);
			else if(op.op== Op.Del)
				d.delete(op.key);
			
		}
		
		
		
		long end = System.currentTimeMillis();
		t.running = false;
		System.out.println(String.format("%s, cost:%d ms", d.getName(), end-start));
	}
	
	
	public class Timer extends Thread{
		
		public boolean running = true;
		public int monitorPara = 0;
		public int preMonitorPara = 0;
		public int speed = 0;

		
		
		@Override
		public void run() {
			while(running) {
				try {
					Thread.sleep(10);
					speed = monitorPara-preMonitorPara;		
					preMonitorPara = monitorPara;
				} catch (InterruptedException e) {
					
				}
				
			}
		}
	}
	
	
	
}
