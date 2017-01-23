import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
/**
 * Scenery class is extends Visual. Size is one quarter of standard building. Rotate function switches through scenery types
 * @author Thomas
 *
 */
public class Scenery extends Visual{
	ArrayList<Image> images = new ArrayList<Image>();//holds image instances
	static int iter;//keeps track of "rotate" which in this case changes image between types of scenery
/**
 * create new scenery object can be tall tree, bush, dead tree, or statue
 * @param x xCoordinates
 * @param y yCoordinates
 */
	Scenery(int x, int y) {
		super(x, y);
		images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/TallTree.png")));
		images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/DeadTree.png")));
		images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Bush.png")));
		images.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Statue.png")));
		image = images.get(iter % 4);//set initial image
		iter++;//increment iter so next instance is new image
		value = 200;
	}
/**
 * rotate function changes image between tall tree, dead tree, bush, and statue
 */
	public void rotate(){
		image = images.get(iter % 4);
		iter++;
	}
}
