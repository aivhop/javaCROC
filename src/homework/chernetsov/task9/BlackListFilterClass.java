package homework.chernetsov.task9;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlackListFilterClass implements BlackListFilter {
    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {
        if (comments != null && blackList != null && !blackList.isEmpty()) {
            ListIterator<String> it = comments.listIterator();
            while (it.hasNext()) {
                String comment = it.next();
                if (comment == null) {
                    it.remove(); // удалим спам
                    continue;
                }
                String[] words = comment.split("[\\s\\p{Punct}\\p{Cntrl}]+");
                for (String word : words) {
                    for (String blackWord : blackList) {
                        if (blackWord != null && isEqualOrMisspellIgnoreCase(word, blackWord)) {
                            comment = modifyComment(comment, word);
                            it.set(comment);
                            break;
                        }
                    }
                }
            }
        }
    }

    private String modifyComment(String comment, String word) {
        StringBuilder refactorComment = new StringBuilder(comment);
        Pattern pattern = Pattern.compile("[\\s\\p{Punct}\\p{Cntrl}]"+word);
        Matcher matcher = pattern.matcher(comment);
        Boolean test = matcher.find();
        int ind = -1;
        try {
            ind = matcher.start() + 1;
        }catch(IllegalStateException ex){
            try{
                pattern = Pattern.compile(word + "[\\s\\p{Punct}\\p{Cntrl}]");
                matcher = pattern.matcher(comment);
                test = matcher.find();
                ind = matcher.start();
            } catch (IllegalStateException e){
                ind = comment.indexOf(word);
            }
        }

        refactorComment.replace(ind, ind + word.length(), "*".repeat(word.length()));
        return refactorComment.toString();
    }

    public boolean isEqualOrMisspellIgnoreCase(String word1, String word2) {
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
