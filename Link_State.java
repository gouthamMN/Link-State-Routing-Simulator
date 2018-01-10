/*
	This Program is a simulator to implement Link-state Routing Protocol. This program performs the following two functions.
	1. Simulate the process of generating forward table for each router in a given network.
	2. Computes optimal path with leat cost between any two specific routers.
	3. A user will be able to perform different tasks by slecting the options from the menu.
			Menu will have follwoing options.
				(1) Create a Network Topology.
				(2) Build a Forward Table.
				(3) Shortest Path to Destination Router.
				(4) Modify a Topology (Change the status of the Router).
				(5) Best Router for Broadcast.
				(6) Exit.
*/

//follwoing are the java libraries imported
import java.util.*;
import java.io.*;

/*
	"Routing" class impliments the follwing functions.		
		Functions:
				a. Dijkstra()
				b. find_Shortest_Path()
				c. find_Interface_Router()
*/
class Routing
{
	
	/*
		This function will be called by Dijkstra function to find the shortest path from source router to destination router.
			@param matrix: This is the matrix containg the cost data from source to destination.
			@param source_node: This is the source router from which path to destination should be returned.
			@param destination_node: This is the destination router.
			@param path_len: This is the length of route matrix from Dijkstra. 
	*/
	public static void find_Shortest_Path(int[] matrix, int source_node, int destination_node, int path_len)
	{
		int[] path_array = new int[path_len];	//delecare an array to keep all routers through which path is established
		
		path_array[0] = destination_node;	//keep destination router in the zero th index 
		int m = 1;
		
		//loop through to find and store the routers through which destination can be reached from source
		for (int i=destination_node; matrix[i] != source_node; i=i) 
		{
			path_array[m++]= i = matrix[i];		//store the intermediate router in path_array matrix
			
		}
		path_array[m] = source_node;	//intialise last index of path_array with source router
		int j = m;
		int router;
		
		//loop through path_array matrix from last index to index one to form a path
		while ( j > 0)
		{
			router = path_array[j--] + 1;
			System.out.print(" " + router + "-->");
			
		}
		
		//print the destination router at the end 
		System.out.print(" " + (destination_node + 1)) ;
	}	
	
	/*
		This function will be called by Dijkstra function to find the shortest path from source router to destination router.
			@param matrix: This is the matrix containg the cost data from source to destination.
			@param source_node: This is the source router from which path to destination should be returned.
			@param destination_node: This is the destination router.
			@param length: This is the length of route matrix from Dijkstra. 
	*/
public static String find_Interface_Router(int[] matrix, int source_node, int destination_node, int length)
{
	
	int k = 1;
	int[] node = new int[length];	//delecare an array to keep all routers through which path is established
	node[0] = destination_node;		//keep destination router in the zero th index 
	
	//loop through to find and store the routers through which destination can be reached from source
	for(int i = destination_node; matrix[i] != source_node; k++)
	{
		node[k]= i = matrix[i];		//store the intermediate router in path_array matrix
	}
	
	node[k] = source_node;	//intialise last index of path_array with source router
	int traverse=0;
	while(k > 0)
	{		
		traverse++;
		if(traverse==2)
			return "     "+(node[k]+1);
		k--;
	}

	 if (traverse==1)
	{
		 destination_node++;
		return "     "+destination_node;
	}
	 return null;
	}
	
