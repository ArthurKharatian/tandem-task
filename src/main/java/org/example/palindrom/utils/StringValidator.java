package org.example.palindrom.utils;


public class StringValidator {

    private StringValidator() {
    }

    /**
     *
     * @param phrase Текст для валидации
     * @return Признак того, что слово является палиндромом
     */

    public static boolean isPalindrome(String phrase) {
        phrase = noSpacePhrase(phrase);
        String reversedText = String.valueOf(new StringBuilder(phrase).reverse());
        return phrase.equals(reversedText);
    }

    /**
     * Удаляет все пробелы
     * @param phrase Текст в котором необходимо убрать все пробелы
     * @return Строка без пробелов
     */
    public static String noSpacePhrase(String phrase) {
        return phrase.replace(" ", "").toLowerCase();
    }

}
