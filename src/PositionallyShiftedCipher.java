import java.util.Scanner;

public class PositionallyShiftedCipher {

    public String encrypt(String plaintext) {
        String cleanPlaintext = plaintext.trim().toLowerCase();
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < cleanPlaintext.length(); i++) {
            char currentLetter = cleanPlaintext.charAt(i);

            if (currentLetter < 'a' || currentLetter > 'z') {
                encryptedText.append(currentLetter);
                continue;
            }

            encryptedText.append(getShiftedLetter(currentLetter, i + 1));
        }

        return encryptedText.toString();
    }

    public String decrypt(String encryptedText) {
        String cleanEncryptedText = encryptedText.trim().toLowerCase();
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < cleanEncryptedText.length(); i++) {
            char currentLetter = cleanEncryptedText.charAt(i);

            if (currentLetter < 'a' || currentLetter > 'z') {
                plaintext.append(currentLetter);
                continue;
            }

            plaintext.append(getUnshiftedLetter(currentLetter, i + 1));
        }

        return plaintext.toString();
    }

    private char getShiftedLetter(char letter, int position) {
        int shifted = ((letter - 'a' + position) % 26 + 26) % 26 + 'a';
        return (char) shifted;
    }

    private char getUnshiftedLetter(char letter, int position) {
        int unshifted = ((letter - 'a' - position) % 26 + 26) % 26 + 'a';
        return (char) unshifted;
    }
}