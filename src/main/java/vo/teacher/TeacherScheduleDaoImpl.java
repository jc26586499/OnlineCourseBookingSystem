package vo.teacher;

import util.Tool;
import vo.student.ViewStudentSchedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

interface TeacherScheduleDao {
    List<String> findAvailableMonthsByTeacher(int teacherId);
    List<ViewStudentSchedule> findByTeacherAndMonth(int teacherId, String yearMonth);
}

public class TeacherScheduleDaoImpl implements TeacherScheduleDao {

    public static void main(String[] args) {

        TeacherScheduleDao dao = new TeacherScheduleDaoImpl();

        int teacherId = 1;

        System.out.println("==== 老師端：可用月份 ====");
        List<String> months = dao.findAvailableMonthsByTeacher(teacherId);
        for (String month : months) {
            System.out.println(month);
        }

        String yearMonth = months.isEmpty() ? "2026-02" : months.get(0);

        System.out.println("\n==== 老師端：某月課表 ====");
        List<ViewStudentSchedule> list = dao.findByTeacherAndMonth(teacherId, yearMonth);

        for (ViewStudentSchedule row : list) {
            System.out.println("time=" + row.getLesson_time()
                    + ", student=" + row.getStudent_name()
                    + ", status=" + row.getBooking_status()
                    + ", zoom=" + row.getZoom_link());
        }
    }

    Connection connection = Tool.getDb();

    @Override
    public List<String> findAvailableMonthsByTeacher(int teacherId) {

        String sql =
                "SELECT DATE_FORMAT(lesson_time, '%Y-%m') AS month_key " +
                "FROM view_student_schedule " +
                "WHERE teacher_id=? " +
                "GROUP BY month_key " +
                "ORDER BY month_key DESC";

        return findMonths(sql, teacherId);
    }

    @Override
    public List<ViewStudentSchedule> findByTeacherAndMonth(int teacherId, String yearMonth) {

        String sql =
                "SELECT booking_id, student_id, student_name, teacher_id, teacher_name, " +
                "       lesson_time, booking_status, zoom_link " +
                "FROM view_student_schedule " +
                "WHERE teacher_id=? " +
                "  AND lesson_time >= STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d') " +
                "  AND lesson_time < DATE_ADD(STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d'), INTERVAL 1 MONTH) " +
                "ORDER BY lesson_time DESC";

        return queryListWithYearMonth(sql, teacherId, yearMonth);
    }

    // ===== 共用：月份清單（impl 可縮寫）=====
    private List<String> findMonths(String sql, int id) {

        List<String> monthList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                monthList.add(resultSet.getString("month_key"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return monthList;
    }

    // ===== 共用：某月課表（impl 可縮寫）=====
    private List<ViewStudentSchedule> queryListWithYearMonth(String sql, int id, String yearMonth) {

        List<ViewStudentSchedule> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, yearMonth);
            preparedStatement.setString(3, yearMonth);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ViewStudentSchedule row = new ViewStudentSchedule();

                row.setBooking_id(resultSet.getInt("booking_id"));
                row.setStudent_id(resultSet.getInt("student_id"));

                row.setStudent_name(resultSet.getString("student_name"));
                row.setTeacher_id(resultSet.getInt("teacher_id"));

                row.setTeacher_name(resultSet.getString("teacher_name"));
                row.setLesson_time(resultSet.getString("lesson_time"));
                row.setBooking_status(resultSet.getString("booking_status"));
                row.setZoom_link(resultSet.getString("zoom_link"));

                list.add(row);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }
}
