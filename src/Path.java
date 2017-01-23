import java.awt.*;
/**
 * Path is Visual object. Size is one quarter of standard building. Visual sorts path on on bottom of stack because it has no 3 dimensional height.
 * @author Thomas
 *
 */
public class Path extends Visual{
/**
 * constructor initializes location image and value	
 * @param x xcoordinate
 * @param y ycoordinate
 */
	Path(int x, int y){
		super(x,y);
			image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Path.png"));//set single path image
			value = 100;//set price
	}
/**
 * does not rotate because same from image from all four sides
 */
	public void rotate(){}
}
