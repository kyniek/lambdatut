/**
 * 
 */
package lambda;


import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.*;
import static java.util.Arrays.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.lambdaj.function.closure.Closure;
import ch.lambdaj.function.closure.ClosureResult;
import ch.lambdaj.function.closure.DelayedClosure;
import ch.lambdaj.group.Group;
import beans.Adress;
import beans.Person;
import beans.Stanowisko;

/**
 * @author Kyniek
 *
 */
public class Closures
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		new Closures();

	}

	
	public Closures()
	{
		List<Person> pers = new ArrayList<Person>();
		
		List<Adress> adr = new ArrayList<Adress>();
		
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		StartLambda.generateData(pers, adr, stan);
		
		delayedClosureDemo(pers);
		
	}
	
	static void delayedClosureDemo(List<Person> prs)
	{
		System.out.println("Etap 0");
		Group<Person> persByZarobki = group(prs, by( on(Person.class).getStanowisko().getName() )  );
		System.out.println("Etap 1");
		ClosureResult<List<String>> res = extractGroup(persByZarobki);
		System.out.println("domykanie...");
		of(System.out).println(var( String.class));
		
		System.out.println("==================================================================================");
		
		for(String s : res.get())
		{
			System.out.println(s);
		}
		
		
	}
	
	static ClosureResult<List<String>> extractGroup(final Group<Person> gr)
	{
		System.out.println("definiowanie definicji domknięcia....");
		return delayedClosure(new DelayedClosure<List<String>>() {

			@Override
			public List<String> doWithClosure(Closure cl)
			{
				System.out.println("doWithClosure....");
				List<String> ls = new ArrayList<String>();
				for(String grName : gr.keySet())
				{
					ls.add(grName);
					cl.apply("=====" + grName + "=====");
					for(Person prl : gr.find(grName) )
					{
						ls.add(prl.getName() + " " + prl.getSurName() );
						cl.apply(prl.getName() + " " + prl.getSurName());
					}
				}
				return ls;
			}			
		});
	}

	
}
