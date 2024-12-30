package model;
/**
 * author k2460782
 */

import java.util.ArrayList;
import java.util.List;

public class Feedback {
    private static int feedbackCounter = 1; // Auto-increment feedback ID
    private int feedbackId;
    private int orderId;
    private int rating; // Rating between 1 and 5
    private String comments;
    private String timestamp; // For simplicity, use a string representation of the timestamp

    // Constructor
    public Feedback(int orderId, int rating, String comments, String timestamp) {
        this.feedbackId = feedbackCounter++;
        this.orderId = orderId;
        this.rating = rating;
        this.comments = comments;
        this.timestamp = timestamp;
    }

    // Getters
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

    // Feedback Storage (In-Memory)
    public static class FeedbackManager {
        private static List<Feedback> feedbackList = new ArrayList<>();

        // Add feedback
        public static void addFeedback(Feedback feedback) {
            feedbackList.add(feedback);
            System.out.println("Feedback for pizza added successfully!");
        }

        // Get feedback by pizza ID
        public static List<Feedback> getFeedbackByPizzaId(int pizzaId) {
            List<Feedback> results = new ArrayList<>();
            for (Feedback feedback : feedbackList) {

                results.add(feedback);

            }
            return results;
        }

        // Calculate average rating for a pizza
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
