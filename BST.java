package project6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.Comparable;
import java.lang.Math;
import java.lang.UnsupportedOperationException;

/**
 * A generic implementation of a binary search tree data structure that implements the Iterable and Cloneable interface and extends Comparable.
 * It contains two nested classes of the node and iterator. 
 * The elements are ordered using their natural ordering. This implementation provides guaranteed O(H). 
 * 
 * @author reemaamhaz
 */

public class BST<E extends Comparable<E>> extends Object implements Iterable<E>,Cloneable{

    private int size = 0; //the initial size of the tree (0)
    private Node<E> root = null; // the initial root of the tree (null)

    /** 
     * Constructs a new, empty tree, sorted according to the natural ordering of its elements. Starts with a null root.
     * All elements inserted into the tree must implement the Comparable interface.
     */
    public BST()
    {
        this.root =  null;
    }
    /** 
     * Constructs a new tree with a single node.
     * 
     * @param root - the node that will become the root of the tree
     */
    public BST(Node<E> root) {
		this.root = root;
		size = 0;
	}

    /**
     * Constructs a new tree containing the elements in the specified collection, sorted according to the natural ordering of its elements. 
     * All elements inserted into the tree must implement the Comparable interface. 
     * 
     * @param collection - collection whose elements will comprise the new tree
     * @throws NullPointerException if collection is null 
     */
    public BST(E[] collection)
    {
        if (collection == null) throw new NullPointerException("No null elements in the collection.");

        for (E e : collection)
        {
            add(e);
        }
    }

    /**
     * This private, static class is a nested class that constructs a node of a BST.
     * It extends Comparable and implements Comparable. 
     * The node will carry the object data, reference to the left and right child,
     * its height, and the size of its subtree to keep track of its position and height for the purpose
     * of modifying the tree by adding or removing nodes later. 
     */
    private static class Node<E extends Comparable<E>> implements Comparable<Node<E>>
    {
        // data fields to give properties to the node
        E data = null; // initializes the node's data field
        Node<E> left = null; // the left child
        Node<E> right = null; // the right child
        int height = 1; //keep track of node's height
        int subtreeSize = 1; //essentially an index but also subtree size

        /**
         * Constructs node with data fields to store the data in the node
         * 
         * @param d - stores the data in the node
         */
        protected Node(E d) {
            this.data = d;
        }
        
        /**
         * Compares the data of the current node to the other node's data
         * 
         * @param o the other node we're comparing the current one to 
         * @return an integer of the difference between the given object and the current position
         */
        public int compareTo(Node<E> o) {
            return this.data.compareTo(o.data);
        }
    }

    /**
     * This private, static Iter class is a nested class that implements iterator.
     * Instantiated when the iterator method is called.
     */
    private class Iter implements Iterator<E>
    {
        // this gives the iterator a few properties as data fields
        Node<E> current; // the current node
        public int index; // the index of the node
        private ArrayList<E> tree; // an array to store all of the nodes inside after iterating through the tree

        /**
         * Constructor that instantiates a new instance of the iterator class.
         * It assigns the current to the beginning of the list.
         */
        public Iter()
        {
            current = root;
        }

        /**
         * Constructor that instantiates a new instance of the iterator class with a key.
         * Depending on the key it calls a method using different types of traversal patterns. 
         * 
         * @param s - the key that determines the kind of traversal 
         */
        public Iter(String s) {
			tree = new ArrayList<>();
            index = 0;
            
            if(s.equals("preorder"))
            {
                preorderIterator(root, tree);
            }

            else if(s.equals("postorder"))
            {
                postorderIterator(root, tree);
            }
 
            inorderIterator(root, tree);
        
        }
        
		/**
         * Iterates through items in the tree adding them to an arraylist using pree order traversal
         * as long as the root isn't null.
         * @param node - the current node
         * @param tree - the arraylist containing all nodes data from the tree
         */
		private void preorderIterator(Node<E> node, ArrayList<E> tree) {
			if (node != null) {
				tree.add(node.data);
				preorderIterator(node.left, tree);
				preorderIterator(node.right, tree);
			}
        }
      
