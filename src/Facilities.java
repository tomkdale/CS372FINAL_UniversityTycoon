import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
/**
 * Facilities is a Visual object. Has standard building size. Can rotate between 4 directional images.
 * @author Thomas
 *
 */
public class Facilities extends Visual {//behaves exactly like AcademicBuilding except with MaintananceShed images
	private ArrayList<Image>images = new ArrayList<Image>();
	private static int iter = 0;
/**
 * constructor initializes location image and value	
 * @param x xcoordinate
 * @param y ycoordinate
 */
	Facilities(int x, int y){
		super(x,y);
		for(int i = 0; i < 4 ;i++)
			images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/MaintananceShed" + i + ".png")));
		image = images.get(iter % 4);
		iter++;
		value = 5000;
	}
/**
 * rotate function changes image making building turn counter-clockwise
 */
	public void rotate(){
		image = images.get(iter % 4);
		iter++;
	}
}
