package service.teacher;

import vo.teacher.ViewTeacherRevenue;
import java.util.List;

public interface TeacherRevenueService {
    List<ViewTeacherRevenue> findRevenueRanking();
}
