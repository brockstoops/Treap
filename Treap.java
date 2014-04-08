//Brock Stoops
//COP 3503 Fall 2013
//Assignment 3 Treaps
//This program is a treap class that will handle insertion, deletion, finding size and height


import java.io.*;
import java.util.*;

//Node class
class Node<AnyType extends Comparable<AnyType>>
{
	//set data and left and right nodes of Treap
	AnyType data;
	int priority;
	Node<AnyType> left, right;

	//Node constructor for only data field
	Node(AnyType data)
	{
		this.data = data;
	}
	//Node constructor for data and priority field
	Node(AnyType data, int priority){
		this.data = data;
		this.priority = priority;
	}
	
}

public class Treap<AnyType extends Comparable<AnyType>> {
	
	//Set constant count equal to zero
	//Count will be used for an O(1) tree size find
	public int count = 0;
	
	//Array list to keep track of the priority integers used
	ArrayList<Integer> l = new ArrayList<Integer>();
	private Node<AnyType> root;
	
	//Method to make compatible with Traversals
	public Node<AnyType> getRoot(){
		return root;
	}
	
	//Public add method
	public void add(AnyType data){
		root = add(root, data);
	}
	
	//Add method when no priority is given
	private Node<AnyType> add(Node<AnyType> root, AnyType data){
		
		//Random genterator to get random value for priorities
		Random generator = new Random();
		int priority = generator.nextInt(Integer.MAX_VALUE);
	
		//Make sure priority wasnt already used, if not add it to array list
		if(l.contains(priority) == false){
			l.add(priority);
			root = add(root, data, priority);
		}
		//If priority was already used then just run add function again to generate new priority
		//and add to array list
		else
			root = add(root, data);
		
		return root;
	}
	//Method to add with priority
	public void add(AnyType data, int priority){
		//add 1 to count to keep track of size
		count+=1;
		root = add(root, data, priority);
	}
	
	//Private add method to run with data and priority
	private Node<AnyType> add(Node<AnyType> root, AnyType data, int priority){
		//If empty tree just make new tree
		if (root == null){
			return new Node<AnyType>(data, priority);
		}
		//If not empty see where to store node
		else if (data.compareTo(root.data) < 0){
			root.left = add(root.left, data, priority);
			//If added and priority is smaller perform a rotation
			if(root.left.priority < root.priority)
				root = rightRotation(root);
		}
		//Find place to store not if it is greater than root, go to right and add
		else if (data.compareTo(root.data) > 0){
			root.right = add(root.right, data, priority);
			//If priority is lower, perform a rotation
			if(root.right.priority < root.priority)
				root = leftRotation(root);
		}
		//If trying to re-add a value decrement the count by one for true size
		else
			count-=1;
		return root;
	}
	
	//Small method to perform a right rotation
	private Node<AnyType> rightRotation(Node<AnyType> root){
		Node<AnyType> temp = root.left;
		root.left = temp.right;
		temp.right = root;
		return temp;
	}
	//Small method to perform a left rotation
	private Node<AnyType> leftRotation(Node<AnyType> root){
		Node<AnyType> temp = root.right;
		root.right = temp.left;
		temp.left = root;
		return temp;
	}
	
	//Public void remove method that references the private method
	public void remove(AnyType data){
		root = remove(root, data);
	}
	
	//Remove from treap function
	private Node<AnyType> remove(Node<AnyType> root, AnyType data){
		//If root is null return null
		if (root == null){
			return null;
		}
		//If data node is less than root data go left in subtree and run delete function again
		else if (data.compareTo(root.data) < 0){
			root.left = remove(root.left, data);
		}
		//If node data is more than root data go to right subtree and run delete function again
		else if (data.compareTo(root.data) > 0){
			root.right = remove(root.right, data);
		}
		else{
			//Leaf node, delete and decrement size of tree by 1
			if (root.left == null && root.right == null){
				count-=1;
				return null;
			}
			//If only one child on right then perform a right rotation and run delete function 
			//again
			else if (root.right == null){
				root = rightRotation(root);
				root = remove(root, data);
			}
			//Do the same if there is only one child and it's on the left side
			else if (root.left == null){
				root = leftRotation(root);
				root = remove(root, data);
			}
			//if there is two children choose the lower priority and bring it up
			//with a rotation
			else{
				if(root.left.priority < root.right.priority){
					root = rightRotation(root);
					root = remove(root, data);
				}
				else{
					root = leftRotation(root);
					root = remove(root, data);
					}
			}
			
		}
		return root;
	}
	
	//Public contains method that finds if a value is in the Treap
	public boolean contains(AnyType data){
		return contains(root, data);
	}
	
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		//Empty tree return false
		if (root == null)
		{
			return false;
		}
		//If data is less than the root go left
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		//If data is greater than root go right
		else if (data.compareTo(root.data) >0)
		{
			return contains(root.right, data);
		}
		//If value equals the node return true, found the value
		else
		{
			return true;
		}
	}
	
	//Order 1 method for the size of the tree
	public int size(){
		return count;
	}
	
	//Public height that references the private height method
	public int height(){
		return height(root);
	}
	//method to get height
	private int height(Node<AnyType> root){
		//if empty tree return -1
		if (root == null)
			return -1;
		//recursively get height of tree
		int left = height(root.left);
		int right = height(root.right);
		
		//return  the bigger side of the tree value plus one for true height
		if (right>left)
			return right+1;
		else 
			return left+1;
	}
	
	public static double difficultyRating(){
		return 2.5;
	}
	
	public static double hoursSpent(){
		return 3.0;
	}	
}