	/*
		This function is used to find the shortest path and cost from source router to destination router.
			@param matrix: This is the original/updated network topology matrix containg the cost data.
			@param source_node: This is the source router from which path to destination should be returned.
			@param destination_node: This is the destination router.
			@param task: This tells what task to performed such as shortest path, cost between routers. 
	*/
public  Object	Dijkstra(int[][] matrix, int source_node , int destination_node ,int task)
	{
		
		int index = source_node;	//intialise index with source
		int length = matrix[0].length;	//get the row/column length of network topology matrix
		
		boolean[] array = new boolean[length];	//declare an array to indicate source(True) and other routers 
		int[] index1 = new int[length];
		
		int k = 0;
		while ( k < index1.length) 	//loop through and store the source router in the index1
		{
			index1[k] = source_node;
			k++;
		}
			
		int int_Max = Integer.MAX_VALUE;	//intialise int_Max with maximum integer value 
		int[] router = new int[length];		//array to store router details 
		int[] route = matrix[index].clone();	//copy the source router row to route array
		array[source_node] = true;
		
		//loop through the source router row to check the direct connection with other routers from source
		for(int node_count =0;node_count < length;node_count=node_count) 
		{
			
			int minimum = int_Max;
			
			for (int i = 0; i < route.length; i++) {
				
				if(!array[i])	//check whether source or other router 
				if(route[i] != -1)	//check if there exists direct connection between source and destination 
				if(i != index) {	// check whether i is equal to source or not
					int m =minimum;
					 minimum=(route[i] < m)?route[i]:minimum;	//store the cost to destination through direct connection
					 index=(route[i] < m)?i:index;	//store the router to which the direct connection exists
					 
				}
			}
			if (index == destination_node)	//checks whether source and destination are same 
			{
				break;
			}
			
			array[index] = true;	//set true to destination which has direct connection from source
			node_count++;
			int m=0;
			router[node_count] = index;
						
			while (m < route.length) {
				if(matrix[index][m] != -1)
				{
				 if((route[m] == -1 && ! array[m]) || (route[router[node_count]] + matrix[router[node_count]][m] < route[m]))
					{
						route[m] = route[router[node_count]] + matrix[router[node_count]][m];
						index1[m] = router[node_count];
					}
				}
				m++;
			}
		}
		
		//switch to a specific task that is indicated in task parameter
		switch(task)
		{
			
			case 1://returns the interface router
					return find_Interface_Router(index1, source_node, destination_node, route.length);
					
			case 2:	//prints the shortest path and total cost of the path from source to destination router
					System.out.print("The shortest path from router " + (source_node + 1) + " to router " + (destination_node + 1) + " is  ");
					find_Shortest_Path(index1, source_node, destination_node, route.length);
					int cost = route[destination_node] - route[source_node];
					System.out.println("\nThe total cost is "+ cost);
					return route[destination_node];
			case 3:	//calling bestRouter function to find the best router
					new Link_State().bestRouter(route,destination_node,source_node);
					return null;
					
			default: return null;		
					
		}
		
	}
	
}

/*
	"Link_State" class impliments the follwing functions along with "main() function".		
		Functions:
				a. ShowMenu()
				b. bestRouter()
				c. CreateNetworkTopology()
				d. Call_Algorithm()
				e. Algorithm_bestRouter()
				f. Create_Routing_Table()
				g. Prepare_Routing_Table()
				h. PrintMatrix()
				i. deleteRouter()
*/

public class Link_State 
{
	static Link_State Lstate_Obj = new Link_State();	//create Link_state object
	static Routing routing_Obj = new Routing();		//create Routing object
	static int network_matrix[][]=null;		//declare a network_matrix to store the network topology matix
	static int bestRouter[]=null;		//matrix to store the total cost of all source routers to all other destination router
	static int UpdatedNetwork_matrix[][]=null;		//To store updated network topology matrix if the any router is made down
	static boolean UpdatedNetworkMatrixStatus=false;	//To check whether the original matrix was updated or not
	public static int src_node = 0;		//make last seleceted source available globally
	public static int router_Down = 0;	//keep track of deleted router
	static int destination_node=0;		//make last seleceted destination router available globally
	
