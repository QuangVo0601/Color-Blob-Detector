//
// Task 1. Set<T> class (10%)
// This is used in DisjointSets<T> to store actual data in the same sets
//

//You cannot import additonal items
import java.util.AbstractCollection;
import java.util.Iterator;
//You cannot import additional items

//
//Hint: if you think really hard, you will realize this class Set<T> is in fact just a list
//      because DisjointSets<T> ensures that all values stored in Set<T> must be unique, 
//      but should it be dynamic array list or linked list??
//

/**
 * The implementation of a Set.
 * @author Quang Vo
 * @param <T> the type of the value of Set.
 */
public class Set<T> extends AbstractCollection<T> {
	//O(1)
	
	/**
	 * The implementation of a node.
	 * @param <T> the type of the value of a Node.
	 */
	private static class Node<T>{
		private T value;
		private Node<T> next;
		
		/**
		 * Create a new node with the specified value.
		 * @param value the specified value.
		 */
		public Node(T value){
			this.value = value;
			this.next = null;
		}
	}
	
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	/**
	 * Construct an empty set.
	 */
	public Set() {
		head = tail = null;
		size = 0;
	}
	
	//O(1)
	/**
	 * Add a specified item to the set.
	 * @param item the item to be added to this set.
	 * @return true if the item is added successfully.
	 * @throws NullPointerException if the item is null.
	 */
	public boolean add(T item) {
		
		if(item == null){ //check if the item is null
			throw new NullPointerException();
		}
		
		Node<T> newNode = new Node<T>(item);
		
		if(this.size == 0){
			head = newNode;
		}
		else{
			tail.next = newNode;
		}
		tail = newNode;
		size++;
		
		return true; 
	}
	
	//O(1)
	/**
	 * Add all of the elements in the specified collection to this set.
	 * @param other collection containing elements to be added to this set.
	 * @return true if this set changed as a result of the call.
	 * @throws NullPointerException if other is null.
	 */
	public boolean addAll(Set<T> other) {
				
		if(other == null){ //check if other is null
			throw new NullPointerException();
		}
		
		this.tail.next = other.head;
		this.tail = other.tail;
		this.size += other.size();
		return true; 
	}
	
	//O(1)
	/**
	 * Remove all of the elements from this set.
	 */
	public void clear() {
		head = tail = null;
		size = 0;
	}
	
	//O(1)
	/**
	 * Number of elements in this set.
	 * @return number of elements in this set.
	 */
	public int size() {
		return size;
	}
	
	//O(1) for next() and hasNext()
	/**
	 * Return an iterator over the elements in this set.
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			Node<T> current = head;
			
			//O(1)
			/**
			 * Obtain the next item in the set.
			 * @return the next item in the set.
			 */
			public T next() {
				
				if(!hasNext()){ //check if there are more items in the set
					throw new NullPointerException("No more items");
				}
				
				T toReturn = current.value;
				current = current.next;
				return toReturn; 
			}
			
			//O(1)
			/**
			 * Test if there are more items in the set.
			 * @return true if there are more items in the set.
			 */
			public boolean hasNext() {
				return current != null;
			}
		};
	}
	
	//main method just for your testing
	//edit as much as you want
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	public static void main(String[] args) {
		
		Set<String> st = new Set<String>();
		st.add("a");
		st.add("b");
		st.add("c");
		
		Set<String> st2 = new Set<String>();
		st2.add("d");
		st2.add("e");
		st2.add("f");
		
		st.addAll(st2);
		
		System.out.println(st);
		
		System.out.println(st.size());
		System.out.println(st2.size());
		
		//System.out.println(st.get(5));
		
		//st.clear();
		
		Iterator<String> itr = st.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}
		
		//st2.clear();

			
	}
}
