import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ArithmeticVsHuffman {
    private static final MathContext PRECISION = new MathContext(100);

    public static void main(String[] args) {
        // Define the input sequence
        String input = "adcbadbcadabcbadcbdabacbadbacbdadadabdbccbadbababcbabccbacbdcabbcdbdcdbcbadbbcdaaabcbdcdccdadcbdabaa";

        // Perform arithmetic coding
        BigDecimal encodedLengthArithmetic = performArithmeticCoding(input);

        // Perform Huffman coding
        BigDecimal encodedLengthHuffman = performHuffmanCoding(input);

        System.out.println("Given input:                        " + input);
        System.out.println("Encoded length (Arithmetic Coding): " + encodedLengthArithmetic);
        System.out.println("Encoded length (Huffman Coding):    " + encodedLengthHuffman);
    }

    // Perform arithmetic coding
    private static BigDecimal performArithmeticCoding(String input) {
        BigDecimal lowerBound = BigDecimal.ZERO;
        BigDecimal upperBound = BigDecimal.ONE;
        BigDecimal range = BigDecimal.ONE;

        int numBits = 0;

        for (char symbol : input.toCharArray()) {
            BigDecimal symbolProb = getSymbolProbability(symbol);

            BigDecimal newLowerBound = lowerBound.add(range.multiply(getSymbolCumulativeProb(symbol)));
            BigDecimal newUpperBound = lowerBound.add(range.multiply(getSymbolCumulativeProb(symbol).add(symbolProb)));

            lowerBound = newLowerBound;
            upperBound = newUpperBound;
            range = upperBound.subtract(lowerBound, PRECISION);

            // Count the number of bits required to represent the precision of the range
            while (range.compareTo(BigDecimal.ONE) < 0) {
                range = range.multiply(BigDecimal.TEN);
                numBits++;
            }
        }

        return new BigDecimal(numBits);
    }

    // Perform Huffman coding
    private static BigDecimal performHuffmanCoding(String input) {
        // Count the frequencies of symbols in the input
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char symbol : input.toCharArray()) {
            frequencyMap.put(symbol, frequencyMap.getOrDefault(symbol, 0) + 1);
        }

        // Create a priority queue to store Huffman nodes
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Build the Huffman tree
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            pq.offer(new HuffmanNode('\0', left.frequency + right.frequency, left, right));
        }

        // Get the root of the Huffman tree
        HuffmanNode root = pq.peek();

        // Generate Huffman codes for each symbol
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "", huffmanCodes);

        // Calculate the encoded length using Huffman codes
        BigDecimal encodedLength = BigDecimal.ZERO;
        for (char symbol : input.toCharArray()) {
            String code = huffmanCodes.get(symbol);
            encodedLength = encodedLength.add(new BigDecimal(code.length()));
        }

        return encodedLength;
    }

    // Generate Huffman codes recursively
    private static void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node.left == null && node.right == null) {
            // Reached a leaf node, assign the code to the symbol
            huffmanCodes.put(node.symbol, code);
            return;
        }

        // Traverse left and right branches, appending '0' or '1' to the code
        generateHuffmanCodes(node.left, code + "0", huffmanCodes);
        generateHuffmanCodes(node.right, code + "1", huffmanCodes);
    }

    // Get the probability of a symbol (hardcoded for this example)
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

    // Get the cumulative probability of a symbol (hardcoded for this example)
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

    // Inner class representing a Huffman node
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        char symbol;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(char symbol, int frequency) {
            this.symbol = symbol;
            this.frequency = frequency;
        }

        HuffmanNode(char symbol, int frequency, HuffmanNode left, HuffmanNode right) {
            this.symbol = symbol;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(HuffmanNode other) {
            // Compare nodes based on their frequencies
            return this.frequency - other.frequency;
        }
    }
}
