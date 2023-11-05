package homework.chernetsov.task9;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class BlackListFilterClass implements BlackListFilter {
    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {
        if (comments != null && blackList != null && blackList.isEmpty()) {
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
        int ind = refactorComment.indexOf(word);
        refactorComment.replace(ind, ind + word.length(), "*".repeat(word.length()));
        return refactorComment.toString();
    }

    private boolean isEqualOrMisspellIgnoreCase(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        return word1.equals(word2) || isMisspell(word1, word2);
    }

    private boolean isMisspell(String word1, String word2) {
        int differenceLength = Math.abs(word1.length() - word2.length());
        if (differenceLength < 2) {
            int countCriticalMisses = differenceLength;
            if (differenceLength == 1) {
                // если разница равна единице, возможно пользователь пропустил первую букву в слове
                // Example: ошка - кошка
                if (word1.length() > word2.length() && word1.substring(1).equals(word2) ||
                        word2.substring(1).equals(word1)) {
                    return true;
                }
            }
            for (int i = 0; i < Math.min(word1.length(), word2.length()) && countCriticalMisses < 2; i++) {
                if (word1.charAt(i) != word2.charAt(i)) { // если несовпадение
                    if (countCriticalMisses == 1 && differenceLength == 0) {
                        // если уже было несовпадение, а длины равны, возможно перепутан порядок двух букв,
                        // тогда нужно попробовать сравнить символ каждого слова с предыдущим другого
                        if (word1.charAt(i) == word2.charAt(i - 1) && word2.charAt(i) == word1.charAt(i - 1)) {
                            // тогда не нужно ничего делать, эта ошибка уже посчитана
                            continue;
                        }
                    } else if (differenceLength == 1) {
                        //возможно вставлена 1 лишняя буква
                        if (word1.length() > word2.length() && word1.substring(i+1).equals(word2.substring(i))||
                                word2.substring(i+1).equals(word1.substring(i))) {
                            return true;
                        }
                    }
                    countCriticalMisses++;
                }
            }
            return countCriticalMisses == 1; // истина если есть только 1 ошибка
        }
        return false;
    }
}
