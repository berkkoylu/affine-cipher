import java.util.ArrayList;

	class TernarySearchTreeNode {
		char data;
		boolean isEnd;
		TernarySearchTreeNode left, middle, right;

		public TernarySearchTreeNode(char data) {
			this.data = data;
			this.isEnd = false;
			this.left = null;
			this.middle = null;
			this.right = null;
		}
	}

	public class TernarySearchTree {

		private TernarySearchTreeNode root;

		private ArrayList<String> listtree;

	public TernarySearchTree() {
			root = null;
	}

	public void insert(String word) {
		root = insert(root, word.toCharArray(), 0);
	}

	//insert node into the ternary search tree
	public TernarySearchTreeNode insert(TernarySearchTreeNode node, char[] word, int index) {
			if (node == null) {

				node = new TernarySearchTreeNode(word[index]);
		}
			
			//***************************
			if (word[index] < node.data) {

				node.left = insert(node.left, word, index);

		} else if (word[index] > node.data) {

			node.right = insert(node.right, word, index);

		} else {
			if (index + 1 < word.length) {
				node.middle = insert(node.middle, word, index + 1);
			}else {
				
				node.isEnd = true;
	}
			
			
			
		}
		return node;
	}

	

	public boolean search(String word) {
		
		return search(root, word.toCharArray(), 0);
		
	}
	
	//search node from tst.
	public boolean search(TernarySearchTreeNode node, char[] word, int index) {
		if (node == null) {
			return false;
		}
		if (word[index] < node.data) {
			return search(node.left, word, index);
		}else if (word[index] > node.data) {
			return search(node.right, word, index);
		}else {
			if (node.isEnd && index == word.length - 1) {
				return true;
			}else if (index == word.length - 1) {
				return false;
			}else
				return search(node.middle, word, index + 1);
			}
		}

	public String toString() {
		listtree = new ArrayList<String>();
		traverse(root, "");
		return "\nTernary Search Tree : " + listtree;
	}

	//traverse the tree.
	public void traverse(TernarySearchTreeNode node, String str) {
		if (node != null) {
			
			traverse(node.left, str);
			str = str + node.data;
			if (node.isEnd)
				listtree.add(str);
			traverse(node.middle, str);
			str = str.substring(0, str.length() - 1);
			traverse(node.right, str);
			
		}
	}
	
	
	
	
}
