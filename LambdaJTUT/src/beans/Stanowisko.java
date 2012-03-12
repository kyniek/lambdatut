/**
 * 
 */
package beans;

/**
 * @author Kyniek
 *
 */
public class Stanowisko
{
	
	private String name;
	
	private int earnings;
	
	
	
	
	public Stanowisko()
	{}
	
	
	public Stanowisko(String name, int earnings)
	{
		super();
		this.name = name;
		this.earnings = earnings;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public int getEarnings()
	{
		return earnings;
	}


	public void setEarnings(int earnings)
	{
		this.earnings = earnings;
	}


	@Override
	public String toString()
	{
		return this.name + " : " + this.earnings;
	}

	

}
