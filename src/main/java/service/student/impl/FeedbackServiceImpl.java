package service.student.impl;

import dao.student.FeedbackDao;
import dao.student.impl.FeedbackDaoImpl;
import model.Feedback;
import service.student.FeedbackService;
import vo.student.ViewStudentFeedback;
import vo.student.ViewStudentFeedbackDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class FeedbackServiceImpl implements FeedbackService {

    FeedbackDao feedbackDao = new FeedbackDaoImpl();

    
    ViewStudentFeedbackDaoImpl viewStudentFeedbackDao = new ViewStudentFeedbackDaoImpl();

    
    public static void main(String[] args) {

        FeedbackService feedbackService = new FeedbackServiceImpl();

        int studentId = 1;

        // 1) 先拿可用月份
        List<String> availableMonths = feedbackService.getAvailableMonths(studentId);
        System.out.println("");
        for (String month : availableMonths) {
            System.out.println(month);
        }

        String yearMonth = availableMonths.isEmpty() ? "2026-02" : availableMonths.get(0);

        System.out.println("\n==== 可評價清單（已完成 + 未評價）====");
        List<ViewStudentFeedback> candidateList = feedbackService.listCandidateFeedback(studentId, yearMonth);
        for (ViewStudentFeedback row : candidateList) {
            System.out.println(
                    "booking_id=" + row.getBooking_id() +
                    ", teacher=" + row.getTeacher_name() +
                    ", time=" + row.getLesson_time()
            );
        }

        System.out.println("\n==== 已評價清單 ====");
        List<ViewStudentFeedback> ratedList = feedbackService.listRatedFeedback(studentId, yearMonth);
        for (ViewStudentFeedback row : ratedList) {
            System.out.println(
                    "booking_id=" + row.getBooking_id() +
                    ", rating=" + row.getRating() +
                    ", comment=" + row.getComment()
            );
        }

        
    }

    
    public List<String> getAvailableMonths(int studentId) {

        if (studentId <= 0) {
            return new ArrayList<>();
        }

        return viewStudentFeedbackDao.findAvailableMonthsByStudent(studentId);
    }

    @Override
    public List<ViewStudentFeedback> listCandidateFeedback(int studentId, String yearMonth) {

        if (studentId <= 0) {
            return new ArrayList<>();
        }
        if (yearMonth == null || yearMonth.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return viewStudentFeedbackDao.findCandidatesByStudentAndMonth(studentId, yearMonth.trim());
    }

    @Override
    public List<ViewStudentFeedback> listRatedFeedback(int studentId, String yearMonth) {

        if (studentId <= 0) {
            return new ArrayList<>();
        }
        if (yearMonth == null || yearMonth.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return viewStudentFeedbackDao.findRatedByStudentAndMonth(studentId, yearMonth.trim());
    }

    @Override
    public String submitFeedback(int studentId, int bookingId, int rating, String comment) {

        if (studentId <= 0) {
            return "送出失敗：studentId 不正確";
        }
        if (bookingId <= 0) {
            return "送出失敗：bookingId 不正確";
        }
        if (rating < 1 || rating > 5) {
            return "送出失敗：rating 必須是 1 ~ 5";
        }

        String commentValue = (comment == null) ? "" : comment.trim();

        // 1) 防止重複評價
        boolean exists = feedbackDao.existsFeedback(bookingId);
        if (exists) {
            return "送出失敗：此課程已評價過";
        }

        
        boolean isCandidate = isCandidateBooking(studentId, bookingId);
        if (!isCandidate) {
            return "送出失敗：此課程目前不可評價";
        }

        // 3) insert feedback
        Feedback feedback = new Feedback();
        feedback.setB_id(bookingId);
        feedback.setRating(rating);
        feedback.setComment(commentValue);

        int insertedRows = feedbackDao.insertFeedback(feedback);
        if (insertedRows <= 0) {
            return "送出失敗：寫入評價失敗";
        }

        return "送出成功：已完成評價";
    }

   
    private boolean isCandidateBooking(int studentId, int bookingId) {

        
        List<String> months = viewStudentFeedbackDao.findAvailableMonthsByStudent(studentId);
        for (String yearMonth : months) {
            List<ViewStudentFeedback> candidates = viewStudentFeedbackDao.findCandidatesByStudentAndMonth(studentId, yearMonth);
            for (ViewStudentFeedback row : candidates) {
                if (row.getBooking_id() == bookingId) {
                    return true;
                }
            }
        }
        return false;
    }
}
