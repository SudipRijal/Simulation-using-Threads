package threadsFinal;


import java.util.*;

public class ThreadProject 
{
	static CashierThread creditCardCashier;
	static CashierThread cashCashier;
	static List<CashierThread> cashiers;
	static List<FloorClerk> floorClerks;
	static List<CustomerThread> customers;
	static List<CustomerThread> customersInCafeteria=new LinkedList<>();
	public static void main(String[] arg) 
	{
		int noOfCustomers=12;  //Default 
		/*
		*make sure no or only one argument is given via the command line.
		*make sure argument is an integer.
		*/
		try
		{
			if(arg.length>1)
			{
				throw new Exception();
			}
			else if(arg.length==1)
			{
				noOfCustomers=Integer.parseInt(arg[0]);
			}
			customers=new ArrayList();
			for(int i=1;i<=noOfCustomers;i++)
			{
				CustomerThread customer=new CustomerThread(""+i,new Random().nextInt(2));
				customers.add(customer);
			}
			cashCashier=new CashierThread(CashierThread.PAYMENT_METHOD_CASH);
			creditCardCashier=new CashierThread(CashierThread.PAYMENT_METHOD_CREDIT_CARD);
			cashiers=new ArrayList(Arrays.asList(cashCashier,creditCardCashier));
			//Create 3 Floor Clerks
			floorClerks=new ArrayList(Arrays.asList(new FloorClerk("Clerk 1"),new FloorClerk("Clerk 2"),new FloorClerk("Clerk 3")));
			/*
			 *make floor clerks wait for customers
			*/
			floorClerks.get(0).start();
			floorClerks.get(1).start();
			floorClerks.get(2).start();
			            
			//customer is randomly put to wait for a clerk
			Random rand=new Random();
			CustomerThread customer;
			while(true)
			{
				if(customers.size()==0)
				{
					break;
				}
				for(int i=0;i<customers.size();i++)
				{
					customer=customers.get(i);
					if(customer.wantToPurchase())
					{
						floorClerks.get(rand.nextInt(3)).customerWaitToBeServiced(customer);
						customers.remove(i);
					}
				}
			}
			//oderly leaving of customer
			while(true)
			{
				if(noOfCustomers==customersInCafeteria.size())
				{
					Collections.sort(customersInCafeteria,new Comparator<CustomerThread>(){
						public int compare(CustomerThread left,CustomerThread right)
						{
							return Integer.parseInt(left.getName().substring(8).trim())-Integer.parseInt(right.getName().substring(8).trim());
						}
					});
					Thread.sleep(30000);
					CustomerThread ct;
					ct=customersInCafeteria.remove(0);
					ct.msgToLeave("I am leaving");
					int count=1;
					for(int i=0;i<customersInCafeteria.size();count++)
					{
						ct=customersInCafeteria.remove(i);
						ct.msgToLeave("I joined customer "+count);
					}
					System.exit(0);
				}
				else
				{
				Thread.sleep(2000);
				}
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}   
}
