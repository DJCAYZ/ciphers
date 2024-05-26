import java.util.ArrayList;

public class PlayfairCipher {
    private static final char[] ALPHABET_LETTERS = new char[] {
            'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'k',
            'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'
    }; // Letter J gets treated as the letter I

    private final char[][] key = new char[5][5];

    public PlayfairCipher() {
        // Set the default key word as an empty string
        // This sets the encryption key to be the default
        // alphabetically-arranged letters
        setKey("");
    }

    public PlayfairCipher(String keyWord) {
        setKey(keyWord);
    }

    public String encrypt(String plaintext) {
        // Method overloading
        // If the user doesn't want to specify a bogus char, then
        // then the default character of 'x' is used
        return encrypt(plaintext, 'x');
    }

    public String encrypt(String plaintext, char bogusChar) {
        // First, we pass the plaintext (which is converted to lowercase and removed of all whitespaces)
        // to the getLetterPairs() method to get an array of pairs of chars (using a 2d array)
        char[][] pairs = getLetterPairs(removeAllSpaces(plaintext.toLowerCase()), bogusChar);
        String encrypted = ""; // String that will hold the encrypted text

        // Iterate through every pair of letters
        for (char[] pair : pairs) {
            // The getEncryptedPair() method accepts an argument
            // of a char array for the letter pair
            // The method also returns a char array, which we then
            // pass to the String() constructor, which converts it
            // to a proper String
            encrypted += new String(getEncryptedPair(pair));
        }

        // Lastly, return the encrypted text
        return encrypted;
    }

    public String decrypt(String encrypted) {
        // Same as the encrypt() method, call getLetterPairs(), but this time we dont specify
        // a bogus character, since it is impossible for a properly encrypted text to need it
        char[][] pairs = getLetterPairs(removeAllSpaces(encrypted.toLowerCase()));
        String plaintext = ""; // String that will hold the plaintext (decrypted text)

        // Iterate through all pairs
        for (char[] pair : pairs) {
            // Same method as the encrypt() method
            // getDecryptPair() accepts the pair array
            // and returns a char array that is passed to the
            // String constructor to make a proper String
            plaintext += new String(getDecryptedPair(pair));
        }

        // Lastly, return the plaintext
        return plaintext;
    }

    // This method sets the key for cipher algorithm
    // using the provided key word
    public void setKey(String keyWord) {
        //  The keyword is first converted to lowercase
        String lowerKeyWord = keyWord.toLowerCase();

        // A char array is created which has enough capacity to
        // store the 25 characters required by the key
        char[] inlineKey = new char[25];

        // This variable keeps track of how many characters
        // have been inserted in the inlineKey[] array
        // since the .length field returns the total size
        // of the array and not the current amount of elements in it
        int inlineKeyCount = 0;

        // Iterate through every letter of the key word
        for (int i = 0; i < lowerKeyWord.length(); i++) {
            // Get the current char being iterated through
            char currentChar = lowerKeyWord.charAt(i);

            // If the character is not within the range of a-z
            // ignore it and continue to the next iteration
            if (currentChar < 'a' || currentChar > 'z') {
                continue;
            }

            // Because the 5x5 key can only hold 25 letters,
            // it is expected that the letter 'j' is treated as
            // the letter 'i'
            if (currentChar == 'j') {
                currentChar = 'i';
            }

            // If the current character is already in the inlineKey array,
            // then ignore it and continue to the next iteration
            if (isLetterInArray(inlineKey, currentChar)) {
                continue;
            }

            // Once it passed all previous checks, the current character
            // is inserted into the inlineKey array while also incrementing
            // the count variable by 1
            inlineKey[inlineKeyCount++] = currentChar;
        }

        // Iterate through every letter in the alphabet
        // defined in the static final field ALPHABET_LETTERS
        for (char letter : ALPHABET_LETTERS) {
            // If the current letter is not in the inlineKey array,
            // then add it to the array and increment the count variable
            // by 1
            if (!isLetterInArray(inlineKey, letter)) {
                inlineKey[inlineKeyCount++] = letter;
            }
        }

        // Lastly, we iterate through every character in the inlineKey array
        // and we add it to the key 2d array
        // The quotient of i and 5 dictates the row that the letter is placed in
        // While the remainder is used to determine the column that it is placed in.
        for (int i = 0; i < inlineKey.length; i++) {
            key[i / 5][i % 5] = inlineKey[i];
        }
    }

