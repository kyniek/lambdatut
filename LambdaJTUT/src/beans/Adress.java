/**
 * 
 */
package beans;

/**
 * @author Kyniek
 *
 */
public class Adress
{
	private String city;
	
	private String code;
	
	private String street;

	
	
	public Adress()
	{	
	}



	public Adress(String city)
	{
		super();
		this.city = city;
	}



	public Adress(String city, String street)
	{
		super();
		this.city = city;
		this.street = street;
	}



	public Adress(String city, String code, String street)
	{
		super();
		this.city = city;
		this.code = code;
		this.street = street;
	}



	public String getCity()
	{
		return city;
	}



	public void setCity(String city)
	{
		this.city = city;
	}



	public String getCode()
	{
		return code;
	}



	public void setCode(String code)
	{
		this.code = code;
	}



	public String getStreet()
	{
		return street;
	}



	public void setStreet(String street)
	{
		this.street = street;
	}



	@Override
	public String toString()
	{
		return this.city + ", " + this.street;
	}



//	@Override
//	public boolean equals(Object arg0)
//	{		
//		return this.city.equals( ((Adress)arg0).city  );
//	}
	
	

}
