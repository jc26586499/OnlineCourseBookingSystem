package dao.student;

import model.Feedback;

public interface FeedbackDao {

    int insertFeedback(Feedback feedback);

    boolean existsFeedback(int bookingId);

    Feedback findByBookingId(int bookingId);
}
