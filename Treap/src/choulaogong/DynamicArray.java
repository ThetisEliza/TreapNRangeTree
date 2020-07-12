package choulaogong;



/**
 * This class is to implement the dynamic array structure;
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */


public class DynamicArray implements DataStructure{
	
	private Element[] arr = new Element[2];
	private int pos = 0;
	private int N = 2;
	
	
	public DynamicArray() {
		
	}
	
	public void insert(Element x) {
		this.pushBack(x);
	}
	
	public void pushBack(Element x) {
		this.pos += 1;

		arr[pos-1] = x;
		if(pos == N) {
			N*=2;
			Element[] aArr = new Element[N];
			for(int i=0;i<pos;i++) {
				aArr[i] = arr[i];
			}
			arr = aArr;
		}
	}
	
	public int searchPos(int key) {
		int result = -1;
		for(int i=0;i<pos;i++) {
			if(key==arr[i].key) {
				result = i;
				break;
			}
		}
		return result;
	}
	
	public Element search(int key) {
		int res = this.searchPos(key);
		if(res==-1)
			return null;
		else
			return arr[res];
	}
	
	public void delete(int key) {
		int res = this.searchPos(key);
		if(res!=-1) {
			arr[res] = arr[pos-1];
			arr[pos-1] = null;
			pos-=1;
			if(pos<N/4){
				N/=2;
				Element[] aArr = new Element[N];
				for(int i=0;i<pos;i++) {
					aArr[i] = arr[i];
				}
				arr = aArr;
			}
		}
	}
	
	public String getName() {
		return "Dynamic Array";
	}
	
	public String toString() {
		String str = "";
		for(int i=0;i<N;i++) {
			if(arr[i]!=null)str+=arr[i].key+" ";
			else str+="Null ";
		}
		return str;
	}

	public static void main(String[] args) {
		DynamicArray a = new DynamicArray();
		a.insert(new Element(1,2));
		a.insert(new Element(1,3));
		a.insert(new Element(1,4));
		a.insert(new Element(1,5));
		System.out.println(a);
		System.out.println(a.search(5));
		System.out.println(a.search(6));
		a.delete(4);
		System.out.println(a);
		a.delete(2);
		System.out.println(a);
		a.delete(5);
		System.out.println(a);
		
	}
	
}



