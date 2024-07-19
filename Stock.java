
public class Stock {
	private String symbol;
	private String name;
	private double returns5Year;
    private double returns1Year;
    private double returns90Day;
    private boolean isStatic;
	public Stock(String symbol, String name, double returns5Year, double returns1Year, double returns90Day)
	{
		this.symbol = symbol;
		this.name = name;
		this.returns5Year = returns5Year;
        this.returns1Year = returns1Year;
        this.returns90Day = returns90Day;
        isStatic = false;
	}
	public Stock(String symbol, String name)
	{
		this.symbol = symbol;
		this.name = name;
		isStatic = true;
	}
	
	public double calculateExpectedReturn() 
	{
		return 0.6 * returns5Year + 0.2 * returns1Year + 0.2 * returns90Day;
    }

    public int calculateFutureValue(int amount) 
    {
        if (isStatic)
        	return amount;
        else
        	return (int)Math.round(amount * Math.pow(1 + calculateExpectedReturn(), 10));
    }
    
	
    public String getSymbol() 
    {
    	return symbol;
    }
	
	public String getName() 
    {
		return name;
	}
}