	/*
		This function display menu to user and prompts for input from user.
		based on user option respective operation will be performed
	*/
	public static void ShowMenu()throws IOException{
		int Choice;
		boolean Menu=true;
		while(Menu){
			try{
				System.out.println("-------------------------------------------------");
				System.out.println("CS542 Link State Routing Simulator\n");
				System.out.println("(1) Create a Network Topology");
				System.out.println("(2) Build a Forward Table");
				System.out.println("(3) Shortest Path to Destination Router");
				System.out.println("(4) Modify a Topology (Change the status of the Router)");
				System.out.println("(5) Best Router for Broadcast");
				System.out.println("(6) Exit");
				System.out.println("--------------------------------------------------");
				System.out.print("Master Command: ");			
				
			//take input from user, if the input is not integer then catch part will be executed 
			Choice = new Scanner(System.in).nextInt();		
			
			}catch(InputMismatchException e)
			{
				System.out.println("Please enter an correct option from menu [Between 1 to 6]");
				continue;
			}
			
			//switch to respective case based on user choice
			switch (Choice)
			   {
				case 1:	//creates a network topology and the matrix will be displayed 
						network_matrix = Lstate_Obj.CreateNetworkTopology();
						PrintMatrix(network_matrix);	//function to print network topology matrix
						break;
					
				case 2:
					//check network topology matrix created or not, if not goto menu again
					if(network_matrix==null)
					{
						System.out.println("You Must First Create a Network Topology");
						continue;	
					}
						Scanner sc = new Scanner(System.in);
						System.out.println("Select a source router: ");
						src_node =sc.nextInt(); // take source router
							
						while(src_node==router_Down || src_node >network_matrix.length ) //if source router and router down is same prompt error and takes proper input
						{
							System.out.printf("Router "+src_node+" is disabled or not in network, Choose other Router: ");
							src_node =sc.nextInt();
						}
					Lstate_Obj.Create_Routing_Table(network_matrix);	//function to create a routing table
					break;
					
				case 3:
					//check network topology matrix created or not, if not goto menu again
					if(network_matrix==null)
					{
						System.out.println("You Must First Create a Network Topology");
						continue;	
					}
					System.out.println("Select the destination router:");
					int dest = new Scanner(System.in).nextInt();
					Lstate_Obj.Call_Algorithm(network_matrix, src_node, dest);	//function to find the path and cost that again calls Dijkstra
					break;
					
				case 4:
					//check network topology matrix created or not, if not goto menu again
					if(network_matrix==null)
					{
						System.out.println("You Must First Create a Network Topology");
						continue;	
					}
					Scanner sc1 = new Scanner(System.in);
					System.out.println("\nDelete a router from this network topology.");
					int node = sc1.nextInt();
					while(node==router_Down || node >network_matrix.length ) //if source router and router down is same prompt error and takes proper input
						{
							System.out.printf("Router "+node+" is already disabled or not in network, Choose other Router: ");
							node =sc1.nextInt();
						}
					deleteRouter(network_matrix, node);	//function call to delete or down router 
					break;
				
				case 5:
					//check network topology matrix created or not, if not goto menu again
					if(network_matrix==null)
					{
						System.out.println("You Must First Create a Network Topology");
						continue;	
					}
					Lstate_Obj.bestRouter();	//function to find best router
					break;
					
				case 6:	
						System.out.println("Exit CS542-04 2017 Fall project.  Good Bye!");
						System.exit(0);	//exit the program
					
				default: System.out.println("Invalid input please try again");
				}
		}
		
	} 
	
	/*
		This function is used to find the total cost of each router 
		and prints the best router with its total cost.
	*/
public void bestRouter()throws IOException{
		
		if(UpdatedNetworkMatrixStatus)	//checks if the original matrix is updated or not
			bestRouter=new int[UpdatedNetwork_matrix.length];	//if updated, use updated matrix length
		else
			bestRouter=new int[network_matrix.length];		//else use original matrix length
		
		for(int src=1;src<=network_matrix.length;src++){	//loop through all the routers
			if(src!=router_Down)	//if source router is not equal to router that is down then enter the condtion
			{
			bestRouter[src-1]=0;	//initialise the total cost of all routers to zero first
			
			for(int dest=1;dest<=network_matrix.length;dest++){		//loop through all the destination from source
				if(dest!=router_Down & src!=dest) 		//check if destination not source and destination not deleted
				{
					if(UpdatedNetworkMatrixStatus)	//check matrix has been updated or not
					{
						Lstate_Obj.Algorithm_bestRouter(UpdatedNetwork_matrix, src, dest);	//passing updated matix
					}else
						Lstate_Obj.Algorithm_bestRouter(network_matrix, src, dest);		//passing original matrix
				}
			}
			}else
				bestRouter[src-1]=0;		// if source is deleted router then keep its total cost zero
		}
		int minimum=Integer.MAX_VALUE, best=0;	
		System.out.println("\n-------------------------------------");
		System.out.println("Router          cost");
		System.out.println("-------------------------------------");
		
		//loop through all the routers and print their respective total cost
		for(int i=0; i<bestRouter.length; i++){
			if((i+1)!=router_Down)		//if router not deleted then print its total cost
			{		
				System.out.println("  "+(i+1)+"              "+bestRouter[i]);
				if(bestRouter[i]<minimum){
					minimum=bestRouter[i];		//find best router by comparing the total cost
					best=i+1;
				}
			}
		}
		
		System.out.println("So best Router with low cost is: "+best+"\n");		//print the best router
		
	}
	
