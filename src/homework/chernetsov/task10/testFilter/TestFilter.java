package homework.chernetsov.task10.testFilter;


import homework.chernetsov.task10.BlackListFilter;
import homework.chernetsov.task9.BlackListFilterClass;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilter {
    @Test
    public void testEmptyComments() {
        List<String> comments = new ArrayList<>();

    }

    @Test
    public void testSomeExample() {
        List<String> comments = new ArrayList<>(Arrays.asList("кока А если пропущена буква", "Кот, собака, попугай?",
                "Кошка", "Допустим это первый комментарий, содержащий blackWord_+_+)_+( пусть это будет слово КОШКА",
                "А это второй комментарий, который не содержит такое слово",
                "А --это ?комментарий, ??&&/с какими-то+ +странными. .знаками. препинания., который не содержит слова",
                "А --это ?комментарий, ??&&/с какими-то+ +странными.КОТ.знаками. препинания., который содержитплохое слово",
                "A это проверка слова с другим регистром КоТ", "И это тоже кОт", "И это коТ",
                "А если добавить еще перенос строки,\n то считывание слов должно быть корректным",
                "А если добавить еще перенос строки,\nКошка\n", "А если добавить еще перенос строки,\n\n\tКоШкА\t ",
                "А это проверка опечаток. ког", "А это проверка опечаток. котт", "И это лошка", "И это ко",
                "И это коетнок", "И это ошка", "", "..", "А если некорректных слов несколько, кот например и кошка",
                "А это кошкка", null));
        List<String> result = new BlackListFilter(){}.filterComments(comments, this::myPredicate);

        ArrayList<String> expected = new ArrayList<>(Arrays.asList("А это второй комментарий, который не содержит такое слово",
                "А --это ?комментарий, ??&&/с какими-то+ +странными. .знаками. препинания., который не содержит слова",
                "А если добавить еще перенос строки,\n то считывание слов должно быть корректным",
                ".."));

        assertEquals(result.size(),expected.size());
        for(int i = 0; i < result.size(); i++){
            assertEquals(result.get(i),expected.get(i));
        }
    }
    private boolean myPredicate(String comment){
        Set<String> blackList = new HashSet<>(Arrays.asList("кот", "Кошка", "котенок", "котик", null));
        if (comment == null || comment.isEmpty()) { // в моем предикате спам тоже подлежит очистке
            return true;
        }
        String[] words = comment.split("[\\s\\p{Punct}\\p{Cntrl}]+");
        for (String word : words) {
            for (String blackWord : blackList) {
                if (blackWord != null && isEqualOrMisspellIgnoreCase(word, blackWord)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isEqualOrMisspellIgnoreCase(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        return word1.equals(word2) || isMisspell(word1, word2);
    }

    private boolean isMisspell(String word1, String word2) {
        String longerWord;
        String shorterWord;
        if (word1.length() > word2.length()) {
            longerWord = word1;
            shorterWord = word2;
        } else {
            longerWord = word2;
            shorterWord = word1;
        }
        int differenceLength = longerWord.length() - shorterWord.length();
        if (differenceLength > 1) {
            return false;
        }
        if (differenceLength == 1 && longerWord.substring(1).equals(shorterWord)) {
            // если разница равна единице, возможно пользователь пропустил первую букву в слове
            // Example: ошка - кошка
            return true;
        }
        return isMisspell(longerWord, shorterWord, differenceLength);
    }

    private boolean isMisspell(String longerWord, String shorterWord, int differenceLength) {
        int countCriticalMisses = differenceLength;
        for (int i = 0; i < Math.min(longerWord.length(), shorterWord.length()) && countCriticalMisses < 2; i++) {
            if (longerWord.charAt(i) != shorterWord.charAt(i)) { // если несовпадение
                if (countCriticalMisses == 1 && differenceLength == 0) {
                    // если уже было несовпадение, а длины равны, возможно перепутан порядок двух букв,
                    // тогда нужно попробовать сравнить символ каждого слова с предыдущим другого
                    if (longerWord.charAt(i) == shorterWord.charAt(i - 1) &&
                            shorterWord.charAt(i) == longerWord.charAt(i - 1)) {
                        // тогда не нужно ничего делать, эта ошибка уже посчитана
                        continue;
                    }
                } else if (differenceLength == 1) {
                    //возможно вставлена или пропущена 1 буква
                    return longerWord.substring(i + 1).equals(shorterWord.substring(i));
                }
                countCriticalMisses++;
            }
        }
        return countCriticalMisses == 1; // истина если есть только 1 ошибка
    }

}

