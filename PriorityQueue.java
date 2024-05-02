import java.util.*;

// A priority queue.
// java -ea -jar testing.jar lab2test.Lab2GenTest Lab2
public class
PriorityQueue<E> {

	private final Comparator<E> comparator;
	private ArrayList<E> heap;
	private HashMap<Integer, E> hash;

	//When creating a Priority Queue object, the constructor initiates
	//an Array list(Heap) a Hash Map and a comparator(Min or Max heap)
	public PriorityQueue(Comparator<E> comparator) {
		this.heap       = new ArrayList<>();
		this.hash       = new HashMap<>();
		this.comparator = comparator;
	}

	// Complexity O(log n)
	//Method searches for the index in the heap and then does a replacement of the old bid with the new.
	//after that checks if the parent value is lesser or greater and then does either a sift up or down.
	//Returns an exception if the element does not exist.
	public void update(E oldObject, E newObject){
		int index = -1;
		for (int i = 0; i < size(); i++) {
			if(heap.get(i).equals(oldObject)){
				index = i;
				break;
			}
		}
		if(index == -1){
			throw new NoSuchElementException("Element dosent exist.");
		}

		heap.set(index, newObject);
		hash.replace(index, oldObject, newObject);

		int parentIndex = parent(index);
		if(parentIndex >= 0 && comparator.compare(newObject, heap.get(parentIndex)) < 0){
			siftUp(index);
		} else{
			siftDown(index);
		}
	}

	// Returns the size of the priority queue.
	// Complexity: O(1)
	public int size() {
		return heap.size();
	}
     
	// Adds an item to the priority queue.
	// O(log n)
	public void add(E x) {
		heap.add(x);
		int index = heap.size()-1;
		hash.put(index, x);			//Hashmap follow the changes done to the heap.
		siftUp(index);				//Maintain heap property by sifting up newly added element.
	}

	/*
	Method takes two indices as argument, swap the elements with corresponding indices,
	and updates the hashmap accordingly.
	Complexity: O(1)
	 */
	public void swap(int i, int j){
		E tmp = heap.get(i);
		E tmp2 = heap.get(j);
		heap.set(j, tmp);
		heap.set(i, tmp2);
		//Hashmap follow the changes done to the heap.
		hash.put(j, tmp);
		hash.put(i, tmp2);
	}

	/*
	Method take no parameters and returns the root element in the priority queue.
	Throws NoSuchElementException if empty.
	Complexity: O(1)
	 */
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	/*
	Method safely deletes the root element from the heap by placing the last element as the root.
	After placing last element as the root it removes the last element from the heap,
	and the element with root key in the hashmap.
	If the heap is not empty after deleting, calling siftDown on index 0 to maintain heap prop.
	Complexity: O(log n)
	 */
	public void deleteMinimum() {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		int lastIndex = heap.size() - 1;
		E lastElement = heap.get(lastIndex);

		heap.set(0, lastElement);		//Setting the last element as the root...
		heap.remove(lastIndex);			//...then deleting the element at last index.
		hash.remove(0); 			//Removing element that earlier represented the root.

		if (!heap.isEmpty()) {			//After deleting, making sure heap is not empty.
			hash.put(0, lastElement);   //Update the index of the last element in the hashmap
			siftDown(0); 		    //Restoring heap property by sifting down the root.
		}
	}

	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	// Complexity: O(log n)
	private void siftDown(int index) {
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller
			int child = left;
			E value = heap.get(index);
			E childValue = heap.get(left);

			// but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}
			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) > 0) {
				swap(index, child);
				index = child;
			} else break;
		}
	}

	/* As long as the size of the heap is larger than 0, siftUp compares the value
	at the specified index with its parent. If the parent is larger they will swap with
	the helper function "swap" and update the index so the while loop may continue until the break command.
	Worth noting is that the indexes that are being compared updates with each run through the loop at line 118
	which eliminates the need for temporary elements.
	Complexity: O(log n)																					*/
	private void siftUp(int index){

		while(index > 0){
			// Will swap indices with parent as long as the current parent value is less.
			if (comparator.compare(hash.get(index), hash.get(parent(index))) < 0){
				int parentIndex = parent(index);
				swap(index, parentIndex);
				index = parentIndex;

			} else {
				//Breaks the loop when parent value is greater than current element.
				break;
			}
		}
		E value = heap.get(index);
		//Finally stores the key and element in hashmap where it ended up.
		hash.put(heap.indexOf(value), value);
	}

	// GETTERS
	// Complexity: O(1)
	public HashMap<Integer, E> getHash(){
		return this.hash;
	}

	public ArrayList<E> getHeap(){
		return this.heap;
	}

	// Helper functions for calculating the children and parent of an index.
	// All of them got Complexity: O(1)
	private int leftChild(int index) {
		return 2*index+1;
	}

	private int rightChild(int index) {
		return 2*index+2;
	}

	private int parent(int index) {
		return (index-1)/2;
	}
}