/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package bankersalgorithm;

import java.util.Arrays;
import java.util.Random;


class Customer implements Runnable {
    
    private Thread t;
    private String threadName = "listener thread";
    private int Customer_no;
    private int Resource_num;
    
    Customer(int Customer_no, int j)
    {
      threadName = Integer.toString(Customer_no) + '_' + Integer.toString( j);
    }
    public void run()
    {
        int customer_no = Customer_no;
        int resource_num = Resource_num;
        Random rand = new Random();// random number object
            int sleeptime = rand.nextInt(5) +1;
            try
            {
                Thread.sleep(sleeptime*1000);
            }
            catch (InterruptedException ioe)
            {}
            BankersAlgorithm Banker = new BankersAlgorithm();
            Banker.release_resources(customer_no,resource_num);
            
    }
    
    public void start()
    {
            if (t==null)
        	{
                    t = new Thread(this,threadName);
                    t.start();
                }//end of start	       
    }
        
}
public class BankersAlgorithm {

    
    public static int NUM_CUSTOMERS = 5;
    public static int NUM_RESOURCES = 3;
    public static int[][] allocated = new int[NUM_CUSTOMERS][NUM_RESOURCES]; //how many resources per customer, per resource is already allocated
    public static int[][] need = new int[NUM_CUSTOMERS][NUM_RESOURCES]; //how much more the new request can demand at max
    public static int[][] max = new int[NUM_CUSTOMERS][NUM_RESOURCES]; //Max cap on each resource demand per customer
    public static int[] available = new int[NUM_RESOURCES]; //defined in the start, the number of resources per each resource type collectively to all customers
    //public int[] allocated_total = new int[NUM_RESOURCES];
    
    public static boolean isSafe(int customer_no, int[] request)
    {
        boolean yes = true;
        for(int i =0; i<NUM_RESOURCES; i++) // check if the system would be in safe state if the request is granted
        {
            if(request[i]> need[customer_no][i])
            {
                yes = false;
                break;
            }
            if(yes && request[i]> available[i])
            {
                yes = false;
                break;
            }
        }
        return yes;
    }
    public static int request_resources(int customer_no, int[] request)
    {
        if(isSafe(customer_no,request))
        {
            for (int i =0 ; i < NUM_RESOURCES; i++)
                {
                    available[i] -= request[i];
                    allocated[customer_no][i] += request[i];
                    need[customer_no][i] -= request[i];
                    for (int j =0; j < request[i]; j++)
                    {
                        Customer cust = new Customer(customer_no, i);
                        cust.start();
                    }
                }
            return 0;
        }
        else
            return -1;
    }
    
    public static int release_resources(int customer_no, int resource_num)
    {
        
        available[resource_num] +=1;
        need[customer_no][resource_num] += 1;
        allocated[customer_no][resource_num] -= 1;
        
        return 0;
    }
 

public static void main(String[] args)
{
    for (int i = 0; i<NUM_RESOURCES; i++)
    {
        available[i] = Integer.parseInt(args[i]);
        for (int j =0; j < NUM_CUSTOMERS; j++ )
        {
            Random rand = new Random();// random number object
            max[j][i] = 10; //randomly set the maximum resource demand per customer for each resource
            allocated[j][i] = 0;  //in the beginning, all the customer start with 0 resources in each resource type
            need[j][i] = max[j][i] - allocated[j][i]; //using the formula, just as it is in the book :P
        }
        
    }

    int[] request = new int[NUM_RESOURCES];
    while(true)
    {
        Random rand = new Random();// random number object
        int customer_no = rand.nextInt(5 ); //randomly select customer
        System.out.println(customer_no);
        for ( int i = 0; i < NUM_RESOURCES; i++ )
            request[i] = rand.nextInt(4);
        System.out.println(Arrays.toString(request));
        int yes = request_resources(customer_no,request);
        if (yes ==0)
        {
            System.out.println("Allocated " + customer_no + "Resources 1 2 and 3 : " + Arrays.toString(request));
        }
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException ex)
        {}
    }
}

}
     
