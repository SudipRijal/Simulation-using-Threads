# Simulation-using-Threads

                                           Simulating Shopping at the Mega store

A customer walks into a Mega showroom and browses around the store for a while ((simulated by sleep(random time)) before (s)he finds an item (s)he likes. When a customer does so, (s)he might be a bit undecided if (s)he should buy or not purchase the item (simulate this with yield() twice). Once decided, the customer must go to the floor-clerks and receive a slip with which (s)he can pay for the item and then take it home. 

Floor clerks wait (by doing busy waiting) for customers to arrive; then, they help them (in a FCFS order) with whatever information they need. However, floor clerks can only help one customer at a time; therefore, a customer must wait (busy waiting on a shared variable) for an available clerk to help him/her. 

After all of the customers are assisted (you should keep track of the number of assisted customers), the floor clerks wait for closing time (sleep(of a long time)).

 Once a customer gets the information needed from the floor clerk, (s)he will go to the cashier to pay for the item. To do so, (s)he will increase his/her priority (use getPriority( ), sleep( of short time), setPriority( )). Once the customer is at the cashier, the customer will reset his/her priority back to the default value. 

There are two cashiers. One takes only cash, the other only credit cards. The customer will decide in which way (s)he will pay, cash or credit (determined randomly) and will get on the corresponding line. 

After the item is paid for, the customer will take a break in the cafeteria and let the rest of the customers do their shopping. When the shopping is done, each customer will join another customer; they will leave in sequential order. Customer N joins with customer N-1, customer N1 joins with N-2, …, customer 2 joins with customer 1 (use join( ), isAlive( )). Customer 1 will not join with any other customer; before (s)he leaves. Customer1 will announce to the floorclerks that it is closing time by waking them up (call interrupt() on these threads). 

The cashiers wait for customers and serve the customers waiting on their line in a first-come first-serve basis. They will also terminate at the closing time.

Using the synchronization tools and techniques learned in class, synchronize the customer and floor clerk threads in the context of the problem described above. 
Your program should have three types of threads: 
Customer threads, Floor-clerk threads, Cashier threads 

The number of customers should be read as command line arguments. 
Default values are: 
number customer: 12, number floor-clerks: 3, number cashiers: 2 

In order to simulate different actions you must pick reasonable intervals of random time. Make sure that the execution of the entire program is somewhere between 40 seconds and 90 seconds.
