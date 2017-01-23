import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
/**
 * MapDraw draws and controls Visible objects such as buildings and people as well as communicates with date and money managers
 * @author Thomas
 *
 */
public class MapDraw extends JComponent implements MouseMotionListener, MouseListener{
	private ArrayList<Visual> visuals = new ArrayList<Visual>();//all visuals held in array, sorted by y values
	private Visual selected;//specific visual selected
	private JLabel gameInfo;//game info from caller to show money loan students and date
	private JLabel dialog;//shows user errors
	private BufferedImage noCursorImg = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);//creates blank cursor image to used when moving objects
	private Image pencil= Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Pencil.png"));//creates pencil cursor
	private Cursor pencilCursor = Toolkit.getDefaultToolkit().createCustomCursor(pencil,new Point(0,0), "pencil cursor");//create blank cursor
	private Cursor noCursor = Toolkit.getDefaultToolkit().createCustomCursor(noCursorImg,new Point(0,0), "blank cursor");//create pencil cursor
	private int mouseX, mouseY;//coordinates of mouse
	private Rectangle sSize;//screen size
	private boolean deleteMode;// enabled when user is deleting something
	private MoneyManager account;//account holding money and loans
	private int population = 0;//number of students
	private int dormBlocks = 0;//number of dormitories
	private int academicBlocks = 0;//number of academic buildings
	private int cafeteriaBlocks = 0;//number of cafeterias
	private int sceneryBlocks = 0;//scenery weighted value total
	private int sceneryBlocksMax = 10;//total scenery value before new facilities building is needed
	private Date now = new Date();//date holds month and year
	private Random rand = new Random();
/**
 * Initializes money account, gameInfo and dialog labels, screen size, mouse motion listeners nad mouse listeners, sets cursor,
 * and creates two new timer threads. one updates new students and calls repaint every 50milliseconds,
 * the other time sets dialog text to show potential problems and updates building maintainance costs each 4seconds.
 * @param gameInfo jlabel to show money, loans, population, and date
 * @param dialog to show user error messages
 */
	MapDraw(JLabel gameInfo,JLabel dialog){
		account = new MoneyManager(0);//start user off with 0$ in bank account
		this.gameInfo = gameInfo;
		this.dialog = dialog;
		sSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();//set screen size
;		addMouseMotionListener(this);
		addMouseListener(this);
		selected = null;//user has not selected anything yet
		deleteMode = false;//delete mode is off
		this.setCursor(pencilCursor);//set cursor to fun pencilCursor
		Timer drawTimer = new Timer();
		drawTimer.scheduleAtFixedRate(new TimerTask() {
			public void run(){//enroll students, tell account new student amount, repaint screen
					enrollStudents();
					account.updatePop(population);
					repaint();
				}
		}, 0, 50);
		Timer printTimer = new Timer();
		printTimer.scheduleAtFixedRate(new TimerTask() {
			public void run(){
				dialog.setText("");//set text for 4 seconds for user to see what they did wrong
				account.subFromBal((dormBlocks * 2 + cafeteriaBlocks * 4 + academicBlocks * 8));//take out money for building maintanance
			}
		}, 0, 4000);
	}
/**
 * sort visuals by y coordinate, paint visuals to screen, and update gameInfo	
 */
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(0,120,0));//sets background color to dark green
		g2.fillRect(0,0, (int) sSize.getWidth(), (int)sSize.getHeight());
		Collections.sort(visuals);//sorts all visuals by y coordinates
		for(int i = 0; i < visuals.size(); i++){//show all visuals
			g2.drawImage(visuals.get(i).getImage(), visuals.get(i).getX(),visuals.get(i).getY(), this);
		}
		//update game info showing money loans students and date
		gameInfo.setText("Balance: $" + account.getBalance() + "      Current Loans: $" + account.getLoans() + "      Students Enrolled: " + population + "     " + now.getDate());
	}
