package choulaogong;

import java.util.HashMap;

public class JavaHashMap implements DataStructure{
	private HashMap<Integer, Element> map = new HashMap<Integer, Element>();

	@Override
	public Element search(int key) {
		// TODO Auto-generated method stub
		return map.get(key);
	}

	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub
		map.remove(key);
	}

	@Override
	public void insert(Element x) {
		map.put(x.key, x);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Java hash map";
	}
	
	
}
