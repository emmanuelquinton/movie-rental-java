package movierental;

import java.util.*;

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

        Statement data = new Statement(getName());
        calculateTotalAmount(data);
        calculateNewFrequentRenterPoint(data);
        return data.getContent();
    }

    private void calculateNewFrequentRenterPoint(Statement data) {
        for (Rental each : _rentals) {
            // add frequent renter points
            data.frequentRenterPoints++;
            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1)
                data.frequentRenterPoints++;
        }
    }

    private void calculateTotalAmount(Statement data) {
        for (Rental each : _rentals) {
            Amount amount;

            //determine amounts for each line
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                       if (each.getDaysRented() > 2) {
                           amount = new Amount (2+( (each.getDaysRented() - 2) * 1.5));
                       } else {
                           amount = new Amount(2);
                       }
                    break;
                case Movie.NEW_RELEASE:
                   amount = new Amount(each.getDaysRented() * 3);
                    break;
                case Movie.CHILDRENS:

                    if (each.getDaysRented() > 3) {
                        amount = new Amount(1.5+((each.getDaysRented() - 3) * 1.5));
                    } else {
                        amount = new Amount(1.5);
                    }
                    break;
                default:
                    amount = new Amount(0);
            }

            data.putTitleAmount(each.getMovie().getTitle(), amount);
            // show figures for this rental
        }
    }

    private static class Statement {
        private String content;
        int frequentRenterPoints = 0;

        private String customerName;

        Map<String, Amount> titleAmounts = new LinkedHashMap<>() ;


        public Statement(String customerName) {
            this.customerName = customerName;
            this. frequentRenterPoints = 0;

        }

        public String getContent() {
            this.content = "Rental Record for " + customerName + "\n";
            this.titleAmounts.forEach((String title, Amount amount ) -> this.content += "\t" + title+ "\t" + String.valueOf(amount.value()) + "\n");
            this.content += "Amount owed is " + String.valueOf(this.getTotalAmount()) + "\n";
            this.content += "You earned " + String.valueOf(this.frequentRenterPoints) + " frequent renter points";
            return this.content;
        }

        public double getTotalAmount() {

           return  this.titleAmounts.values().stream().mapToDouble(Amount::value).sum();

        }

        public void putTitleAmount(String title, Amount amount) {
            titleAmounts.put(title, amount);
        }
    }
}