	/*
		This function will create a network topology matrix by reading the data from input file 
		and prints the network topology matrix by calling printmatrixtable().
	*/	
	public  int[][] CreateNetworkTopology() throws IOException 
	{
		
		int col = 0;	//column and row length set to zero first
		int matrix[][]=null;	//declare an array to store or create a matrix
		BufferedReader bfr=null;
		System.out.println("Input original network topology matix data file: ");
		
		String fileName = new Scanner(System.in).nextLine();
		
		try{ 
			bfr =  new BufferedReader(new FileReader(fileName));	//read the file into buffer reader
			//bfr.mark(0);
		}		
		 catch(Exception e)
		{
			System.out.println("\nEnter correct file name..!! Redirecting to Menu\n");
			ShowMenu();			//IF any exception occurs call/show menu to user
		}
		String rows=null;
		System.out.println("\nReview original topology matrix: ");
		rows= bfr.readLine();	//read first line of bufferreader into rows
		col= (rows.split("\\s")).length;	//this is the length of row and column of the network topology matrix
		
		  matrix= new int[col][col];	//a memory/array size declared
		
		for(int k=0; rows != null; k++)		//loop through all the values in each line of buffereader and store the value in matrix
			{
				String row[] = rows.split("\\s");	//split the string of line across the space
				int i = 0;
			
				while( i < col )	//loop through to store the data into matrix 
				{
					matrix[k][i] = Integer.parseInt(row[i]);	//convert string to int before storing
					i++;
				}
				rows = bfr.readLine();		//read the next line in the buffer
				
			}
					
			bfr.close();	//close the buffer reader once all the lines have been read
		
		return matrix;		//return the network topology matrix
	
	}

	/*
		This function calls Dijkstra algorithm by making sure that all source and destination exists
		and also it checks whether a path can be established or not between the source and destination
		before calling the Dijkstra
		@param: matrix[][]: is the network topology matrix
		@param: src : is the source router
		@param: dest: is the destination router
	*/
	public void Call_Algorithm (int matrix[][], int src, int dest) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		destination_node=dest;		//keep track of destination router always to know which was used last
		if(dest==src)		//if source and destination are same then a cost of 0 is displayed and return
		{
			System.out.println("The total cost is: 0");
			return;
		}
		
		while(dest==router_Down || dest > network_matrix.length )	//if the destination router is deleted then prompt user to enter new destination router
		{
			System.out.printf("Router "+dest+" is disabled or not in network, Choose other Router: ");
			dest = sc.nextInt();
		}
		
		int i=0;
		int count=0;
		while(i<matrix.length)		//loop through and keep count of how many of routers are not directly connected from source
		{
			if(matrix[src-1][i]==-1) count++;	//if cost is -1 then increment count
			i++;
		}
		
		if(count==(matrix.length)-1)	//check if there is no direct connection?
		{
			System.out.println("There is no path available from "+src+" to "+dest);		
			return;
		}
		
		routing_Obj.Dijkstra(matrix,src-1,dest-1,2);	//if there exists atleast one direct connection to any router then call the Dijkstra
		
		System.out.println("\nSelect the next destination router Or Select Zero(0) for main menu");
		dest = sc.nextInt();
		