/**
 * rotate if right mouse button is clicked, delete item if deleter is being used, otherwise place selected item
 * used rather than mouse clicked for more precise user selection
 * @param MouseEvent e, tells where and which button on mouse was pressed	
 */
	public void mouseReleased(MouseEvent e) {
		try{//try to do things to selected if not selected catch null pointer
			if(SwingUtilities.isRightMouseButton(e)){//rotate if right mouse button clicked
				selected.rotate();
			}
			else{//left mouse button clicked
				if(selected instanceof Deleter ){//if deleting something
					for(int i = 0; i < visuals.size() -1 ; i++){//check all objects to see if cursor is over top
						if(visuals.get(i).isOverlapped(selected)){
							account.addToBal(visuals.get(i).getPrice()* 0.6);//add portion of building value
							visuals.remove(i);//remove building
							visuals.remove(selected);//remove deleter
							selected = null;// remove selected
						}
					}
				}
				else{//if placing something
					for(int i = 0; i < visuals.size(); i++){//check location of all other objects
						if(visuals.get(i) != selected && visuals.get(i).isOverlapped(selected)){
							return;//if other object present dont place overtop
						}
					}
					selected.place();//if clear build the building
					selected = null;
					this.setCursor(pencilCursor);//put cursor back to pencil
				}
			}
		}
		catch(Exception exc){
			//selected is equal to null, do nothing
		}
	}
/**
 * called when mouse is moved move path and scenery on small grid, other objects on big grid
 * @param MouseEvent e shows new coordinates of mouse
 */
	public void mouseMoved(MouseEvent e) {
		try{//if selected != null
			if(selected instanceof Path || selected instanceof Scenery){//small objects snap to small grid
				int graphOffsetX = 0;//edit coordinates to snap to small grid 30x15 pixels
				int graphOffsetY = 0;
				if(e.getY() / 15 % 2 == 0)
					graphOffsetX = 1;
				if(e.getX() / 30 % 2 == 0)
					graphOffsetY =1;
				mouseX = (e.getX() / 30 + graphOffsetX )*30;
				mouseY = (e.getY() / 15 + graphOffsetY )*15;
				selected.setPosition(mouseX,mouseY);
			}
			else{//medium and big objects use medium grid
				int graphOffsetX = 0;//edit coordinates to snap to medium grid 60x30 pixels
				int graphOffsetY = 0;
				if(e.getY() / 30 % 2 == 0)
					graphOffsetX = 1;
				if(e.getX() / 60 % 2 == 0)
					graphOffsetY =1;
				mouseX = (e.getX() / 60 + graphOffsetX )*60-60;
				mouseY = (e.getY() / 30 + graphOffsetY )*30-60;
				selected.setPosition(mouseX,mouseY);
			}
		}
		catch(Exception exc){
			//selected is equal to null, do nothing
		}
	}
