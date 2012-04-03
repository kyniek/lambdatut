package lambda;

import static ch.lambdaj.Lambda.*;
import static java.util.Arrays.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matcher;
import beans.Adress;
import beans.Person;
import beans.Stanowisko;

import com.google.common.primitives.Chars;

import ch.lambdaj.function.matcher.Predicate;



public class ListUtils
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//generacja danych		
		List<Person> pers = new ArrayList<Person>();
		
		List<Adress> adr = new ArrayList<Adress>();
		
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		StartLambda.generateData(pers, adr, stan);		
		
		//mapCount(pers);
		
		//anagramTest();

		//joinStrings(pers);
		
		//sortowanie(pers);	
		
		extraktowanie(pers);

	}
	
	
	
	
	/**
	 * Ekstraktownie zadanej własności do listy
	 * @param p
	 */
	public static void extraktowanie(List<Person> p)
	{
		long l1 = System.nanoTime();
		List<String> names1 = new ArrayList<String>();
		for(Person pers : p)
		{
			names1.add(pers.getName());
		}
		long l2 = System.nanoTime();
		
		long l3 = System.nanoTime();
		List<String>names2 = extract(p, on(Person.class).getName());
		long l4 = System.nanoTime();
		
				
//		StartLambda.print.each(names1);
//		System.out.println("===============================================");
//		StartLambda.print.each(names2);
//		System.out.println("===============================================");
		
		System.out.println("lambda/classic : " + (((double)(l4-l3))/((double)(l2-l1)))  );
	}
	

	/**
	 * sortowanie, klasyczny sposób - sortowanie przez wstawianie - najbardziej przewidywalny algorytm
	 * @param p
	 */
	public static void sortowanie(List<Person> p)
	{
		//ArrayList<Object> p1 = new ArrayList<Object>((index(p, on(Person.class).getName() )).values() );
		//ArrayList<Object> p2 = new ArrayList<Object>((index(p, on(Person.class).getName() )).values() );
		ArrayList<Person> p1 = new ArrayList<Person>(p );
		ArrayList<Person> p2 = new ArrayList<Person>(p );
		
		List<Person> ps2 = null; 

		//klasyczne sortowanie przez wstawianie
		
		StartLambda.print.each(p1);
		System.out.println("===============================================");
		
		long l1 = System.nanoTime();
		for(int i = 0; i < p1.size(); i++)
		{
			//System.out.println(((Person)p1.get(i)).getName());
			//System.out.println("==");
			for(int j = i; j > 0; j--)
			{
				if(  ((Person)p1.get(j - 1)).getName().compareTo(((Person)p1.get(j)).getName())  > 0   )
				{					
					Person tmp = (Person)p1.get(j - 1);					
					p1.set(j - 1, p1.get(j));
					p1.set(j, tmp);
				}
			}
		}
		long l2 = System.nanoTime();
		
		System.out.println("===============================================");
		StartLambda.print.each(p1);
		System.out.println("===============================================");
		
		long l3 = System.nanoTime();
		ps2 = sort(p2, on(Person.class).getName() );
		long l4 = System.nanoTime();
		
		StartLambda.print.each(  ps2 );
		
		System.out.println("lambda/classic : " + (((double)(l4-l3))/((double)(l2-l1)))  );
	}
	
	
	/**
	 * Łączenie list w jeden łańcuch znakowy
	 * @param p lista osób do połączenia
	 */
	public static void joinStrings(List<Person> p)
	{
		long l1 = System.nanoTime();
		String s1 = "";
		for(Person p1 : p)
		{
			s1 += p1.toString() + "\n";
		}
		long l2 = System.nanoTime();
		
		//System.out.println(s1);
		//System.out.println("================================================");
		
		long l3 = System.nanoTime();
		String s2 = joinFrom(p, "\n").toString();
		long l4 = System.nanoTime();
		//System.out.println(s2);
		
		System.out.println("lambda/classic : " + (l4-l3)/(l2-l1)  );
	}
	
	
	/**
	 * Demonstruje zliczanie - Lambda.count
	 */
	public static void mapCount( List<Person> prs)
	{
		List<String> imiona = asList("Ania", "Magda", "Ola", "Ania", "Ola", "Ania", "Aneta", "Magda", "Ania");
		
		Map<String, Integer> cnt = count(imiona);
		
		for(String key : cnt.keySet())
		{
			System.out.println(key + " : " + cnt.get(key));
		}
		
		System.out.println("==================================");
				
		Map<String, Integer> mp = count(prs, on(Person.class).getName()  );
		
		for(String key : mp.keySet())
		{
			System.out.println(key + " : " + mp.get(key));
		}		
		
	}	
	
	/**
	 * testowanie przykładowego zadania
	 */
	static void anagramTest()
	{
		Matcher<Integer> odd = new Predicate<Integer>() {
	        public boolean apply(Integer item) {
	                return item % 2 == 1;
	        }
		};
		
		long l1 = System.nanoTime();
		isAnagramOfPalindrome("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", "") );
		long l2 = System.nanoTime();
		System.out.println("result : " + isAnagramOfPalindrome("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", "") )  );
		System.out.println("result : " + isAnagramOfPalindrome("Rats live on no evil star".toLowerCase().replace(" ", "")  ));
		System.out.println("result : " + isAnagramOfPalindrome("kayak"));
		System.out.println("result : " + isAnagramOfPalindrome("rraattsslliivveeoonn"));
		
		
		System.out.println("=======================================");
		long l3 = System.nanoTime();
		isAnagram_lambda("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", ""), odd );
		long l4 = System.nanoTime();		
		System.out.println("result : " + isAnagram_lambda("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", ""), odd )  );
		System.out.println("result : " + isAnagram_lambda("Rats live on no evil star".toLowerCase().replace(" ", "") ,  odd )  );
		System.out.println("result : " + isAnagram_lambda("kayak" , odd  )  );
		System.out.println("result : " + isAnagram_lambda("rraattsslliivveeoonn", odd)   );
		
		System.out.println("lambda/classic : " + (l4-l3)/(l2-l1)  );
	}
	
	
	
	/**
	 * Czy z podanego słowa da się ułożyć anagram
	 * @param S
	 * @return jeśli się da to 1 a jeśli się nie da to 0
	 */
	static int isAnagramOfPalindrome(String S)
	{		
		int flags[] = new int[26];		
		for(int i = 0; i < S.length(); i++)
		{
			flags[S.charAt(i) - 'a' ] = (flags[S.charAt(i) - 'a' ] + 1)%2; 
		}		
		int sum = 0;
		for(int val : flags)
		{
			sum += val;
		}		
		
		if( (S.length()%2 == 0 && sum == 0) || (S.length()%2 == 1 && sum == 1) )
		{
			return 1;
		}		
		return 0;
	}	
	
	static int isAnagram_lambda( String s, Matcher<Integer> match)
	{			
		List<Integer> ints = filter(match, (count(  Chars.asList( s.toCharArray() ) )).values());		
		
		if( (s.length()%2 == 0 && ints.size() == 0 ) || (s.length()%2 == 1 && ints.size() == 1 ) )
		{			
			return 1;
		}		
		return 0;
	}
	

}
