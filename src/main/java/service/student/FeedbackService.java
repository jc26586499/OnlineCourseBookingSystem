package service.student;

import java.util.List;

import vo.student.ViewStudentFeedback;

public interface FeedbackService {

    // 可評價清單（已完成 + 未評價）
    List<ViewStudentFeedback> listCandidateFeedback(int studentId, String yearMonth);

    // 已評價清單
    List<ViewStudentFeedback> listRatedFeedback(int studentId, String yearMonth);

    // 送出評價（rating 1~5）
    String submitFeedback(int studentId, int bookingId, int rating, String comment);
    
    //by 月份
    List<String> getAvailableMonths(int studentId);
}
