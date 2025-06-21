import java.util.Scanner;

public class PalindromeChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input string
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        // Preprocess: Remove spaces and convert to lowercase
        String cleanedInput = input.replaceAll("\\s+", "").toLowerCase();

        // Check if it's a palindrome
        boolean isPalindrome = true;
        int len = cleanedInput.length();

        for (int i = 0; i < len / 2; i++) {
            if (cleanedInput.charAt(i) != cleanedInput.charAt(len - 1 - i)) {
                isPalindrome = false;
                break;
            }
        }

        // Output result
        if (isPalindrome) {
            System.out.println("\"" + input + "\" is a palindrome.");
        } else {
            System.out.println("\"" + input + "\" is not a palindrome.");
        }

        scanner.close();
    }
}
