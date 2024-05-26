public class PolybiusSquareCipher {
    private static final char[][] KEY = new char[][] {
            { 'a', 'b', 'c', 'd', 'e' },
            { 'f', 'g', 'h', 'i', 'k' },
            { 'l', 'm', 'n', 'o', 'p' },
            { 'q', 'r', 's', 't', 'u' },
            { 'v', 'w', 'x', 'y', 'z' }
    };

    public String encrypt(String plaintext) {
        String lowerPlaintext = plaintext.toLowerCase(); // Turn the given plaintext to lowercase
        String encrypted = "";

        for (int i = 0; i < lowerPlaintext.length(); i++) {
            char currentChar = lowerPlaintext.charAt(i); // Get the character at the position of i

            // If the current character is not in the range a-z
            // append it to the encrypted text as is, then
            // continue to the next character.
            if (currentChar < 'a' || currentChar > 'z') {
                encrypted += currentChar;
                continue;
            }

            // If it is a character from a-z,
            // get its position from the key, then
            // append it to the encrypted string.
            encrypted += getLetterRowAndColumn(currentChar);
        }

        return encrypted;
    }

    public String decrypt(String encrypted) {
        String plaintext = "";

        int i = 0;

        while (i < encrypted.length()) {
            int row = Character.getNumericValue(encrypted.charAt(i++));
            int col = Character.getNumericValue(encrypted.charAt(i++));

            plaintext += KEY[row-1][col-1];
        }

        return plaintext;
    }

    public void printKey() {
        System.out.println("  1 2 3 4 5");
        for (int row = 0; row < KEY.length; row++) {
            System.out.print(row);
            for (int col = 0; col < KEY[row].length; col++) {
                System.out.print(" " + Character.toUpperCase(KEY[row][col]));
            }
            System.out.println();
        }
    }

    private String getLetterRowAndColumn(char letter) {

        // Iterate through every row of KEY array
        for (int row = 0; row < KEY.length; row++) {

            // Iterate through every element of the current row of the KEY array
            for (int col = 0; col < KEY[row].length; col++) {

                // If the letter parameter matches the character
                // from the current row and column, return it
                // incremented by one, since the cipher starts
                // with 1 and not 0
                if (KEY[row][col] == letter) {
                    return String.valueOf(row + 1) + String.valueOf(col + 1);
                }
            }
        }

        // Added to stop IDE from complaining
        // In theory, the method should always be able to
        // return a valid row and column value
        return "";
    }
}
