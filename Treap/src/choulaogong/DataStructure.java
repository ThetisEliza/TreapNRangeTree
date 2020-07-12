package choulaogong;



/**
 * This is the data structure interface for the availability of being invoked;
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */


public interface DataStructure {
	public Element search(int key);
	
	public void delete(int key);
	
	public void insert(Element x);
	
	public String getName();

}
