package mp1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;

public class SearchMethods 
{
	/**
	 * Stores the cost for a turn
	 */
	private static int turnCost = 2;
	
	/**
	 * Stores the cost for a move
	 */
	private static int moveCost = 1;
	
	/**
	 * Stores the nodes expanded 
	 */
	public static int nodesExpanded = 0;
	
	/**
	 * Implements a Depth First Search on a Maze Object
	 * @param myMaze
	 * @return
	 */
	public static char[] depthFirstSearch(Maze myMaze)
	{
		//For breadth-first search
		Stack <MazeCell> frontier = new Stack<MazeCell>();
						
		//Add the Starting State
		frontier.add(myMaze.getCell(myMaze.getStartX(),myMaze.getStartY()));
				
		while(! frontier.isEmpty())
		{
			//Get the Top Node
			MazeCell currentNode = frontier.pop();
			
			if(currentNode.getVisited() == true)
			{
				continue;
			}
					
			//Mark the current node as visited
			currentNode.setVisited();
			
			nodesExpanded += 1;
			
			int currentX = currentNode.getX();
			int currentY = currentNode.getY();
					
			//Reached the Goal State
			if ( (currentX == myMaze.getGoalX()) && (currentY == myMaze.getGoalY()))
			{
				return myMaze.getCell(currentX, currentY).getPath().toCharArray();
			}
			
			//Go Rightwards (0)
			if ((!myMaze.getCell(currentX, currentY+1).isWall()) && (!myMaze.getCell(currentX, currentY+1).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX, currentY+1));
				
				//Adjust it's Path
				myMaze.getCell(currentX, currentY+1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "0");

			}
			
			//Go Downwards (1)
			if ((!myMaze.getCell(currentX+1, currentY).isWall()) && (!myMaze.getCell(currentX+1, currentY).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX+1, currentY));
				
				//Adjust it's Path
				myMaze.getCell(currentX+1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "1");
			}
			
			//Go Leftwards (2)
			if ((!myMaze.getCell(currentX, currentY-1).isWall()) && (!myMaze.getCell(currentX, currentY-1).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX, currentY-1));
				
