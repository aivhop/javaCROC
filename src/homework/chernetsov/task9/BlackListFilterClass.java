package homework.chernetsov.task9;

import java.util.*;

public class BlackListFilterClass implements BlackListFilter {
    @Override
    public void filterComments(List<String> comments, Set<String> blackList) {
        if (comments != null && blackList != null && blackList.size() != 0) {
            Iterator<String> it = comments.iterator();
            while (it.hasNext()) {
                String[] words = it.next().split("[\\p{Punct}\s\\p{Cntrl}]+");
                nextComment:
                for (String word : words) {
                    for (String blackWord : blackList) {
                        if (blackWord.equalsIgnoreCase(word)) {
                            it.remove();
                            break nextComment;
                        }
                    }
                }
            }
        }
    }
}
