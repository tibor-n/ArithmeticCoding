# Information about Arithmetic Coding
Here, we will put all the sources of information that we use during the implementation of this algorithm.

## Resources
- Practical Implementation of Arithmetic Coding: http://www.ittc.ku.edu/~jsv/Papers/HoV92.actech.pdf
- Wikipedia page on Arithmetic Coding: https://en.wikipedia.org/wiki/Arithmetic_coding

## Detailed step-by-step explanation of the provided code. 
#### It implements arithmetic coding and decoding using the specified probability distribution and Laplace estimate for conditional probabilities. The code takes a sequence of symbols, encodes it, and then decodes the encoded value to retrieve the original sequence.


1. First, we define the class `ArithmeticCoding`.

2. Inside the class, we create a `MathContext` object named `PRECISION` with a precision of 100 decimal places. This is used for performing arithmetic calculations with high precision.

3. The `main` method is the entry point of the program.

4. We define the input sequence as a `String` named `input`.

5. We initialize the lower bound, upper bound, and range for arithmetic coding. Initially, the lower bound is set to 0, the upper bound is set to 1, and the range is set to 1.

6. Next, we iterate through each symbol in the input sequence using a `for` loop.

7. Inside the loop, we calculate the probability of the current symbol by calling the `getSymbolProbability` method, which returns a `BigDecimal` value.

8. We calculate the new lower bound and upper bound based on the cumulative probabilities of the current symbol and its probability. The new lower bound is calculated by adding the product of the range and the cumulative probability of the symbol. The new upper bound is calculated by adding the product of the range and the sum of the cumulative probability and the probability of the symbol.

9. We update the lower bound, upper bound, and range with the new values.

10. After the loop finishes, we have encoded the input sequence. We calculate the encoded value by adding the lower bound to half of the range. This represents a value within the final range of the arithmetic coding process.

11. We print the given input sequence and the encoded value.

12. Next, we call the `decode` method to decode the encoded value. We pass the encoded value and the length of the input sequence as arguments.

13. Inside the `decode` method, we create a `StringBuilder` named `decodedInput` to store the decoded symbols.

14. We initialize the lower bound, upper bound, and range for decoding. Initially, the lower bound is set to 0, the upper bound

 is set to 1, and the range is set to 1.

15. We iterate `length` times to decode each symbol in the input sequence.

16. Inside the loop, we calculate the value within the range corresponding to the current symbol. This is done by subtracting the lower bound from the encoded value and dividing it by the range.

17. We call the `getSymbolFromProbability` method to determine the symbol based on the calculated value.

18. We append the symbol to the `decodedInput` string.

19. We calculate the probability of the symbol by calling the `getSymbolProbability` method.

20. We calculate the new lower bound and upper bound based on the cumulative probabilities of the symbol and its probability, similar to the encoding process.

21. We update the lower bound, upper bound, and range with the new values.

22. After the loop finishes, we have decoded the encoded value. We return the `decodedInput` string.

23. Finally, in the `getSymbolFromProbability` method, we determine the symbol based on a given probability value. We compare the value with predefined ranges for each symbol and return the corresponding symbol.


## Arithmetic vs Huffman Coding
#### Arithmetic coding and Huffman coding are both techniques used for data compression, but they differ in their approach and characteristics.
Regarding the difference in encoded lengths, arithmetic coding and Huffman coding can yield different results due to their distinct encoding approaches. Arithmetic coding, with its ability to encode fractional ranges, can achieve more precise compression and potentially produce shorter encoded lengths compared to Huffman coding. However, the actual compression achieved depends on the characteristics of the input data and the assigned probabilities or frequencies.

### Similarities:
1. Lossless compression: Both arithmetic coding and Huffman coding are lossless compression techniques. They aim to compress data without losing any information, allowing for exact reconstruction of the original data.

2. Symbol-based compression: Both techniques operate on the basis of symbol frequencies or probabilities. They take advantage of the statistical properties of the input data to assign shorter codes to more frequently occurring symbols, achieving better compression.

### Differences:
1. Coding approach: Arithmetic coding operates on the entire input sequence as a whole, whereas Huffman coding operates on individual symbols or groups of symbols. Arithmetic coding uses fractional values to represent the cumulative probabilities of the symbols, allowing for more precise encoding. In contrast, Huffman coding assigns fixed-length codes to each symbol based on their frequencies, resulting in variable-length codes for different symbols.

2. Encoding efficiency: Arithmetic coding typically achieves higher compression ratios compared to Huffman coding. Arithmetic coding can encode sequences of symbols more efficiently by representing fractional ranges, while Huffman coding assigns fixed-length codes. This flexibility in arithmetic coding allows it to adapt to the statistical properties of the input data more accurately.

3. Decoding complexity: Decoding with arithmetic coding is more computationally intensive compared to Huffman coding. Arithmetic decoding involves complex arithmetic operations, including divisions and multiplications. In contrast, Huffman decoding is simpler as it only requires traversing the Huffman tree based on the encoded bits.

4. Adaptive vs. static coding: Arithmetic coding is inherently adaptive, meaning it can dynamically update the probability distribution as it encounters symbols during encoding and decoding. This adaptability allows arithmetic coding to respond to changes in the input data. Huffman coding, on the other hand, is typically static, meaning the probability distribution and code assignments are predetermined based on the initial analysis of the input data.
