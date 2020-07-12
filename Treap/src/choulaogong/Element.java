package choulaogong;



/**
 * This class is to combine an element for the convenience of data;
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */


public class Element {
	public int id = 0;
	public int key = 0;
	public Element(int id, int key) {
		this.id = id;
		this.key = key;
	}
	
	public int getID() {
		return id;
	}
	public int getKey() {
		return key;
	}
	public String toString() {
		return this.id + " "+this.key;
	}
}