    public void printKey() {
        for (int row = 0; row < key.length; row++) {
            for (int col = 0; col < key[row].length; col++) {
                System.out.print(" " + Character.toUpperCase(key[row][col]));
            }
            System.out.println();
        }
    }

    private char[] getEncryptedPair(char[] pair) {
        // Get the row and column of both letters in the pair
        // using the getLetterPositionInKey() method
        int[] firstLetterPos = getLetterPositionInKey(pair[0]);
        int[] secondLetterPos = getLetterPositionInKey(pair[1]);

        // Char array to hold the encrypted pair of letters
        char[] encryptedPair = new char[2];

        // If both letters are located in the same row (index 0 of the position arrays)
        if (firstLetterPos[0] == secondLetterPos[0]) {
            // Set their encrypted letter to be the letter to their right in the key
            // The % 5 (modulo 5) is there in case the letter is at the end of the row
            // and has to loop back to the start of the row
            encryptedPair[0] = key[firstLetterPos[0]][(firstLetterPos[1] + 1) % 5];
            encryptedPair[1] = key[secondLetterPos[0]][(secondLetterPos[1] + 1) % 5];

            // Then, we return the encryptedPair array, which stops the execution
            // of the rest of the method
            return encryptedPair;
        }

        // If both letters are located in the same column (index 1 of the position arrays)
        if (firstLetterPos[1] == secondLetterPos[1]) {
            // Set their encrypted letter to be the letter below their position in the key
            // The % 5 (modulo 5) is there in case the letter is at the end of the column
            // and has to wrap back to the start of the column
            encryptedPair[0] = key[(firstLetterPos[0] + 1) % 5][firstLetterPos[1]];
            encryptedPair[1] = key[(secondLetterPos[0] + 1) % 5][secondLetterPos[1]];

            // Then, return the encryptedPair array, which stops the execution
            // of the rest of the method
            return encryptedPair;
        }

        // Lastly, if the letters are in different rows and columns,
        // Their encrypted letter is the letter in the same row
        // but in the column of the other letter
        encryptedPair[0] = key[firstLetterPos[0]][secondLetterPos[1]];
        encryptedPair[1] = key[secondLetterPos[0]][firstLetterPos[1]];

        // Then we return the encryptedPair
        return encryptedPair;
    }

    private char[] getDecryptedPair(char[] pair) {
        // Decrypting the pair is almost the same as
        // the method of encrypting pairs
        int[] firstLetterPos = getLetterPositionInKey(pair[0]);
        int[] secondLetterPos = getLetterPositionInKey(pair[1]);
        char[] decryptedPair = new char[2];

        // If both letters are in the same row in the key
        if (firstLetterPos[0] == secondLetterPos[0]) {
            // Instead of looking at the right of the character in the key
            // we look at their left, and making use of the Math.floorMod method
            // to properly handle decryptions that need to wrap back to the end of the row
            decryptedPair[0] = key[firstLetterPos[0]][Math.floorMod(firstLetterPos[1] - 1, 5)];
            decryptedPair[1] = key[secondLetterPos[0]][Math.floorMod(secondLetterPos[1] - 1, 5)];

            // We also return the decrypted pair here, which stops executing the rest of the method
            return decryptedPair;
        }

        // If both letters are in the same column in the key
        if (firstLetterPos[1] == secondLetterPos[1]) {
            // Same thing here, but for the column instead of the row
            decryptedPair[0] = key[Math.floorMod(firstLetterPos[0] - 1, 5)][firstLetterPos[1]];
            decryptedPair[1] = key[Math.floorMod(secondLetterPos[0] - 1, 5)][secondLetterPos[1]];

            // Again, we return here to stop the rest of the method from executing
            return decryptedPair;
        }

        // Else if the letters are not in the same row and column,
        // we look at the letter in the same row
        // but in the column of the other letter
        decryptedPair[0] = key[firstLetterPos[0]][secondLetterPos[1]];
        decryptedPair[1] = key[secondLetterPos[0]][firstLetterPos[1]];

        // Then we return the decrypted pair
        return decryptedPair;
    }

