package mp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Maze 
{
	/**
	 * Stores the Start State
	 * x traverses along the height
	 * y traverses along the width
	 */
	private int startX, startY;
	
	/**
	 * Stores the Co-ordinates of the Goal State
	 */
	private int goalX, goalY;
	
	/**
	 * Store the maze dimensions
	 */
	private int height, width;
	
	/**
	 * The Ghost for this Maze
	 */
	public Ghost scaryGhost;
	
	/**
	 * Store the name of the mazefile
	 */
	String mazeFilePath;
	
	/**
	 * Store the Maze
	 */
	private MazeCell[][] myMaze;
	
	/**
	 * Store the final Path
	 */
	private char[] solution;
	
	/**
	 * Constructor for the Maze
	 * Creates and Initializes the Maze
	 * @param fileName The name of the file that has the Maze
	 */
	public Maze(String filename)
	{	
		//Store the name of the file
		mazeFilePath = filename;
		
		//Store the width and height of the maze
		setHeightWidth();
		
		//Initialize the Maze
		myMaze = new MazeCell[width][height];
		
		//Initialize the ghost
		scaryGhost = new Ghost();
		
		//Store the maze as an Object
		makeMaze();
	}
	
	/**
	 * Return the height of the maze
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Return the width of the Maze
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the Cell at the specified location
	 */
	public MazeCell getCell(int x, int y)
	{
		return myMaze[y][x];
	}
	
	/**
	 * Returns the x Co-ordinate of the Start State
	 * @return
	 */
	public int getStartX()
	{
		return startX;
	}
	
	/**
	 * Returns the y Co-ordinate of the Start State
	 * @return
	 */
	public int getStartY()
	{
		return startY;
	}
	
	/**
	 * Returns the x Co-ordinate of the Goal State
	 * @return
	 */
	public int getGoalX()
	{
		return goalX;
	}
	
	/**
	 * Returns the y Co-ordinate of the Goal State
	 * @return
	 */
	public int getGoalY()
	{
		return goalY;
	}
	
	/**
	 * Stores the Height and Width of the maze
	 * in the file "fileName"
	 * @param fileName The name of the file that contains the maze
	 */
	private void setHeightWidth()
	{
		File mazeFile = new File(mazeFilePath);
		
		try
		{
			Scanner inputReader = new Scanner(mazeFile);
			
			int curr_height = 0, curr_width = 0;
			
			while(inputReader.hasNextLine())
			{
				//Increment the number of rows
				curr_height += 1;
				
				//Store the width
				curr_width = inputReader.nextLine().length();
			}
			
			//Store the Height and Width of this maze
			this.height = curr_height;
			this.width = curr_width;
			
			//Close the inputStream
			inputReader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.toString());
		}
		
	}
	
	/**
	 * Reads the Maze into a Char Array
	 */
	private void makeMaze()
	{
		File mazeFile = new File(mazeFilePath);
		
		try
		{
			Scanner inputReader = new Scanner(mazeFile);
			
			//Iterators to traverse the rows
			int h = 0;
			
			//Parse the Maze into individual cells
			while(inputReader.hasNextLine())
			{
				//Get the current line
				String curr_line = inputReader.nextLine();
				
				for (int w = 0; w < curr_line.length() ; w++)
				{
					//curr_line[h] is the current char being read
					char curr_value = curr_line.charAt(w);
					
					//Intialize the Cell Block
					if (curr_value == '%')
					{
						//Wall
						myMaze[w][h] = new MazeCell(true, h, w);
					}
					else
					{
						if(curr_value == 'P')
						{
							//Start State
							startX = h;
							startY = w;
						}
						else if(curr_value == '.')
						{
							//Goal State
							goalX = h;
							goalY = w;
						}
						else if(curr_value == 'G')
						{
							//Ghost Start State
							scaryGhost.setCurrentX(h);
							scaryGhost.setCurrentY(w);
							
							if(myMaze[w-1][h].isWall())
							{
								scaryGhost.setMinimumY(w);
							}
							else if(curr_line.charAt(w+1) == '%')
							{
								scaryGhost.setMaximumY(w);
							}
						}
						else if( (curr_value == 'g') && (myMaze[w-1][h].isWall()) )
						{
							//MinY for Ghost
							scaryGhost.setMinimumY(w);
						}
						else if( (curr_value == 'g') && (curr_line.charAt(w+1) == '%') )
						{
							//MaxY for Ghost
							scaryGhost.setMaximumY(w);
						}
						else
						{
							//Normal Cell
						}
						
						//Not a Wall
						myMaze[w][h] = new MazeCell(false, h, w);
					}
				}
				
				//Move onto the next row
				h++;
			}
			
			inputReader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Adds the Solution Characters to the Maze
	 */
	private void addSolution()
	{
		int x = startX, y = startY;
		
		for (char c : solution)
		{
			if ( (x >= myMaze[0].length) || (y >= myMaze.length) || (x < 0) || (y < 0) )
			{
				System.out.println("Out of Bounds, addSolution() Method, Maze Class");
				break;
			}
			else if(c == '0')	//Rightwards
			{
				y += 1;
			}
			else if(c == '1')	//Downwards
			{
				x += 1;
			}
			else if(c == '2')	//Leftwards
			{
				y -= 1;
			}
			else	//Upwards
			{
				x -= 1;
			}
			myMaze[y][x].setSolution(true);
		}
	}
	
	/**
	 * Solves the Maze
	 * Choose the Search Algorithm Here
	 */
	public void solve()
	{
		solution = SearchMethods.aStarSearch(this);
	}

	/**
	 * Prints the solution of the current maze
	 * to an output file
	 */
	public void printSolution()
	{
		//Add the Solution to the Maze
		addSolution();

		try
		{
			PrintWriter writer = new PrintWriter(mazeFilePath.substring(0, mazeFilePath.length() - 4) + "_soln.txt");
			
			for (int h = 0; h < height ; h++)
			{
				String line = "";
				
				for(int w = 0; w < width ; w++)
				{
					//Output char for a wall
					if(myMaze[w][h].isWall())
					{
						line += '%';
					}
					//Start State
					else if((h == startX) && (w == startY))
					{
						line += 'P';
					}
					//Goal State
					else if((h == goalX) && (w == goalY))
					{
						line += '.';
					}
					//Solution Character
					else if(myMaze[w][h].isSolution())
					{
						line += '.';
					}
					else if( (scaryGhost.getGhostX() == h) && (w >= scaryGhost.getMinimumY()) && (w <= scaryGhost.getMaximumY()) )
					{
						line += 'G';
					}
					//Output char for an empty cell
					else
					{
						line += ' ';
					}
				}
				
				writer.println(line);
				
			}
			
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Tests a few basic Stats for the Search Algorithms
	 */
	public static void testStats()
	{
		//Create the Maze
		Maze BigMaze = new Maze("src/mp1/BigMaze.txt");
		Maze MediumMaze = new Maze("src/mp1/MediumMaze.txt");
		Maze OpenMaze = new Maze("src/mp1/OpenMaze.txt");
		Maze BigMaze2 = new Maze("src/mp1/BigMaze.txt");
		Maze MediumMaze2 = new Maze("src/mp1/MediumMaze.txt");
		Maze OpenMaze2 = new Maze("src/mp1/OpenMaze.txt");

		//Times
		long BigTime = 0, MediumTime = 0, OpenTime = 0, BigTime2 = 0, MediumTime2 = 0, OpenTime2 = 0;
		
		long StartTime = System.currentTimeMillis();
		int x1 = SearchMethods.breadthFirstSearch(BigMaze).length;
		BigTime = (System.currentTimeMillis() - StartTime)/1000;
		
		StartTime = System.currentTimeMillis();
		int x2 = SearchMethods.depthFirstSearch(BigMaze2).length;
		BigTime2 = (System.currentTimeMillis() - StartTime)/1000;
		
		StartTime = System.currentTimeMillis();
		int y1 = SearchMethods.breadthFirstSearch(MediumMaze).length;
		MediumTime = (System.currentTimeMillis() - StartTime)/1000;
		
		StartTime = System.currentTimeMillis();
		int y2 = SearchMethods.depthFirstSearch(MediumMaze2).length;
		MediumTime2 = (System.currentTimeMillis() - StartTime)/1000;
		
		StartTime = System.currentTimeMillis();
		int z1 = SearchMethods.breadthFirstSearch(OpenMaze).length;
		OpenTime = (System.currentTimeMillis() - StartTime)/1000;
		
		StartTime = System.currentTimeMillis();
		int z2 = SearchMethods.depthFirstSearch(OpenMaze2).length;
		OpenTime2 = (System.currentTimeMillis() - StartTime)/1000;
		
		System.out.println("BreadthFirstSearch on BigMaze: " + BigTime + "seconds " + "Path Length: " + x1);
		System.out.println("DepthFirstSearch on BigMaze: " + BigTime2 + "seconds " + "Path Length: " + x2);
		System.out.println("BreadthFirstSearch on MediumMaze: " + MediumTime + "seconds " + "Path Length: " + y1);
		System.out.println("DepthFirstSearch on MediumMaze: " + MediumTime2 + "seconds " + "Path Length: " + y2);
		System.out.println("BreadthFirstSearch on OpenMaze: " + OpenTime + "seconds " + "Path Length: " + z1);
		System.out.println("DepthFirstSearch on OpenMaze: " + OpenTime2 + "seconds " + "Path Length: " + z2);
		
	}
	
	/**
	 * Driver function to test the above code
	 */
	public static void main(String []args)
	{
		
		Maze mediumMaze = new Maze("src/mp1/bigTurns.txt");
		mediumMaze.solve();
		mediumMaze.printSolution();
		
		System.out.println("Path Length: " + mediumMaze.getCell(mediumMaze.getGoalX(), mediumMaze.getGoalY()).getPath().length());
		System.out.println("Nodes Expanded: " + SearchMethods.nodesExpanded);
		System.out.println("Path Cost: " + SearchMethods.pathCost(mediumMaze.getCell(mediumMaze.getGoalX(), mediumMaze.getGoalY()).getPath().toCharArray()));
	}
}
