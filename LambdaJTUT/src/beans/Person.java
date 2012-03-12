/**
 * 
 */
package beans;

/**
 * @author Kyniek
 *
 */
public class Person
{
	private String name;
	
	private String surName;
	
	private Adress adres;
	
	
	
	
	private Stanowisko stanowisko;

	public Person()
	{
		;
	}

	public Person(String name, String surName)
	{
		super();
		this.name = name;
		this.surName = surName;
	}

	public Person(String name, String surName, Adress adres)
	{
		super();
		this.name = name;
		this.surName = surName;
		this.adres = adres;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public Adress getAdres()
	{
		return adres;
	}

	public void setAdres(Adress adres)
	{
		this.adres = adres;
	}

	public Stanowisko getStanowisko()
	{
		return stanowisko;
	}

	public void setStanowisko(Stanowisko stanowisko)
	{
		this.stanowisko = stanowisko;
	}

	@Override
	public String toString()
	{
		return this.name + " " + this.surName;
	}
	


}
