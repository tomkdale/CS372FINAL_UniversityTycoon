import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * Main object holds controls screen layout, menu organization and calls simulator object 
 * @author Thomas Dale
 *
 */
public class Main {
	//Instructions to be displayed in top right of screen
	private String howToString = 
			"<html>Welcome to University Tycoon<br><br>Purchase buildings by clicking a button on the lefthand menu."
			+ " While a building is selected right click to rotate and edit style. Left click to place building."
			+ " Warning, once buildings are placed they cannot be moved, only deleted."
			+ " Use the delete objects button to remove items for a small refund."
			+ " Take out and pay loans in quanties of $10,000. Loans charge 0.10% interest each year and cannot exceed $100,000."
			+ " Once there is space to sleep, eat, and learn students will start coming to the university."
			+ " Scenery and extra buildings will increase the popularity of the school."
			+ " Students will pay regular tuition increasing your account balance."
			+ " Buildings cost some upkeep, and banks charge interest on loans, be careful not to go bankrupt!"
			+ " Create a beautiful campus and your university will become popular in no time.</html>";
	private JFrame screen;//entire screen buttons, labels, and gamescreeen
	private JLabel gameInfo,howTo,updates;//various on screen information labels
	private MapDraw gameScreen;//screen on which game display map is shown
	public static void main(String[] args){
		Main game = new Main();
	}
/**
 * constructor initializes frame and settings then calls newGame function	
 */
	Main(){
		screen = new JFrame();
		screen.setLayout(new BorderLayout());//set borderlayout to arrange gamescreen,labels, and buttons on
		screen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    	screen.setExtendedState(JFrame.MAXIMIZED_BOTH);//maximize screen on startup
    	screen.setVisible(true);
    	newGame();
		
	}
/**
 * new game sets buttons labels and initializes game	
 */
	private void newGame(){
		JPanel infoRight = new JPanel();//create panel for how to and updates
		infoRight.setLayout(new GridLayout(2,1));
		howTo = new JLabel(howToString);//how to displays long explination of how to play
		howTo.setPreferredSize(new Dimension(200,600));
		updates = new JLabel("",SwingConstants.CENTER);//updates in initialized to blank screen, will be used by MapDraw to show errors
		updates.setPreferredSize(new Dimension(200,600));;
		updates.setForeground(Color.RED);//set updates color to red because errors are bad
		infoRight.add(howTo);
		infoRight.add(updates);
		screen.add(infoRight,BorderLayout.EAST);//put infoRight on right side of screen
		
		gameInfo = new JLabel("",	SwingConstants.CENTER);//game info controlled by MapDraw and located at bottom of screen
		gameScreen = new MapDraw(gameInfo,updates);
		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(8,2));//menu holds 10 buttons plus spacing
		//create button for each object to be selected or function to be done and add action listeners calling MapDraw
		JButton buyDorm = new JButton("Add Residence Hall: -$" + new Dormitory(0,0).getPrice());
		buyDorm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addDorm();
			}
		});
		JButton buyAcademic = new JButton("Add Academic Building: -$" + new AcademicBuilding(0,0).getPrice());
		buyAcademic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addAcademicBuilding();
			}
		});
		JButton buyStadium = new JButton("Add Stadium: -$" + new Stadium(0,0).getPrice());
		buyStadium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addStadium();
			}
		});
		JButton buyCafeteria = new JButton("Add Dining Hall: -$" + new Cafeteria(0,0).getPrice());
		buyCafeteria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addCafeteria();
			}
		});
		JButton buyFacilities = new JButton("Add Facilities Building: -$" + new Facilities(0,0).getPrice());
		buyFacilities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addFacilities();
			}
		});
		JButton buyPath = new JButton("Add Path: -$" + new Path(0,0).getPrice());
		buyPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addPath();
			}
		});
		JButton buyScenery = new JButton("Add Scenery: -$" + new Scenery(0,0).getPrice());
		buyScenery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.addScenery();
			}
		});
		JButton deleteStuff = new JButton("Delete Objects");
		deleteStuff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameScreen.deleteStuff();
			}
		});
		JButton getLoan = new JButton("Take Loan: +$10000");
		getLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.buyLoan();
			}
		});
		JButton payLoan = new JButton("Pay Loan: -$10000");
		payLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameScreen.payLoan();
			}
		});
		//add Build menu title and all buttons to menu jpanel
		menu.add(new JLabel("Build Menu:",SwingConstants.RIGHT));
		menu.add(new JLabel(""));
		menu.add(buyDorm);
		menu.add(buyAcademic);
		menu.add(buyCafeteria);
		menu.add(buyStadium);
		menu.add(buyPath);
		menu.add(buyFacilities);
		menu.add(buyScenery);
		menu.add(deleteStuff);
		menu.add(getLoan);
		menu.add(payLoan);
		screen.add(menu, BorderLayout.WEST);//add button menu on left screen
		screen.add(gameScreen,BorderLayout.CENTER);//add game screen in center
		screen.add(gameInfo,BorderLayout.SOUTH);//add game info on bottom of screen
		gameInfo.setFont(new Font("Serif",Font.BOLD,50));//set fonts for jlabels
		updates.setFont(new Font("Serif",Font.BOLD,40));
		
	}
	
}
