# 8 Puzzle Solver

Java program that calculates the number of possible solutions for two input states of the famous 8-puzzle game. It further calculates the common states between them.

## User input

User input is required for both input states, S1 & S2. Users can enter any set of characters, with a compulsory 'x' for the empty space in the game. While validation is not strict, some basic validation of user responses exists. ***States S1 & S2 are then stored in an ArrayList***.

`static ArrayList<ArrayList<String>> startStates = new ArrayList<ArrayList<String>>();`

## Algorithm

The algorithm for finding possible states is complex and consists of the following stages:
1. Start from the first state
2. Use Depth-First-Search to discover new states and a Stack to keep track of discovered states
3. Once all possible states are explored, retrieve the next item in the stack ***until it is empty***
4. Once the stack is empty and there are no more states to explore, all ***unique*** states have been explored

## Possible solutions

Due to the nature of this problem, the number of solutions may be significantly large. Therefore, a ***HashSet*** is used to store all possible solutions, rather than an ***ArrayList*** or anything similar. ***HashSet*** is efficient for data lookup, providing constant time performance for basic operations such as adding, checking if it's contained, and removing. For checking if a state is already explored, this is crucial. Furthermore, ***HashSet*** automatically eliminates duplicated elements and is a more suitable choice for this problem.

No matter what characters are entered, as long as we follow the rules of the game (8 filled squares and one empty), there will always be `181440` possible states for any starting state, and the same number of common states between two input states, as long as they have the same set of input characters.
