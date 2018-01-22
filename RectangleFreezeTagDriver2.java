/*
 * Author: Cole Polyak
 * Rectangle Freeze Tag Driver v2
 * 16 November 2017
 * 
 */

//This driver creates a freeze tag game using the moving rectangles class.

//Import block.
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;


public class MovingRectangleDriver2 {
	public static final int canvas = 500;
	public static void main(String[] args) {
		
		//Canvas Setup
		StdDraw.setCanvasSize(canvas, canvas);
		StdDraw.setXscale(0, canvas);
		StdDraw.setYscale(0, canvas);
		
		//Creation of random object and rectangles array.
		MovingRectangle2[] rectangles = new MovingRectangle2[5];
		Random rng = new Random();
		
		//Enable Double Buffering to make game smoother
		StdDraw.enableDoubleBuffering();
		
		
		//Populating the array.
		for (int i = 0; i < rectangles.length; ++i) {
			int height = rng.nextInt(69) + 70;
			int width = rng.nextInt(69) + 70;
			int x = rng.nextInt(410) + (width/2) + 1;
			int y = rng.nextInt(410) + (height/2) + 1;
			int xVelocity = rng.nextInt(2) + 1;
			int yVelocity = rng.nextInt(2) + 1;
			int hitCount = rng.nextInt(3)+1;

			//Constructing Rectangle objects.
			rectangles[i] = new MovingRectangle2(x, y, width, height, xVelocity, yVelocity, hitCount);
		}
		
		//Loop for Moving Rectangles
		while(true)
		{
			int count = 0;
			StdDraw.clear();
		
			//Iterates over all Rectangles
			for(int i = 0; i < rectangles.length; ++i)
			{
				rectangles[i].update();
				
				//Checks to see if the mouse is clicked within the rectangle
				if (StdDraw.mousePressed() && rectangles[i].boxClicked(StdDraw.mouseX(), StdDraw.mouseY()))
				{
					//This loop slows down mouse input so that the hit count doesn't decrease all at once.
					while(true)
					{
						int clickStatus = 0;
						
						//Waits for mouse to be clicked
						if(StdDraw.mousePressed() && clickStatus == 0)
						{
							//Essentially pauses loop here waiting for mouse to be released.
							clickStatus = 1;
						}
						
						//Once mouse is released...
						else if(!(StdDraw.mousePressed()))
						{	
							//We can decrement the hitcount of the rectangles
							if(rectangles[i].getHitCount() > 0)
							{
								rectangles[i].decrementHitCount();
							}
						
							if(rectangles[i].getHitCount() == 0)
							{
								rectangles[i].stopRectangle();
							}
							
							//Resets clickstatus and breaks the inner while loop.
							clickStatus = 0;
							break;
						
						}
					}
				}
				
				//Count in incremented if rectangle is stopped. 
				if(!(rectangles[i].isStopped()))
				{
					++count;
					
					//Loop checks if each rectangle has collided with a stopped rectangle
					for(int j = 0; j < rectangles.length; ++j)
					{
						//Skips the rectangle if i  is the same.
						if(j == i)
						{
							continue;
						}
						else
						{
							//Unfreezes rectangle if it is collided with.
							if(rectangles[i].collidesWith(rectangles[j]))
							{
								rectangles[i].unfreeze();
							}
						}
					}
					
					//If all rectangles are stopped.
					if(count == rectangles.length)
					{
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.text(canvas/2, canvas/2, "You win!");
					}
				}
				rectangles[i].draw();
			}
			
			//Show + DoubleBuffer make the game smoother 
			StdDraw.show();
			StdDraw.pause(5);
			
		}
		
		}
	}