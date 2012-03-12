/**
 * 
 */
package tasks;

/**
 * @author Kyniek
 * 
 * zrobione :
 *   countDistinct (simpleTests : basic.codility2 ) 
 *
 */
public class Codility1
{

	public void testAnagram()
	{
		System.out.println("result : " + isAnagramOfPalindrome("ZAble was I ere I saw ElbaZ".toLowerCase().replace(" ", "") )  );
		System.out.println("result : " + isAnagramOfPalindrome("Rats live on no evil star".toLowerCase().replace(" ", "")  ));
		System.out.println("result : " + isAnagramOfPalindrome("kayak"));
		System.out.println("result : " + isAnagramOfPalindrome("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbba"));
	}
		
	/**
	 * Czy z podanego słowa da się ułożyć anagram
	 * @param S
	 * @return jeśli się da to 1 a jeśli się nie da to 0
	 */
	int isAnagramOfPalindrome(String S)
	{
		System.out.println(S);		
		int flags[] = new int[26];			
		
		for(int i = 0; i < S.length(); i++)
		{
			//System.out.println(S.charAt(i) + " : " +  (S.charAt(i) - 'a'));
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
	
	
	
	
	public void testArrayJmp()
	{
		int[] A = { 2, 3, 1, 1, 3 };
		System.out.println( arrayJmp(A) );
		
		A = new int[]{ 3, 1, 1, 0, 3 };
		System.out.println( arrayJmp(A) );
		

		A = new int[]{ 3, 3, -1, -1, 1 };
		System.out.println( arrayJmp(A) );

		A = new int[]{ 7, 2, -1, 1, 1, 6, -4, -1 };
		System.out.println( arrayJmp(A) );		
		
	}
	
	/**
	 * Każdy element tablicy A wskazuje relatywne położenie następnego elementu. Jeśli A[k] = m to skok z indeksu k powinien być k + A[k] => k+m
	 * Napisz funkcję która zlicza liczbę skoków zanim wskaźnik wyskoczy po za granicę tablicy, zaczynamy od pierwszego elementu
	 * A[]=[2, 3, 1, 1, 3]  => A[0]=2, A[1]=3, A[2]=1, A[3]=1, A[4]=3
	 * wskaźnik zaczyna na indexie 0, następnie skacze na 2, potem na index 3, następnie na index 4 a potem na 7, ale 7 jest po za zakresem
	 */
	public int arrayJmp(int[] A)
	{
		int cnt = 0;
		int indx = 0;

		for (int i = 0; i < A.length; i++)
		{
			indx = A[indx] + indx;
			cnt++;

			if (indx >= A.length)
			{
				return cnt;
			}
		}
		return -1;
	}	
	
}
