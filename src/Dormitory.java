import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Dormitory class is a Visual object. Is standard building size. Can rotate through 4 directional images
 * @author Thomas
 *
 */
public class Dormitory extends Visual{//behaves exactly like AcademicBuilding except with Dorm images
	List<Image> images = new ArrayList<Image>();
	static int iter = 0;
/**
 * constructor initializes location image and value	
 * @param x xcoordinate
 * @param y ycoordinate
 */
	Dormitory(int x, int y){
		super(x,y);
		for(int i = 0; i < 4 ; i++)
			images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Dorm" + i + ".png")));
		image = images.get(iter % 4);
		iter++;
		value = 10000;
	}
/**
 * rotate function changes image making building turn counter-clockwise
 */
	public void rotate(){
		image = images.get(iter % 4);
		iter++;
	}
	
}
