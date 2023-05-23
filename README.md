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
