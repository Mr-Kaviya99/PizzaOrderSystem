package model;
/**
 * author k2460782
 */

import java.util.ArrayList;
import java.util.List;

public class Feedback {
    private static int feedbackCounter = 1;
    private int feedbackId;
    private int orderId;
    private int rating;
    private String comments;
    private String timestamp;

    public Feedback(int orderId, int rating, String comments, String timestamp) {
        this.feedbackId = feedbackCounter++;
        this.orderId = orderId;
        this.rating = rating;
        this.comments = comments;
        this.timestamp = timestamp;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static class FeedbackManager {
        private static List<Feedback> feedbackList = new ArrayList<>();

        public static void addFeedback(Feedback feedback) {
            feedbackList.add(feedback);
            System.out.println("Feedback for pizza added successfully!");
        }

        public static List<Feedback> getFeedbackByPizzaId(int pizzaId) {
            List<Feedback> results = new ArrayList<>();
            for (Feedback feedback : feedbackList) {

                results.add(feedback);

            }
            return results;
        }

        public static double calculateAverageRatingByPizza(int pizzaId) {
            int count = 0;
            double totalRating = 0.0;
            for (Feedback feedback : feedbackList) {
                totalRating += feedback.getRating();
                count++;
            }

            return count > 0 ? totalRating / count : 0.0;
        }
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
