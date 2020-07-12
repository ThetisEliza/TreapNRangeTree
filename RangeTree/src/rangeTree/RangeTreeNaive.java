package rangeTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import generator.Point;

public class RangeTreeNaive extends RangeTreeOrg{
	
	
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
			
		
			if(node.leftChild != null) {
				Point[] L_Y_left = Arrays.copyOf(node.leftChild.point_set,node.leftChild.point_set.length);
				for(Point p: L_Y_left) {
					  p.setYPrior();
				  }
				Arrays.sort(L_Y_left);
				node.leftChild.point_set_sorted_by_Y = L_Y_left;
				Q.offer(node.leftChild);
				
				
			}
			if(node.rightChild != null) {
				Point[] L_Y_right = Arrays.copyOf(node.rightChild.point_set,node.rightChild.point_set.length);
				for(Point p: L_Y_right) {
					  p.setYPrior();
				  }
				Arrays.sort(L_Y_right);
				node.rightChild.point_set_sorted_by_Y = L_Y_right;
				Q.offer(node.rightChild);
			}
			
			node.secondary_tree_root = construct_WB_BST(node.point_set_sorted_by_Y);
			
			
			

			
		}
	}
}
