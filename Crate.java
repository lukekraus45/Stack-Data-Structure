/*
 * 
 * 
 * 
 * Luke Kraus
 * CS  0445
 * John Rameriez
 * February 10, 2015
 * Tues/Thurs 9:30-10:45
 * 
 * 
 * The purpose of this class is to allow impliment the main program in an object oriented way. This class creats the crate object that stores each of the different bananas.
 * The information is read into a file and then turned into crate objects that have the various values bellow. This class also allows the values (exp. date) of two crates
 * to be compared.
 * 
 * 
 * */


public class Crate implements Comparable<Crate> {
	
	private int d;
	private int c1;
	private int r;
	private double c2;
	
	public Crate(int date,int count, int remain, double cost )
	{
		d = date;
		c1 = count;
		r = remain;
		c2 = cost;
		
	}
	public int compareTo(Crate rhs) {
		 
		if (this.d == rhs.d)
            return 0;
        else if ((this.d) > rhs.d)
            return 1;
        else
            return -1;
	}
	//to string method
	public String toString()
	{
		return ("Expires:" + d + " \tStart Count:"+ c1 + " \tRemain:"+ r + " \tCost:"+ c2);
	}

	//experation date
	
	public int getExp(){
		
		return d;
		
	}
	
	//initial bannana count
	
	public int InitCount(){
		return c1;
	}
	

	//current bananna count 
	
	public int Currentcount(){
		
		return r;
	}
	
	public void setCurrCount(int curr){
		r = curr; 
	}
	//cost for the entire crate
	
	private double totalCost(){
		
		return c2;
		
	}

	

}
