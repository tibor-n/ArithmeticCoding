import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class ArithmeticCoding {
    private static final MathContext PRECISION = new MathContext(100);
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Predefined input sequence
        //String input = "adcbadbcadabcbadcbdabacbadbacbdadadabdbccbadbababcbabccbacbdcabbcdbdcdbcbadbbcdaaabcbdcdccdadcbdabaa";

        // Random input sequence
        String input = generateRandomString(100);

        // Initialize the lower bound, upper bound, and range
        BigDecimal lowerBound = BigDecimal.ZERO;
        BigDecimal upperBound = BigDecimal.ONE;
        BigDecimal range = BigDecimal.ONE;

        // Perform arithmetic coding for each symbol in the input sequence
        for (char symbol : input.toCharArray()) {
            // Get the probability of the current symbol
            BigDecimal symbolProb = getSymbolProbability(symbol);

            // Calculate the new lower bound and upper bound
            BigDecimal newLowerBound = lowerBound.add(range.multiply(getSymbolCumulativeProb(symbol)));
            BigDecimal newUpperBound = lowerBound.add(range.multiply(getSymbolCumulativeProb(symbol).add(symbolProb)));

            // Update the lower bound, upper bound, and range
            lowerBound = newLowerBound;
            upperBound = newUpperBound;
            range = upperBound.subtract(lowerBound, PRECISION);
        }

        System.out.println("Given input:   " + input);

        // Calculate the encoded value
        BigDecimal code = lowerBound.add(range.divide(BigDecimal.valueOf(2), PRECISION));
        System.out.println("Encoded value: " + code);

        // Decode the encoded value
        String decodedInput = decode(code, input.length());
        System.out.println("Decoded input: " + decodedInput);

        // Correctness check
        if(input.equals(decodedInput)){
            System.out.println("The input sequence and the decoded one are IDENTICAL!");
        }
        else {
            System.out.println("The input sequence and the decoded one are NOT identical!");
        }
    }

    // Get the probability of a symbol
    private static BigDecimal getSymbolProbability(char symbol) {
        switch (symbol) {
            case 'a':
                return new BigDecimal("0.5", PRECISION);
            case 'b':
                return new BigDecimal("0.3", PRECISION);
            case 'c':
                return new BigDecimal("0.1", PRECISION);
            case 'd':
                return new BigDecimal("0.1", PRECISION);
            default:
                throw new IllegalArgumentException("Invalid symbol: " + symbol);
        }
    }

    // Get the cumulative probability of a symbol
    private static BigDecimal getSymbolCumulativeProb(char symbol) {
        BigDecimal cumulativeProb = BigDecimal.ZERO;
        switch (symbol) {
            case 'a':
                cumulativeProb = cumulativeProb.add(new BigDecimal("0.0", PRECISION));
                break;
            case 'b':
                cumulativeProb = cumulativeProb.add(new BigDecimal("0.5", PRECISION));
                break;
            case 'c':
                cumulativeProb = cumulativeProb.add(new BigDecimal("0.8", PRECISION));
                break;
            case 'd':
                cumulativeProb = cumulativeProb.add(new BigDecimal("0.9", PRECISION));
                break;
            default:
                throw new IllegalArgumentException("Invalid symbol: " + symbol);
        }
        return cumulativeProb;
    }

    // Decode the encoded value
    private static String decode(BigDecimal code, int length) {
        StringBuilder decodedInput = new StringBuilder();
        BigDecimal lowerBound = BigDecimal.ZERO;
        BigDecimal upperBound = BigDecimal.ONE;
        BigDecimal range = BigDecimal.ONE;

        for (int i = 0; i < length; i++) {
            // Calculate the value within the range for the current symbol
            BigDecimal value = code.subtract(lowerBound, PRECISION).divide(range, PRECISION);

            // Get the symbol corresponding to the value
            char symbol = getSymbolFromProbability(value);
            decodedInput.append(symbol);

            // Get the probability of the symbol
            BigDecimal symbolProb = getSymbolProbability(symbol);

            // Calculate the new lower bound and upper bound
            BigDecimal newLowerBound = lowerBound.add(range.multiply(getSymbolCumulativeProb(symbol)));
            BigDecimal newUpperBound = lowerBound.add(range.multiply(getSymbolCumulativeProb(symbol).add(symbolProb)));

            // Update the lower bound, upper bound, and range
            lowerBound = newLowerBound;
            upperBound = newUpperBound;
            range = upperBound.subtract(lowerBound, PRECISION);
        }

        return decodedInput.toString();
    }

    // Get the symbol based on a given probability value
    private static char getSymbolFromProbability(BigDecimal value) {
        if (value.compareTo(new BigDecimal("0.0", PRECISION)) >= 0 &&
                value.compareTo(new BigDecimal("0.5", PRECISION)) < 0) {
            return 'a';
        } else if (value.compareTo(new BigDecimal("0.5", PRECISION)) >= 0 &&
                value.compareTo(new BigDecimal("0.8", PRECISION)) < 0) {
            return 'b';
        } else if (value.compareTo(new BigDecimal("0.8", PRECISION)) >= 0 &&
                value.compareTo(new BigDecimal("0.9", PRECISION)) < 0) {
            return 'c';
        } else if (value.compareTo(new BigDecimal("0.9", PRECISION)) >= 0 &&
                value.compareTo(new BigDecimal("1.0", PRECISION)) <= 0) {
            return 'd';
        } else {
            throw new IllegalArgumentException("Invalid probability value: " + value);
        }
    }

    // Methods below used to generate random strings for testing purposes
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(4);
            char randomChar = getRandomCharacter(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static char getRandomCharacter(int index) {
        switch (index) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            default:
                throw new IllegalArgumentException("Invalid index: " + index);
        }
    }
}
