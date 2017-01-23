import java.awt.Toolkit;
/**
 * Deleter is special instance of Visual which will not be placed but used to control a deleting cursor that snaps to grid
 * @author Thomas
 *
 */
public class Deleter extends Visual {
/**
 * constructor initializes location and image value stays = 0
 * @param x xcoordinate
 * @param y ycoordinate
 */
	Deleter(int x, int y){
		super(x,y);
		image = (Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Delete.png")));//set delete cursor image
	}
/**
 * deleter does not use necessary Visual object abstract rotate method
 */
	public void rotate(){}
}
