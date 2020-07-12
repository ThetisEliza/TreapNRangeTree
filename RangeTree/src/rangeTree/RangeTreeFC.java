package rangeTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import generator.Point;
import generator.Range;

public class RangeTreeFC extends RangeTree {



	public void construct(Point[] point_set) {
		Point[] sorted_point_set = Arrays.copyOf(point_set, point_set.length);
		
		for(Point p: sorted_point_set) {
			  p.setXPrior();
		  }
		Arrays.sort(sorted_point_set);
		
		this.root = construct_WB_BST(sorted_point_set);
		construct_pointers();
	}
	
	
	public void construct_pointers() {
		Queue<Node> Q = new LinkedList<Node>();
		Q.offer(this.root);
		
		Point[] point_set_sorted_by_Y = Arrays.copyOf(this.root.point_set, this.root.point_set.length);
		
		
		for(Point p: point_set_sorted_by_Y) {
			  p.setYPrior();
		  }
		Arrays.sort(point_set_sorted_by_Y);
		this.root.point_set_sorted_by_Y = point_set_sorted_by_Y;
		while(Q.size()>0) {
			Node node = Q.poll();
			
			node.successor_pointers = new int[node.point_set_sorted_by_Y.length][2];
			for(int i=0;i<node.successor_pointers.length;i++) {
				node.successor_pointers[i][0] = -1;
				node.successor_pointers[i][1] = -1;
			}
			
			ArrayList<Point> L_Y_left = new ArrayList<Point>();
			ArrayList<Point> L_Y_right = new ArrayList<Point>();
			
			

			for(Point point:node.point_set_sorted_by_Y) {
				if (point.isSmallerThan(node.point, 0)) {
					
					L_Y_left.add(point);
				}
				else if(point.isLargerThan(node.point, 0)) {
					L_Y_right.add(point);
					
				}
			}
			

			
			


			if(node.leftChild != null) {
				Point[] left = new Point[L_Y_left.size()];
				for (int i=0;i<left.length;i++)
					left[i] = L_Y_left.get(i);
				node.leftChild.point_set_sorted_by_Y = left;
				Q.offer(node.leftChild);
				construct_pointer(node, node.leftChild, 0);
				
			}
			if(node.rightChild != null) {
				Point[] right = new Point[L_Y_right.size()];
				for (int i=0;i<right.length;i++)
					right[i] = L_Y_right.get(i);
				node.rightChild.point_set_sorted_by_Y = right;
				Q.offer(node.rightChild);
				construct_pointer(node, node.rightChild, 1);
				

			}
			
			
//			
//			
//			
//
//			
		}
	}
	
	public void construct_pointer(Node parent, Node child, int index) {
		Point[]  L_Y_parent = parent.point_set_sorted_by_Y;
		Point[]  L_Y_child = child.point_set_sorted_by_Y;
		
		int i=0;
		int j=0;
		while(i<L_Y_parent.length && j<L_Y_child.length) {
			Point p = L_Y_parent[i];
			Point q = L_Y_child[j];
			
			if (! p.isLargerThan(q, 1)) {
				parent.successor_pointers[i][index] = j;
				i += 1;
			}
			else {
				j += 1;
			}
		}
	}

	public int binarySearch(Point[] point_set, int low, int high, int y, int dimension, int succ) {

		
		if(low<=high) {
			int mid = (int) Math.floor(low+(high-low)/2);
			if(point_set[mid].point[dimension]>=y) {
				if (succ==-1)
					succ = mid;
				if (succ>-1&& point_set[mid].isSmallerThan(point_set[succ], dimension))
					succ = mid;
				return binarySearch(point_set, 0, mid-1, y, dimension, succ);
			}
			else {
				return binarySearch(point_set, mid+1, high, y, dimension, succ);
			}
		
		}
		else {
			return succ;
		}
		
	}
	public Point[] query(Range query_range) {
		ArrayList<Point> report = query_base_tree(query_range);
		Point[] r = new Point[report.size()];
		for (int i=0;i<report.size();i++)
			r[i] = report.get(i);
		return r;
	}
	
	public ArrayList<Point> query_base_tree(Range query_range) {
		ArrayList<Point> report = new ArrayList<Point>();
		int dimension = 0;
		Node root = this.root;
		
		int a = query_range.x_min;
		int b = query_range.x_max;
		
		int a2 = query_range.y_min;
		int b2 = query_range.y_max;
		
		Node succ_a = get_succ(root, a, dimension);
		Node pred_b = get_pred(root, b, dimension);
		
		
		if (succ_a == null || pred_b == null ||
				succ_a.point.point[dimension]>b ||
				pred_b.point.point[dimension] < a)
			return report;
		
		lca.get_LCA(succ_a, pred_b);
		Node u_split = lca.split_u;
		Node[] La = lca.La;
		Node[] Lb = lca.Lb;
		
		if (belongs_to_Q(u_split.point, query_range)) {
			report.add(u_split.point);
		}
		
		Point[] Ly_u_split = u_split.point_set_sorted_by_Y;
		int pointer = binarySearch(Ly_u_split,0,Ly_u_split.length-1,a2,1,-1);
		
		if(pointer==-1)
			return report;
		
		Point succ_a2 = Ly_u_split[pointer];
		
		Node w = u_split;
		Point succ_w = succ_a2;
		int p = pointer;
		
		for(Node node: La) {
			if (node != u_split) {
				if (belongs_to_Q(node.point, query_range)) {
					report.add(node.point);
				}
				int index = -1;
				if(w.leftChild == node)
					index = w.successor_pointers[p][0];
				else if(w.rightChild==node)
					index = w.successor_pointers[p][1];
				if (index==-1)
					break;
				else {
					p = index;
					w = node;
					Node u = node.rightChild;
					if(succ_a.point.point[0]<=w.point.point[0] && u!=null) {
						index =w.successor_pointers[p][1];
						
						if (index>-1) {
							for (int i=index;i<u.point_set_sorted_by_Y.length;i++) {
								if(u.point_set_sorted_by_Y[i].point[1]<=b2)
									report.add(u.point_set_sorted_by_Y[i]);
								else
									break;
							}
						}
					}
				}
			}
		}
		
		w = u_split;
		p = pointer;
		for(Node node: Lb) {
			if(node != u_split) {
				if (belongs_to_Q(node.point, query_range)) {
					report.add(node.point);
				}
				
				int index = -1;
				if(w.leftChild == node)
					index = w.successor_pointers[p][0];
				else if(w.rightChild==node)
					index = w.successor_pointers[p][1];
				if (index==-1)
					break;
				else {
					p = index;
					w = node;
					Node u = node.leftChild;
					if(pred_b.point.point[0]>=w.point.point[0] &&
							u != null) {
						index = w.successor_pointers[p][0];
						if (index>-1) {
							for (int i=index;i<u.point_set_sorted_by_Y.length;i++) {
								if(u.point_set_sorted_by_Y[i].point[1]<=b2)
									report.add(u.point_set_sorted_by_Y[i]);
								else
									break;
							}
						}
					}
				}
			}
		}		
		return report;
	}

}
