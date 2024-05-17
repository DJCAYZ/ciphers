public class Ciphers {
    public static void main(String[] args) {
        PolybiusSquareCipher psCipher = new PolybiusSquareCipher();
        CaesarCipher cCipher = new CaesarCipher(5);
        PlayfairCipher pCipher = new PlayfairCipher("larkspur");

        String word = "geeksforgeeks";
        String encrypted;

        encrypted = psCipher.encrypt(word);

        System.out.println("'" + word + "' encrypted is: " + encrypted);
        System.out.println("decrypted: " + psCipher.decrypt(encrypted));



    }
}
