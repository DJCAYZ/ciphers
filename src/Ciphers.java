import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ciphers {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CaesarCipher caesarCipher = new CaesarCipher();
        PlayfairCipher playfairCipher = new PlayfairCipher();
        PolybiusSquareCipher polybiusSquareCipher = new PolybiusSquareCipher();
        boolean running = true;
        int programState = 0; // 0 for menu, 1 for caesar, 2 for playfair, 3 for polybius
        String userInput;

        while (running) {
            switch(programState) {
                case 0:
                    System.out.println("Encryption Algorithms\n");
                    System.out.println("C - Caesar Cipher");
                    System.out.println("P - Playfair Cipher");
                    System.out.println("S - Polybius Square Cipher");
                    System.out.println("E - Exit");
                    System.out.print("Select Encryption Algorithm: ");

                    userInput = input.nextLine();

                    if(userInput.isBlank()) {
                        System.out.println("Invalid input. Try again\n");
                        continue;
                    }

                    switch(userInput.toLowerCase().charAt(0)) {
                        case 'c':
                            programState = 1;
                            break;
                        case 'p':
                            programState = 2;
                            break;
                        case 's':
                            programState = 3;
                            break;
                        case 'e':
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid input. Try again\n");
                    }
                    break;
                case 1:
                    System.out.println("Caesar Cipher");
                    System.out.println("Current Shift Amount: " + caesarCipher.getShift() + "\n");
                    System.out.println("Operations:");
                    System.out.println("E - Encrypt");
                    System.out.println("D - Decrypt");
                    System.out.println("S - Set Shift Amount");
                    System.out.println("B - Back to Main Menu");
                    System.out.print("Select operation: ");
                    userInput = input.nextLine();

                    if (userInput.isEmpty()) {
                        System.out.println("Invalid input. Try again\n");
                        continue;
                    }

                    String ccPlaintext;
                    String ccEncrypted;

                    switch(userInput.toLowerCase().charAt(0)) {
                        case 'e':
                            System.out.print("Enter plaintext: ");

                            ccPlaintext = input.nextLine();
                            ccEncrypted = caesarCipher.encrypt(ccPlaintext);

                            System.out.println("'" + ccPlaintext.toUpperCase() + "' encrypted using Caesar Cipher is '" + ccEncrypted.toUpperCase() + "'");
                            System.out.print("Press ENTER to continue");
                            input.nextLine();
                            break;
                        case 'd':
                            System.out.print("Enter encrypted text: ");

                            ccEncrypted = input.nextLine();
                            ccPlaintext = caesarCipher.decrypt(ccEncrypted);

                            System.out.println("'" + ccEncrypted.toUpperCase() + "' decrypted using Caesar Cipher is '" + ccPlaintext.toUpperCase() + "'");
                            System.out.print("Press ENTER to continue");
                            input.nextLine();
                            break;
                        case 's':
                            int amountToShift;
                            do {
                                System.out.print("Enter amount to shift (1-25): ");

                                while (!input.hasNextInt()) {
                                    System.out.println("Invalid input. Try again");
                                    input.next();
                                    System.out.print("Enter amount to shift (1-25): ");
                                }

                                amountToShift = input.nextInt();
                            } while (amountToShift < 1 || amountToShift > 25);

                            caesarCipher.setShift(amountToShift);
                            break;
                        case 'b':
                            programState = 0;
                            break;
                        default:
                            System.out.println("Invalid input. Try again");
                    }
                    break;
                case 2:
                    System.out.println("Playfair Cipher");
                    System.out.println("Encryption Key:");
                    playfairCipher.printKey();
                    System.out.println("Operations:");
                    System.out.println("E - Encrypt");
                    System.out.println("D - Decrypt");
                    System.out.println("S - Set Encryption Key Word");
                    System.out.println("B - Back to Main Menu");
                    System.out.print("Select operation: ");
                    userInput = input.nextLine();

                    if (userInput.isEmpty()) {
                        System.out.println("Invalid input. Try again\n");
                        continue;
                    }

                    String pcPlaintext;
                    String pcEncrypted;

                    switch(userInput.toLowerCase().charAt(0)) {
                        case 'e':
                            System.out.print("Enter plaintext: ");

                            pcPlaintext = input.nextLine();
                            pcEncrypted = playfairCipher.encrypt(pcPlaintext);

                            System.out.println("'" + pcPlaintext.toUpperCase() + "' encrypted using Playfair Cipher is '" + pcEncrypted.toUpperCase() + "'");
                            System.out.print("Press ENTER to continue");
                            input.nextLine();
                            break;
                        case 'd':
                            System.out.print("Enter encrypted text: ");

                            pcEncrypted = input.nextLine();
                            pcPlaintext = playfairCipher.decrypt(pcEncrypted);

                            System.out.println("'" + pcEncrypted.toUpperCase() + "' decrypted using Playfair Cipher is '" + pcPlaintext.toUpperCase() + "'");
                            System.out.print("Press ENTER to continue");
                            input.nextLine();
                            break;
                        case 's':
                            String newKeyWord;
                            do {
                                System.out.print("Enter new encryption key word: ");

                                newKeyWord = input.nextLine();

                            } while(newKeyWord.isEmpty());

                            playfairCipher.setKey(newKeyWord);
                            break;
                        case 'b':
                            programState = 0;
                            break;
                        default:
                            System.out.println("Invalid input. Try again");
                    }
                    break;
                case 3:
                    System.out.println("Polybius Square Cipher");
                    System.out.println("Encryption Key: ");
                    polybiusSquareCipher.printKey();
                    System.out.println("Operations:");
                    System.out.println("E - Encrypt");
                    System.out.println("D - Decrypt");
                    System.out.println("B - Back to Main Menu");
                    System.out.print("Select operation: ");
                    userInput = input.nextLine();

                    if (userInput.isEmpty()) {
                        System.out.println("Invalid input. Try again\n");
                        continue;
                    }

                    String pscPlaintext;
                    String pscEncrypted;

                    switch(userInput.toLowerCase().charAt(0)) {
                        case 'e':
                            System.out.print("Enter plaintext: ");

                            pscPlaintext = input.nextLine();
                            pscEncrypted = polybiusSquareCipher.encrypt(pscPlaintext);

                            System.out.println("'" + pscPlaintext.toUpperCase() + "' encrypted with Polybius Square Cipher is '" + pscEncrypted.toUpperCase() + "'");
                            System.out.print("Press ENTER to continue");
                            input.nextLine();
                            break;
                        case 'd':
                            boolean validEncryptedText = false;

                            do {
                                System.out.print("Enter encrypted text (numbers only): ");
                                pscEncrypted = input.nextLine();

                                Matcher matcher = Pattern.compile("^\\d+$").matcher(pscEncrypted);

                                validEncryptedText = matcher.matches();

                            } while (!validEncryptedText);

                            pscPlaintext = polybiusSquareCipher.decrypt(pscEncrypted);

                            System.out.println("'" + pscEncrypted.toUpperCase() + "' decrypted with Polybius Square Cipher is '" + pscPlaintext.toUpperCase() + "'");
                            System.out.print("Press ENTER to continue");
                            input.nextLine();
                        case 'b':
                            programState = 0;
                            break;
                        default:
                            System.out.println("Invalid input. Try again");
                    }
            }
            System.out.println();

        }

    }
}