         /**
         * Iterates through items in the tree adding them to an arraylist using in order traversal
         * as long as the root isn't null.
         * @param node - the current node
         * @param tree - the arraylist containing all nodes data from the tree
         */
        private void inorderIterator(Node<E> node, ArrayList<E> tree) {
			if (node != null) {
				inorderIterator(node.left, tree);
				tree.add(node.data);
				inorderIterator(node.right, tree);
			}
        }
        
        /**
         * Iterates through items in the tree adding them to an arraylist using post order traversal
         * as long as the root isn't null.
         * @param node - the current node
         * @param tree - the arraylist containing all nodes data from the tree
         */
        private void postorderIterator(Node<E> node, ArrayList<E> tree) {
			if (node != null) {
				postorderIterator(node.left, tree);
				postorderIterator(node.right, tree);
				tree.add(node.data);
			}
        }

         /**
         * This method returns whether the tree contains another node
         * 
         * @return boolean true/false if there is another node
         */
        public boolean hasNext() 
        {
            if (index < tree.size()) return true; // checks if there is a current node if there is it will return true
            return false;
        }
        
        /**
         * This method returns the next node in the list to be iterated over
         * 
         * @throws NoSuchElementException if there is not another node in the list
         * @return the next node in the list
         */
        public E next() 
        {
            if (!hasNext())
            {
                throw new NoSuchElementException("Next node does not exist."); // throws exception if there is not a node at current because it is empty
            }
            return tree.get(index++);  // sets current to the next node
        }

