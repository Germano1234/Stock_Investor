
public class Investments {
	private String symbol;
	private int invested;
	private int moneyTen;
	
	public Investments(String symbol, int moneyTen, int invested)
	{
		this.moneyTen = moneyTen;
		this.symbol = symbol;
		this.invested = invested;
	}
	

	public String getSymbol()
	{
		return symbol;
	}
	
	public int getMoney()
	{
		return moneyTen;
	}
	
	public int getInvested()
	{
		return invested;
	}
}

