package service.teacher;

import vo.student.ViewStudentFeedback;
import java.util.List;

public interface TeacherFeedbackService {
    List<String> findAvailableMonths(int teacher_id);
    List<ViewStudentFeedback> findFeedbackByMonth(int teacher_id, String yearMonth);
}
