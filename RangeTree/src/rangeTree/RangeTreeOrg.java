package rangeTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import generator.Point;
import generator.Range;

import java.util.Queue;
public class RangeTreeOrg extends RangeTree {
	
	public void construct(Point[] point_set) {
		Point[] sorted_point_set = Arrays.copyOf(point_set, point_set.length);
		
		for(Point p: sorted_point_set) {
			  p.setXPrior();
		  }
		Arrays.sort(sorted_point_set);
		this.root = construct_WB_BST(sorted_point_set);
		construct_secondary_trees();
		
	}
	public void construct_secondary_trees() {};
	
	public Point[] query(Range query_range) {
		ArrayList<Point> report = query_base_tree(this.root, query_range,0);
		Point[] r = new Point[report.size()];
		for (int i=0;i<report.size();i++)
			r[i] = report.get(i);
		return r;
	}
	public ArrayList<Point> query_base_tree(Node root, Range query_range, int dimension) {
		ArrayList<Point> report = new ArrayList<Point>();
		int a = 0;
		int b = 0;
		if (dimension==0) {
			a = query_range.x_min;
			b = query_range.x_max;
		}
		else if(dimension == 1) {
			a = query_range.y_min;
			b = query_range.y_max;
		}
		
		Node succ_a = get_succ(root, a, dimension);
		Node pred_b = get_pred(root, b, dimension);
		if(dimension==0) {
			
			
		}
		
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
		
		for(Node node: La) {
			if (node != u_split) {
				if (belongs_to_Q(node.point, query_range)) {
					report.add(node.point);
				}
				
				if(succ_a.point.point[dimension] <= node.point.point[dimension] &&
						node.rightChild != null) {
					if(dimension==1) {
						for(Point n: node.rightChild.point_set)
							report.add(n);
					}
					if (dimension==0) {
						ArrayList<Point> new_report = query_base_tree(node.rightChild.secondary_tree_root, query_range,1);
						for (int i=0;i<new_report.size();i++)
							report.add(new_report.get(i));
					}
				}
			}
		}
		for(Node node: Lb) {
			if(node != u_split) {
				if (belongs_to_Q(node.point, query_range)) {
					report.add(node.point);
				}
				if(pred_b.point.point[dimension] >= node.point.point[dimension] &&
						node.leftChild != null) {
					if(dimension==1) {
						for(Point n: node.leftChild.point_set)
							report.add(n);
					}
					if(dimension==0) {
						ArrayList<Point> new_report = query_base_tree(node.leftChild.secondary_tree_root, query_range,1);
						for (int i=0;i<new_report.size();i++)
							report.add(new_report.get(i));
					}
				}
			}
		}
		
		return report;
		
	}





	

	



	

}
