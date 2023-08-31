package edu.ics211.hw12;

/* a class for binary tree nodes
 * @author Biagioni, Edoardo
 * @assignment lecture 17
 * @date March 12, 2008
 * @inspiration William Albritton's binary search tree class,
 http://www2.hawaii.edu/~walbritt/ics211/treeBinarySearch/BinarySearchTree.java
 */

import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<String extends java.lang.Comparable<String>, Long> {

    /**
     * A node in a binary tree
     * @author         Edo Biagioni
     * @lecture        ICS 211 Mar 15 or later
     * @date           March 14, 2011
     * @bugs           private class: include this code within a larger class
     */

    class BinaryNode {
        private String key;
        private long count;
        private BinaryNode left;
        private BinaryNode right;


        /**
         * constructor to build a node with no subtrees
         * @param the key to be stored by this node
         */
        private BinaryNode(String key) {
            this.key = key;
            count = 1;
            left = null;
            right = null;
        }


        /**
         * constructor to build a node with a specified (perhaps null) subtrees
         * @param the key to be stored by this node
         * @param the left subtree for this node
         * @param the right subtree for this node
         */
        private BinaryNode(String key, BinaryNode l, BinaryNode r) {
            this.key = key;
            left = l;
            right = r;
        }
    }

    /* the root of the tree is the only data field needed */
    protected BinaryNode root = null; // null when tree is empty

    /* constructs an empty tree
     */
    public BinarySearchTree() {
        super();
    }

    /* constructs a tree with one element, as given
     * @param value to be used for the one element in the tree
     */
    public BinarySearchTree(String key) {
        super();
        root = new BinaryNode(key);
    }

    /* constructs a tree with the given node as root
     * @param newRoot to be used as the root of the new tree
     */
    public BinarySearchTree(BinaryNode newRoot) {
        super();
        root = newRoot;
    }
    
    public BinarySearchTree(java.util.ArrayList<String> wordList) {
    	super();
    	for (String word : wordList) {
    		this.add(word);
    	}
    }


    /* find a value in the tree
     * @param key identifies the node value desired
     * @return the node value found, or null if not found
     */
    public long get(String key) {
        BinaryNode node = root;
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                return node.count;
            }
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            }
            else {
                node = node.right;
            }
        }
        return 0;
    }

    /* add a key to the tree, replacing an existing value if necessary
     * @param value to be inserted
     */
    public void add(String key) {
        root = add(key, root);
    }

    /* add a key to the tree, replacing an existing key if necessary
     * @param key to be inserted
     * @param node that is the root of the subtree in which to insert
     * @return the subtree with the node inserted
     */
    protected BinaryNode add(String key, BinaryNode node) {
        if (node == null) {
            return new BinaryNode(key); // will initialize node.count to 1
        }
        if (key.compareTo(node.key) == 0) {
            // replace the value in this node with a new value
            //node.key = key;
            node.count += 1;
            // alternative code creates new node, leaves old node unchanged:
            //return new BinaryNode<T>(value, node.left, node.right);
        }
        else {
            if (key.compareTo(node.key) < 0) { // add to left subtree
                node.left = add(key, node.left);
            }
            else {  // add to right subtree
                node.right = add(key, node.right);
            }
        }
        return node;
    }

    /* remove a key from the tree, if it exists
     * @param key such that key.compareTo(key) == 0 for the node to remove
     */
    public void remove(String key) {
        root = remove(key, root);
    }

    /* remove a key from the tree, if it exists
     * @param key such that key.compareTo(key) == 0 for the node to remove
     * @param node the root of the subtree from which to remove the key
     * @return the new tree with the key removed
     */
    protected BinaryNode remove(String key, BinaryNode node) {
        if (node == null) { // key not in tree
            return null;
        }
        if (key.compareTo(node.key) == 0) { // remove this node
            if (node.left == null) { // replace this node with right child
                return node.right;
            }
            else if (node.right == null) { // replace with left child
                return node.left;
            }
            else {
                // replace the key in this  node with key in the
                // rightmost node of the left subtree
                String replacement = getRightmost(node.left);
                // now remove the rightmost node in the left subtree,
                // by calling ourselves recursively
                BinaryNode newNode = new
                    BinaryNode(replacement,
                               remove(replacement, node.left),
                               node.right);
                return newNode;
            }
        } else {  // remove from left or right subtree
            if (key.compareTo(node.key) < 0) {
                // remove from left subtree
                node.left = remove(key, node.left);
            } else {  // remove from right subtree
                node.right = remove(key, node.right);
            }
        }
        return node;
    }

    protected String getRightmost(BinaryNode node) {
        assert(node != null);
        BinaryNode right = node.right;
        if (right == null) {
            return node.key;
        } else {
            return getRightmost(right);
        }
    }

    /* iterator, traverses the tree in order */
    public Iterator<String> iterator() {
        return new TreeIterator(root);
    }

    /* traverses the tree in pre-order */
    public Iterator<String> preIterator() {
        return new TreeIterator(root, true);
    }

    /* traverses the tree in post-order */
    public Iterator<String> postIterator() {
        return new TreeIterator(root, false);
    }

    /* toString
     * @returns the string representation of the tree.
     */
    public java.lang.String toString() {
        return toString(root);
    }

    protected java.lang.String toString(BinaryNode node) {
        if (node == null) {
            return "";
        }
        return node.key.toString() + "(" + toString(node.left) + ", " +
            toString(node.right) + ")";
    }


    public int height() {
    	return height(root);
    }
    private int height(BinaryNode nd) {
    	if (nd == null) {
    		return 0;
    	}
    	int leftHt = height(nd.left);
    	int rightHt = height(nd.right);
    	if (leftHt > rightHt) {
    		return leftHt + 1;
    	}
    	else {
    		return rightHt + 1;
    	}
    }
    
    public int countNodes() {
    	return countNodes(root);
    }
    private int countNodes(BinaryNode nd) {
    	if (nd == null) {
    		return 0;
    	}
    	int leftNodes = countNodes(nd.left);
    	int rightNodes = countNodes(nd.right);
    	return leftNodes + rightNodes + 1;
    }
    
    public WordFrequency[] toArray() {
    	int numNodes = countNodes();
    	WordFrequency[] array = new WordFrequency[numNodes];
    	TreeIterator iterator = new TreeIterator(root);
    	for (int i = 0; i < numNodes; i++) {
    		String key = iterator.inorderNext();
    		long count = get(key);
    		array[i] = new WordFrequency((java.lang.String) key, count);
    	}
    	return array;
    }

    /* an iterator class to iterate over binary trees
     * @author Biagioni, Edoardo
     * @assignment lecture 17
     * @date March 12, 2008
     */

    private class TreeIterator implements Iterator<String> {
        /* the class variables keep track of how much the iterator
         * has done so far, and what remains to be done.
         * root is null when the iterator has not been initialized,
         * or the entire tree has been visited.
         * the first stack keeps track of the last node to return
         * and all its ancestors
         * the second stack keeps track of whether the node visited
         * is to the left (false) or right (true) of its parent
         */
        protected BinaryNode root = null;
        protected Stack<BinaryNode> visiting = new Stack<BinaryNode>();
        protected Stack<Boolean> visitingRightChild = new Stack<Boolean>();
        /* only one of these booleans can be true */
        boolean preorder = false;
        boolean inorder = true;
        boolean postorder = false;

        /* constructor for in-order traversal
         * @param root of the tree to traverse
         */
        public TreeIterator(BinaryNode root) {
            this.root = root;
            visiting = new Stack<BinaryNode>();
            visitingRightChild = new Stack<Boolean>();
            preorder = false;
            inorder = true;
            postorder = false;
        }

        /* constructor for pre-order or post-order traversal
         * @param root of the tree to traverse
         * @param inPreorder true if pre-order, false if post-order
         */
        public TreeIterator(BinaryNode root, boolean inPreorder) {
            this.root = root;
            visiting = new Stack<BinaryNode>();
            visitingRightChild = new Stack<Boolean>();
            preorder = inPreorder;
            inorder = false;
            postorder = ! preorder;
        }

        public boolean hasNext() {
            return (root != null);
        }

        public String next() {
            if (! hasNext()) {
                throw new java.util.NoSuchElementException("no more elements");
            }
            if (preorder) {
                return preorderNext();
            } else if (inorder) {
                return inorderNext();
            } else if (postorder) {
                return postorderNext();
            } else {
                assert(false);
                return null;
            }
        }

        // return the node at the top of the stack, push the next node if any
        private String preorderNext() {
            if (visiting.empty()) { // at beginning of iterator
                visiting.push(root);
            }
            BinaryNode node = visiting.pop();
            String result = node.key;
            // need to visit the left subtree first, then the right
            // since a stack is a LIFO, push the right subtree first, then
            // the left.  Only push non-null trees
            if (node.right != null) {
                visiting.push(node.right);
            }
            if (node.left != null) {
                visiting.push(node.left);
            }
            // may not have pushed anything.  If so, we are at the end
            if (visiting.empty()) { // no more nodes to visit
                root = null;
            }
            return node.key;
        }

        /* find the leftmost node from this root, pushing all the
         * intermediate nodes onto the visiting stack
         * @param node the root of the subtree for which we
         *  are trying to reach the leftmost node
         * @changes visiting takes all nodes between node and the leftmost
         */
        private void pushLeftmostNode(BinaryNode node) {
            // find the leftmost node
            if (node != null) {
                visiting.push(node); // push this node
                pushLeftmostNode(node.left); // recurse on next left node
            }
        }

        /* return the leftmost node that has not yet been visited
         * that node is normally on top of the stack
         * inorder traversal doesn't use the visitingRightChild stack
         */
        private String inorderNext() {
            if (visiting.empty()) { // at beginning of iterator
                // find the leftmost node, pushing all the intermediate nodes
                // onto the visiting stack
                pushLeftmostNode(root);
            } // now the leftmost unvisited node is on top of the visiting stack
            BinaryNode node = visiting.pop();
            String result = node.key; // this is the key to return
            // if the node has a right child, its leftmost node is next
            if (node.right != null) {
                BinaryNode right = node.right;
                // find the leftmost node of the right child
                pushLeftmostNode (right);
                // note "node" has been replaced on the stack by its right child
            } // else: no right subtree, go back up the stack
            // next node on stack will be next returned
            if (visiting.empty()) { // no next node left
                root = null;
            }
            return result;
        }

        /* find the leftmost node from this root, pushing all the
         * intermediate nodes onto the visiting stack
         * and also stating that each is a left child of its parent
         * @param node the root of the subtree for which we
         *  are trying to reach the leftmost node
         * @changes visiting takes all nodes between node and the leftmost
         */
        private void pushLeftmostNodeRecord(BinaryNode node) {
            // find the leftmost node
            if (node != null) {
                visiting.push(node); // push this node
                visitingRightChild.push(false); // record that it is on the left
                pushLeftmostNodeRecord(node.left); // continue looping
            }
        }

        //
        private String postorderNext() {
            if (visiting.empty()) { // at beginning of iterator
                // find the leftmost node, pushing all the intermediate nodes
                // onto the visiting stack
                pushLeftmostNodeRecord(root);
            } // the node on top of the visiting stack is the next one to be
            // visited, unless it has a right subtree
            if ((visiting.peek().right == null) || // no right subtree, or
                (visitingRightChild.peek())) { // right subtree already visited
                // already visited right child, time to visit the node on top
                String result = visiting.pop().key;
                visitingRightChild.pop();
                if (visiting.empty()) {
                    root = null;
                }
                return result;
            } else { // now visit this node's right subtree
                // pop false and push true for visiting right child
                if (visitingRightChild.pop()) {
                    assert(false);
                }
                visitingRightChild.push(true);
                // now push everything down to the leftmost node
                // in the right subtree
                BinaryNode right = visiting.peek().right;
                assert(right != null);
                pushLeftmostNodeRecord(right);
                // use recursive call to visit that node
                return postorderNext();
            }
        }

        /* not implemented */
        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove");
        }

        /* give the entire state of the iterator: the tree and the two stacks */
        public java.lang.String toString() {
            if (preorder) {
                return "pre: " + toString(root) + "\n" + visiting + "\n";
            }
            if (inorder) {
                return "in: " + toString(root) + "\n" + visiting + "\n";
            }
            if (postorder) {
                return "post: " + toString(root) + "\n" + visiting + "\n" +
                    visitingRightChild;
            }
            return "none of pre-order, in-order, or post-order are true";
        }

        private java.lang.String toString(BinaryNode node) {
            if (node == null) {
                return "";
            } else {
                return node.toString() + "(" + toString(node.left) + ", " +
                    toString(node.right) + ")";
            }
        }


    }
}
