package threadsFinal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class CustomerThread extends Thread
{
	static List<CustomerThread> customers=new ArrayList();
	public static long time = System.currentTimeMillis();
	int paymentMethod;
	int nameInt;
	private String shoppingId;
	private boolean shoppingDone;
	private boolean wantToPurchase;
	CustomerThread(String name,int paymentMethod)
	{
		super("Customer "+name);
		this.nameInt=Integer.parseInt(name);
		this.paymentMethod=paymentMethod;
		this.shoppingId=null;
		this.shoppingDone=false;
		this.wantToPurchase=false;
		start();
	}
	@Override
	public void run()
	{
	/*
	 *Simulate Customer browsing around store 
	*/
		msg("browsing store");
		Random sleepTime=new Random();
		try
		{
			Thread.sleep(sleepTime.nextInt(3000));
		}catch(InterruptedException interruptedException){}
		/*
		*Simulate Customer being undecided
		*/
		msg("deciding whether to pick product or not");
		Thread.yield();
		msg("still deciding whether to pick product or not");
		Thread.yield();
		//keep looping until all customers have purchursed product
		this.wantToPurchase=true;
		while(this.shoppingId==null && !shoppingDone)suspend();
		msg("my shopping id is ["+this.shoppingId+"] "+"I have purchased item and i am going to cafeteria");
		if(CashierThread.noOfCustomersAttendedByCahier!=FloorClerk.totalCustomers)
		{
			suspend();
		}
	}
	synchronized public void msg(String m) 
	{
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
		try
		{
			Thread.sleep(6000);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean wantToPurchase()
	{
		return this.wantToPurchase;
	}
	public void setShoppingId(String shoppingId)
	{
		this.shoppingId=shoppingId;
	}
	public void setShoppingDone(boolean shoppingDone)
	{
		this.shoppingDone=shoppingDone;
		resume();
	}
	synchronized public void msgToLeave(String m) 
	{
		resume();
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}

}
