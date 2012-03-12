package lambda;

import static ch.lambdaj.Lambda.*;
import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matcher;

import com.google.common.primitives.Chars;

import ch.lambdaj.function.matcher.Predicate;



public class ListUtils
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		
		//mapCount();
		

		
		

	}
	
	public ListUtils()
	{		
		
	}
	
	
	
	/**
	 * Demonstruje zliczanie - Lambda.count
	 */
	public static void mapCount()
	{
		List<String> imiona = asList("Ania", "Magda", "Ola", "Ania", "Ola", "Ania", "Aneta", "Magda", "Ania");
		
		Map<String, Integer> cnt = count(imiona);
		
		for(String key : cnt.keySet())
		{
			System.out.println(key + " : " + cnt.get(key));
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
		System.out.println("result : " + isAnagramOfPalindrome("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", "") )  );
		System.out.println("result : " + isAnagramOfPalindrome("Rats live on no evil star".toLowerCase().replace(" ", "")  ));
		System.out.println("result : " + isAnagramOfPalindrome("kayak"));
		System.out.println("result : " + isAnagramOfPalindrome("rraattsslliivveeoonn"));
		long l2 = System.nanoTime();
		
		System.out.println("=======================================");
		long l3 = System.nanoTime();
		System.out.println("result : " + isAnagram_lambda("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", ""), odd )  );
		System.out.println("result : " + isAnagram_lambda("Rats live on no evil star".toLowerCase().replace(" ", "") ,  odd )  );
		System.out.println("result : " + isAnagram_lambda("kayak" , odd  )  );
		System.out.println("result : " + isAnagram_lambda("rraattsslliivveeoonn", odd)   );
		long l4 = System.nanoTime();		
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