/**
 * creates new dorm to be placed by user at desired position
 */
	public void addDorm(){
		if(sceneryBlocksMax <= sceneryBlocks)//if scenery still has not reached max value place object
			dialog.setText("<html>Not enough<br>facilities workers<br>to upkeep<br>scenery</html>");
		if(account.purchase(new Dormitory(0,0).getPrice())){//if new dorm can be bought
			this.setCursor(noCursor);
			selected = new Dormitory(0,200);//select a new dorm object
			visuals.add(selected);
			dormBlocks++;
			sceneryBlocks += 2;//worth two scenery blocks
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * creates new AcademicBuilding to be placed by user at desired position
 */
	public void addAcademicBuilding(){
		if(sceneryBlocksMax <= sceneryBlocks)//if scenery still has not reached max value place object
			dialog.setText("<html>Not enough<br>facilities workers<br>to upkeep<br>scenery</html>");
		if(account.purchase(new AcademicBuilding(0,0).getPrice())){
			this.setCursor(noCursor);
			selected = new AcademicBuilding(0,200);
			visuals.add(selected);//select new academic building
			academicBlocks++;
			sceneryBlocks += 2;// worth two scenery blocks
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * creates new stadium to be placed by user at desired position
 */
	public void addStadium(){
		if(sceneryBlocksMax <= sceneryBlocks)//if scenery still has not reached max value place object
			dialog.setText("<html>Not enough<br>facilities workers<br>to upkeep<br>scenery</html>");
		if(account.purchase(new Stadium(0,0).getPrice())){
			this.setCursor(noCursor);
			selected = new Stadium(0,200);
			visuals.add(selected);//select new stadium
			sceneryBlocks += 15;//worth 15 scnery blocks
			sceneryBlocksMax += 5;//raise max for high scenery value to mainance
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * creates new cafeteria to be placed by user at desired position
 */
	public void addCafeteria(){
		if(sceneryBlocksMax <= sceneryBlocks)//if scenery still has not reached max value place object
			dialog.setText("<html>Not enough<br>facilities workers<br>to upkeep<br>scenery</html>");
		if(account.purchase(new Cafeteria(0,0).getPrice())){
			this.setCursor(noCursor);
			selected = new Cafeteria(0,200);
			visuals.add(selected);//select new cafeteria
			cafeteriaBlocks++;
			sceneryBlocks++;//worth one scenery block
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * creates new facilities building to be placed by user at desired position
 */
	public void addFacilities(){
		if(sceneryBlocksMax <= sceneryBlocks)//if scenery still has not reached max value place object
			dialog.setText("<html>Not enough<br>facilities workers<br>to upkeep<br>scenery</html>");
		if(account.purchase(new Facilities(0,0).getPrice())){
			this.setCursor(noCursor);
			selected = new Facilities(0,300);//select new facilities
			visuals.add(selected);
			sceneryBlocksMax += 10;//increase scenery blocks maximum value
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * creates new Path to be placed by user at desired position
 */
	public void addPath(){
		if(account.purchase(new Path(0,0).getPrice())){
			this.setCursor(noCursor);
			selected = new Path(0,300);
			visuals.add(selected);//select new path
			//path has no inherit value really, eventually it would be cool to make buildings require path connections, but not for now
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * add scenery where user wants
 */
	public void addScenery(){
		if(sceneryBlocksMax <= sceneryBlocks)//if scenery still has not reached max value place object
			dialog.setText("<html>Not enough<br>facilities workers<br>to upkeep<br>scenery</html>");
		else if(account.purchase(new Scenery(0,0).getPrice())){
			this.setCursor(noCursor);
			selected = new Scenery(0,300);//select new scenery
			visuals.add(selected);
			sceneryBlocks++;//scenery worth one scenery block
		}
		else
			dialog.setText("<html>Not<br>Enough<br>Cash!</html>");
	}
/**
 * delete items by creating a gridsnapping cursor 
 */
	public void deleteStuff() {
		deleteMode = true;//turn on delete mode
		this.setCursor(noCursor);//remove standard cursor
		selected = new Deleter(0,400);//set deleting cursor object
		visuals.add(selected);
	}
/**
 * if deleteMode is on remove deleter from screen and turn off delete mode
 */
	public void mouseExited(MouseEvent e) {
		if(deleteMode){
			visuals.remove(selected);
			deleteMode = false;
		}
	}
/**
 * take loan or warn that no more loans can be taken
 */
	public void buyLoan(){
		if(!account.buyLoan())//take out loan of give warning
			dialog.setText("<html>Bank has denied<br> loan above $100000</html>");
	}
/**
 * pay loan if funds are sufficient, give user error if insufficient funds or no loans
 */
	public void payLoan(){
		if(account.getBalance() < 10000)
			dialog.setText("<html>Not enough<br>cash to<br>pay back loan</html>");
		else if(!account.payLoan())
			dialog.setText("<html>No Loans<br>to pay!</html>");
	}
/**
 * calculate maxCapacity and randomly add students if population under max capacity
 */
	private void enrollStudents() {
		int maxCapacity;//student max capacity controls student attendance
		int[] blocks = new int[3];//blocks sorts out the needed school building by weighted points
		blocks[0] = dormBlocks * 10;
		blocks[1] = academicBlocks * 15;
		blocks[2] = cafeteriaBlocks * 40;
		Arrays.sort(blocks);//find weighted building type with fewest buildings
		if(blocks[0] == 0)//if one of each of the essential buildings is not built, do students come
			return;
		maxCapacity = blocks[0] + (sceneryBlocks/2);//max capacity is a function of the number of fewest essential buildings and scenery objects / 2
		int odds = maxCapacity - population;//odds is the amount of students that could still fit in the school
		if(rand.nextInt(300) < odds){//new students arrive more frequently when capacity is far from reached
			population++;
			if(population % 4 == 1)//one in four students are represented by student images
				visuals.add(new Person(rand.nextInt(900)+200,900));
			return;
		}
	}

	public void mouseDragged(MouseEvent e) {}//useless stubs for mouse listeners
	public void mousePressed(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