		if(dest==0)	//if dest is zero return and display menu
			return;
		else		
			Call_Algorithm (matrix, src, dest);	//else call the function again
				
	}
	
	/*
		Print the matrix table
	*/
	public static void PrintMatrix(int x[][])
	{
		//loop through row index
		for(int m=0;m<x.length;m++)
		{
			//loop through column index
			for(int n=0;n<x.length;n++)
			{
				//print the value of the matrix
				System.out.print(x[m][n] + "\t");
			}
			System.out.println("\n");
		}
	}
	
	/*
		This function calls Dijkstra algorithm by making sure that all source and destination exists
		and also it checks whether a path can be established or not between the source and destination
		before calling the Dijkstra
		@param: matrix[][]: is the network topology matrix
		@param: src : is the source router
		@param: dest: is the destination router
	*/	
	public void Algorithm_bestRouter (int matrix[][], int src, int dest) throws IOException
	{
		int count=0;
		
		int i=0;
		while(i<matrix.length)	//loop through and keep count of how many of routers are not directly connected from source
		{
			if(matrix[src-1][i]==-1) count++;	//if cost is -1 then increment count
			i++;
		}
		
		if(count==(matrix.length)-1)	//check if there is no direct connection, if yes return 
		{
			return;
		}
		
		routing_Obj.Dijkstra(matrix,src-1,dest-1,3);	//call Dijkstra to get the total cost of routers and give the best router
	}
	
	/*
		This function stores the total cost of each router in a array
	*/
	public  void bestRouter(int[] route,int destination_node, int source_node){
		int cost = route[destination_node]-route[source_node];
		bestRouter[source_node]=bestRouter[source_node]+cost;
	}
	
	//this function calls Prepare_Routing_Table() to find the routing table
	public  void Create_Routing_Table(int matrix[][])
	{			
		Prepare_Routing_Table(matrix, src_node);		
	}
	
	/*
		This function is will create a routing table for a specified router and prints its table
		@param: matrix[][]: original network topology matrix
		@param: src: is the router to which routing table needs to be created
	*/
	public  void Prepare_Routing_Table(int matrix[][], int src)
	{
		try{
			System.out.println("\nRouter "+src +" Connection Table");
			System.out.println("Destination      Interface");
			System.out.println("===============================");
			System.out.println("       "+(src)+"            "+ " -");
			Object arr[][]=new Object[1][3];		//declare an array to store the destination router and the interface router
			src_node= src;		//keep track of source router to know which was the last router we created routing table
			
			int i=0;
			while( i<=matrix.length )	//loop through the matrix to create the the routing table by calling the Dijkstra
				{
				if(i==(src-1) || i==(router_Down-1))	//if either i/destination is source or is down increment i
				  { 
					i=i+1;
				  }
				   
					arr[0][0]=src;		//keep the source in the arr[0][0]
					arr[0][1]="       "+(i+1)+"       ";	//store every other destination router in arr[0][1]
					
					arr[0][2]= routing_Obj.Dijkstra(matrix,src-1,i,1);	//call Dijkstra to get the interface router
					System.out.println(arr[0][1]+" "+arr[0][2]);	//display the destination and interface router respectively
					
					i++;	//increment i/destination
				}
	           
			System.out.println("");
			
		}
		catch(Exception e)
		{
			System.out.println();
		}
	}
		
	/*
		This function will delete the specified router from the network topology.
		@param: matrix[][]: this is the original network topology matrix
		@param: delrouter: this is the router that needs to be deleted
	*/
	public static void deleteRouter(int matrix[][], int delrouter)
	{
	       
	        int updatedMatrix[][] = new int[matrix.length][matrix.length];	//declare an array to store updated topology
			UpdatedNetwork_matrix=updatedMatrix;	//also store updated matrix in global matrix/array
			router_Down = delrouter;	//keep track of deleted node
	        UpdatedNetworkMatrixStatus=true;	//make UpdatedNetworkMatrixStatus true to indicate that original topology has modified
			
			int i=0;
			int j=0;
			
			//loop through rows
	        while( i < matrix.length)
	        {	          
				j=0;
				//loop through columns
	            while( j < matrix.length)
	            {
					//make the cost as -1 of the router that needs to be deleted
	               updatedMatrix[i][j] = ( i == (delrouter-1) || j == (delrouter-1)) ? -1 : matrix[i][j];
	         		j++;
	            }
				i++;
	        }
	        
			i=0;
			//loop through rows
			while( i < matrix.length )
            { 
				j=0;
				//loop through columns
	        	while(j< matrix.length)
	        	{
					//store the updated matrix in the original matrix
	        		matrix[i][j]=updatedMatrix[i][j];
					j++;
				}
				i++;				
            }
	       try{
			   
			   if(src_node!=0)
					Lstate_Obj.Create_Routing_Table(updatedMatrix);		//create a routing table for last seleceted source router
				System.out.println("Network topology has been updated");
				if(destination_node!=0)					
					Lstate_Obj.Call_Algorithm(updatedMatrix, src_node, destination_node);	//also find updated path to destination from source
			}
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
	}
	
	public static void main(String[] args) throws Exception 
	{
	
		 System.out.println("\n******************CS-542 Final Project******************");
		 System.out.println("Name: Goutham Muguluvalli Niranjan");
		 System.out.println("Student ID: A20391757 \n");
	
			ShowMenu();
		
	}

	
	
}