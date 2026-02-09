package service.teacher.impl;

import service.teacher.TeacherScheduleService;


import vo.teacher.TeacherScheduleDaoImpl;

import vo.student.ViewStudentSchedule;

import java.util.ArrayList;
import java.util.List;

public class TeacherScheduleServiceImpl implements TeacherScheduleService {
	
	public static void main(String[] args) {

        TeacherScheduleService service = new TeacherScheduleServiceImpl();
        int teacher_id = 1;

        System.out.println(service.findAvailableMonths(teacher_id));

        List<ViewStudentSchedule> list = service.findScheduleByMonth(teacher_id, null);
        for (ViewStudentSchedule row : list) {
            System.out.println(row.getLesson_time()
                    + " | student_id=" + row.getStudent_id()
                    + " | status=" + row.getBooking_status());
        }
    }

    private TeacherScheduleDaoImpl teacherScheduleDao = new TeacherScheduleDaoImpl();

    @Override
    public List<String> findAvailableMonths(int teacher_id) {

        if (teacher_id <= 0) return new ArrayList<>();

        return teacherScheduleDao.findAvailableMonthsByTeacher(teacher_id);
    }

    @Override
    public List<ViewStudentSchedule> findScheduleByMonth(int teacher_id, String yearMonth) {

        if (teacher_id <= 0) return new ArrayList<>();

        String finalMonth = resolveDefaultMonth(teacher_id, yearMonth);
        if (finalMonth == null) return new ArrayList<>();

        return teacherScheduleDao.findByTeacherAndMonth(teacher_id, finalMonth);
    }

    private String resolveDefaultMonth(int teacher_id, String yearMonth) {

        if (yearMonth != null && !yearMonth.trim().isEmpty()) {
            return yearMonth.trim();
        }

        List<String> months = teacherScheduleDao.findAvailableMonthsByTeacher(teacher_id);
        return months.isEmpty() ? null : months.get(0);
    }

    
}
