package service;
/**
 * author k2460782
 */

import model.Feedback;

public interface FeedbackService {
    void submitFeedback(Feedback feedback);

    void deleteFeedback(Feedback feedback);
}
