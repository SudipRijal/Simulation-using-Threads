package threadsFinal;


import java.util.*;
public class FloorClerk extends Thread
	{
	static int noOfAssistedCustomers=0;
	static int totalCustomers;
	public static long time = System.currentTimeMillis();
	public Queue<CustomerThread> currentCustomers=new LinkedList();
	int currentIndex=0;
	//To-Do:find a better way to initialise variable [totalCustomers]
	static
	{
		totalCustomers=ThreadProject.customers.size();
	}
	FloorClerk(String name)
	{
		super(name);
	}
	@Override
	public void run()
	{
		CustomerThread currentCustomer;
		//Keep looping until all no customers entered via command line are serviced
		while(noOfAssistedCustomers<totalCustomers)
		{
			while(currentCustomers.peek()==null)
			{
				suspend();//keep looping until a customer ready to be serviced is present in List [currentCustomers]
			}
			/*
			*Service pending customers
			*/
			Random sleepTime=new Random();
			currentCustomer=currentCustomers.remove();
			msg(currentCustomer.getName()+" Waiting to be serviced by Clerk");
			try
			{
			//simulate a customer being serviced. This allows for a time to pass before another customer is serviced.
				Thread.sleep(sleepTime.nextInt(6000));
				currentCustomer.setShoppingId(UUID.randomUUID().toString());
			}catch(Exception exception){}
			//msg(currentCustomers.get(currentIndex).getName()+" ");
			//put Customer in appropriate cashier queue
			try
			{ 
				if(currentCustomer.paymentMethod==CashierThread.PAYMENT_METHOD_CASH)
				{
					ThreadProject.cashCashier.customerWaitToBeServiced(currentCustomer);
				}
				else
				{
					ThreadProject.creditCardCashier.customerWaitToBeServiced(currentCustomer);
				}
			}catch(Exception exception)
			{
				exception.printStackTrace();
			}
			//add customers to customers for joining
			CustomerThread.customers.add(currentCustomer);
			//clear customer from clerk queue
			noOfAssistedCustomers+=1;
		}
		msg(" waiting for store to close");
		for(CashierThread ct:ThreadProject.cashiers)
		{
				ct.resume();
			try
			{
				ct.join();
			}catch(Exception e){}
		}
	}
	public void customerWaitToBeServiced(CustomerThread customer)
	{
		synchronized(currentCustomers)
		{
			currentCustomers.add(customer); 
		}
		resume();
	}
	public void msg(String m) 
	{ 
	try
	{
		Thread.sleep(3000);
	}catch(Exception e){}
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
}
 
