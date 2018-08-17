# Rate Calculator CLI

Rate calculator which allows borrowers to obtain a quote from a pool of lenders. 
Repayments are based on monthly compounding interest formula.

### Assumptions
* Borrowers should be able to request a loan of any £100 increment between £1000 and £15000 inclusive.
    * Conversion between currencies has not been considered. Only Sterling Pound is currently supported.
    * Amounts are between 1000 and 15000 inclusive. Integer values are used to store amounts. This is not going to work well with bigger amounts, where BigDecimal is a better type for money.
* Repayment amounts should be displayed to 2 decimal places and the rate of the loan should be displayed to one decimal place.
    * Used double for rates and repayment amounts.
* The monthly and total repayment should use monthly compounding interest
    * Used formula defined here: [Formula](https://www.mtgprofessor.com/formulas.htm)

### Dependencies
* Project lombok [https://projectlombok.org/](https://projectlombok.org/) - Reduces Java verbosity
* OpenCSV [http://opencsv.sourceforge.net/](http://opencsv.sourceforge.net/) - Read CSV files
* Testing libraries

### How to run

Install maven and run
```bash
mvn clean install
```

run
```bash
java -jar target/rate-calculator-1.0-SNAPSHOT.jar [market_file] [loan_amount]
```

```bash
java -jar target/rate-calculator-1.0-SNAPSHOT.jar market.csv 1000
Requested amount: £1000
Rate: 7.0%
Monthly repayment: £30.78
Total repayment: £1108.10
```