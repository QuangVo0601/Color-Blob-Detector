// Task 1. DisjointSets class (15%)

// Hint: you can use the DisjointSets from your textbook
// but it must be changed also union the the actual data together

import java.util.ArrayList;

// disjoint sets class, using union by size and path compression.
/**
 * The implementation of Disjoint Sets, using union by size and path compression.
 * @author Quang Vo
 * @param <T> the type of value of Disjoint Sets.
 */
public class DisjointSets<T>
{
	//Data - You must use these variables for credit...
	//we will be "breaking in" to peak at them for testing
	//DO NOT change their names, types, etc.
	private int[] s; //the sets
	private ArrayList<Set<T>> sets; //the actual data for the sets

	//Constructor
	/**
	 * Construct new Disjoint Sets with a list of data.
	 * @param data the list of data.
	 * @throws NullPointerException if data is empty.
	 */
	public DisjointSets(ArrayList<T> data) {
		//your code here
		
		if(data == null){ //if data is empty
			throw new NullPointerException();
		}
		
		s = new int[data.size()];
		
		sets = new ArrayList<Set<T>>();
		
		for(int i = 0; i < s.length; i++){ 
			s[i] = -1; //initialize the sets
			Set<T> newSet = new Set<T>();
			newSet.add(data.get(i)); //add new data to the set
			sets.add(newSet); //add each set the the list of sets
		}
	}

	//Compute the union of two sets using rank union by size
	//returns the new root of the unioned set
	//if two sets are equal, root1 is the new root
	//Must be O(1) time (do not perform find())
	//throw IllegalArgumentException() if non-roots provided
	/**
	 * Compute the union of two sets using rank union by size.
	 * @param root1 the root of the first set.
	 * @param root2 the root of the second set.
	 * @return the new root of the unioned set.
	 * @throws IllegalArgumentException if non-roots provided.
	 */
	public int union(int root1, int root2) {
		
		if(root1 == root2){ //if two roots are the same, return root 1
			return root1;
		}
		
		checkIsRoot(root1);
		checkIsRoot(root2);
		
		if(sets.get(root2).size() > sets.get(root1).size()){ //if set of root2 is greater than set of root1, root2 is the new root
			s[root1] = root2;
			sets.get(root2).addAll(sets.get(root1)); //add root1 to root2
			sets.get(root1).clear(); //clear root1
			return root2;
		}
		else{ //if set of root2 is less than or equal to set of root1, root1 is the new root
			s[root2] = root1;
			sets.get(root1).addAll(sets.get(root2)); //add root2 to root1
			sets.get(root2).clear(); //clear root2
			return root1;
		}
	}
	
	/**
	 * Helper method to check if the non-roots provided.
	 * @param root the given root of the set.
	 * @throws IllegalArgumentException if non-roots provided.
	 */
	private void checkIsRoot(int root){
		
		if(s[root] >= 0){
			throw new IllegalArgumentException(root + " is not a root"); //one of them is not a root
		}
	}

	//Find and return the root
	//Must implement path compression
	/**
	 * Find and return the root of a set, using path compression.
	 * @param x the item to find the root for.
	 * @return the root of the set.
	 * @throws IllegalArgumentException if x is not a valid item.
	 */
	public int find(int x) {
				
		checkIsItem(x); //check to see if x is a valid item
		
		if(s[x] < 0){
			return x; //if x is the root, return it
		}
		else{
			return s[x] = find(s[x]); //Find out who the root is; compress path by making the root x's parent.
		}
	}
	
	/**
	 * Helper method to check if x is a valid item.
	 * @param x the item to be checked.
	 * @throws IllegalArgumentException if x is not a valid item.
	 */
    private void checkIsItem( int x )
    {
        if( x < 0 || x >= s.length )
            throw new IllegalArgumentException( "Disjoint sets: " + x + " is not an item" );       
    }

