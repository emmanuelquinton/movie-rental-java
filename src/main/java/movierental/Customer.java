package movierental;

import java.util.*;
import java.util.stream.DoubleStream;

public class Customer {

    private String _name;
    private List<Rental> _rentals = new ArrayList<Rental>();

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.add(arg);
    }

    public String getName() {
        return _name;
    }

    public String statement() {

        DataTemp data = new DataTemp(getName());
        calculateTotalAmount(data);
        calculateNewFrequentRenterPoint(data);

        // add footer lines


        return data.getStatements();
    }

    private void calculateNewFrequentRenterPoint(DataTemp data) {
        for (Rental each : _rentals) {
            // add frequent renter points
            data.frequentRenterPoints++;
            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1)
                data.frequentRenterPoints++;
        }
    }

    private void calculateTotalAmount(DataTemp data) {
        for (Rental each : _rentals) {
            double thisAmount = 0;

            //determine amounts for each line
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2)
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += each.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3)
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    break;
            }

            data.putTitleAmount(each.getMovie().getTitle(), thisAmount);
            // show figures for this rental
        }
    }

    private static class DataTemp {
        public double totalAmount;
        public String result;
        int frequentRenterPoints = 0;

        Map<String, Double> titleAmounts = new LinkedHashMap<>() ;


        public DataTemp(String name) {
            this.totalAmount = 0;
            this. frequentRenterPoints = 0;
            this.result = "Rental Record for " + name + "\n";
        }

        public String getStatements() {
            this.titleAmounts.forEach((String title, Double amount ) -> this.result += "\t" + title+ "\t" + String.valueOf(amount) + "\n");
            this.result += "Amount owed is " + String.valueOf(this.getTotalAmount()) + "\n";
            this.result += "You earned " + String.valueOf(this.frequentRenterPoints) + " frequent renter points";
            return this.result;
        }

        public double getTotalAmount() {

           return  this.titleAmounts.values().stream().mapToDouble(amount -> amount).sum();

        }

        public void putTitleAmount(String title, double amount) {
            titleAmounts.put(title, amount);
        }
    }
}
