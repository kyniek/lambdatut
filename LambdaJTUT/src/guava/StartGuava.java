package guava;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;
import lambda.StartLambda;
import beans.Adress;
import beans.Person;
import beans.Stanowisko;



public class StartGuava
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		new StartGuava();

	}
	
	public StartGuava()
	{
		//generacja danych		
		List<Person> pers = new ArrayList<Person>();
		
		List<Adress> adr = new ArrayList<Adress>();
		
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		StartLambda.generateData(pers, adr, stan);
		
		
		//sprawdzanie argumentów
		//System.out.println(argumentCheck("abecadło") );		
		//System.out.println(argumentCheck(null) );
		
		System.out.println(stringHelper(pers));
	}
	
	
	
	/**
	 * testowanie Optional
	 * @param val
	 * @return
	 */
	public boolean argumentCheck(String val)
	{
		checkNotNull(val);
		checkArgument(val.startsWith("a"), "value must starts with a");
		
		return true;
	}
	
	/**
	 * stringHelper demonstruje uproszczone tworzenie metod typu toString
	 * @param p
	 * @return
	 */
	public String stringHelper(List<Person> p)
	{
		String res = "";
		for(Person per : p)
		{
			res += toStringHelper(per).add("imie", per.getName()).add("nazwisko", per.getSurName()).toString() + "\n";
		}
		return res;
	}

}
