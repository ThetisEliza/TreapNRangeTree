package choulaogong;

/**
 * This class is to implement the operation which combines the operation type and the augments together
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */



public class Operation {
	
	
	public Op op = null;
	public int id = 0;
	public int key = 0;
	public Element e = null;
	
	public Operation(Op op, Element x) {
		this.op = op;
		this.e = x;
	}
	
	public Operation(Op op, int key) {
		this.op = op;
		this.key = key;
	}
	
}
