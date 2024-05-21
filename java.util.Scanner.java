import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();
        
        try {
            String result = calc(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
    }
    
    public static String calc(String input) throws Exception {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 3) {
            throw new Exception();
        }

        String num1 = parts[0];
        String operator = parts[1];
        String num2 = parts[2];

        boolean isRoman = RomanNumeral.isRoman(num1) && RomanNumeral.isRoman(num2);
        boolean isArabic = isArabic(num1) && isArabic(num2);

        if (isRoman) {
            int a = RomanNumeral.toArabic(num1);
            int b = RomanNumeral.toArabic(num2);
            int result = performOperation(a, b, operator);
            if (result < 1) {
                throw new Exception();
            }
            return RomanNumeral.toRoman(result);
        } else if (isArabic) {
            int a = Integer.parseInt(num1);
            int b = Integer.parseInt(num2);
            int result = performOperation(a, b, operator);
            return String.valueOf(result);
        } else {
            throw new Exception();
        }
    }

    private static boolean isArabic(String s) {
        try {
            int value = Integer.parseInt(s);
            return value >= 1 && value <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int performOperation(int a, int b, String operator) throws Exception {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new Exception();
        };
    }
}

class RomanNumeral {
    private static final String[] ROMAN_NUMERALS = {
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"
    };
    private static final int[] ARABIC_NUMERALS = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };

    public static boolean isRoman(String s) {
        for (String numeral : ROMAN_NUMERALS) {
            if (numeral.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static int toArabic(String roman) throws Exception {
        for (int i = 0; i < ROMAN_NUMERALS.length; i++) {
            if (ROMAN_NUMERALS[i].equals(roman)) {
                return ARABIC_NUMERALS[i];
            }
        }
        throw new Exception();
    }

    public static String toRoman(int number) throws Exception {
        if (number < 1) {
            throw new Exception();
        }

        StringBuilder result = new StringBuilder();
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanValues = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };

        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                number -= arabicValues[i];
                result.append(romanValues[i]);
            }
        }

        return result.toString();
    }
}