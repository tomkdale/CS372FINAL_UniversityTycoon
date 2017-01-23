import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
/**
 * Cafeteria is Visual object that is three times the size of a standard building
 * @author Thomas
 *
 */
public class Cafeteria extends Visual{//behaves like AcademicBuilding but with cafeteria image and modified isOverlapped method for larger surface area
	List<Image> images = new ArrayList<Image>();
	static int iter = 0;
/**
 * constructor initializes location image and value	
 * @param x xcoordinate
 * @param y ycoordinate
 */
	Cafeteria(int x, int y){
		super(x,y);
		for(int i = 0; i < 4 ; i++)
			images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Cafeteria" + i + ".png")));
		image = images.get(iter % 4);
		iter++;
		value = 25000;
	}
/**
 * rotate function changes image making building turn counter-clockwise
 */
	public void rotate(){
		image = images.get(iter % 4);
		iter++;
	}
/**
 * isOverlapped has been modified for the larger area taken by cafeteria
 * @return boolean true if overlapped false if not
 */
	public boolean isOverlapped(Visual other){
		Rectangle x = new Rectangle(xPosition,yPosition,180,60);//set rectangle over base of building
		if(x.contains(other.getX(), other.getY()))//check if other coprdinates are within this cafeterias base
			return true;
		return false;
	}
}
