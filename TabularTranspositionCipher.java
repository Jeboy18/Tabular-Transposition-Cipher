     import java.util.*;

public class TabularTranspositionCipher {

    // ENCRYPT
    public static String encrypt(String plaintext, int[] key) {
        int numCols = key.length;
        // key={3, 2, 1, 4} numcols=4
        // convert to uppercase and remove spaces
        plaintext = plaintext.toUpperCase().replaceAll("\\s+", "");
        //replace the plaintext to upppercase
        //remmove all sapaces

        int numRows = (int) Math.ceil((double) plaintext.length() / numCols);
        //computes how many rows are needed in the table
        //using celing para ma allocate ang incomplete row
        
        char[][] table = new char[numRows][numCols];

        // fill table row-wise
        int index = 0;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (index < plaintext.length()) {
                    table[r][c] = plaintext.charAt(index++);
                } else {
                    table[r][c] = 'X'; // padding
                }
            }
        }

        // show encryption table
        System.out.println("\nEncryption Table:");
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                System.out.print(table[r][c] + " ");
            }
            System.out.println();
        }

        // read by columns according to key
        StringBuilder ciphertext = new StringBuilder();
        for (int k = 0; k < key.length; k++) {
            int col = key[k] - 1;
            for (int r = 0; r < numRows; r++) {
                ciphertext.append(table[r][col]);
            }
        }
           return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int[] key) {
        // convert to uppercase and remove spaces
        ciphertext = ciphertext.toUpperCase().replaceAll("\\s+", "");
        int numCols = key.length;
        int numRows = (int) Math.ceil((double) ciphertext.length() / numCols);

        char[][] table = new char[numRows][numCols];

        // fill column-wise according to key
        int index = 0;
        for (int k = 0; k < key.length; k++) {
            int col = key[k] - 1;
            for (int r = 0; r < numRows; r++) {
                if (index < ciphertext.length()) {
                    table[r][col] = ciphertext.charAt(index++);
                }
            }
        }

        // show decryption table
        System.out.println("\nDecryption Table:");
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                System.out.print(table[r][c] + " ");
            }
            System.out.println();
        }

        // read row-wise to recover plaintext
        StringBuilder plaintext = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                plaintext.append(table[r][c]);
            }
        }
        return plaintext.toString().replaceAll("X+$", "");
    }
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean again = true;
        while (again) {
            // ask for message (spaces allowed)
            System.out.print("\nEnter message (letters and spaces allowed): ");
            String message = sc.nextLine().trim();
            if (!message.matches("[A-Za-z ]+")) {
                System.out.println("Error: Message must contain only letters and spaces.");
                continue;
            }

            // ask for key once
            System.out.print("Enter key (like 213 with no spaces): ");
            String keyString = sc.nextLine().trim();
            int[] key = new int[keyString.length()];
            boolean keyValid = true;
            for (int i = 0; i < keyString.length(); i++) {
                if (!Character.isDigit(keyString.charAt(i))) {
                    System.out.println("Error: Key must be digits only.");
                    keyValid = false;
                    break;
                }
                key[i] = Character.getNumericValue(keyString.charAt(i));
            }
            if (!keyValid) continue;

            // encrypt first
            String cipher = encrypt(message, key);
            System.out.println("Ciphertext: " + cipher);

            // ask if user wants to decrypt (trap: only Y or n)
            String answer;
            while (true) {
                System.out.print("Do you want to decrypt the message? (Y/n): ");
                answer = sc.nextLine().trim().toLowerCase();
                if (answer.equals("y") || answer.equals("n")) break;
                System.out.println("Invalid input. Please enter only Y or n.");
            }
            if (answer.equals("y")) {
                String plain = decrypt(cipher, key);
                System.out.println("Recovered Plaintext: " + plain);
            } else {
                System.out.println("Okay, skipping decryption.");
            }
            String againAnswer;
            while (true) {
                System.out.print("\nDo you want to encrypt another message? (Y/n): ");
                againAnswer = sc.nextLine().trim().toLowerCase();
                if (againAnswer.equals("y") || againAnswer.equals("n")) break;
                System.out.println("Invalid input. Please enter only Y or n.");
            }
            if (againAnswer.equals("n")) {
                again = false;
                System.out.println("Goodbye!");
            }
        }
        sc.close();
    }
}
