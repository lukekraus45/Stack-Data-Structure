import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Scanner;

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
 * The purpose of this program is to implement a real life example with a stack. The idea is that a shipment of bananas will be brought in and they will be unordered
 * These items need to be taken off the truck and then sorted by their expiration date. The idea of a stack makes this difficult because you can only see the top item. Also
 * effiecncy is key in this project. The employee who has to move the crates is paid per move which makes this difficult. There are different functions in the file that are read
 * in and treated differently based on what they say to do. The program will execute by reading functions from the file and applying them. For example the use function will use
 * a certain amount bananas checking the experation dates and making sure that there are still bananas in the crate.  
 * */


public class Assig2 {
	static int storeCounter;
	static Crate currCrate;
	public static void main(String[] args) {
		//initiliaze global variables
		ArrayStack storeStack = new ArrayStack(50);
		ArrayList tempStorage = new ArrayList();
		int size = 0;
		int totalCrates = 0;
		int date = 0;
		double laborCost = 0;
		double totalCost= 0;
		double totalBananaCost = 0;
		int moves = 0;
		int date2 = 0;
		double totalLaborCost = 0;
		int totalMoves = 0;
		int count =0; 
		double cost = 0;
		String filename = args[0];
		File infile = new File(filename);
		Scanner theFile = null;
		//try catch for the file being read in
		try {
			theFile = new Scanner(infile);
			
		} catch (FileNotFoundException e) {
			System.out.println("That file was not found. Please rerun the program and enter a valid filename");
			System.exit(0);
		}
	//main execution of the program. 
	//if the file runs out of lines the loop ends	
	while(theFile.hasNextLine()){
		
		String command = theFile.nextLine();
		//receive command
		//takes the crates from the truck and sorts them into the store stack
		if(command.equals("receive")){
			moves = 0;
			totalCost = 0;
			laborCost=0;
			size = Integer.parseInt(theFile.nextLine());
			totalCrates = totalCrates + size;
			System.out.println("Receiving " + size + " crates of bananas");
			System.out.println(" ");
			ArrayStack truck = new ArrayStack(size);
			ArrayStack temp = new ArrayStack();
			for(int i = 0; i < size; i++){
				date2 = Integer.parseInt(theFile.next());
				count =Integer.parseInt(theFile.next()); 
				cost = Double.parseDouble(theFile.next());
				Crate new1 = new Crate(date2,count,count, cost);
				totalCost = cost + totalCost;
				
				truck.push(new1);
				
				
			}

			
			while (!truck.isEmpty())
			{
			    
			    Crate x =  ((Crate)truck.pop());
			   
			    while(!storeStack.isEmpty() &&  ((Crate) storeStack.peek()).getExp() < x.getExp())
			    {
			        // Pop all the elements off the S2 that are 
			        // smaller than x and push them on S1
			         truck.push(storeStack.pop());   
			         moves++;
			    }
			    //push x on S2. Now x will be at its right position in sorted stack. 
			    storeStack.push(x);
			    moves++;
			}
			
		
			
			laborCost = moves * 1.0;
			totalMoves = moves + totalMoves;
			totalBananaCost = totalCost + totalBananaCost;
			totalLaborCost = laborCost + totalLaborCost;
			
	
		//report function	
		//prints a report of the most recent transaction as well as the the total expenses
		}else if(command.equals("report")){
			System.out.println("Lickety Splits Financial Statement:");
			System.out.println("\tMost Recent Shipment:");
			System.out.println("\t\tCrates: " + size);
			System.out.println("\t\tBanana Cost: " + totalCost);
			System.out.println("\t\tLabor (moves): " + moves);
			System.out.println("\t\tLabor cost: " + moves*1.0);
			System.out.println("\t\t----------------------");
			System.out.println("\t\tTotal: " + (totalCost+moves));
			System.out.println(" ");
			System.out.println("\tOverall Expenses: ");
			System.out.println("\t\tCrates: " + totalCrates);
			System.out.println("\t\tBanana Cost: " + totalBananaCost);
			System.out.println("\t\tLabor (moves): " + totalMoves);
			System.out.println("\t\tLabor cost: " + totalLaborCost);
			System.out.println("\t\t----------------------");
			System.out.println("\t\tTotal cost: " + (totalBananaCost  + totalLaborCost));
			System.out.println(" ");
		
			//display function
			//displays the stack of crates including all the info in them
		}else if(command.equals("display")){
			
			if(currCrate !=null){
				
			System.out.println("Current Crate " + currCrate.toString());
			
			}
			if(!storeStack.isEmpty()){
				System.out.println("Stack Crates (Top to Bottom)");	
				ArrayList display = new ArrayList();
				while(!storeStack.isEmpty()){	
					Crate f = ((Crate) storeStack.pop());
					System.out.println(f.toString());
					display.add(f);
				}
				for(int i = display.size()-1; i >= 0; i--){
				storeStack.push(display.remove(i));
					
				}	
			}else{
				System.out.println("No crates in the stack - please reorder!");
			}
			System.out.println(" ");
				
		//use function
		//the bananas are used and then checked to make sure the ammound being used is avaliable
		//if it is not avaliable another crate is taken from the stack	
		}else if(command.equals("use")){
			int use = Integer.parseInt(theFile.nextLine());
			System.out.println(use+" bananas needed for the order");
			try{
				if(currCrate == null){
					currCrate  = (Crate) storeStack.pop();
					}
					if(storeCounter == 0){
						
						System.out.println("Getting crate: " + currCrate.toString());
						storeCounter = currCrate.InitCount();
						currCrate.setCurrCount(storeCounter);
					}
				
					
					if(storeCounter > use){
						//between 5 bananas need for the order and 5 used from current crate
						storeCounter = storeCounter - use;
						System.out.println(use + " bananas were used from the current crate");
						System.out.println(" ");
					}else{
						
						
						if(storeStack.isEmpty()){	
							System.out.println("Out of crates");
							break;
						}
						
						//before 1 banana were used from the crate
						

						System.out.println(storeCounter + " bananas were used from the crate");
						use = use - storeCounter;
						currCrate  = (Crate) storeStack.pop();
						
						System.out.println("Getting crate " + currCrate.toString());
						storeCounter = currCrate.InitCount();
						currCrate.setCurrCount(storeCounter);
						if(storeCounter > use){
							
							storeCounter = storeCounter - use;
							//Prints out 4 bananas were used
							

							System.out.println(use + " bananas were used from the current crate");
							System.out.println(" ");
							currCrate.setCurrCount(storeCounter);
						}else{
							System.out.println(storeCounter + " bananas were used from the crate");
							use = use - storeCounter;
							
							currCrate  = (Crate) storeStack.pop();
							System.out.println("Getting crate " + currCrate.toString());
							storeCounter = currCrate.InitCount();
						
						storeCounter = storeCounter - use;
						System.out.println(use + " bananas were used from the current crate");
						currCrate.setCurrCount(storeCounter);
						System.out.println(" ");
						}
					}
						
					
				
			}catch(EmptyStackException e1){
				System.out.println("Store is out of bananas! The horror!");
			}
			

			
		//skip function	
		//skips to the next day	
		}else if(command.equals("skip")){
			
			date++;
			System.out.println("The current day is now " + date);
			boolean expired = true;
			while(expired && !storeStack.isEmpty()){
				Crate y = ((Crate)storeStack.peek());
				if(y.getExp() < date){
					System.out.println("Crate: " + y + " is expired");
					storeStack.pop();
					expired = true;
				}else{
					expired = false;	
				}
				
			}
			System.out.println(" ");
			
		}
		
		
	}
	//end of program
		System.out.println("End of Simulation");

	}

}
