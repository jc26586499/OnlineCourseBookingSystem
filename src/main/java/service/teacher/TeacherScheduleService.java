package service.teacher;

import vo.student.ViewStudentSchedule;
import java.util.List;

public interface TeacherScheduleService {
    List<String> findAvailableMonths(int teacher_id);
    List<ViewStudentSchedule> findScheduleByMonth(int teacher_id, String yearMonth);
}
