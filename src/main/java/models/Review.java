package models;

/**
 * Created by Guest on 1/22/18.
 */
public class Review {

    private String writtenBy;
    private int rating;
    private int id;
    private int restaurantId;
    private String content;

    public Review(String writtenBy, int rating, String content, int restaurantId) {
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.restaurantId = restaurantId;
        this.content = content;

    }
    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (rating != review.rating) return false;
        if (id != review.id) return false;
        if (restaurantId != review.restaurantId) return false;
        if (writtenBy != null ? !writtenBy.equals(review.writtenBy) : review.writtenBy != null) return false;
        return content != null ? content.equals(review.content) : review.content == null;
    }

    @Override
    public int hashCode() {
        int result = writtenBy != null ? writtenBy.hashCode() : 0;
        result = 31 * result + rating;
        result = 31 * result + id;
        result = 31 * result + restaurantId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
