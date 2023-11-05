package homework.chernetsov.task9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowTask {
    public static void main(String[] args) {
        BlackListFilterClass myFilter = new BlackListFilterClass();

        List<String> comments = new ArrayList<>();
        comments.add("Кот, собака, попугай?");
        comments.add("Кошка");
        Set<String> blackList = new HashSet<>();
        blackList.add("кот");
        blackList.add("Кошка");
        blackList.add("котенок");
        blackList.add("котик");
        myFilter.filterComments(comments,blackList);
        for(String comment: comments){
            System.out.println(comment);
        }
        comments.clear();


        comments.add("Допустим это первый комментарий, содержащий blackWord_+_+)_+( пусть это будет слово КОШКА");
        comments.add("А это второй комментарий, который не содержит такое слово");
        comments.add("А --это ?комментарий, ??&&/с какими-то+ +странными. .знаками. препинания., который не содержит слова");
        comments.add("А --это ?комментарий, ??&&/с какими-то+ +странными.КОТ.знаками. препинания., который содержитплохое слово");
        comments.add("A это проверка слова с другим регистром КоТ");
        comments.add("И это тоже кОт");
        comments.add("И это коТ");
        comments.add("А если добавить еще перенос строки,\n то считывание слов должно быть корректным");
        comments.add("А если добавить еще перенос строки,\nКошка\n");
        comments.add("А если добавить еще перенос строки,\n\n\tКоШкА\t ");

        myFilter.filterComments(comments,blackList);
        for(String comment: comments){
            System.out.println(comment);
        }
    }
}
