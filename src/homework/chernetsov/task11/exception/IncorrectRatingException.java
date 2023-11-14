package homework.chernetsov.task11.exception;

public class IncorrectRatingException extends RuntimeException {
    private final int rating;
    private final int minRating;
    private final int maxRating;

    public IncorrectRatingException(int rating, int minRating, int maxRating) {
        super("Sorry, the rating must be from " + minRating + " to " + maxRating + ", not " + rating);
        this.rating = rating;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public int getRating() {
        return rating;
    }

    public int getMinRating() {
        return minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }
}
