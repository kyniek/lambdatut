package guava;

import java.util.ArrayList;
import java.util.List;

import lambda.StartLambda;
import beans.Adress;
import beans.Person;
import beans.Stanowisko;

public class ListUtils
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new ListUtils();

	}
	
	public ListUtils()
	{
		//generacja danych		
		List<Person> pers = new ArrayList<Person>();
		
		List<Adress> adr = new ArrayList<Adress>();
		
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		StartLambda.generateData(pers, adr, stan);
		
		
		
		StartLambda.print.each(pers);
	}

}
