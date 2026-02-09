package dao.student.impl;

import dao.student.FeedbackDao;
import model.Feedback;
import util.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDaoImpl implements FeedbackDao {

 
    Connection connection = Tool.getDb();

    public static void main(String[] args) {

        FeedbackDao feedbackDao = new FeedbackDaoImpl();

        int bookingId = 1;

        System.out.println("==== existsFeedback 測試 ====");
        boolean exists = feedbackDao.existsFeedback(bookingId);
        System.out.println("bookingId=" + bookingId + " exists=" + exists);

        System.out.println("\n==== findByBookingId 測試 ====");
        Feedback feedback = feedbackDao.findByBookingId(bookingId);
        if (feedback == null) {
            System.out.println("查無評價");
        } else {
            System.out.println("id=" + feedback.getId()
                    + ", b_id=" + feedback.getB_id()
                    + ", rating=" + feedback.getRating()
                    + ", comment=" + feedback.getComment());
        }

       
    }

    @Override
    public int insertFeedback(Feedback feedback) {

        if (feedback == null) {
            return 0;
        }
        if (feedback.getRating() < 1 || feedback.getRating() > 5) {
            return 0;
        }

        String sql = "INSERT INTO feedback(b_id, rating, comment) VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, feedback.getB_id());
            preparedStatement.setInt(2, feedback.getRating());
            preparedStatement.setString(3, feedback.getComment());

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean existsFeedback(int bookingId) {

        String sql = "SELECT 1 FROM feedback WHERE b_id=? LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookingId);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public Feedback findByBookingId(int bookingId) {

        String sql = "SELECT id, b_id, rating, comment FROM feedback WHERE b_id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookingId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getInt("id"));
                feedback.setB_id(resultSet.getInt("b_id"));
                feedback.setRating(resultSet.getInt("rating"));
                feedback.setComment(resultSet.getString("comment"));
                return feedback;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
