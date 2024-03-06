package PuzzleProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class PuzzleProblem {
	//Create ArrayLists to store the start states
	static ArrayList<ArrayList<String>> startStates = new ArrayList<ArrayList<String>>();
	
	//Create sets to store all explored states 
	static Set<String> possibleStatesA = new HashSet<String>();
	static Set<String> possibleStatesB = new HashSet<String>();
	
	//Implement stack structure (needed for DFS)
	static Stack<ArrayList<String>> stack = new Stack<ArrayList<String>>();
	
	//Create set to hold all common states between both start states
	static Set<String> commonStates = new HashSet<String>();
	

	public static void main(String[] args) {
		//Take input for start states
		out("\nINPUT STATE S1\n");
		startStates.add(takeInput());
		out("\nINPUT STATE S2\n");
		startStates.add(takeInput());
		
		//Find possible states for both input states
		for(int iteration=0;iteration<2;iteration++) {
			//Add start state to explored states set AND to the stack
			if(iteration==0) {
				possibleStatesA.add(startStates.get(iteration).toString());
			} else {
				possibleStatesB.add(startStates.get(iteration).toString());
			}
			stack.push(startStates.get(iteration));
			
			//WHILE the stack is not empty keep exploring all possible states
			while(stack.isEmpty() == false) {
				//Information about program execution (Uncomment if you want to see more details)
				/*
				if(iteration==0) {
					out("[State S" + iteration+1 + "] --> States discovered: " + possibleStatesA.size() + " | Stack size: " + stack.size());
				} else {
					out("[State S" + iteration+1 + "] --> States discovered: " + possibleStatesB.size() + " | Stack size: " + stack.size());
				}
				*/
				
				//Create boolean to identify whether the next node has been found
				boolean nextFound = false;
				
				//Check if there is a state to the LEFT
				if(nextState(stack.peek(),'L') != null) {
					//Check if it has already been discovered
					nextFound = possibleNode('L',iteration);
				} 
				
				//Check if there is a state to the UP
				if(nextState(stack.peek(),'U') != null && nextFound != true) {
					//Check if it has already been discovered
					nextFound = possibleNode('U',iteration);
				}
				
				//Check if there is a state to the RIGHT
				if(nextState(stack.peek(),'R') != null && nextFound != true) {
					//Check if it has already been discovered
					nextFound = possibleNode('R',iteration);
				}
				
				//Check if there is a state to the DOWN
				if(nextState(stack.peek(),'D') != null && nextFound != true) {
					//Check if it has already been discovered
					nextFound = possibleNode('D',iteration);
				}
				
				//If no new states has been found/All new states are duplicated :. Maximum depth of branch reached
				if(!(nextFound)) {
					stack.pop();
				}
			}
		}
		
		//OUTPUTS
		out("Possible states for input A: ");
		for(String each: possibleStatesA) {
			out(each.substring(1,2) + "|" + each.substring(4,5) + "|" + each.substring(7,8));
			out(each.substring(10,11) + "|" + each.substring(13,14) + "|" + each.substring(16,17));
			out(each.substring(19,20) + "|" + each.substring(22,23) + "|" + each.substring(25,26));
			out("");
		}
		
		out("Possible states for input B: ");
		for(String each: possibleStatesA) {
			out(each.substring(1,2) + "|" + each.substring(4,5) + "|" + each.substring(7,8));
			out(each.substring(10,11) + "|" + each.substring(13,14) + "|" + each.substring(16,17));
			out(each.substring(19,20) + "|" + each.substring(22,23) + "|" + each.substring(25,26));
			out("");
		}
		
		commonStates = possibleStatesA;
		commonStates.retainAll(possibleStatesB);
		
		out("Common possible states between input A & input B: ");
		for(String each: commonStates) {
			out(each.substring(1,2) + "|" + each.substring(4,5) + "|" + each.substring(7,8));
			out(each.substring(10,11) + "|" + each.substring(13,14) + "|" + each.substring(16,17));
			out(each.substring(19,20) + "|" + each.substring(22,23) + "|" + each.substring(25,26));
			out("");
		}
		
		out("Total possible states for input A: " + possibleStatesA.size());
		out("Total possible states for input B: " + possibleStatesB.size());
		out("Total common states between input A & input B: " + commonStates.size());

		out("\nEnd");
	}
	
	//Checks if a given node has anymore non-explored/non-null/distinct sub-nodes or not
	private static boolean possibleNode(char dir, int iteration) {
		boolean completed = false;
		
		if(iteration == 0) {
			if(possibleStatesA.contains(nextState(stack.peek(),dir).toString()) == false) {
				//If there exist new state and it is UNIQUE, add it to explored states ArrayList and to the Stack
				possibleStatesA.add(nextState(stack.peek(),dir).toString());
				stack.push(nextState(stack.peek(),dir));
				completed = true;
			}
		} else {
			if(possibleStatesB.contains(nextState(stack.peek(),dir).toString()) == false) {
				//If there exist new state and it is UNIQUE, add it to explored states ArrayList and to the Stack
				possibleStatesB.add(nextState(stack.peek(),dir).toString());
				stack.push(nextState(stack.peek(),dir));
				completed = true;
			}
		}
		
		//return True if new state was found; False - otherwise
		return completed;
	}
	
	//Function that will take a state as input and a direction and will check if the blank tile in the state can be moved in the direction
	//If yes, returns the new state
	//If no, returns NULL
	private static ArrayList<String> nextState(ArrayList<String> currentState, char direction) {
		//Create new ArrayList to store the new state or null
		ArrayList<String> newState = new ArrayList<String>();
		//Get the index of the 'gap'
		int indexOfGap = currentState.indexOf(" ");
		
		if(direction == 'L') {
			//Check if it is possible to move LEFT
			if(indexOfGap % 3 == 0) {
				//It is not possible to move LEFT
				newState = null;
			} else {
				//It is possible to move LEFT :. Generate new state
				for(int index=0;index<indexOfGap-1;index++) {
					newState.add(currentState.get(index));
				}
				newState.add(" ");
				newState.add(currentState.get(indexOfGap-1));
				for(int index=indexOfGap+1;index<currentState.size();index++) {
					newState.add(currentState.get(index));
				}
			}
		} else if(direction == 'R') {
			//Check if it is possible to move RIGHT
			if((indexOfGap+1) % 3 == 0) {
				//It is not possible to move RIGHT
				newState = null;
			} else {
				//It is possible to move RIGHT :. Generate new state
				for(int index=0;index<indexOfGap;index++) {
					newState.add(currentState.get(index));
				}
				newState.add(currentState.get(indexOfGap+1));
				newState.add(" ");
				for(int index=indexOfGap+2;index<currentState.size();index++) {
					newState.add(currentState.get(index));
				}
			}
		} else if(direction == 'U') {
			//Check if it is possible to move UP
			if(indexOfGap >= 0 && indexOfGap <= 2) {
				//It is not possible to move UP
				newState = null;
			} else {
				//It is possible to move UP :. Generate new state
				for(int index=0;index<indexOfGap-3;index++) {
					newState.add(currentState.get(index));
				}
				newState.add(" ");
				for(int index=indexOfGap-2;index<indexOfGap;index++) {
					newState.add(currentState.get(index));
				}
				newState.add(currentState.get(indexOfGap-3));
				for(int index=indexOfGap+1;index<currentState.size();index++) {
					newState.add(currentState.get(index));
				}
			}
		} else if(direction == 'D') {
			//Check if it is possible to move DOWN
			if(indexOfGap >= 6 && indexOfGap <= 8) {
				//It is not possible to move DOWN
				newState = null;
			} else {
				//It is possible to move DOWN :. Generate new state
				for(int index=0;index<indexOfGap;index++) {
					newState.add(currentState.get(index));
				}
				newState.add(currentState.get(indexOfGap+3));
				for(int index=indexOfGap+1;index<indexOfGap+3;index++) {
					newState.add(currentState.get(index));
				}
				newState.add(" ");
				for(int index=indexOfGap+4;index<currentState.size();index++) {
					newState.add(currentState.get(index));
				}
			}
		}
		
		return newState;
	}
	
	//Global System.out.println(x) function
	private static void out(Object value) {
		System.out.println(value);
	}

	//A function to take the user input in easy and friendly way
	private static ArrayList<String> takeInput() {
		//Take input states
		Scanner sc = new Scanner(System.in);
		ArrayList<String> temp = new ArrayList<String>();
		boolean proceed = false;
		
		while(proceed != true) {
			temp = new ArrayList<String>(Arrays.asList("?","?","?","?","?","?","?","?","?"));
			
			for(int count=0;count<9;count++) {
				temp.set(count,"*");
				out(temp.get(0) + "|" + temp.get(1) + "|" + temp.get(2));
				out(temp.get(3) + "|" + temp.get(4) + "|" + temp.get(5));
				out(temp.get(6) + "|" + temp.get(7) + "|" + temp.get(8));
				out("\nEnter character or 'x' for space: (To replace the '*' in the diagram above): ");
				String newChar = sc.next();
				if(newChar.equals("X") || newChar.equals("x")) {
					temp.set(count, " ");
				} else {
					temp.set(count, newChar);
				}
				out("");
			}
			
			out("Current configuration:");
			for(int row=0;row<3;row++) {
				out(temp.get(row*3) + "|" + temp.get(row*3+1) + "|" + temp.get(row*3+2));
			}
			
			out("\nIs this configuration right? (Y/N):");
			if (sc.next().equalsIgnoreCase("Y")) {
				proceed = true;
			} else {
				out("\nReseting configuration...");
			}
		}
		
		return temp;
		
	}
}
