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
		


	}

	
	public Closures()
	{
		List<Person> pers = new ArrayList<Person>();
		
		List<Adress> adr = new ArrayList<Adress>();
		
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		StartLambda.generateData(pers, adr, stan);
		
		
		
	}
	
	
	

	
}
