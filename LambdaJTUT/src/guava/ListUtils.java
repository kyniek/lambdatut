package guava;


import static ch.lambdaj.Lambda.*;
import static com.google.common.collect.Lists.*;
import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.print.attribute.standard.PresentationDirection;

import ch.lambdaj.group.Group;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

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
		
		
		
//		StartLambda.print.each(pers);
//		System.out.println("========================================");
		
		//ordering(pers);
		
		//simpleOrdering();
		
		//filtering(pers);
		
		//ranges();
		
		//transformator(pers);
		
		groupBy(pers);
		
		
	}
	
	
	
	
	
	/**
	 * grupowanie z wykorzystaniem MultiMap oraz Multimaps.index
	 * @param p
	 */
	public void groupBy(List<Person> p)
	{
		long l1 = System.nanoTime();
		Function<Person, Stanowisko> f = new Function<Person, Stanowisko>() {
			
			@Override
			public Stanowisko apply(Person arg0)
			{
				return arg0.getStanowisko();
			}
		};
		
		Multimap<Stanowisko, Person> multi = Multimaps.index(p, f);
		long l2 = System.nanoTime();
		
		
//		for(Stanowisko st : multi.keySet())
//		{
//			System.out.println("___STANOWISKO : " + st.getName());
//			StartLambda.print.each(multi.get(st));
//		}
		
		long l3 = System.nanoTime();
		Group<Person> persByStan = group(p, by( on(Person.class).getStanowisko().getName() )  );
		long l4 = System.nanoTime();
		
//		for(String k :  persByStan.keySet())
//		{
//			System.out.println("___STANOWISKO : " + k);
//			StartLambda.print.each( persByStan.find(k)	);
//		}
		
		
		System.out.println("lambda/guava : " + (((double)(l4-l3))/((double)(l2-l1)))  );
	}	
	
	
	/**
	 * transformacje - hurtowe przekształcenia kolekcji
	 * @param prs
	 */
	public void transformator(List<Person> prs)
	{
		Function<Person, Stanowisko> f = new Function<Person, Stanowisko>() {
			
			@Override
			public Stanowisko apply(Person arg0)
			{				
				return arg0.getStanowisko();
			}
		}; 
		
		Collection<Stanowisko> stans = Collections2.transform(prs, f);
		
//		StartLambda.print.each(adr);
		
		Multiset<Stanowisko> multi = HashMultiset.create();
		multi.addAll(stans);
		
		
		//sprawdzany ile jest zawodów
		for(Stanowisko st : Sets.newHashSet(stans) )
		{
			//st.setEarnings(-10); //pytanie - co się stanie jak odkomentuję ten wiersz
			System.out.println(st.toString() + " : " + multi.count(st));
		}
		
		//StartLambda.print.each(stans);
	}
	
	
	/**
	 * Demonstruje użycie zakresów i predykatów Range będących podklasą Predicate
	 */
	public void ranges()
	{
		List<Integer> ints = Ints.asList(1,2,3,4,5,6,7,8,9);
		
		Range<Integer> rng = Ranges.closed(3, 6);
		
		Collection<Integer> filtered = Collections2.filter(ints, rng);
		
		StartLambda.print.each(filtered);
	}
	
	
	
	/**
	 * przykład sortowania obiektów prostych
	 */
	public void simpleOrdering()
	{
		List<String> ls = newArrayList("Milanówek", "Brwinów", "Grodzisk", "Pruszków", "Błonie", "Milęcin", "Jaktorów", "Międzyborów", "Podkowa", "Nadarzyn");
		
		Ordering<String> ord = Ordering.natural();
		
		List<String> ls2 = ord.sortedCopy(ls);
		
		StartLambda.print.each(ls2);
	}
	
	
	/**
	 * przykład sortowania złożonych obiektów za pomocą guavy, oraz porównanie z LambdaJ
	 * @param p
	 */
	public void ordering(List<Person> p)
	{
		long l1 = System.nanoTime();
		Ordering<Person> ord = Ordering.natural().onResultOf(new Function<Person, String>() {
		    public String apply(Person per) {
		      return per.getSurName();
		    }
		});		
		List<Person> p2 = ord.sortedCopy(p);
		long l2 = System.nanoTime();
		
//		StartLambda.print.each(p2);
		
		long l3 = System.nanoTime();
		List<Person> p3 = sort(p, on(Person.class).getSurName() );
		long l4 = System.nanoTime();
		
//		System.out.println("========================================");
//		StartLambda.print.each(p3);
		
		System.out.println("lambda/guava : " + (((double)(l4-l3))/((double)(l2-l1)))  );
	}
	
	
	
	public void filtering(List<Person> p)
	{
		long l1 = System.nanoTime();
		Predicate<Person> pred = new Predicate<Person>() 
		{
			public boolean apply(Person per) 
			{
			    return per.getAdres().getCity().equals("Milanówek");
			  }
		};
		Collection<Person> p2 = Collections2.filter(p, pred);
		long l2 = System.nanoTime();
		
//		StartLambda.print.each(p2);
		
		long l3 = System.nanoTime();
		List<Person> p3 = select(p, having(on(Person.class).getAdres().getCity(), equalTo("Milanówek") ));
		long l4 = System.nanoTime();
		
//		System.out.println("========================================");
//		StartLambda.print.each(p2);
		
		System.out.println("lambda/guava : " + (((double)(l4-l3))/((double)(l2-l1)))  );
	}

}
