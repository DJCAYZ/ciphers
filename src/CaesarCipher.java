public class CaesarCipher {
    private int shift;

    public CaesarCipher() {
        setShift(5);
    }

    public CaesarCipher(int shift) {
        setShift(shift);
    }

    public void setShift(int newShift) {
        this.shift = newShift;
    }

    public int getShift() {
        return shift;
    }

    public String encrypt(String plaintext) {
        String lowerPlaintext = plaintext.toLowerCase();
        String encrypted = "";

        for (int i = 0; i < lowerPlaintext.length(); i++) {
            if (lowerPlaintext.charAt(i) < 'a' || lowerPlaintext.charAt(i) > 'z') {
                encrypted += lowerPlaintext.charAt(i);
                continue;
            }

            encrypted += shiftLetter(lowerPlaintext.charAt(i));
        }

        return encrypted;
    }

    public String decrypt(String encryptedText) {
        String lowerEncrypted = encryptedText.toLowerCase();
        String plaintext = "";

        for (int i = 0; i < lowerEncrypted.length(); i++) {
            if (lowerEncrypted.charAt(i) < 'a' || lowerEncrypted.charAt(i) > 'z') {
                plaintext += lowerEncrypted.charAt(i);
                continue;
            }

            plaintext += unshiftLetter(lowerEncrypted.charAt(i));
        }

        return plaintext;
    }

    private char shiftLetter(char letter) {
         int shifted = letter + shift;

         if (shifted > 'z') {
             shifted -= 26;
         }

         return (char) shifted;
    }

    private char unshiftLetter(char letter) {
        int shifted = letter - shift;

        if (shifted < 'a') {
            shifted += 26;
        }

        return (char) shifted;
    }
}
