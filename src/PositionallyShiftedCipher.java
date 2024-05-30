public class PositionallyShiftedCipher {
    public String encrypt(String plaintext) {
        // Trim whitespace at the start and end of the string
        // then, convert it to all lowercase
        String cleanPlaintext = plaintext.trim().toLowerCase();
        String encryptedText = ""; // String to hold the encrypted text

        // Iterate through every character in the plaintext string
        for (int i = 0; i < cleanPlaintext.length(); i++) {
            // Get the current character being iterated
            char currentLetter = cleanPlaintext.charAt(i);

            // if the character is not in the range a-z
            if (currentLetter < 'a' || currentLetter > 'z') {
                // just add it as is, then continue to the next iteration
                encryptedText += currentLetter;
                continue;
            }

            // else, shift the letter using the getShiftedLetter() method
            encryptedText += getShiftedLetter(currentLetter, i+1);
        }

        // Once every character is iterated through, return the encrypted text result
        return encryptedText;
    }

    private char getShiftedLetter(char letter, int position) {
        // we first subtract 96 to the letter to get its numerical value
        // from 1 to 26
        // then we add the position of it in the string (not the index, so it starts with 1)
        int shifted = (letter - 96) + (position % 26);

        // if the shifted value is greater than 26
        if (shifted > 26) {
            // subtract 26 to loop back around to the start of the alphabet
            shifted -= 26;
        }

        // return the shifted value as a char by first adding 96 to it to make it a valid ASCII value
        // then type cast it to a char
        return (char) (shifted + 96);
    }

    public String decrypt(String encryptedText) {
        // Same thing as the encrypt() method
        String cleanEncryptedText = encryptedText.trim().toLowerCase();
        String plaintext = "";

        for (int i = 0; i < cleanEncryptedText.length(); i++) {
            char currentLetter = cleanEncryptedText.charAt(i);

            if (currentLetter < 'a' || currentLetter > 'z') {
                plaintext += currentLetter;
                continue;
            }

            // We instead put the letter into the getUnshiftedLetter() method
            plaintext += getUnshiftedLetter(currentLetter,i + 1);
        }

        return plaintext;
    }

    private char getUnshiftedLetter(char letter, int position) {
        // same thing as in getShiftedLetter, but we instead subtract the position instead of adding it
        int unshifted = (letter - 96) - (position % 26);

        // if the unshifted value is less than 1
        if (unshifted < 1) {
            // add 26 so it goes back to the end of the alphabet
            unshifted += 26;
        }

        // add 96 to make it a valid ASCII value then type cast it to a char
        return (char) (unshifted + 96);
    }

}
