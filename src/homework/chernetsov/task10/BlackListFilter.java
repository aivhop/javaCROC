package homework.chernetsov.task10;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public interface BlackListFilter {
    default <T> List<T> filterComments(Iterable<T> comments, Predicate<T> isBlack){
        Objects.requireNonNull(comments);
        Objects.requireNonNull(isBlack);

        ArrayList<T> filteredComments = new ArrayList<>();

        Iterator<T> itComments = comments.iterator();
        while(itComments.hasNext()){
            T comment = itComments.next();
            if(!isBlack.test(comment)){
                filteredComments.add(comment);
            }
        }
        return filteredComments;
    }
}

