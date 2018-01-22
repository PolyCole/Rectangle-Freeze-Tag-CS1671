/*
 * Author: Cole Polyak
 * Rectangle Freeze Tag v2
 * 16 November 2017
 * 
 */

//This class creates Rectangles for a freeze tag game


//Import block
import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;

public class MovingRectangle2 {
	
	//Instance variable declaration.
	private double xVelocity, yVelocity;
	private int x, y, height, width, r, g, b, hitCount;
	private boolean moving;
	
	public MovingRectangle2(int x, int y, int width, int height, double xVelocity, double yVelocity, int hitCount) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.width = width/2;
		this.height = height/2;
		this.hitCount = hitCount;
		moving = true;
		
		//Constructor automatically randomizes colors initially.
		randomColors();
	}
	
	public void draw()
	{
		//Draws the rectangle with random color unless frozen.
		StdDraw.setPenColor(r, g, b);
		if (!(moving)) {
			StdDraw.setPenColor(StdDraw.RED);
		}
		StdDraw.filledRectangle(x, y, width/2, height/2);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(500);
		StdDraw.text(x, y, "-"+hitCount+"-");
	}
	
	//Checks if mouse clicks within rectangle
	public boolean boxClicked(double x, double y)
	{
		return ((x > getLeft()) && (x < getRight())) && 
				((y > getBottom()) && (y < getTop()));
	}

	//Makes rectangles move
	public void update()
	{
		//Added from movingRectangles so that velocity is preserved.
		if(moving)
		{
			x += xVelocity;
			y += yVelocity;			
		}
		
		//Makes rectangles "bounce" off the edge of the canvas
		//This was revised from the first version to fix the "color spasm" bug.
		if(x + width/2 >= 500) {
			//Randomizes colors with impact.
			randomColors();
			xVelocity *= -1;
			
			//This line fixes color spasm.
			x -= 1;
		} 
		else if(x - width/2 <= 0){
			randomColors();
			xVelocity *= -1;
			x += 1;
		}
		if(y + height/2 >= 500) {
			randomColors();
			yVelocity *= -1;
			y -= 1;
		}
		else if(y - height/2 <= 0){
			randomColors();
			yVelocity *= -1;
			y += 1;
		}
		
	}
	
	//Unfreezes Rectangles
	public void unfreeze()
	{
		moving = true;
		setHitCounter();
		
	}
	
	//Returns boolean based on whether rectangles collide.
	public boolean collidesWith(MovingRectangle2 other)
	{
		return getRight() > other.getLeft() && 
			   getLeft() < other.getRight() &&
			   getTop() > other.getBottom() &&
			   getBottom() < other.getTop();
	}
	
	public boolean isStopped()
	{
		return moving;
	}
	
	//Stops the rectangle.
	public void stopRectangle()
	{
		moving = false;
	}
	
	//Reverses velocity of rectangle.
	public void setVelo()
	{
		xVelocity *= -1;
		yVelocity *= -1;
	}
	
	//Randomly assigns a hitCounter from 1-3.
	public void setHitCounter()
	{
		Random rng = new Random();
		hitCount = rng.nextInt(3)+1;
	}
	
	//Decreases hitCount.
	public void decrementHitCount()
	{
		--hitCount;
	}
	
	//Getters.
	public int getLeft()
	{
		return x - (width/2);
	}
	public int getRight()
	{
		return x + (width/2);
	}
	public int getTop()
	{
		return y + (height/2);
	}
	public int getBottom()
	{
		return y - (height/2);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public int getHitCount() {
		return hitCount;
	}
	
	//Randomizes rectangle color.
	public void randomColors()
	{
		Random rng = new Random();
		r = rng.nextInt(255) + 1;
		g = rng.nextInt(255) + 1;
		b = rng.nextInt(255) + 1;
	}
		
}	
