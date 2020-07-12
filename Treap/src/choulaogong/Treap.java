package choulaogong;

import java.util.Random;

/**
 * This class is to implement the treap data structure
 * 
 * @author Xiaofei
 * @version V_0.2
 *
 */

public class Treap implements DataStructure{
	
	private Random r = new Random();
	private TreapNode root = null;
	private int all = 0;
	private static int MAX = 1000000000;
	
	public Treap() {
		this(6);
	}
	
	public Treap(int priorityPower) {
		this.all = (int)Math.pow(10, priorityPower);
	}
	
	public void insert(Element x, int priority) {
		TreapNode node = new TreapNode(x, priority);
		if (root == null)
			root = node;
		else {
			TreapNode comparingNode = root;
			while(true) {
				if(node.key<comparingNode.key||node.key==comparingNode.key&&node.id<comparingNode.id) {
					if(comparingNode.leftC == null) {
						comparingNode.setLeftC(node);
						break;
					}
					else
						comparingNode = comparingNode.leftC;
				}
				else {
					if(comparingNode.rightC == null) {
						comparingNode.setRightC(node);
						break;
					}
					else
						comparingNode = comparingNode.rightC;
				}
				
			}
			
//			if(comparingNode.key<node.key)
//				node.setLeftC(comparingNode);
//			else
//				node.setRightC(comparingNode);
//			root = node;
			
			int pos = 0;
			while(true) {
				pos = node.getPos();
				comparingNode = node.getParent();
				if(comparingNode == null || comparingNode.priority < node.priority)
					break;
				else {
					if(pos==-1)
						this.rotation(node, comparingNode, false);
					else
						this.rotation(node, comparingNode, true);
				}
				
			}
			
		}
		
	}
	
	public void rotation(TreapNode node, TreapNode parent, boolean isLeft) {
		int pPos = parent.getPos();
		TreapNode pParent = parent.getParent();
		TreapNode bTreeRoot = null;		
		if(isLeft) {
			bTreeRoot = node.leftC;
			parent.setRightC(bTreeRoot);
			node.setLeftC(parent);			
		}
		else {
			bTreeRoot = node.rightC;
			parent.setLeftC(bTreeRoot);
			node.setRightC(parent);	
		}
		
		if(pPos==-1)
			pParent.setLeftC(node);
		else if(pPos==1)
			pParent.setRightC(node);
		else {
			node.parent = null;
			this.root = node;
		}
		
		
	}
	
	
	
	public void insert(Element x) {
		int priority = r.nextInt(all);
		this.insert(x, priority);		
	}
	
	

	public TreapNode searchNode(int key) {
		if(root!=null) {
			TreapNode comparingNode = root;
			
			while(true) {			
				if(comparingNode.key == key)
					return comparingNode;
				else {
					if(key<comparingNode.key) {
						if(comparingNode.leftC == null) {
							return null;
						}
						else
							comparingNode = comparingNode.leftC;
					}
					else if (key>comparingNode.key) {
						if(comparingNode.rightC == null) {
							return null;
						}
						else
							comparingNode = comparingNode.rightC;
					}
				}

			}
		}
		return null;
		
		
	}
	public Element search(int key) {
		TreapNode node = this.searchNode(key);
		if(node == null)
			return null;
		else
			return node.x;
	}

	public void delete(int key) {
		TreapNode node = this.searchNode(key);
		if(node != null) {
			node.priority = MAX;
			while(true) {
				if(node.leftC==null && node.rightC==null) {
					int pos = node.getPos();
					TreapNode parent = node.getParent();
					if(parent != null) {
						if(pos==-1)
							parent.setLeftC(null);
						else
							parent.setRightC(null);
						
					}
					break;
				}
				
				else {
					TreapNode child = null;
//					System.out.println(node.leftC);
//					System.out.println(node.rightC);
					if(node.rightC == null )
						child = node.leftC;
					else if(node.leftC == null)
						child = node.rightC;
					else {
						if(node.leftC.priority<node.rightC.priority)
							child = node.leftC;
						else
							child = node.rightC;
					}
					
					int pos = child.getPos();
					if(pos==-1)
						this.rotation(child, node, false);
					else
						this.rotation(child, node, true);
					
					
				}
				
				
			}
		}
		
	}
	
	public String getName() {
		return "Treap";
	}
	
	
	
	
	private class TreapNode{
		private Element x = null;
		private int key = 0;
		private int id = 0;
		private int priority = 0;
		private TreapNode leftC = null;
		private TreapNode rightC = null;
		private TreapNode parent = null;
		
		public TreapNode(Element x, int priority) {
			this.x = x;
			this.priority = priority;
			this.key = x.key;
			this.id = x.id;
		}
		
		public void setLeftC(TreapNode node) {
			if(node !=null)
				node.parent = this;
			this.leftC = node;
		}
		
		public void setRightC(TreapNode node) {
			if(node !=null)
				node.parent = this;
			this.rightC = node;
		}
		
		public TreapNode getParent() {
			return parent;
		}
		
		public int getPos() {
			if(parent == null)
				return 0;
			if(this==parent.leftC)
				return -1;
			if(this==parent.rightC)
				return 1;
			return 0;
		}
		
		public String toString() {
			return String.format("key:%d, priority:%d, pk:%d", this.key, this.priority, this.parent.key);
			
		}
		
	}

	
	
	public static void main(String[] args) {
		Treap p = new Treap();
		p.insert(new Element(1,2));
		p.insert(new Element(1,3));
		p.insert(new Element(1,4));
		p.insert(new Element(1,5));
		System.out.println(p.search(5));
		p.delete(5);
		System.out.println(p.search(5));
		System.out.println(p.search(4));
		p.delete(4);
		System.out.println(p.search(3));
		System.out.println(p.search(2));
		System.out.println(p.search(1));
		
	}

	
}
