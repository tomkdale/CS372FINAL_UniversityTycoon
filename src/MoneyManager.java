import java.util.Timer;
import java.util.*;
/**
 * MoneyManager acts like the bank for University Tycoon. Can add/withdraw funds, pay/take out loans, and run thread for loan interest
 * @author Thomas
 *
 */
public class MoneyManager {
	private double balance;//spendable money
	private double loan = 0;//loan amount, charges interest
	private int population =0;
	private Timer loanTimer;
/**
 * initializes balance and creates new timer to subtract loan interest and add student tuition
 * @param bal is balance user starts game with
 */
	MoneyManager(double bal){
		balance = bal;
		loanTimer = new Timer();
		loanTimer.scheduleAtFixedRate(new TimerTask() {
			public void run(){
				balance += (double)population * 200;
				balance -= loan * 0.01;
			}
			},0,10000);
	}
/**
 * buy loan increases balance and loan by 10000 as long as loans dont exceed 100000
 * @return
 */
	public boolean buyLoan(){
		if(loan <= 90000){
			loan += 10000;
			balance += 10000;
			return true;
		}
		else return false;
	}
/**
 * pay off 10000 loan as long as user has 10000 in account
 * @return true if loan was paid, false if user had insufficient funds
 */
	public boolean payLoan(){
		if(balance >= 10000 && loan > 0){
			balance -= 10000;
			loan -= 10000;
			return true;
		}
		else return false;
	}
/**
 * updates population in university
 * @param pop student population
 */
	public void updatePop(int pop){ population = pop;}
/**
 * @return balance in bank account
 */
	public int getBalance(){return (int) balance;}
/**
 * @return loan amount
 */
	public int getLoans(){return (int)loan;}
/**
 * @param money to be subtracted for purchase
 * @return true if sucsessful purchase
 * @return false if insufficient funds
 */
	public boolean purchase(int money){
		if(money > balance)
			return false;
		balance -= money;
		return true;
	}
/**
 * add money to account
 * @param money to add to account
 */
	public void addToBal(double money){balance += money;}
/**
 * subract money from account
 * @param money to take from account, even if no money is present
 */
	public void subFromBal(double money){balance -= money;}
}
