import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StockTradingPlatform {

    // Stock class representing a stock
    static class Stock {
        private String ticker;
        private String name;
        private double price;

        public Stock(String ticker, String name, double price) {
            this.ticker = ticker;
            this.name = name;
            this.price = price;
        }

        public String getTicker() {
            return ticker;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void updatePrice(double newPrice) {
            this.price = newPrice;
        }

        @Override
        public String toString() {
            return ticker + " (" + name + ") - $" + price;
        }
    }

    // Portfolio class to manage user's stocks
    static class Portfolio {
        private HashMap<Stock, Integer> stocks;

        public Portfolio() {
            this.stocks = new HashMap<>();
        }

        public void buyStock(Stock stock, int quantity) {
            stocks.put(stock, stocks.getOrDefault(stock, 0) + quantity);
        }

        public void sellStock(Stock stock, int quantity) {
            if (stocks.containsKey(stock) && stocks.get(stock) >= quantity) {
                stocks.put(stock, stocks.get(stock) - quantity);
                if (stocks.get(stock) == 0) {
                    stocks.remove(stock);
                }
            } else {
                System.out.println("Not enough shares to sell.");
            }
        }

        public void displayPortfolio() {
            System.out.println("Your Portfolio:");
            for (var entry : stocks.entrySet()) {
                System.out.println(entry.getKey() + " | Quantity: " + entry.getValue());
            }
        }

        public double getTotalValue() {
            return stocks.entrySet().stream()
                    .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                    .sum();
        }
    }

    // Main application class
    public static void main(String[] args) {
        List<Stock> market = new ArrayList<>();
        market.add(new Stock("AAPL", "Apple Inc.", 150.00));
        market.add(new Stock("GOOGL", "Alphabet Inc.", 2800.00));
        market.add(new Stock("AMZN", "Amazon.com Inc.", 3400.00));

        Portfolio portfolio = new Portfolio();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Stock Trading Platform!");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Available Stocks:");
                    for (Stock stock : market) {
                        System.out.println(stock);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock ticker to buy: ");
                    String buyTicker = scanner.nextLine();
                    Stock buyStock = market.stream()
                            .filter(stock -> stock.getTicker().equalsIgnoreCase(buyTicker))
                            .findFirst()
                            .orElse(null);
                    if (buyStock != null) {
                        System.out.print("Enter quantity: ");
                        int buyQuantity = scanner.nextInt();
                        portfolio.buyStock(buyStock, buyQuantity);
                        System.out.println("Bought " + buyQuantity + " shares of " + buyStock.getTicker());
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock ticker to sell: ");
                    String sellTicker = scanner.nextLine();
                    Stock sellStock = market.stream()
                            .filter(stock -> stock.getTicker().equalsIgnoreCase(sellTicker))
                            .findFirst()
                            .orElse(null);
                    if (sellStock != null) {
                        System.out.print("Enter quantity: ");
                        int sellQuantity = scanner.nextInt();
                        portfolio.sellStock(sellStock, sellQuantity);
                        System.out.println("Sold " + sellQuantity + " shares of " + sellStock.getTicker());
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 4:
                    portfolio.displayPortfolio();
                    System.out.println("Total Portfolio Value: $" + portfolio.getTotalValue());
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}