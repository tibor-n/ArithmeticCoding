import java.util.Random;

public class RandomStrings {
    private static final Random random = new Random();

    public static void main(String[] args) {

        // Used to generate random strings for testing purposes

        String randomString1 = generateRandomString(100);
        String randomString2 = generateRandomString(100);
        String randomString3 = generateRandomString(100);
        String randomString4 = generateRandomString(100);
        String randomString5 = generateRandomString(100);

        System.out.println("Random String 1: " + randomString1);
        System.out.println("Random String 2: " + randomString2);
        System.out.println("Random String 3: " + randomString3);
        System.out.println("Random String 4: " + randomString4);
        System.out.println("Random String 5: " + randomString5);
    }

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
