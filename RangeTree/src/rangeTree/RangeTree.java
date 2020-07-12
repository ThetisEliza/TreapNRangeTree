package rangeTree;

import java.util.ArrayList;
import java.util.Arrays;
import generator.Point;
import generator.Range;
import generator.DataPointGenerator;
import java.util.Collections;
public class RangeTree {
	
	public Node root;
	LCA lca = new LCA();

	
	
	public Node construct_WB_BST(Point[] sorted_point_set) {
		// TODO Auto-generated method stub
		
		int length = sorted_point_set.length;
		if (length==0)
			return null;
		
		int median = length/2;
		Point point = sorted_point_set[median];
		Node u = new Node(point);
		u.point_set = sorted_point_set;
		
		
		Point[] left_point_set = new Point[Math.max(0,median)];
		for(int i=0; i<left_point_set.length; i++) {
			left_point_set[i] = sorted_point_set[i];
		}
//		
		Node left_child = construct_WB_BST(left_point_set);
		if (left_child != null) {
			u.leftChild = left_child;
			left_child.parent = u;
		}
		
		
		Point[] right_point_set = new Point[Math.max(0,sorted_point_set.length-left_point_set.length-1)];
		
		for(int i=0; i<right_point_set.length; i++) {
			right_point_set[i] = sorted_point_set[median+i+1];
		}

		Node right_child = construct_WB_BST(right_point_set);
		if (right_child != null) {
			u.rightChild = right_child;
			right_child.parent = u;
		}
		
		return u;
	}
	public Point[] query(Range query_range) {
		return null;
	};
	public Node get_succ(Node root, int a, int dimension) {
		Node succ = null;
		Node node = root;
		while(node != null) {
			if (succ == null && node.point.point[dimension] >= a ||
					succ != null && node.point.point[dimension] >= a &&
					node.point.isSmallerThan(succ.point, dimension))
				succ = node;
			if (a <= node.point.point[dimension])
				node = node.leftChild;
			else if (a>node.point.point[dimension])
				node = node.rightChild;
//			
		}
		return succ;
	}
	
	public Node get_pred(Node root, int b, int dimension) {
		Node pred = null;
		Node node = root;
		while(node != null) {
			if (pred==null && node.point.point[dimension]<=b ||
			pred != null && node.point.point[dimension]<=b &&
			node.point.isLargerThan(pred.point, dimension))
				pred =node;
			if (b< node.point.point[dimension])
				node = node.leftChild;
			else if (b>=node.point.point[dimension])
				node = node.rightChild;
		}
		return pred;
	}
	
	public boolean belongs_to_Q(Point point, Range query_range) {
		int x = point.point[0];
		int y = point.point[1];
		int a1 = query_range.x_min;
		int b1 = query_range.x_max;
		int a2 = query_range.y_min;
		int b2 = query_range.y_max;
		if(x >= a1 && x <= b1 && y >= a2 && y <= b2)
			return true;		
		return false;
	}
	public class LCA{
		Node split_u;
		Node[] La;
		Node[] Lb;
		public void get_LCA(Node succ_a, Node pred_b) {
			Node node = succ_a;
			ArrayList<Node> root_to_La = new ArrayList<Node>();
			ArrayList<Node> root_to_Lb = new ArrayList<Node>();
			while(node != null) {
				root_to_La.add(node);
				node = node.parent;
			}
			
			node = pred_b;
			while(node!=null) {
				root_to_Lb.add(node);
				node = node.parent;
			}
			Collections.reverse(root_to_La);
			Collections.reverse(root_to_Lb);

//			
//			
			
			for(int i=0; i<Math.min(root_to_La.size(), root_to_Lb.size());i++) {
				
//				
//				
				if(root_to_La.get(i) != root_to_Lb.get(i) ||
						i == Math.min(root_to_La.size(), root_to_Lb.size())-1) {
					
					if(root_to_La.get(i) != root_to_Lb.get(i))
						i-=1;
					
					split_u = root_to_La.get(i);
					La = new Node[root_to_La.size()-i];
					Lb = new Node[root_to_Lb.size()-i];
					for(int j=0;j<La.length;j++)
						La[j] = root_to_La.get(i+j);
					for(int j=0;j<Lb.length;j++)
						Lb[j] = root_to_Lb.get(i+j);
					break;
				}
			}
//			
//			
//			

		}
	}
	
	
	@Override
	public void finalize() {
		
	}
}
