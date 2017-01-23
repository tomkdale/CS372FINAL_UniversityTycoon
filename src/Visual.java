import java.awt.Image;
import java.awt.Toolkit;
/**
 * Visual is an abstract component to be printed to screen, is sortable, movable, and placable
 * @author Thomas
 *
 */
abstract class Visual implements Comparable<Visual> {
	protected Image image;//displayed image
	protected int xPosition, yPosition;//coordinates
	protected int value = 0;//monetary value of visible item
	protected boolean isPlaced = false;//shows if item is placed or still being moved by user
/**
 * @param x sets xCoordinates
 * @param y sets yCoordinates
 */
	Visual(int x, int y){
		xPosition = x;
		yPosition = y;
	}
/**
 * used to sort objects by their y position. objects with greater y values are put later in sort, deleters are put at back of sort, paths are put in front
 */
	public int compareTo(Visual compareVisual){
		if(compareVisual instanceof Deleter)//deleters are at back because they act like a cursor
			return -1;
		else if (this instanceof Deleter)
			return 1;
		if(compareVisual instanceof Path)//paths are at front because they have no 3rd dimensional height
			return 1;
		else if (this instanceof Path)
			return -1;
		if(yPosition < compareVisual.getY())
			return -1;
		else if(yPosition > compareVisual.getY())
			return 1;
		else return 0;
	}
/**
 * set new coordinates
 * @param x xposition
 * @param y yposition
 */
	public void setPosition(int x, int y){
		xPosition = x;
		yPosition = y;
	}
/**
 * returns true if coordinates overlap
 * @param other otherObject to check postion
 * @return true if overlapped, false if not overlapped
 */
	public boolean isOverlapped(Visual other){
		if(xPosition == other.getX() && yPosition == other.getY()){
			return true;
		}
		else 
			return false;
	}
/**
 * abstract rotate funtion for objects to be rotated
 */
	abstract void rotate();
/**
 * @return x position
 */
	public int getX(){return xPosition;}
/**
 * @return y position
 */
	public int getY(){return yPosition;}
/**
 * @return Image
 */
	public Image getImage(){ return image;}
/**
 * places object on map
 */
	public void place(){isPlaced = true;}
/**
 * @return true if object is placed, false if object is not placed
 */
	public boolean isPlaced(){return isPlaced;}
/**
 * @return value of visual object
 */
	public int getPrice(){return value;}
}
