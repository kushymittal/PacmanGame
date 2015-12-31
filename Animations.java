package src.mp1;

import java.util.Queue;
import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;

public class Animation extends JPanel{

	Maze maze;
	char[] solution;

	public Animation(Maze maze, char[] solution){
		super();
		setBackground(Color.WHITE);
		this.maze = maze;
		this.solution = solution;

	}



	public void paintComponent(Graphics g)  
    {
        int width = getWidth();          
        int height = getHeight();           
 

        for(int x = 0 ; x < maze.getHeight() ; x++){
			for(int y = 0 ; y < maze.getWidth() ; y++){
				if(maze.getCell(x,y).isWall()){
					g.setColor(Color.BLACK);
					g.fillRect(20 * y, 20 * x , 20, 20);
				}
				else{
					g.setColor(Color.WHITE);
					g.fillRect(20 * y, 20 * x , 20, 20);
				}
			}
		}

		int i = 1;

		int divideFactor; 

		if(SearchMethods.nodesExpanded < 256) divideFactor = 1;
		else if(SearchMethods.nodesExpanded > 255 && SearchMethods.nodesExpanded < 511) divideFactor = 2;
		else if(SearchMethods.nodesExpanded > 510 && SearchMethods.nodesExpanded < 766) divideFactor = 3;
		else if(SearchMethods.nodesExpanded > 765 && SearchMethods.nodesExpanded < 1021) divideFactor = 4;
		else divideFactor = 5;


		while(!(maze.nodesExpanded).isEmpty()){
			MazeCell curr = (maze.nodesExpanded).remove();

			g.setColor(new Color(255-(i/divideFactor),255-(i/divideFactor),255));
			g.fillRect(curr.getY()  * 20,curr.getX() * 20, 20 , 20);


			i++;

			//Waits 1/4 second and then draws the next node
			// try {

   //  			Thread.sleep(250);
			// } catch (InterruptedException e) {
   //  			Thread.currentThread().interrupt();
			// }


		}

		if(maze.scaryGhost.exists){
			int min = maze.scaryGhost.getMinimumY();
			int max = maze.scaryGhost.getMaximumY();

			for(int y = min ; y < max + 1 ; y++){
				g.setColor(Color.YELLOW);
				g.fillRect(y * 20, (maze.scaryGhost).getGhostX() * 20, 20, 20);
			}
				
			
			
		}
		int x = maze.getStartX() * 20;
		int y = maze.getStartY() * 20;

		for(int j =0 ; j<solution.length ; j++){
			if(solution[j] == '0'){
				y+=20;
				g.setColor(Color.RED);
				g.fillOval(y,x,20,20);

			}
			else if(solution[j] == '1'){
				x+=20;
				g.setColor(Color.RED);
				g.fillOval(y,x,20,20);

			}
			else if(solution[j] == '2'){
				y-=20;
				g.setColor(Color.RED);
				g.fillOval(y,x,20,20);

			}
			else{
				x-=20;
				g.setColor(Color.RED);
				g.fillOval(y,x,20,20);

			}

		}  

		g.setColor(Color.GREEN);
		g.fillRect(maze.getStartY() * 20, maze.getStartX() * 20, 20, 20); 
		g.setColor(Color.RED);
		g.fillRect(maze.getGoalY() * 20, maze.getGoalX() * 20, 20, 20); 


        
    }

	public static void drawMaze(Maze maze, char[] solution){

		
		JFrame mazeWindow = new JFrame();
		Animation panel = new Animation(maze, solution);
		

		mazeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mazeWindow.add(panel);

		mazeWindow.setSize(20*maze.getWidth(), 20*maze.getHeight() + 50);
		mazeWindow.setVisible(true);

		

	
	}
	


}


