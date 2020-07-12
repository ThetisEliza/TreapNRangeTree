package choulaogong;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * This class is to implement the data generator;
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */

public class DataGenerator {
	private int power = 6;
	private int next_id = 1;
	private HashSet<Integer> deleted_id = new HashSet<Integer>();
	private HashMap<Integer, Integer> keyMap = new HashMap<Integer, Integer>();
	private Random r = new Random();
	
	public DataGenerator() {
		this(1);
	}
	
	public DataGenerator(int power) {
		this.power = power;
	}
	
	public Element gen_Element() {
		int id = next_id;	
		next_id++;			
		int key = r.nextInt((int)(Math.pow(10, power)));
		this.keyMap.put(id, key);
		return new Element(id, key);		
	}
	
	public Operation gen_insertion() {
		Element x = this.gen_Element();
		Operation o = new Operation(Op.Ins, x);
		return o;
	}
	
	public Operation gen_deletion() {
		
		int id = 0;
		if(next_id>1)
			id = r.nextInt(this.next_id-1)+1;
		else
			id = 1;
		int key = 0;
		if(deleted_id.contains(id)|| id == 1) {
			key = r.nextInt((int)(Math.pow(10, power)));
		}
		else {
			key = (int)this.keyMap.get(id);

			deleted_id.add(id);
		}
		return new Operation(Op.Del, key);
	}
	
	public Operation gen_search() {
		int key = r.nextInt((int)(Math.pow(10, power)));
		return new Operation(Op.Src, key);
	}
	
	public static void main(String[] args) {
		DataGenerator d = new DataGenerator();
		for(int i=0;i<(int)(Math.pow(10, 6));i++)
			d.gen_insertion();
		d.gen_deletion();
		d.gen_search();
		
	}
}
