import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * AcademicBuilding class is a visual object with four images showing an academic building from 4 different sides
 * @author Thomas
 *
 */
public class AcademicBuilding extends Visual{
	List<Image> images = new ArrayList<Image>();
	static int iter = 0;//iter keeps track of which image to choose next when rotated or new builing is made
/**
 * constructor initializes location image and value	
 * @param x xcoordinate
 * @param y ycoordinate
 */
	AcademicBuilding(int x, int y){
		super(x,y);
		for(int i = 0; i < 4 ; i++)//initialize images array showing four different angled building views
			images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Academic" + i + ".png")));
		image = images.get(iter % 4);//set initial image
		iter++;//increase iter for next instance to be rotated
		value = 15000;//set cost of academic building
	}
/**
 * rotate function changes image making building turn counter-clockwise
 */
	public void rotate(){
		image = images.get(iter % 4);//turn clockwise
		iter++;
	}
	
}
