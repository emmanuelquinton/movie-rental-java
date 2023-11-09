package movierental;

import java.util.ArrayList;
import java.util.List;

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
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for " + getName() + "\n";
        DataTemp data = new DataTemp(totalAmount, result);
        calculateTotalAmout(data);

        frequentRenterPoints = calculateNewfrequentrenterPoint(frequentRenterPoints);

        // add footer lines
        data.result += "Amount owed is " + String.valueOf(data.totalAmount) + "\n";
        data.result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";

        return data.result;
    }

    private int calculateNewfrequentrenterPoint(int frequentRenterPoints) {
        for (Rental each : _rentals) {



            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1)
                frequentRenterPoints++;


        }
        return frequentRenterPoints;
    }

    private void calculateTotalAmout(DataTemp data) {
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


            // show figures for this rental
            data.result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            data.totalAmount += thisAmount;

        }
    }

    private static class DataTemp {
        public double totalAmount;
        public String result;

        public DataTemp(double totalAmount, String result) {
            this.totalAmount = totalAmount;
            this.result = result;
        }

    }
}
