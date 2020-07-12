package rangeTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import generator.Point;

public class RangeTreeSorted extends RangeTreeOrg{
	public void construct_secondary_trees() {
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
			
			ArrayList<Point> L_Y_left = new ArrayList<Point>();
			ArrayList<Point> L_Y_right = new ArrayList<Point>();
			
			for(Point point:node.point_set_sorted_by_Y) {
				if (point.isSmallerThan(node.point, 0))
					L_Y_left.add(point);
				else if(point.isLargerThan(node.point, 0))
					L_Y_right.add(point);
			}
			
			
			
			if(node.leftChild != null) {
				Point[] left = new Point[L_Y_left.size()];
				for (int i=0;i<left.length;i++)
					left[i] = L_Y_left.get(i);
				node.leftChild.point_set_sorted_by_Y = left;
				Q.offer(node.leftChild);
			}
			if(node.rightChild != null) {
				Point[] right = new Point[L_Y_right.size()];
				for (int i=0;i<right.length;i++)
					right[i] = L_Y_right.get(i);
				node.rightChild.point_set_sorted_by_Y = right;
				Q.offer(node.rightChild);
			}
			node.secondary_tree_root = construct_WB_BST(node.point_set_sorted_by_Y);
			
			
			
			

			
		}
	}
}
