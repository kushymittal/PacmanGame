package mp1;

public class MazeCell 
{
	/**
	 * The X and Y Co-ordinates
	 */
	private int x, y;
	
	//______________________CONSTRUCTOR___________________________//
	
	/**
	 * Constructor for a Maze Cell
	 */
	public MazeCell(boolean WallStatus, int currentX, int currentY)
	{
		setWall(WallStatus);
		x = currentX;
		y = currentY;
		
	}
	
	//______________________WALL DATA______________________________//
	
	/**
	 * Stores if this cell is a wall
	 */
	private boolean Wall;
	
	/**
	 * Returns true if this cell is a wall
	 */
	public boolean isWall()
	{
		return Wall;
	}
	
	/**
	 * Set the Wall Status for this cell
	 */
	public void setWall(boolean wallStatus)
	{
		Wall = wallStatus;
	}
	
	//______________________PATH DATA______________________________//
	
	/**
	 * Stores the path of how to get to this Cell
	 */
	private String path = "";
	
	/**
	 * Stores the heuristic from this cell to the end
	 */
	private int heuristicDistance;
	
	/**
	 * Stores the actual number of steps traversed
	 * to get to this cell
	 */
	private int currentDistanceCost = 0;
	
	/**
	 * The sum of currentDistance and heuristicDistance
	 */
	private int totalDistance = 0;
	
	/**
	 * Append something to the path for this cell
	 */
	public void appendPath(String currentPath)
	{
		path = currentPath;
	}
	
	/**
	 * Get the Path for this Cell
	 */
	public String getPath()
	{
		return path;
	}
	
	/**
	 * Setter method for the Heuristic Distance
	 * @param dist THe Heuristic Function's Value
	 */
	public void setHeuristicDistance(int dist)
	{
		heuristicDistance = dist;
		setTotalDistance();
	}
	
	/**
	 * Returns the value of the Heuristic Function
	 */
	public int getHeuristicDistance()
	{
		return heuristicDistance;
	}
	
	/**
	 * Returns the value of currentDistance
	 */
	public int getCurrentDistanceCost()
	{
		return currentDistanceCost;
	}
	
	/**
	 * Setter method for the currentDistance
	 * @param dist THe currentDistance's Value
	 */
	public void setCurrentDistanceCost(int dist)
	{
		currentDistanceCost = dist;
		setTotalDistance();
	}
	
	/**
	 * Returns the total distance
	 */
	public int getTotalDistance()
	{
		return totalDistance;
	}
	
	/**
	 * Sets the total distance
	 */
	private void setTotalDistance()
	{
		totalDistance = currentDistanceCost + heuristicDistance;
	}
	
	//___________________CORDINATE DATA__________________________//
	
	/**
	 * Return the X Co-ordinate of this cell
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Return the Y Co-ordinate of this cell
	 * @return
	 */
	public int getY()
	{
		return y;
	}
	
	//__________________VISITED DATA____________________________//
	/**
	 * Stores if the Cell has been visited
	 */
	private boolean haveVisited = false;
	
	/**
	 * Mark the Cell as Visited
	 */
	public void setVisited()
	{
		haveVisited = true;
	}
	
	/**
	 * Get the status of this cell
	 * @return
	 */
	public boolean getVisited()
	{
		return haveVisited;
	}
	
	//_________________SOLUTION DATA___________________________//
	/**
	 * If this Cell is on the solution path
	 */
	private boolean solution = false;
	
	/**
	 * Returns True if this Cell is on the Solution Path 
	 */
	public boolean isSolution()
	{
		return solution;
	}
	
	/**
	 * Set the solution data
	 */
	public void setSolution(boolean status)
	{
		solution = status;
	}
}
