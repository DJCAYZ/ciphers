public class Ciphers {
    public static void main(String[] args) {
        PlayfairCipher pCipher = new PlayfairCipher("primrose");

        String plaintext = "hike the foothills";
        String encrypted = "ILMILDRKRY";

        System.out.println(plaintext + " encrypted is: " + pCipher.encrypt(plaintext));

        pCipher.setKey("larkspur");

        System.out.println(encrypted + " decrypted is: " + pCipher.decrypt(encrypted));
    }
}