    private int[] getLetterPositionInKey(char letter) {
        // Iterate through every value in the 2d key array
        for (int row = 0; row < key.length; row++) {
            for (int col = 0; col < key[row].length; col++) {
                // If the current key being iterated through matches
                // the given letter
                if (key[row][col] == letter) {
                    // Return an integer array containing the row and column
                    // of the found letter
                    return new int[] {row, col};
                }
            }
        }

        // Added to stop my IDE from complaining
        // Theoretically, the program will never have to return -1
        return new int[] {-1, -1};
    }

    private static boolean isLetterInArray(char[] arr, char letter) {
        // Iterate through the arr given in the arguments
        for (int i = 0; i < arr.length; i++) {
            // If the iterated letter and the letter argument matches
            if (letter == arr[i]) {
                // Return true
                return true;
            }
        }

        // If the letter is not in the array, return false
        return false;
    }

    private static char[][] getLetterPairs(String text) {
        // Method overloading, makes the default bogus character 'x' if nothing is defined
        return getLetterPairs(text, 'x');
    }

    private static char[][] getLetterPairs(String text, char bogusChar) {
        // Uses ArrayList to store char arrays which is temporarily used as a storage for the pairs
        ArrayList<char[]> pairs = new ArrayList<char[]>();

        // Counter variable to track the current letter being checked in the text
        int i = 0;

        // While i has not reached the end of the text string
        while (i < text.length()) {
            // Store the first letter (the current letter) in this variable
            char firstLetter = text.charAt(i);

            // If the first letter is not in the range of a-z
            if (firstLetter < 'a' || firstLetter > 'z') {
                // increment the counter variable by 1 and start over from the top of the loop
                i++;
                continue;
            }

            if (firstLetter == 'j') {
                // If the first letter is the letter 'j'
                // treat it as the letter 'i'
                firstLetter = 'i';
            }

            // If this is the last character in the String,
            // then add the bogusChar as the pair for the first letter
            // then end the loop
            if (i == text.length() - 1) {
                pairs.add(new char[] {firstLetter, bogusChar});
                break;
            }

            // If it is still not the end of the string,
            // get the second letter using the next character in the String
            char secondLetter = text.charAt(i + 1);

            // If the character is not in the range a-z
            while (secondLetter < 'a' || secondLetter > 'z') {
                // Increment i by 1 then set the next letter as the second letter,
                // then the loop checks if the second letter is also a valid letter
                i++;
                secondLetter = text.charAt(i + 1);
            }

            // again, if the letter is 'j'
            if (secondLetter == 'j') {
                // set it to 'i'
                secondLetter = 'i';
            }

            // If the first and second letters are the same
            if (firstLetter == secondLetter) {
                // set the second letter as the bogus char
                // then increment the counter by 1
                secondLetter = bogusChar;
                i--;
            }

            // Once all checks are done, add the letters into a char array,
            // Then add it to the pairs ArrayList
            pairs.add(new char[] {firstLetter, secondLetter});

            // Lastly, we increment the counter by 2 to look at the next two letters
            i += 2;

        }

        // We then create an output 2d array containing all the pairs
        char[][] output = new char[pairs.size()][2];

        for (int j = 0; j < output.length; j++) {
            output[j] = pairs.get(j);
        }

        // Then we return it
        return output;

    }

    private static String removeAllSpaces(String text) {
        String noSpaceText = ""; // String that holds the text with the spaces removed

        // Iterate through every word in the text, which is split for every whitespace character in the text
        for (String word : text.split(" ")) {
            // The word is appended to the end of the String that holds the final text
            noSpaceText += word;
        }

        // Lastly, we return it
        return noSpaceText;
    }
}