        /**
         * This method removes an object from the tree. It is not supported by this program.
         * 
         * @param o - the object to be removed
         * @return boolean on whether or not the item was removed
         * @throws UnsupportedOperationException if there is a call to this function
         */
        public void remove(Object o) throws UnsupportedOperationException
        {
            //no implementation because it's not supported
        }
    }
    /**
     * Adds the specified element to this set if it is not already present by calling helper method to find a position for it.
     * 
     * @param - data is the content stored in the node
     * @return boolean whether or not the node was added
     * */
    public boolean add(E data)
    {
        if (data == null) throw new NullPointerException("No null data.");
        if (contains(data)) return false;

        if (root == null)
        {
            root = new Node<E> (data);
            size++;
            root.height = 1;
            return true;
        }
        add(root, data);
        return true;
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * This method recursively finds the position to add the node in respect to its root.
     * -- increments size, height, and index of the nodes stored in the tree that were visited
     * 
     * @param data - is the content stored in the node
     * @param node - the current node
     * */
    private void add(Node<E> node, E data)
    {
        if(data.compareTo(node.data) < 0)  
        {
            if (node.left != null)
            {
                int s = size;
                add(node.left, data);
                if (s < size)
                {
                    node.subtreeSize++;
                }
                updateHeight(node);
                return;
            }
            //once its null add the element as a left child
            Node<E> addElement = new Node<E>(data);
            size++; // increment size
            node.left = addElement;
            updateHeight(node); // increment height
            node.subtreeSize++;
        }
        else
        {
            if (node.right != null)
            {
                int s = size;
                add(node.right, data);
                if (s < size)
                {
                    node.subtreeSize++;
                }
                updateHeight(node);
                return;
            }
            //once its null add the element as a right child
            Node<E> addElement = new Node<E>(data);
            size++; // increment size
            node.right = addElement;
            updateHeight(node); // increment height
            node.subtreeSize++;
        }
    } 
    /** 
     * Adds all of the elements in the specified collection to this tree.
     * 
     * @param collection - the collection of nodes we're adding to the tree
     * @return boolean indicating whether the collection was added or not
     * */
    public boolean addAll(Collection<? extends E> collection)
    {
        for (E nodes : collection) //iterates through all data in the collection 
        {
            add(nodes); // sends to the add function to add the data to a node that gets added to the tree
        }
        return false; // it was not added
    }

    /**
     * Returns the least element in this tree greater than or equal to the given element, or null if there is no such element using a recursive helper function. 
     * 
     * @param data - the element data we're trying to find the ceiling of
     * @return the data of the ceiling element
     */
    public E ceiling(E data)
    {
        // if the data is null
        if (data == null) throw new NullPointerException("Cannot find ceiling of null.");
        

            E d = (E)data;
    
            if (isEmpty()) return null;
            
            if (contains(d))
            {
                return d;
            }
            else 
            {
                return ceiling(root, d); 
            }
    }

    /**
     * Finds the least element in this tree greater than or equal to the given element by choosing
     * a subtree to traverse based on the values position in comparison to the root. 
     * 
     * @param node - the current node
     * @param data - the value we're finding the ceiling of
     * @return the least element in this tree greater than or equal to the given element
     */
    private E ceiling(Node<E> node, E data)
    {
        // if the data is null
        if (node == null) return null;

        if (data.compareTo(node.data) > 0)
        {
            return ceiling(node.right, data);
        }
        else 
        {
            E ceil = ceiling(node.left, data);
            if (ceil == null)
            {
                return node.data;
            }
            return (ceil.compareTo(data) > 0)? ceil : node.data;   
        }
    }

    /**
     * Removes all nodes in the tree by setting the root to null. Tree is empty after this call.
     */
    public void clear()
    {
        root = null;
        size = 0; //if we clear out all elements we set the size to 0
    }

    /**
     * Returns a shallow copy of this tree instance
     * This operation should be O(N).
     * 
     * @return a BST that is a clone of the other BST
     */
    public BST<E> clone()
    {
        if (this.root == null) return null;
        Node<E> cloNode = clone(this.root);
        BST<E> cloneT =  new BST<E>(cloNode);
        cloneT.size = this.size;

        return cloneT;
    }

    /**
     * Recurses through the tree to be cloned and adds them in ascending order to the cloned tree
     * 
     * @param node - the current node
     * @return the node that was cloned or null if the tree is null
     */
    private Node<E> clone(Node<E> node)
    {
        Node<E> temp = node;
        if (temp == null) return null;
        temp.data = node.data;
        temp.left = clone(node.left);
        temp.right = clone(node.right);
        return temp;
    }

    /**
     * This method returns true if this tree contains the specified element. -- performance O(H)
     * 
     * @param o - the object we are trying to find
     * @return true or false if the set contains element
     * 
     * @throws ClassCastException - if the specified object cannot be compared with the elements currently in the set
     * @throws NullPointerException - if the specified element is null and this set uses natural ordering, or its comparator does not permit null elements
     */
    public boolean contains(Object o)
    {
        if (o == null)  throw new NullPointerException("No null objects");
        if (root == null) return false;

        Node<E> node = root;
        if (node == null) return false;
        E objData = (E)o;

        do {
            if (objData.compareTo(node.data) == 0)
            {
                return true;
            }
            else if (objData.compareTo(node.data) < 0)
            {
                node = node.left;
            }
            else
            {
                node = node.right;
            }
        }while (node != null);

        return false;
    }
    /**
     * This method returns true if this collection contains all of the elements in the specified collection
     * It iterates over the collection checking each element returned by iterator to see if it's in the tree.
     * -- performance O(MH)
     * 
     * @return true or false if the collection contains all elements
     * @param c - the collection of nodes we're adding to the tree
     * 
     * @throws NullPointerException if one of the values is null or entire collection is null
     */
    public boolean containsAll(Collection<?> c)
    {
        if (c.isEmpty()) return false; //if there is nothing in the collection return false
        for (Object o : c) // iterating through each object in the collection
        {
            if (o == null) throw new NullPointerException("There is a null element in the tree");
            if (!contains(o))
            {
                return false; // when the collection does not contain the element return false
            }
        }
        return true; // otherwise return true
    }

    /**
     * This method compares the specified object with this collection for equality. Walks through each set using the iterator. 
     * 
     * @param o - the object we are trying to equality to another object
     * @return boolean true or false if the object equals an object in the collection.
     * 
     * @throws ClassCastException if the data cannot be compared to the data in the collection 
     */
    public boolean equals(Object o)
    {
        if (this == null)
            return false;
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof BST))
            return false;

