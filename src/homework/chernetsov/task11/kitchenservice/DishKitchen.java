package homework.chernetsov.task11.kitchenservice;

import homework.chernetsov.task11.exception.IncorrectRatingException;

import java.util.*;

public class DishKitchen extends Dish implements Comparable<DishKitchen> {

    private int ratingKing;
    private int ratingCourtiers;

    public DishKitchen(String label, List<String> ingredients, Category category,
                       int ratingKing, int ratingCourtiers) {
        super(label, ingredients, category);
        setRatingKing(ratingKing);
        setRatingCourtiers(ratingCourtiers);
    }


    @Override
    public String toString() {
        return super.toString() + ", rating King: " + ratingKing +
                ", rating Courtiers: " + ratingCourtiers;
    }


    @Override
    public int compareTo(DishKitchen o) {
        int compareKingRating = Integer.compare(ratingKing, o.ratingKing);
        if (compareKingRating == 0) {
            return Integer.compare(ratingCourtiers, o.ratingCourtiers);
        }
        return compareKingRating;

    }

    public void setRatingKing(int ratingKing) {
        checkRating(ratingKing);
        this.ratingKing = ratingKing;
    }

    public void setRatingCourtiers(int ratingCourtiers) {
        checkRating(ratingCourtiers);
        this.ratingCourtiers = ratingCourtiers;
    }


    public int getRatingKing() {
        return ratingKing;
    }

    public int getRatingCourtiers() {
        return ratingCourtiers;
    }

    private void checkRating(int rating) {
        if (rating > 100 || rating < 0) {
            throw new IncorrectRatingException(rating, 0, 100);
        }
    }
}
