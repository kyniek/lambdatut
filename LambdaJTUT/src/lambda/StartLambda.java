/**
 * 
 */
package lambda;

import static ch.lambdaj.Lambda.*;
import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beans.Adress;
import beans.Person;
import beans.Stanowisko;
import ch.lambdaj.function.closure.Closure;
import ch.lambdaj.function.closure.Closure1;
import ch.lambdaj.function.closure.Closure3;
import ch.lambdaj.group.Group;

/**
 * @author Kyniek
 *
 */
public class StartLambda
{
	public static Closure1<Object> print = new Closure1<Object>()
	{{		
		of( System.out ).println(  var(Object.class).toString()       ) ;
	}};

	public abstract class Bazowa
	{
		public int x;
		
		public Bazowa(int xx)
		{
			x = xx;
		}
		
		public Bazowa(int xx, int yy)
		{
			x = yy + xx;
		}
		
		public abstract String doCos();
		
		public int getSum(int a, int b)
		{
			return (a + b) *x;
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new StartLambda();
		
		
		//filtrowanie();
		
        
	}
	
	public StartLambda()
	{
		//closures1();
		
		//closures2();
		
		//closures3();//wykomentowane
		
		//filtrowanie();
		
		filtrowanie2();
	}
	
	
	public static void filtrowanie()
	{
		//selekcja większych niż.... użycie wbudowanych kryteriów
		List<Integer> biggerThan3 = filter(greaterThan(3), asList(1, 2, 3, 4, 5));
		
		//wywołanie - tzw. domknięcie wyrażenia.
		print.each(  biggerThan3    );	
	}
	
	
	
