package service.teacher.impl;

import vo.student.ViewStudentFeedback;

import vo.teacher.TeacherFeedbackDaoImpl;

import java.util.ArrayList;
import java.util.List;

import service.teacher.TeacherFeedbackService;

public class TeacherFeedbackServiceImpl implements TeacherFeedbackService {
	
	public static void main(String[] args) {

        TeacherFeedbackService service = new TeacherFeedbackServiceImpl();
        int teacher_id = 1;

        System.out.println("可用月份 ");
        System.out.println(service.findAvailableMonths(teacher_id));

        System.out.println("最新月份評價 ");
        List<ViewStudentFeedback> list = service.findFeedbackByMonth(teacher_id, null);

        if (list.isEmpty()) {
            System.out.println("本月沒有收到評價");
        }

        for (ViewStudentFeedback f : list) {
            System.out.println(
                f.getLesson_time()
              + " | student_id=" + f.getStudent_id()
              + " | rating=" + f.getRating()
              + " | comment=" + f.getComment()
            );
        }
    }
	
    TeacherFeedbackDaoImpl teacherFeedbackDao = new TeacherFeedbackDaoImpl();

    @Override
    public List<String> findAvailableMonths(int teacher_id) {

        if (teacher_id <= 0) return new ArrayList<>();
        return teacherFeedbackDao.findAvailableMonthsByTeacher(teacher_id);
    }

    @Override
    public List<ViewStudentFeedback> findFeedbackByMonth(int teacher_id, String yearMonth) {

        if (teacher_id <= 0) return new ArrayList<>();

        String finalMonth = resolveDefaultMonth(teacher_id, yearMonth);
        if (finalMonth == null) return new ArrayList<>();

        return teacherFeedbackDao.findByTeacherAndMonth(teacher_id, finalMonth);
    }

    private String resolveDefaultMonth(int teacher_id, String yearMonth) {

        if (yearMonth != null && !yearMonth.trim().isEmpty()) {
            return yearMonth.trim();
        }

        List<String> months = teacherFeedbackDao.findAvailableMonthsByTeacher(teacher_id);
        return months.isEmpty() ? null : months.get(0);
    }

   
    
}
