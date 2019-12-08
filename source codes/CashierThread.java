package threadsFinal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Administrator
 */
import java.util.*;
public class CashierThread extends Thread
{
	int defaultPriority;
	static final short PAYMENT_METHOD_CASH=0;
	static final short PAYMENT_METHOD_CREDIT_CARD=1;
	static int noOfCustomersAttendedByCahier=0;
	public static long time = System.currentTimeMillis();
	int paymentMethod;
	static int totalCustomers=0;
	Queue<CustomerThread> currentCustomers=new LinkedList();
	static
	{
		totalCustomers=ThreadProject.customers.size();
	}
	CashierThread(int paymentMethod)
	{
		if(paymentMethod==CashierThread.PAYMENT_METHOD_CASH)
		{
			this.setName("Cash-Cashier");
		}
		else
		{
			this.setName("Credit-Card Cashier");
		}
		this.paymentMethod=paymentMethod;
		this.defaultPriority=this.getPriority();
		start();
	}
	    
	@Override
	public void run()
	{
	//Keep looping until all no customers entered via command line are serviced
		while(noOfCustomersAttendedByCahier<totalCustomers)
		{
			while(currentCustomers.size()==0)
			{
				suspend();//keep looping until a customer ready to be serviced is present in List [currentCustomers]
			}         
			/*
			*Service pending customers
			*/
			CustomerThread currentCustomer=currentCustomers.remove();
			msg(currentCustomer.getName()+" buying product");
			Random sleepTime=new Random();
			try
			{
				//simulate a customer being serviced. This allows for a time to pass before another customer is serviced.
				Thread.sleep(sleepTime.nextInt(3000));
				currentCustomer.setShoppingDone(true);
			}catch(Exception exception)
			{
				exception.printStackTrace();
			}
			currentCustomer.setPriority(Thread.NORM_PRIORITY);// return priority of customer back to default when customer is at cashier
			ThreadProject.customersInCafeteria.add(currentCustomer);
			noOfCustomersAttendedByCahier+=1;
		}
		msg("Wait for shop closing");
	}
	public void msg(String m) 
	{ 
		try
		{
			Thread.sleep(3000);
		}catch(Exception e){}
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	public void customerWaitToBeServiced(CustomerThread customer) throws Exception
	{
		customer.setPriority(customer.getPriority()+2);//increase customer priority
		currentCustomers.add(customer);
		resume();
	}
}
