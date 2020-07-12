package generator;

public class QueryGenerator {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QueryGenerator object = new QueryGenerator();
		Range r = object.generate_a_query(5);
		System.out.println(r);
	}
	public static Range generate_a_query(int s) {
		DataPointGenerator object = new DataPointGenerator();
		int[] q = object.generate_a_point(1, Config.M-s);
		Range r = new Range(q, s);
		return r;
	}
	


}
