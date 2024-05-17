public class PlayfairCipher {
    private static final char[] ALPHABET_LETTERS = new char[] {
            'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'k',
            'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'
    };

    private final char[][] key = new char[5][5];

    public PlayfairCipher(String keyWord) {
        setKey(keyWord);
    }

    public String encrypt(String plaintext) {
        return "";
    }

    public String decrypt(String encrypted) {
        return "";
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
    }

    private int getLetterPositionInArray(char[] arr, char letter) {
        for (int i = 0; i < arr.length; i++) {
            if (letter == arr[i]) {
                return i;
            }
        }

        return -1;
    }


}
