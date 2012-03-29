package lambda;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.*;
import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.List;
import beans.Adress;
import beans.Person;
import beans.Stanowisko;

public class Aggregation
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		
		new Aggregation();
	}
	
	public Aggregation()
	{
		//generacja danych		
		List<Person> pers = new ArrayList<Person>();
		
		List<Adress> adr = new ArrayList<Adress>();
		
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		StartLambda.generateData(pers, adr, stan);	
		
		
		
		
		//maxAge(pers);
		
		sumCash(pers);
	}
	
	
	public void sumCash(List<Person> p)	
	{		
		long l1 = System.nanoTime();
		int cache0 = 0;		
		for(Person pers : p)
		{
			cache0 += pers.getStanowisko().getEarnings();
		}		
		long l2 = System.nanoTime();
		
		
		long l3 = System.nanoTime();		
		int cache = sum(p, on(Person.class).getStanowisko().getEarnings() );
		long l4 = System.nanoTime();
		
		long l5 = System.nanoTime();
		int cache2 = sumFrom( extract(p, on(Person.class).getStanowisko() ) ).getEarnings();
		long l6 = System.nanoTime();
		
		System.out.println("cache0/cache/cache : " + cache0 + "/" + cache + "/" + cache2);
		
		System.out.println("lambda1/classic : " + (((double)(l4-l3))/((double)(l2-l1)))  );
		
		System.out.println("lambda1/lambda2 : " + (((double)(l4-l3))/((double)(l6-l5)))  );	
	}
	
	
	
	
	public void maxAge(List<Person> pers)
	{
		long l1 = System.nanoTime();		
		int max = 0;
		for(Person p : pers)
		{
			if(p.getAge() > max)
				max = p.getAge();
			//System.out.println(p.getAge());
		}
		long l2 = System.nanoTime();
		
		
		long l3 = System.nanoTime();		
		int max3 = maxFrom(pers).getAge();
		long l4 = System.nanoTime();
		
		long l5 = System.nanoTime();
		int max2 = max(pers, on(Person.class).getAge() );
		long l6 = System.nanoTime();
		
		System.out.println("maxAge : clasic/lambda1/lambda2 " + max + "/" + max2 + "/" + max3);
		
		System.out.println("lambda1/classic : " + (((double)(l4-l3))/((double)(l2-l1)))  );
		
		System.out.println("lambda1/lambda2 : " + (((double)(l4-l3))/((double)(l6-l5)))  );		
	}

}
