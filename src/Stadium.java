import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * Stadium is Visual object. Is four times size of standard building.
 * @author Thomas
 *
 */
public class Stadium extends Visual{//Stadium acts exactly like AcademicBuilding but with Stadium images.
	List<Image> images = new ArrayList<Image>();
	int iter = 0;
/**
 * constructor initializes location image and value	
 * @param x xcoordinate
 * @param y ycoordinate
 */
	Stadium(int x, int y){
		super(x,y);
		for(int i = 0; i < 4 ; i++)
			images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Stadium" + i + ".png")));
		image = images.get(0);
		value = 50000;//set value(most expensive building avaliable)
	}
/**
 * rotate function changes image making building turn counter-clockwise
 */
	public void rotate(){
		iter++;
		image = images.get(iter % 4);
	}
/**
 * isOverlapped has been modified for the larger area taken by stadium
 * @return boolean true if overlapped false if not
 */
	public boolean isOverlapped(Visual other){
		Rectangle x = new Rectangle(xPosition-60,yPosition,270,180);//create rectangle covering stadium base
		if(x.contains(other.getX(), other.getY()))//check if other object's coordinates are within the stadium
			return true;
		return false;
	}
}
