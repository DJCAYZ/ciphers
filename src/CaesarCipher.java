public class CaesarCipher {
    private int shift;

    public CaesarCipher() {
        // Sets the default shift value to 5, if the user doesn't specify anything
        setShift(5);
    }

    public CaesarCipher(int shift) {
        setShift(shift);
    }

    // Mutator for the private 'shift' field
    public void setShift(int newShift) {
        this.shift = newShift;
    }

    // Accessor for the private 'shift' field
    public int getShift() {
        return shift;
    }

    // Encryption method
    public String encrypt(String plaintext) {
        // First, convert the plaintext given to lowercase
        String lowerPlaintext = plaintext.toLowerCase();
        String encrypted = ""; // String that will hold the encrypted text

        // Iterate through every character in the string
        for (int i = 0; i < lowerPlaintext.length(); i++) {
            if (lowerPlaintext.charAt(i) < 'a' || lowerPlaintext.charAt(i) > 'z') {
                // If the current character is not within the range of characters a-z,
                // then append it to the encrypted text, as is,
                // then continue to the next character in the string
                encrypted += lowerPlaintext.charAt(i);
                continue;
            }

            // If the character is a letter from a-z, then pass it on to the shiftLetter() method
            encrypted += shiftLetter(lowerPlaintext.charAt(i));
        }

        // Once every character has been iterated through, return the encrypted text
        return encrypted;
    }

    public String decrypt(String encryptedText) {
        // Convert the given encrypted text to lowercase
        String lowerEncrypted = encryptedText.toLowerCase();
        String plaintext = ""; // String that will hold the plaintext (decrypted text)

        // Iterate through the encrypted text
        for (int i = 0; i < lowerEncrypted.length(); i++) {
            // If the current character is not in the range of a-z
            if (lowerEncrypted.charAt(i) < 'a' || lowerEncrypted.charAt(i) > 'z') {
                // Add it to the plaintext as is,
                // then continue to the next character
                plaintext += lowerEncrypted.charAt(i);
                continue;
            }

            // Else pass the character to the unshiftLetter() method
            plaintext += unshiftLetter(lowerEncrypted.charAt(i));
        }

        // Once it is done iterating through every character,
        // return the plaintext
        return plaintext;
    }

    private char shiftLetter(char letter) {
        // This makes use of the fact that Java chars are stored as integers internally
        // So by adding a character and an integer, you get the sum of the ASCII value
        // of the character and the integer
        int shifted = letter + shift;

        // If the computed shifted value is greater than the internal value of the 'z' character,
        // decrement it by 26 to loop back to the character 'a'
        if (shifted > 'z') {
            shifted -= 26;
        }

        // Lastly, return the shifted character by type casting it back as a char
        return (char) shifted;
    }

    private char unshiftLetter(char letter) {
        // Same method as the shiftLetter() method
        // Except this time, we subtract the shift value
        // to the ASCII value of the character
        int shifted = letter - shift;

        // If the value is less than the ASCII code for the character 'a'
        // Add 26 so it loops back to the character 'z'
        if (shifted < 'a') {
            shifted += 26;
        }

        // Then return the computed value as a char using type casting
        return (char) shifted;
    }
}
