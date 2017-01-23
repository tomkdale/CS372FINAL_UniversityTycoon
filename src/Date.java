/**
 * Date class to be used in Univesity Tycoon
 * @author Thomas
 *
 */
public class Date {
	String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	int iter = 0;
	int jiffy = 0;
	int year = 2017;
/**
 * sole method of Date, increments unit of time "jiffy", 200 jiffys = 1 month
 * @return string showing month and year, starting Jan 2017
 */
	public String getDate(){
		jiffy++;
		if(jiffy % 200 == 0)//200 jiffies means the end of the month has come
			iter++;
		if(iter == 12){//12 months means the end of the year has come
			iter = 0;
			year++;
		}
		return month[iter] + " " + year;//return month and year, but not jiffy time
	}
}