	public void filtrowanie2()
	{
		boolean bl_print = false;	//wypisywanie wszystkich stanowisk
		boolean bl_townSel = true;	//wypisanie wszystkich osób mieszkających w danym mieście
		boolean bl_min = false;		//zawody w których zarabia się (minimum + 100) 		
		boolean bl_group_min = false;//pogrupować osoby po stanowiskach - tylko tych gdzie zarabia się (min + 100) 
		
		
		List<Person> pers = new ArrayList<Person>();
		List<Adress> adr = new ArrayList<Adress>();
		List<Stanowisko> stan = new ArrayList<Stanowisko>();
		
		generateData(pers, adr, stan);		
		
		
		//wypisywanie wszystkich stanowisk
		if(bl_print)
		{
			System.out.println("==============================================================================");
			long l1 = System.nanoTime();
			for(Stanowisko s: stan)
			{
				System.out.println(s.toString());
			}		
			long l2 = System.nanoTime();		
			
			long l3 = System.nanoTime();
			print.each( extract(stan, on(Stanowisko.class).toString() )  );
			long l4 = System.nanoTime();
			
			//średni wynik : Lambda : 12895 for : 411
			System.out.println("Wypisywanie stanowisk : Lambda/for : " + (l4-l3)/(l2-l1) );			
		}
		
		
		//wypisanie wszystkich osób mieszkających w danym mieście
		if(bl_townSel)
		{
			System.out.println("==============================================================================");
			String miasto = "Brwinów";
			long l5 = System.nanoTime();
			List<Person> ls_p = new ArrayList<Person>();
			for(Person p : pers)
			{
				if(p.getAdres().getCity().equals(miasto))
				{
					ls_p.add(p);
				}
			}
			long l6 = System.nanoTime();
			//print.each(extract(ls_p, on(Person.class).toString() ) );
			
			long l7 = System.nanoTime();
			ls_p = select(pers, having(on(Person.class).getAdres().getCity() ,  equalTo(miasto) ) );
			long l8 = System.nanoTime();
			//wypisanie wyniku	
			//print.each( extract(select(pers, having(on(Person.class).getAdres().getCity() ,  equalTo(miasto) ) ) , on(Person.class).toString() ) );
			
			//wyszukiwanie po mieście 
			System.out.println("osoby mieszkające w " + miasto + " lambda/for : " + (l8-l7)/(l6-l5) ) ;			
		}
		
		
		
		//zawody w których zarabia się (minimum + 100) 		
		if(bl_min)
		{
			System.out.println("==============================================================================");			
			long l1 = System.nanoTime();
			List<Stanowisko> ls_minCache = new ArrayList<Stanowisko>();			
			int min = 0;
			
			//System.out.println("max : " + max);
			min = stan.get(0).getEarnings();
			for(Stanowisko s : stan)
			{
				if(min > s.getEarnings())
				{
					min = s.getEarnings();
				}
			}
			//System.out.println("min : " + min);
			for(Stanowisko s : stan)
			{
				if((min + 100) > s.getEarnings() )
				{
					ls_minCache.add(s);
					//System.out.println("add : " + s.getName());
				}
			}
			long l2 = System.nanoTime();
			//wypisanie
			print.each(extract(ls_minCache, on(Stanowisko.class).toString()  ));
			
			
			//lambda :
			long l3 = System.nanoTime();
			ls_minCache = select(stan, having( on(Stanowisko.class).getEarnings(), lessThan( min(stan, on(Stanowisko.class).getEarnings() ) + 100) )  );
			long l4 = System.nanoTime();
			System.out.println("Lambda : ");
			print.each(extract(ls_minCache, on(Stanowisko.class).toString()  ));
			
			System.out.println("zawody ze stawkami (min + 100) : " + " lambda/for : " + (l4-l3)/(l2-l1) ) ;				
		}
	
		
		//pogrupować osoby po stanowiskach - tylko tych gdzie zarabia się (min + 100)
		if(bl_group_min)
		{
			System.out.println("==============================================================================");			
			long l1 = System.nanoTime();
			List<Stanowisko> ls_minCache = new ArrayList<Stanowisko>();			
			
			int min = 0;
			
			//System.out.println("max : " + max);
			min = stan.get(0).getEarnings();
			for(Stanowisko s : stan)
			{
				if(min > s.getEarnings())
				{
					min = s.getEarnings();
				}
			}
			//System.out.println("min : " + min);
			for(Stanowisko s : stan)
			{
				if((min + 100) > s.getEarnings() )
				{
					ls_minCache.add(s);
					//System.out.println("add : " + s.getName());
				}
			}
			//mam listę stanowisk 
			Map<String, List<Person>> osobaStanowisko = new HashMap<String, List<Person>>();
			for(Stanowisko s : ls_minCache)
			{
				List<Person> lsTmp = new ArrayList<Person>();
				osobaStanowisko.put(s.getName(), lsTmp);
			}
			for(Person p : pers)
			{	
				if(osobaStanowisko.containsKey(p.getStanowisko().getName()))
					osobaStanowisko.get(p.getStanowisko().getName()).add(p);				
			}
			long l2 = System.nanoTime();		
			//sprawdzenie :
			for(String key : osobaStanowisko.keySet())
			{
				for(Person p : osobaStanowisko.get(key))
				{
					System.out.println(key + " : " + p.toString());
				}
			}
			//lambdda 
			System.out.println("---------------------------------------------------");
			
			long l3 = System.nanoTime();
			//najpierws osoby zarabiające nie więcej jak minimum + 100
			List<Person> osobyZarobki = select(pers, having(on(Person.class).getStanowisko().getEarnings(), 
					lessThan(min(stan, on(Stanowisko.class).getEarnings()  ) + 100 ) )  );		
			//listę osób o konkretnych zarobkach grupuję po nazwie stanowiska
			Group<Person> persByZarobki = group(osobyZarobki, by( on(Person.class).getStanowisko().getName() )  );
			long l4 = System.nanoTime();
			
			//sprawdzenie
			for(String head : persByZarobki.keySet()  )
			{
				System.out.println(head);
				for(Person p : persByZarobki.find(head)  )
				{
					System.out.println(head + " : " + p.toString());
				}
			}
			
			//spłaszczanie
			//List<Person> ls_prs = flatten(persByZarobki);
			//print.each(ls_prs);


			for(Group<Person> ob : persByZarobki.subgroups())
			{
				System.out.println(ob.getSize() + " : " + ob.getHeads() + " , " + ob.key());;
			}
					
			
			System.out.println("grupowanie osób ze stawkami (min + 100)  po stanowisku : " + " lambda/for : " + (l4-l3)/(l2-l1) ) ;	
		}
		

	}
	
	

	
	
	
	public void closures2()
	{
		//nowa lista - 100 obiektów
		List<Bazowa> ls = new ArrayList<StartLambda.Bazowa>();
		List<Integer> par1 = new ArrayList<Integer>();
		List<Integer> par2 = new ArrayList<Integer>();
		
		for(int i = 0; i < 100; i++)
		{
			Bazowa cl = new Bazowa(i * 10, 20)
			{
				public String doCos() 
				{
					return "val : " + x;
				}
			};
			ls.add(cl);
			par1.add(new Integer(i));
			par2.add(new Integer(100 - i));
		}
		
		Closure3<Bazowa, Integer, Integer> cl = closure(Bazowa.class, Integer.class, Integer.class);
		of(Bazowa.class).getSum( var(Integer.class), var(Integer.class)  );
		
				
		List<?> ls2 = cl.each(ls, par1, par2);		
		print.each(ls2);		

		
	}
	