				//Adjust it's Path
				myMaze.getCell(currentX, currentY-1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "2");

			}
			
			//Go Upwards (3)
			if ((!myMaze.getCell(currentX-1, currentY).isWall()) && (!myMaze.getCell(currentX-1, currentY).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX-1, currentY));
				
				//Adjust it's Path
				myMaze.getCell(currentX-1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "3");

			}
			
		}
		
		return null;
	}
	
	/**
	 * Implements a Greedy Best First Search on a Maze Object
	 * @param myMaze
	 * @return
	 */
	public static char[] greedyBestFirstSearch(Maze myMaze)
	{
		//For Greedy Best First Search
		Stack <MazeCell> frontier = new Stack<MazeCell>();
						
		//Add the Starting State
		frontier.add(myMaze.getCell(myMaze.getStartX(),myMaze.getStartY()));
		
		//Store the current Co-ordinates
		int currentX = 0, currentY= 0;
		
		while(! frontier.isEmpty())
		{
			//Get the Top Node
			MazeCell currentNode = frontier.pop();
									
			//Check if this node has been visited
			if(currentNode.getVisited() == true)
			{
				continue;
			}
			
			//Mark the current node as visited
			currentNode.setVisited();
			
			//Obtain the current cordinates
			currentX = currentNode.getX();
			currentY = currentNode.getY();
			
			//Reached the Goal State
			if ( (currentX == myMaze.getGoalX()) && (currentY == myMaze.getGoalY()))
			{
				return myMaze.getCell(myMaze.getGoalX(), myMaze.getGoalY()).getPath().toCharArray();
			}
			
			//Increase the number of Expanded Nodes
			nodesExpanded += 1;
			
			//Check that you're not out of Bounds
			if(currentX <= 0 || currentY <= 0 || currentX >= myMaze.getHeight()-1 || currentY >= myMaze.getWidth()-1)
			{
				continue;
			}
			
			//Initial values of heuristics
			int heuristicRight = Integer.MAX_VALUE, heuristicLeft = Integer.MAX_VALUE, 
				heuristicUp = Integer.MAX_VALUE, heuristicDown = Integer.MAX_VALUE;
				
			//Go Rightwards (0)
			if ((!myMaze.getCell(currentX, currentY+1).isWall()) && (!myMaze.getCell(currentX, currentY+1).getVisited()) )
			{
				//Obtain the heuristic
				heuristicRight = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX, currentY+1);
				
				//Adjust the Path
				myMaze.getCell(currentX, currentY+1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "0");
				
				//Set the Heuristic
				myMaze.getCell(currentX, currentY+1).setHeuristicDistance(heuristicRight);
			}
			
			//Go Downwards (1)
			if ((!myMaze.getCell(currentX+1, currentY).isWall()) && (!myMaze.getCell(currentX+1, currentY).getVisited()) )
			{
				heuristicDown = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX+1, currentY);
				myMaze.getCell(currentX+1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "1");
				myMaze.getCell(currentX+1, currentY).setHeuristicDistance(heuristicDown);

			}
			
			//Go Leftwards (2)
			if ((!myMaze.getCell(currentX, currentY-1).isWall()) && (!myMaze.getCell(currentX, currentY-1).getVisited()) )
			{
				heuristicLeft = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX, currentY-1);
				myMaze.getCell(currentX, currentY-1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "2");
				myMaze.getCell(currentX, currentY-1).setHeuristicDistance(heuristicLeft);
			}
			
			//Go Upwards (3)
			if ((!myMaze.getCell(currentX-1, currentY).isWall()) && (!myMaze.getCell(currentX-1, currentY).getVisited()) )
			{
				heuristicUp = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX-1, currentY);
				myMaze.getCell(currentX-1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "3");
				myMaze.getCell(currentX-1, currentY).setHeuristicDistance(heuristicUp);
			}
			
			//Used to Sort the Values
			ArrayList<MazeCell> myNeighbors = new ArrayList<MazeCell>();
			if(heuristicRight != Integer.MAX_VALUE)
			{
				myNeighbors.add(myMaze.getCell(currentX, currentY+1));
			}
			if(heuristicDown != Integer.MAX_VALUE)
			{
				myNeighbors.add(myMaze.getCell(currentX+1, currentY));
			}
			if(heuristicLeft != Integer.MAX_VALUE)
			{
				myNeighbors.add(myMaze.getCell(currentX, currentY-1));
			}
			if(heuristicUp != Integer.MAX_VALUE)
			{
				myNeighbors.add(myMaze.getCell(currentX-1, currentY));			
			}
				
			//Sort the Cells based on Heuristic Values
			myNeighbors = sortNeighbors(myNeighbors);
			
			//Add the Cells to the Frontier in the Sorted Order
			for(MazeCell temp: myNeighbors)
			{
				if(temp.getHeuristicDistance() != Integer.MAX_VALUE)
				{
					frontier.add(temp);
				}
			}			
		}
		
		return myMaze.getCell(myMaze.getGoalX(), myMaze.getGoalY()).getPath().toCharArray();
	}
	
	/**
	 * Implements a Breadth First Search on a Maze Object
	 * @param myMaze
	 * @return
	 */
	public static char[] breadthFirstSearch(Maze myMaze)
	{
		//For breadth-first search
		Queue <MazeCell> frontier = new LinkedList<MazeCell>();
				
		//Add the Starting State
		frontier.add(myMaze.getCell(myMaze.getStartX(),myMaze.getStartY()));
		
		while(! frontier.isEmpty())
		{
			//Get the Top Node
			MazeCell currentNode = frontier.remove();
			
			//Check if this node has been visited
			if(currentNode.getVisited() == true)
			{
				continue;
			}
						
			//Mark the current node as visited
			currentNode.setVisited();
			
			int currentX = currentNode.getX();
			int currentY = currentNode.getY();
			
			//Reached the Goal State
			if ( (currentX == myMaze.getGoalX()) && (currentY == myMaze.getGoalY()))
			{
				return myMaze.getCell(currentX, currentY).getPath().toCharArray();
			}
			
			nodesExpanded += 1;
			
			//Go Rightwards (0)
			if ((!myMaze.getCell(currentX, currentY+1).isWall()) && (!myMaze.getCell(currentX, currentY+1).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX, currentY+1));
				
				//Adjust it's Path
				myMaze.getCell(currentX, currentY+1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "0");

			}
			
			//Go Downwards (1)
			if ((!myMaze.getCell(currentX+1, currentY).isWall()) && (!myMaze.getCell(currentX+1, currentY).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX+1, currentY));
				
				//Adjust it's Path
				myMaze.getCell(currentX+1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "1");
			}
			
			//Go Leftwards (2)
			if ((!myMaze.getCell(currentX, currentY-1).isWall()) && (!myMaze.getCell(currentX, currentY-1).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX, currentY-1));
				
				//Adjust it's Path
				myMaze.getCell(currentX, currentY-1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "2");

			}
			
			//Go Upwards (3)
			if ((!myMaze.getCell(currentX-1, currentY).isWall()) && (!myMaze.getCell(currentX-1, currentY).getVisited()) )
			{
				//Add to the Frontier
				frontier.add(myMaze.getCell(currentX-1, currentY));
				
				//Adjust it's Path
				myMaze.getCell(currentX-1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "3");

			}
			
		}
		
		return myMaze.getCell(myMaze.getGoalX(), myMaze.getGoalY()).getPath().toCharArray();	}
	
	/**
	 * Implements an A-Star Search on a Maze Object
	 * @param myMaze
	 * @return
	 */
	public static char[] aStarSearch(Maze myMaze)
	{
		//For Greedy Best First Search
		List <MazeCell> frontier = new LinkedList<MazeCell>();
								
		//Add the Starting State
		frontier.add(myMaze.getCell(myMaze.getStartX(),myMaze.getStartY()));
				
		//Store the current Coordinates
		int currentX = 0, currentY= 0;
		
		while(!frontier.isEmpty())
		{
			//Remove the best node based on heuristic path cost and current path cost
			int currentNodeIndex = getIndexWithSmallestCurrentPath(frontier);
			MazeCell currentNode = frontier.remove(currentNodeIndex);
			
			//Check if this node has been visited
			if(currentNode.getVisited() == true)
			{
				continue;
			}
			
			//Move the Ghost
			//myMaze.scaryGhost.move();
			
			//Mark the current node as visited
			currentNode.setVisited();
			
			//Obtain the current coordinates
			currentX = currentNode.getX();
			currentY = currentNode.getY();
			
			//myMaze.getCell(myMaze.scaryGhost.getGhostX(), myMaze.scaryGhost.getGhostY()).setWall(true);
			//The ghost is in the same place
			if((currentX == myMaze.scaryGhost.getGhostX()) && (currentY == myMaze.scaryGhost.getGhostY()) )
			{
				continue;
			}
			
			//Reached the Goal State
			if ( (currentX == myMaze.getGoalX()) && (currentY == myMaze.getGoalY()))
			{
				return myMaze.getCell(myMaze.getGoalX(), myMaze.getGoalY()).getPath().toCharArray();
			}			
			
			//Increase the number of Expanded Nodes
			nodesExpanded += 1;
			
			//Initial values of heuristics
			int heuristicRight = Integer.MAX_VALUE, heuristicLeft = Integer.MAX_VALUE, 
				heuristicUp = Integer.MAX_VALUE, heuristicDown = Integer.MAX_VALUE;
				
			//Go Rightwards (0)
			if ((!myMaze.getCell(currentX, currentY+1).isWall()) && (!myMaze.getCell(currentX, currentY+1).getVisited()) )
			{
				//Obtain the heuristic
				//heuristicRight = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX, currentY+1);
				heuristicRight = estimatedManhattan(myMaze.getCell(currentX, currentY+1), myMaze.getGoalX(), myMaze.getGoalY());
				
				//Adjust the Path
				myMaze.getCell(currentX, currentY+1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "0");
				
				//Set the Heuristic
				myMaze.getCell(currentX, currentY+1).setHeuristicDistance(heuristicRight);
				
				//Find the current path cost
				int currentDistanceCost = pathCost(myMaze.getCell(currentX, currentY+1).getPath().toCharArray());
				
				//Set the Current Path Distance
				myMaze.getCell(currentX, currentY+1).setCurrentDistanceCost(currentDistanceCost);
			}
			
			//Go Downwards (1)
			if ((!myMaze.getCell(currentX+1, currentY).isWall()) && (!myMaze.getCell(currentX+1, currentY).getVisited()) )
			{
				//Obtain the Heuristic
				//heuristicDown = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX+1, currentY);
				heuristicDown = estimatedManhattan(myMaze.getCell(currentX+1, currentY), myMaze.getGoalX(), myMaze.getGoalY());
				
				//Adjust the Path
				myMaze.getCell(currentX+1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "1");
				
				//Set the Heuristic
				myMaze.getCell(currentX+1, currentY).setHeuristicDistance(heuristicDown);
				
				//Find the current path cost
				int currentDistanceCost = pathCost(myMaze.getCell(currentX+1, currentY).getPath().toCharArray());
				
				//Set the Current Path Distance
				myMaze.getCell(currentX+1, currentY).setCurrentDistanceCost(currentDistanceCost);
			}
			
			//Go Leftwards (2)
			if ((!myMaze.getCell(currentX, currentY-1).isWall()) && (!myMaze.getCell(currentX, currentY-1).getVisited()) )
			{
				//Obtain the Heuristic
				//heuristicLeft = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX, currentY-1);
				heuristicLeft = estimatedManhattan(myMaze.getCell(currentX, currentY-1), myMaze.getGoalX(), myMaze.getGoalY());
				
				//Adjust the Path
				myMaze.getCell(currentX, currentY-1).appendPath(myMaze.getCell(currentX, currentY).getPath() + "2");
				
				//Set the Heuristic
				myMaze.getCell(currentX, currentY-1).setHeuristicDistance(heuristicLeft);
				
				//Find the current path cost
				int currentDistanceCost = pathCost(myMaze.getCell(currentX, currentY-1).getPath().toCharArray());
				
				//Set the Current Path Distance
				myMaze.getCell(currentX, currentY-1).setCurrentDistanceCost(currentDistanceCost);
			}
			
			//Go Upwards (3)
			if ((!myMaze.getCell(currentX-1, currentY).isWall()) && (!myMaze.getCell(currentX-1, currentY).getVisited()) )
			{
				//Obtain the Heuristic
				//heuristicUp = manhattan(myMaze.getGoalX(), myMaze.getGoalY(), currentX-1, currentY);
				heuristicUp = estimatedManhattan(myMaze.getCell(currentX-1, currentY), myMaze.getGoalX(), myMaze.getGoalY());
				
				//Adjust the Path
				myMaze.getCell(currentX-1, currentY).appendPath(myMaze.getCell(currentX, currentY).getPath() + "3");
				
				//Set the Heuristic
				myMaze.getCell(currentX-1, currentY).setHeuristicDistance(heuristicUp);
				
				//Find the current path cost
				int currentDistanceCost = pathCost(myMaze.getCell(currentX-1, currentY).getPath().toCharArray());
				
				//Set the Current Path Distance
				myMaze.getCell(currentX-1, currentY).setCurrentDistanceCost(currentDistanceCost);
			}
			
			//Add the Neighbors to the frontier
			if(heuristicRight != Integer.MAX_VALUE)
			{
				frontier.add(myMaze.getCell(currentX, currentY+1));
			}
			if(heuristicDown != Integer.MAX_VALUE)
			{
				frontier.add(myMaze.getCell(currentX+1, currentY));
			}
			if(heuristicLeft != Integer.MAX_VALUE)
			{
				frontier.add(myMaze.getCell(currentX, currentY-1));
			}
			if(heuristicUp != Integer.MAX_VALUE)
			{
				frontier.add(myMaze.getCell(currentX-1, currentY));			
			}
			
			//myMaze.getCell(myMaze.scaryGhost.getGhostX(), myMaze.scaryGhost.getGhostY()).setWall(false);
			
		}
		
		return myMaze.getCell(myMaze.getGoalX(), myMaze.getGoalY()).getPath().toCharArray();
	}
	
	/**
	 * Finds the MazeCell index with the lowest evaluation function
	 * @param frontier
	 * @return
	 */
	private static int getIndexWithSmallestCurrentPath(List<MazeCell> frontier)
	{
		int minIndex = 0;
		
		for(int i = 1; i < frontier.size(); i++)
		{
			if(frontier.get(i).getTotalDistance() < frontier.get(minIndex).getTotalDistance())
			{
				minIndex = i;
			}
		}
		
		return minIndex;
	}
	
	/**
	 * Computes the Path Cost for the Solution Path
	 * @param path
	 * @return
	 */
	public static int pathCost(char []path)
	{
		int runningCost = 0;
		
		for(int i = 0; i < path.length; i++) 
		{
			switch(path[i]) 
			{
				case '0' : if(i == 0 || path[i-1] == '0') 
						   {
							  runningCost += moveCost;
							  break; 
						   }
						   else if(path[i-1] == '1' || path[i-1] == '3')
						   {
							  runningCost += (moveCost + turnCost);
							  break;
						   }
							 
				case '1' : if(i == 0 || path[i-1] == '1')
							{
					  			runningCost += moveCost;
					  			break; 
						   }
				   		   else if(path[i-1] == '0' || path[i-1] == '2')
				   		   {
				   			   runningCost += (moveCost + turnCost);
				   			   break;
				   		   }
				
				case '2' : if(i == 0 || path[i-1] == '2') 
							{
								runningCost += moveCost;
								break; 
						   }
						  else if(path[i-1] == '1' || path[i-1] == '3')
						  {
							  	runningCost += (moveCost + turnCost);
							  	break;
						  }
				
				case '3' : if(i == 0 || path[i-1] == '3') 
							{
		  						runningCost += moveCost;
		  						break; 
						   }
	   		   			   else if(path[i-1] == '0' || path[i-1] == '2')
	   		   			   {
	   		   				   runningCost += (moveCost + turnCost);
	   		   				   break;
	   		   			   }
			}
		}
		
		return runningCost;
	}
	
	
	
	/**
	 * The Manhattan Distance between start point and end point
	 */
	private static int manhattan(int endX, int endY, int startX, int startY)
	{
		return Math.abs(endX - startX) + Math.abs(endY - startY);
	}

	/**
	 * Custom Heuristic Function
	 */
	private static int estimatedManhattan(MazeCell currentCell, int goalX, int goalY)
	{
		//Obtain the current path
		String currentPath = currentCell.getPath();
		
		//Get the Current Path Cost
		int currentPathCost = pathCost(currentPath.toCharArray());
		
		int currNodesExpanded = nodesExpanded;
		
		int length = currentPath.length();
		if(length == 0)
		{
			length += 1;
		}
		
		//Cost per move
		//int averagePathCost = currentPathCost/length;
		int averageNodesExpanded = currNodesExpanded/length;
		
		//Get the number of moves left
		int distanceLeft = manhattan(goalX, goalY, currentCell.getX(), currentCell.getY());
	
		return averageNodesExpanded*distanceLeft;
		
	}

	/**
	 * Sorts an ArrayList of MazeCells based on the value
	 * of the Heuristic Function
	 * @param neighbors
	 * @return
	 */
	private static ArrayList<MazeCell> sortNeighbors(ArrayList<MazeCell> neighbors) 
	{				
		for(MazeCell temp: neighbors)
		{
			if (temp.getHeuristicDistance() == Integer.MAX_VALUE)
			{
				neighbors.remove(temp);
			}
		}
		
		//Lowest index has highest path cost
		for(int i = 0; i < neighbors.size(); i++)
		{
			for(int j = 1; j < neighbors.size() - i; j++)
			{
				if(neighbors.get(j-1).getHeuristicDistance() < neighbors.get(j).getHeuristicDistance())
				{
					MazeCell temp = neighbors.get(j-1);
					neighbors.set(j-1, neighbors.get(j));
					neighbors.set(j, temp);
				}
			}
		}
		return neighbors;
	}
}