	//Get all the data in the same set
	//Must be O(1) time
    /**
     * Get all the data in the same set.
     * @param root the root of the set.
     * @return all the data in the same set.
     * @throws IndexOutOfBoundsException if the root is invalid.
     */
	public Set<T> get(int root) {
				
		if(root < 0 || root >= sets.size()){
			throw new IndexOutOfBoundsException("Index: " + root + ", Size: " + sets.size());
		}
		
		return sets.get(root); 
	}
	
	//main method just for your testing
	//edit as much as you want
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	public static void main(String[] args) {
		
		ArrayList<String> arr2 = new ArrayList<>();
		arr2.add("ryan");
		arr2.add("vo");
		arr2.add("quang");
		arr2.add("hi");
		arr2.add("hey");
		arr2.add("hello");
		
		DisjointSets<String> ds2 = new DisjointSets<>(arr2);
				
		System.out.println(ds2.find(0)); //0
		System.out.println(ds2.get(0)); //[ryan]
		System.out.println(ds2.find(1)); //1
		System.out.println(ds2.union(0, 1)); //0
		System.out.println(ds2.get(0)); //[ryan, vo]
		System.out.println(ds2.union(0, 2)); //0
		System.out.println(ds2.get(0)); //[ryan, vo, quang]
		System.out.println(ds2.get(1)); //[]
		ds2.union(5, 3);
		ds2.union(5, 4);
		System.out.println(ds2.union(5, 0));
		System.out.println(ds2.get(5));
		
		
		ArrayList<Integer> arr = new ArrayList<>();
		for(int i = 0; i < 10; i++)
			arr.add(i);
		
		DisjointSets<Integer> ds = new DisjointSets<>(arr);
				
		System.out.println(ds.find(0)); //should be 0
		System.out.println(ds.find(1)); //should be 1
		System.out.println(ds.union(0, 1)); //should be 0
		System.out.println(ds.find(0)); //should be 0
		System.out.println(ds.find(1)); //should be 0
		System.out.println("-----");
		System.out.println(ds.find(0)); //should be 0
		System.out.println(ds.find(2)); //should be 2
		System.out.println(ds.union(0, 2)); //should be 0
		System.out.println(ds.find(0)); //should be 0
		System.out.println(ds.find(2)); //should be 0
		System.out.println("-----");
		//Note: AbstractCollection provides toString() method using the iterator
		//see: https://docs.oracle.com/javase/8/docs/api/java/util/AbstractCollection.html#toString--
		//so your iterator in Set needs to work for this to print out correctly
		System.out.println(ds.get(0)); //should be [0, 1, 2]
		System.out.println(ds.get(1)); //should be []
		System.out.println(ds.get(2)); //should be []
		System.out.println(ds.get(3)); //should be [3]
		//System.out.println(ds.get(-2));
		System.out.println();
		
		
		ArrayList<Integer> arr3 = new ArrayList<>();
		for(int i = 0; i < 20; i++)
			arr3.add(i);
		
		DisjointSets<Integer> ds3 = new DisjointSets<>(arr3);
		System.out.println(ds3.find(0)); //should be 0
		System.out.println(ds3.union(0, 1)); //should be 0
		System.out.println(ds3.union(0, 2)); //should be 0
		System.out.println(ds3.union(0, 10));
		System.out.println(ds3.union(0, 19));
		System.out.println(ds3.union(0, 15));
		System.out.println(ds3.union(0, 12));
		System.out.println(ds3.find(10)); //should be 0
		System.out.println(ds3.find(15)); //should be 0
		System.out.println(ds3.union(3, 4)); //should be 3
		System.out.println(ds3.union(3, 5)); //should be 3
		//System.out.println(ds3.union(3, 0)); //should be 0
		//System.out.println(ds3.union(0, 3)); //should be 0

		System.out.println(ds3.get(0).size()); //should be 
		System.out.println(ds3.get(0));
		System.out.println(ds3.get(3));
		
		
		
		
	}
}
