package mp1;

public class Ghost 
{
	/**
	 * Stores the current direction the Ghost is moving
	 * 1 is right, -1 is left
	 */
	private int direction = 1;
	
	/**
	 * Stores Current Co-ordinates
	 */
	private int currentX, currentY;
	
	/**
	 * Stores the max and the min range of the ghost
	 */
	private int maximumY, minimumY;
	
	/**
	 * Constructor
	 */
	/*public Ghost(int maxX, int minX, int y, int x)
	{
		direction = 0;
		currentX = x;
		currentY = y;
		maximumX = maxX;
		minimumX = minX;
	}*/
	
	/**
	 * Sets the current X-coordinate
	 */
	public void setCurrentX(int x)
	{
		currentX = x;
	}
	
	/**
	 * Sets the current Y-coordinate
	 */
	public void setCurrentY(int y)
	{
		currentY = y;
	}
	
	/**
	 * Sets the minimum X-coordinate
	 */
	public void setMinimumY(int y)
	{
		minimumY = y;
	}
	
	/**
	 * Sets the minimum Y-coordinate
	 */
	public void setMaximumY(int y)
	{
		maximumY = y;
	}
	
	/**
	 * Moves the Ghost 1 Step
	 */
	public void move()
	{
		//Set the direction
		if(currentY == maximumY)
		{
			direction = -1;
		}
		else if(currentY == minimumY)
		{
			direction = 1;
		}
		
		//Move the Ghost
		currentY = currentY + direction;
	}
	
	/**
	 * Gives the Position of the Ghost
	 * in a certain number of unit movements
	 * @param time The number of unit movements
	 */
	public int getGhostY()
	{
		return currentY;
	}
	
	/**
	 * Returns the X Co-ordinate of the Ghost
	 * in a certain number of unit movements
	 * @param time The number of unit movements
	 */
	public int getGhostX()
	{
		return currentX;
	}

	public int getMinimumY()
	{
		return minimumY;
	}
	
	public int getMaximumY()
	{
		return maximumY;
	}

}
