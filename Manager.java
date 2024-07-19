import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
public class Manager {
	
	private List<Stock> availableStocks;
	private List<Investments> userInvestment;
	
	public Manager(List<Stock> availableStocks)
	{
		this.availableStocks = availableStocks;
		this.userInvestment = new ArrayList<>();
	}
	
	public static void main(String[] args) {
        List<Stock> availableAssets = readFromFile("C:\\Users\\Usuário\\eclipse-workspace\\Project4\\src\\assetData.txt");

        Manager manager = new Manager(availableAssets);
        manager.startInvestment();
    }
	
	private static List<Stock> readFromFile(String path)
	{
		List<Stock> stock = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader (new FileReader (path)))
		{
			String line;
			
			while ((line = br.readLine()) != null)
			{
				String[] parts = line.split(",");
				String symbol = parts[0];
				String name = parts[1];
				
				if (name.equals("Bitner")) {
                    System.out.println("THE DATA READ FOR " + name + " WAS NOT VALID, SO IT WILL NOT BE AN AVAILABLE INVESTMENT!");
                }


                if (parts.length > 4) {
                    try {
                        double returns5Year = Double.parseDouble(parts[2]);
                        double returns1Year = Double.parseDouble(parts[3]);
                        double returns90Day = Double.parseDouble(parts[4]);
                        stock.add(new Stock(symbol, name, returns5Year, returns1Year, returns90Day));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid return values for " + name + ". Skipping...");
                    }
                }
                else if (parts.length == 3) {
                    try {
                        stock.add(new Stock(symbol, name));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid return values for " + name + ". Skipping...");
                    }
                }
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return stock;
	}
	
	private void showAvailableStocks() {
        System.out.println("Available assets for investment:");
        System.out.println("-------------------------------");

        for (Stock stock : availableStocks) {
            System.out.println(stock.getSymbol() + " - " + stock.getName());
        }
    }

    private Stock findStock(String symbol) {
        for (Stock stock : availableStocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }
    
    private void invest(int amount, Stock stock)
    {
    	int returnedMoney = stock.calculateFutureValue(amount);
    	Investments i = new Investments(stock.getSymbol(), returnedMoney, amount);
    	userInvestment.add(i);
    	
    	System.out.println("Investing " + amount + " in " + stock.getSymbol() +" has an expected future value of: " + returnedMoney);
    }
	
	public void startInvestment()
	{
		Scanner scnr = new Scanner(System.in);
		showAvailableStocks();
		
		while (true)
		{		
			System.out.print("Enter the amount to invest in dollar (enter a negative value to exit): ");
			int amount = scnr.nextInt();
			
			if (amount < 0)
			{
				break;
			}
			
			System.out.print("Enter the asset symbol to invest in: ");
            String symbol = scnr.next();
            
            Stock selected = findStock(symbol);
            if (selected != null)
            {
            	invest(amount, selected);
            }
            else
            {
            	System.out.println(symbol + " is not a valid asset. Choose something else.");
            }
		}
		writeToFile();
        System.out.println("Program ended. Portfolio details written to portfolio.txt.");
	}
	
	private void writeToFile()
	{
		final String fileName = "portfolio.txt";
		PrintWriter p = null;
		try {
			p = new PrintWriter(new FileOutputStream(fileName));
			p.print("+--------------+-----------------+--------------------+\n");
            p.print("| ASSET SYMBOL | AMOUNT INVESTED | VALUE IN TEN YEARS |\n");
            p.print("+==============+=================+====================+\n");
            
            int totalInvested = 0;
            int totalValueInTenYears = 0;
            for (Investments i : userInvestment) {
            	totalInvested += i.getInvested();
                totalValueInTenYears += i.getMoney();
            	p.print(String.format("| %-13s| %-16d| %-19d|\n", i.getSymbol(), i.getInvested(), i.getMoney()));
            }
            p.print("+--------------+-----------------+--------------------+\n");
            p.print(String.format("| TOTAL        | %-16d| %-19d|\n", totalInvested, totalValueInTenYears));
            p.print("+--------------+-----------------+--------------------+\n");
		} catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
        	p.close();
        }
	}
}