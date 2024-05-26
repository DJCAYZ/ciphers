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
        setKey("");
    }

    public PlayfairCipher(String keyWord) {
        setKey(keyWord);
    }

    public String encrypt(String plaintext) {
        return encrypt(plaintext, 'x');
    }

    public String encrypt(String plaintext, char bogusChar) {
        char[][] pairs = getLetterPairs(removeAllSpaces(plaintext.toLowerCase()), bogusChar);
        String encrypted = "";

        for (char[] pair : pairs) {
            encrypted += new String(getEncryptedPair(pair));
        }

        return encrypted;
    }

    public String decrypt(String encrypted) {
        char[][] pairs = getLetterPairs(removeAllSpaces(encrypted.toLowerCase()));
        String plaintext = "";

        for (char[] pair : pairs) {
            plaintext += new String(getDecryptedPair(pair));
        }

        return plaintext;
    }

    public void setKey(String keyWord) {
        String lowerKeyWord = keyWord.toLowerCase();
        char[] inlineKey = new char[25];
        int inlineKeyCount = 0;

        for (int i = 0; i < lowerKeyWord.length(); i++) {
            char currentChar = lowerKeyWord.charAt(i);

            if (currentChar < 'a' || currentChar > 'z') {
                continue;
            }

            if (getLetterPositionInArray(inlineKey, currentChar) >= 0) {
                continue;
            }

            if (currentChar == 'j') {
                currentChar = 'i';
            }

            inlineKey[inlineKeyCount++] = currentChar;
        }

        for (char letter : ALPHABET_LETTERS) {
            if (getLetterPositionInArray(inlineKey, letter) == -1) {
                inlineKey[inlineKeyCount++] = letter;
            }
        }

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
        int[] firstLetterPos = getLetterPositionInKey(pair[0]);
        int[] secondLetterPos = getLetterPositionInKey(pair[1]);
        char[] encryptedPair = new char[2];

        if (firstLetterPos[0] == secondLetterPos[0]) {
            encryptedPair[0] = key[firstLetterPos[0]][(firstLetterPos[1] + 1) % 5];
            encryptedPair[1] = key[secondLetterPos[0]][(secondLetterPos[1] + 1) % 5];
            return encryptedPair;
        }

        if (firstLetterPos[1] == secondLetterPos[1]) {
            encryptedPair[0] = key[(firstLetterPos[0] + 1) % 5][firstLetterPos[1]];
            encryptedPair[1] = key[(secondLetterPos[0] + 1) % 5][secondLetterPos[1]];
            return encryptedPair;
        }

        encryptedPair[0] = key[firstLetterPos[0]][secondLetterPos[1]];
        encryptedPair[1] = key[secondLetterPos[0]][firstLetterPos[1]];
        return encryptedPair;
    }

    private char[] getDecryptedPair(char[] pair) {
        int[] firstLetterPos = getLetterPositionInKey(pair[0]);
        int[] secondLetterPos = getLetterPositionInKey(pair[1]);
        char[] decryptedPair = new char[2];

        if (firstLetterPos[0] == secondLetterPos[0]) {
            decryptedPair[0] = key[firstLetterPos[0]][Math.floorMod(firstLetterPos[1] - 1, 5)];
            decryptedPair[1] = key[secondLetterPos[0]][Math.floorMod(secondLetterPos[1] - 1, 5)];
            return decryptedPair;
        }

        if (firstLetterPos[1] == secondLetterPos[1]) {
            decryptedPair[0] = key[Math.floorMod(firstLetterPos[0] - 1, 5)][firstLetterPos[1]];
            decryptedPair[1] = key[Math.floorMod(secondLetterPos[0] - 1, 5)][secondLetterPos[1]];
            return decryptedPair;
        }

        decryptedPair[0] = key[firstLetterPos[0]][secondLetterPos[1]];
        decryptedPair[1] = key[secondLetterPos[0]][firstLetterPos[1]];
        return decryptedPair;
    }

    private int[] getLetterPositionInKey(char letter) {
        for (int row = 0; row < key.length; row++) {
            for (int col = 0; col < key[row].length; col++) {
                if (key[row][col] == letter) {
                    return new int[] {row, col};
                }
            }
        }

        return new int[] {-1, -1};
    }

    private static int getLetterPositionInArray(char[] arr, char letter) {
        for (int i = 0; i < arr.length; i++) {
            if (letter == arr[i]) {
                return i;
            }
        }

        return -1;
    }

    private static char[][] getLetterPairs(String text) {
        return getLetterPairs(text, 'x');
    }

    private static char[][] getLetterPairs(String text, char bogusChar) {
        ArrayList<char[]> pairs = new ArrayList<char[]>();
        int i = 0;

        while (i < text.length()) {
            char firstLetter = text.charAt(i);

            if (firstLetter < 'a' || firstLetter > 'z') {
                i++;
                continue;
            }

            if (firstLetter == 'j') {
                firstLetter = 'i';
            }

            if (i == text.length() - 1) {
                pairs.add(new char[] {firstLetter, bogusChar});
                break;
            }

            char secondLetter = text.charAt(i + 1);

            if (secondLetter < 'a' || secondLetter > 'z') {
                i++;
                secondLetter = text.charAt(i + 1);
            }

            if (firstLetter == secondLetter) {
                secondLetter = bogusChar;
                i--;
            }

            if (secondLetter == 'j') {
                secondLetter = 'i';
            }

            pairs.add(new char[] {firstLetter, secondLetter});
            i += 2;

        }

        char[][] output = new char[pairs.size()][2];

        for (int j = 0; j < output.length; j++) {
            output[j] = pairs.get(j);
        }

        return output;

    }

    private static String removeAllSpaces(String text) {
        String noSpaceText = "";
        for (String word : text.split(" ")) {
            noSpaceText += word;
        }

        return noSpaceText;
    }
}