        BST<E> currBST = (BST<E>) o; // cast the list

        if (size != currBST.size)
        {
            return false;
        }

        Node<E> thisPos = this.root; // starts at the head of this list
        Node<E> oPos = currBST.root; // starts at the head of the list of the object

        while (thisPos != null)
        {
            if (!thisPos.data.equals(oPos.data))
            { // if an element in the list doesn't equal the one in the object list it returns false
                return false;
            }
        
            Iterator<E> thisIter = this.iterator();
		    Iterator<E> oIter = currBST.iterator();
            while (thisIter.hasNext()) {
                if (!thisIter.next().equals(oIter.next())) {
                    return false;
                }
            }
            return true;
        }

        return true; // the lists match
    }

    /**
	 * Finds the lowest/left-most element in the tree (Wrapper)
	 * 
     * @return E - lowest element in the tree 
	 */
    public E first()
    {  
        if (isEmpty()) throw new NoSuchElementException("Empty tree");

        return first(root);
    }

     /**
	 * Recursively finds the left-most non-null node in the tree
     * 
     * @param n - the current node 
	 * @return the lowest element in the tree 
	 */
    private E first(Node<E> n)
    {  
        if (n.left == null) 
            return n.data;

        return first(n.left);
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     * It calls a recursive helper function to traverse the tree. 
     * 
     * @return the greatest element in this set less than or equal to the given element
     */
    protected E floor(E data)
    {
        //if its null or empty return null
        if (isEmpty()) return null;
        if (data == null) return null;
        //if the left side is greater than the data it is not the floor
		if (first().compareTo(data) > 0) {
			return null;
		}
		if (contains(data)) {
			return data;
		}
        return floor(data, root);
    }

    /**
     * Finds the the greatest element in this set less than or equal to the given element, or null if there is no such element
     * by comparing the data to the root and recursing. 
     * 
     * @param data - the value that we are trying to find the greatest value less than or equal to
     * @param node - the current node
     * @return the value of the greatest element in this set less than or equal to the given element
     */
    private E floor(E data, Node<E> node)
    {
        if (node == null) return null;

        if (data.compareTo(node.data) > 0) 
        {
            if (node.right != null) 
            {
                if (node.right.data.compareTo(data) < 0) 
                {
					return floor(data, node.right);
				}
                else 
                {
					return node.data;
				}
			}
            else 
            {
				return node.data;
			}
		}
        else 
        {
			return floor(data, node.left);
		}
    }

    /**
     * Returns the element at the specified position in this tree (that uses 0 based indexing). The order of the indexed elements is the same as provided by this tree's iterator. 
     *  
     * @param i - index of element to be returned
     * @return element of index
     */
    public E get(int i) // put index field in the node
    {
        return get(i, root);
    }

    /**
     * Returns the element at the specified position in this tree. Uses the size of the subtree as a way of indexing the elements. 
     * If the value to be found is greater than the size of the left subtree we know it is in the right subtree or is the root. 
     * This makes the pivot depending on the left subtree size and traveres down in order to index every node. 
     *  
     * @param i - index of element to be returned
     * @param node - the current node
     * @return element of index
     */
    private E get (int i, Node<E> node)
    {
        if (node.left != null)
        {
            if (node.left.subtreeSize <= i) // its too big to be on the left
            {
                i -= node.left.subtreeSize; //subtract the left side of the tree from the index you're looking for
            }
            else
            {
                return get(i, node.left); // recurse
            }
        }
        
        if(i == 0) // if it is the current node 
        {
            return node.data;
        }
        else
        {
            i--;
        }
        return get(i, node.right);
    }
    /**
     * Returns a collection whose elements range from fromElement, inclusive, to toElement, inclusive recursively using a helper method.  
     * This operation should be O(M) where M is the number of elements in the returned list.
     * 
     * @param start - the first element in the list
     * @param end - the last element in the list
     * @return an array list of the elements in the range from start to end
     */
    protected ArrayList<E> getRange(E start, E end)
    {
        if (start == null) throw new NullPointerException("No null parameters");
        if (end == null) throw new NullPointerException("No null parameters");

        if(start.compareTo(end) > 0) throw new IllegalArgumentException("The start element cannot be bigger than end element");

        ArrayList<E> arr = new ArrayList<E>(); 

        findLast(root, arr, start, end);
        
        return arr;
    }
    
    /**
     * Finds all the elements from one starting value to a given value recusively so long as the comparison
     * between the root and the start/end is less than/greater than 0(or equal) respectively, and adds them
     * to an arraylist. 
     * 
     * @param node - the current node
     * @param a - the arraylist to be filled with the nodes to be added
     * @param start - the starting value
     * @param end - the last value in the array
     */
    private void findLast(Node<E> node, ArrayList a, E start, E end) 
    { 
        if (node == null) { 
			return; 
        } 

        int startComp = start.compareTo(node.data);
        int endComp = end.compareTo(node.data);

        if (startComp < 0) 
        { 
			findLast(node.left, a, start, end);
        } 

        if (startComp <= 0 && endComp >= 0) 
        { 
			a.add(node.data);
        } 

        if (endComp > 0)
        {
            findLast(node.right, a, start, end);
        }
    } 

    /** 
     * Returns the height of this tree. The height of a leaf is 1. 
     * The height of the tree is the height of its root node
     * 
     * @return the height of the tree or 0 if empty
     */
    public int height()
    {
        if (root == null) return 0;
        return root.height; 
    }

    /** 
     * Updates the height of all nodes in a subtree based on their children
     * 
     * @param root - the root node
     * @param height - the height of the node
     * @return the height of a node
     */
    private void updateHeight(Node<E> root)
    {
        if (root == null) return;
        if(root.left == null && root.right == null)  // if there are no children the height is 1
        {
            root.height = 1;
        }

        // 1 child on right
        else if(root.left == null) // for every time right isn't null incremement height by 1, recurse down
        {
            root.height = root.right.height + 1;
        }
        // 1 child on left
        else if(root.right == null)  // for every time left isn't null incremement height by 1, recurse down
        {
            root.height = root.left.height + 1;
        }
        // 2 children - pick the max of the two 
        else {
            root.height = Math.max(root.left.height, root.right.height) + 1;
        }
    }

    /**
     * Finds the least element strictly greater than the given data or null if it DNE recursively by calling it's helper method
     * 
     * @param data - the value to find the higher value of
     * @return least element strictly greater than the given data or null
     */
    protected E higher(E data)
    {
        if (data == null) throw new NullPointerException("No null params.");
        E dataCast = (E)data;

		if (last().compareTo(dataCast) <= 0) {
			return null;
        }
        
        return higher(dataCast, root);
    }

    /**
     * Finds the east element strictly greater than the given data starting at the root
     * 
     * @param data - the data we want to find the higher value of
     * @param curr - the current node
     * @return the value higher than the given parameter or null
     */
    private E higher(E data, Node<E> curr)
    {
        if (curr == null) return null;
        //if the root is less than or equal to the data, go right
        if (curr.data.compareTo(data) <= 0) 
        {
            return higher(data, curr.right);
        }
        else 
        {
            E d = higher(data, curr.left);
            if (d == null)
            {
                return curr.data;
            }
            return d; // going left
        }
    }

    /**
     * This method returns true if this collection contains no elements.
     * 
     * @return boolean true or false if the collection does not have any elements
     */
    protected boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Finds the data of the largest node in the tree recursively by calling its helper function
     * 
     * @return the data of the largest node
     */
    protected E last()
    {
        if (isEmpty())
			return null;
		
		return last(root);
    }
    /**
     * Finds the last node in the tree at the right most point recursively and stops when the right most path becomes null
     * 
     * @param - the current node
     * @return the right most node
     */
    private E last(Node<E> n) {
		if (n.right == null) 
			return n.data;
		else
			return last(n.right);
    }
    /**
     * Takes in the parameter to find the lower value of by calling the lower value
     * that takes in the current node and the given parameter
     * 
     * @param data - the value to find the lower value of
     * @return the lower value data
     */
    protected E lower(E data)
    {
        if (data == null) throw new NullPointerException("No null params");
        E dataCast = (E)data;

		if (first().compareTo(dataCast) > 0) {
			return null;
        }
        
        return lower(dataCast, root);
    }

    /**
     * Recursively gets the node that is greatest node in the tree is that is strictly lower than the given value 
     * or null if it's not present. 
     * 
     * @param data - the data we're trying to get the lower value of 
     * @param curr - the current node
     * @return - the data of the node lower than the given value
     */
    private E lower(E data, Node<E> curr)
    {
        if (curr == null) return null;
        //if the root is bigger than or equal to the data, go left
        if (curr.data.compareTo(data) >= 0) 
        {
			return lower(data, curr.left);
		}
        else // its smaller so go right
        {
            E d = lower(data, curr.right);
            if (d == null)
            {
                return curr.data;
            }
            return d; // if we found the lower element 
        }
    }

    /**
     * This method returns an iterator over the elements in ascending order.
     * Key determines the type of traversal.
     * 
     * @return an iterator
     */

    public Iterator<E> iterator()
    {
        String s = "";
        return new Iter(s);
    }

    /**
     * Returns an iterator over the elements in this tree in order of the preorder traversal.
     * Key determines the type of traversal.
     * 
     * @return iterator over elements in the tree in preorder traversal
     */
    protected Iterator<E> preorderIterator()
    {
        String s = "preorder";
        return new Iter(s);
    }

    /**
     * Returns an iterator over the elements in this tree in order of the postorder traversal.
     * Key determines the type of traversal.
     * 
     * @return iterator over elements in the tree in postorder traversal
     */
    protected Iterator<E> postorderIterator()
    {
        String s = "postorder";
        return new Iter(s);
    }

    /**
     * Returns an iterator over the elements in this tree in order of the inorder traversal.
     * Key determines the type of traversal.
     * 
     * @return iterator over elements in the tree in inorder traversal
     */
    protected Iterator<E> inorderorderIterator()
    {
        String s = "inorder";
        return new Iter(s);
    }
/**
 * Removes the specified element from this tree if it is present. More formally, removes
 * an element e such that Objects.equals(o, e), if this tree contains such an element. Returns 
 * true if this tree contained the element (or equivalently, if this tree changed as a result of the call).
 * 
 * @param o - object to be removed from set if present
 * @return true if set contained the specified element
 * @throws ClassCastException - if the specified object cannot be compared with the elements currently in this tree
 * @throws NullPointerException - if the specified element is null
 */
    protected boolean remove(Object o)
    {
        if (o == null) throw new NullPointerException("No null objects.");

        if (size == 1)
        {
            root = null; 
            size--;
            return true;
        }
        if (size == 0)
        {
            return false;
        }
       
        E data = (E)o;
        if (!contains(data)) return false;
        
        remove(data, root);
        return true;
    }

    /**
     * Finds the location of the node to be removed. Traverses left or right of the tree depending on 
     * it's value in comparison to the root's data. Calls the removeNode function to remove the node. 
     * -- updates the size, index, and height of the tree 
     * @param data - the data to be removed
     * @param node - the current node
     * @return the node to be removed
     */
    private Node<E> remove(E data, Node<E> node)
    {
        if (node == null) return null;

        else if(data.compareTo(node.data) < 0)
        {
            int s = size;
            node.left = remove(data, node.left);
            if (s > size)
            {
                node.subtreeSize--;
            }
        }
        else if (data.compareTo(node.data) > 0)
        {
            int s = size;
            node.right = remove(data, node.right);
            if (s > size)
            {
                node.subtreeSize--;
            }
        }
        else 
        {
            node.subtreeSize--;
            node = removeNode(node);
        }
        updateHeight(node);
        return node;
    }
/**
 * Finds the node to be removed and checks the children to see where it's safe to remove the node.
 * Returns the node of the non null child to take its place or the predecessor
 * -- updates the size but not height because the node is being removed
 * 
 * @param node - the node to be removed
 * @return the child of the node to replace the root or predecessor
 */
    private Node<E> removeNode(Node<E> node)
    {
        //only right child
        if(node.left == null)
        {
            size--; 
            return node.right;
        }
        //only left child
        else if (node.right == null)
        {
            size--; 
            return node.left;
        }

        else{
            // two children 
            E copy = getPredecessor(node.left);
            node.data = copy;
            node.left = remove(copy, node.left);
            return node;
        }
    }


/**
 * Gets the greatest node that is less than the node being removed
 * 
 * @param s - the node to the left
 * @return the predecessor
 */
    private E getPredecessor(Node<E> s)
    {
        Node<E> n = s;
        while (n.right != null)
            n = n.right;
        return n.data;
    }

    /**
     * Returns the number of elements in this tree. 
     * 
     * @return the number of nodes
     */
    public int size()
    {
        return size;
    }

    /**
     * This function returns an array containing all the elements returned by this tree's iterator, 
     * in the same order, stored in consecutive elements of the array
     * 
     * @return the array of nodes 
     */
    protected Object[] toArray()
    {
        ArrayList<E> arr = new ArrayList<>();

        arrBuilder(root, arr);
        
		Object[] result = arr.toArray();
		return result;
    }

    /**
     * Builds the array using the inorder iterator pattern.
     * 
     * @param root - the root of the tree
     * @param arr - the array to be built with nodes 
     */
    private void arrBuilder(Node<E> root, ArrayList<E> arr) {
		if (root != null) {
			arrBuilder(root.left, arr);
			arr.add(root.data);
			arrBuilder(root.right, arr);
		}
	}

    /**
     * Returns a string representation of this tree. The string representation consists 
     * of a list of the tree's elements in the order they are returned by its iterator between brackets and separated by commas. 
     */
    @Override
    public String toString()
    {
        Iter strIter = new Iter("inorder");
        return String.valueOf(strIter.tree);
    }


    /**
     * Creates a stringbuilder to print the nodes as a tree 
     * @return a string 
     * 
     * @author Professor Klukowska
     */
    public String toStringTreeFormat()
    {
        StringBuilder str = new StringBuilder();

        preOrderPrint(root, 0, str);
        return str.toString();
    }

    /**
     * Produces tree like string representation of this tree. Returns a string representation of this tree in a tree-like 
     * format. The string representation consists of a tree-like representation of this tree. Each node is shown in its own
     * line with the indentation showing the depth of the node in this tree. The root is printed on the first line,
     * followed by its left subtree, followed by its right subtree.
     * @param tree - the BST node to be printed 
     * @param level - the level in the BST
     * @param output - the string result  of the tree
     * 
     * @author Professor Klukowska
     */
    private void preOrderPrint(Node<E> tree, int level, StringBuilder output) 
    {
        if (tree != null) 
        {
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append(tree.data);
            preOrderPrint(tree.left, level + 1, output);
            preOrderPrint(tree.right, level + 1, output);
        }
        // uncomment the part below to show "null children" in the output
        else 
        {
            String spaces = "\n";
            if (level > 0) 
            {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append("null");
        }
    }
}