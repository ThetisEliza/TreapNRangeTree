package rangeTree;

import generator.Point;

public class Node {
	
	Point point;
	Node parent;
	public Node leftChild;
	public Node rightChild;
	Point[] point_set;
	Point[] point_set_sorted_by_Y;
	int[][] successor_pointers;
	Node secondary_tree_root;
	public Node(Point point) {
		this.point = point;
	}
	
	public String toString() {
		return "N:"+point.toString();
	}
}