public class ArithmeticCoding {
    private static final int PRECISION = 32; // Number of bits in the precision

    public static void main(String[] args) {
        String sequence = "abadccdabdbadcabdcbabdcab";
        double[] cumulativeProbabilities = { 0.5, 0.8, 0.9, 1.0 };

        // Encoding
        long lowerBound = 0L;
        long upperBound = (1L << PRECISION) - 1L; // Maximum value with given precision
        long encodedValue = 0L;
        int bitsWritten = 0;

        for (char symbol : sequence.toCharArray()) {
            int symbolIndex = getSymbolIndexFromChar(symbol);
            long range = upperBound - lowerBound + 1;
            upperBound = lowerBound + (range * (long) (cumulativeProbabilities[symbolIndex] * (1L << PRECISION))) - 1L;
            lowerBound = lowerBound + (range * (long) (cumulativeProbabilities[symbolIndex - 1] * (1L << PRECISION)));

            while (true) {
                if (upperBound < (1L << (PRECISION - 1)) || lowerBound >= (1L << (PRECISION - 1))) {
                    // No renormalization needed
                    break;
                } else if (upperBound < (1L << PRECISION) && lowerBound >= (1L << (PRECISION - 1))) {
                    // Renormalization by shifting out the most significant bit
                    encodedValue ^= (1L << (PRECISION - 1));
                    lowerBound <<= 1;
                    upperBound <<= 1;
                    upperBound |= 1L;
                    bitsWritten++;
                } else {
                    // Output a bit, renormalization not possible
                    break;
                }
            }
        }

        // Decoding
        StringBuilder decodedSequence = new StringBuilder();
        lowerBound = 0L;
        upperBound = (1L << PRECISION) - 1L;
        long value = encodedValue;
        int bitsRead = 0;

        while (bitsRead < PRECISION) {
            long range = upperBound - lowerBound + 1;
            long scaledValue = ((value - lowerBound + 1) * (1L << PRECISION) - 1L) / range;
            int symbolIndex = getSymbolIndexFromScaledValue(scaledValue, cumulativeProbabilities);
            char symbol = getCharFromSymbolIndex(symbolIndex);
            decodedSequence.append(symbol);

            upperBound = lowerBound + (range * (long) (cumulativeProbabilities[symbolIndex] * (1L << PRECISION))) - 1L;
            lowerBound = lowerBound + (range * (long) (cumulativeProbabilities[symbolIndex - 1] * (1L << PRECISION)));

            while (true) {
                if (upperBound < (1L << (PRECISION - 1)) || lowerBound >= (1L << (PRECISION - 1))) {
                    // No renormalization needed
                    break;
                } else if (upperBound < (1L << PRECISION) && lowerBound >= (1L << (PRECISION - 1))) {
                    // Renormalization by shifting out the most significant bit
                    lowerBound <<= 1;
                    upperBound <<= 1;
                    upperBound |= 1L;
                    value <<= 1;
                    value |= (1L << (PRECISION - 1));
                    bitsRead++;
                } else {
                    // Read a bit, renormalization not possible
                    bitsRead++;
                    value <<= 1;
                    lowerBound <<= 1;
                    upperBound <<= 1;
                    upperBound |= 1L;
                    break;
                }
            }
        }

        System.out.println("Encoded value: " + encodedValue);
        System.out.println("Decoded sequence: " + decodedSequence.toString());
    }

    private static int getSymbolIndexFromChar(char symbol) {
        switch (symbol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            default:
                throw new IllegalArgumentException("Invalid symbol: " + symbol);
        }
    }

    private static int getSymbolIndexFromScaledValue(long scaledValue, double[] cumulativeProbabilities) {
        for (int i = 1; i < cumulativeProbabilities.length; i++) {
            if (scaledValue < cumulativeProbabilities[i] * (1L << PRECISION)) {
                return i;
            }
        }
        return cumulativeProbabilities.length - 1;
    }

    private static char getCharFromSymbolIndex(int index) {
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
                throw new IllegalArgumentException("Invalid symbol index: " + index);
        }
    }
}
