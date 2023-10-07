package org.example.palindrom.utils;


public class PalindromeValidator {

    private PalindromeValidator() {
    }

    /**
     *
     * @param text Текст для валидации
     * @return Признак того, что слово является палиндромом
     */

    public static boolean isPalindrome(String text) {
        text = text.replace(" ", "").toLowerCase();
        String reversedText = String.valueOf(new StringBuilder(text).reverse());
        return text.equals(reversedText);
    }

}