	/**
	 * Domknięcia
	 * - Domknięcia ze zmienną związaną oraz z ze zmienną wolną
	 * - możliwość zagnieżdżania domknięć
	 */
	public void closures1()
	{
		//nowa lista - 100 obiektów
		List<Bazowa> ls = new ArrayList<StartLambda.Bazowa>();
		for(int i = 0; i < 100; i++)
		{
			Bazowa cl = new Bazowa(i * 10, 20)
			{
				public String doCos() 
				{
					//System.out.println(x);;
					return "val : " + x;
				}
			};
			ls.add(cl);
		}
		
		List<String> ls2 = new ArrayList<String>();
		for(int i = 0; i < 100; i++)
		{
			ls2.add("val : " + i);
		}
		

		//tworzenie dwóch domknięć - II zagnieżdżone w I
		Closure cl = closure();
		of(System.out).println(var( String.class));		
		Closure cl2 = closure();
		of(Bazowa.class).doCos();
		
		
		//tworzenie domknięcia dla listy stringów
		Closure cl3 = closure();
		of(System.out).println( var(String.class) ); 

		
	
		//wykonanie i pomiar dla domknięć - domknięcia zagnieżdżone
		//I użycie domknięcia
		long l1 = System.nanoTime();
		cl.each( cl2.each(ls)  );
		long l2 = System.nanoTime();
		// II użycie domknięcia
		long l5 = System.nanoTime();
		cl.each( cl2.each(ls)  );
		long l6 = System.nanoTime();		
		
		
		//klasyczne podejście z pętlą - wypisanie obiektów wraz z wołaniem metody
		long l3 = System.nanoTime();
		for(Bazowa b : ls)
		{
			System.out.println(b.doCos());
		}
		long l4 = System.nanoTime();
		
		
		long l7 = System.nanoTime();
		for(String s : ls2)
		{
			System.out.println(s);
		}
		long l8 = System.nanoTime();
		
		//wypisanie za pomocą domknięć
		//I wykonanie domknięcia
		long l9 = System.nanoTime();
		cl3.each(ls2);
		long l10 = System.nanoTime();
		//II wykonanie domknięcia
		long l11 = System.nanoTime();
		cl3.each(ls2);
		long l12 = System.nanoTime();
		
		//koniec testów wydajności
		
			
				
		System.out.println("lambda zagnieżdżone : " + (l2 - l1) + " for : " + ( l4 - l3)  + " lambda2 : " + (l6 - l5));		
		
		System.out.println("lambda : " + (l10 - l9) + " for : " + (l8 - l7) + " lambda2 : " + (l12 - l11));
	}
	
	

	public static void generateData(List<Person> pers, List<Adress> adr, List<Stanowisko> stan)
	{
		String miasta[] = new String[]{ "Milanówek", "Brwinów", "Grodzisk", "Pruszków", "Błonie", "Milęcin", "Jaktorów", "Międzyborów", "Podkowa", "Nadarzyn" };		
		
		String ulica[] = new String[]{"Błońska", "Brwinowska", "Grodziska", "Klonowa", "Brzozowa", "Majowa", "Pruszkowska", "Myśliwska"};
		
		String imiona[] = new String[]{ "Dobromir", "Bolemir", "Radomir", "Sawa", "Nadbor", "Wojciech", "Świetopełk", "Domami", "Dana", 
				"Wanda", "Jagoda", "Olga", "Czech", "Jantarka", "Gniewosz", "Wacław", "Iwan", "Marzena"  };
		
		String nazwiska[] = new String[]{"Krawczyk", "Szewczyk", "Waligóra", "Kowalczyk", "Dratewka", "Zając", "Sikora", "Saleta", "Karczmarek", "Jajcarz", "Michalak"};
		
		
		String stanowiska[] = new String[]{"strażnik", "drwal", "cieśla", "kowal", "myśliwy", "piekarz", "alchemik", "karczmarz"};
		
		
		for(String s : stanowiska)
		{
			stan.add(new Stanowisko(s, (1000 - (int)(400 * Math.random() ) ))  );
		}
		
		for(String imie : imiona)
		{
			for(String nazwisko : nazwiska)
			{
				Person p = new Person(imie, nazwisko);
				Adress a = new Adress(miasta[ (int) (Math.random() * miasta.length)], ulica[ (int)(Math.random() * ulica.length)]);
				p.setAdres(a);
				p.setStanowisko(  stan.get(  (int)(Math.random() * stan.size()) )  );
				
				pers.add(p);
				adr.add(a);				
			}				
		}
		
		
	}
 

}
