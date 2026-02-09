package vo.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Tool;

interface ViewStudentScheduleDao {
    // 根據學生ID查課表
    List<ViewStudentSchedule> findByStudentId(int studentId);
}

public class ViewStudentScheduleDaoImpl implements ViewStudentScheduleDao {

    public static void main(String[] args) {
        ViewStudentScheduleDaoImpl dao = new ViewStudentScheduleDaoImpl();
        List<ViewStudentSchedule> mySchedule = dao.findByStudentId(1); // ID:1 學生

        System.out.println("--- 我的課表 ---");
        for (ViewStudentSchedule schedule : mySchedule) {
            System.out.println("時間：" + schedule.getLesson_time() +
                               " | 老師：" + schedule.getTeacher_name() +
                               " | 狀態：" + schedule.getBooking_status() +
                               " | 連結：" + schedule.getZoom_link());
        }
    }

    Connection connection = Tool.getDb();

    @Override
    public List<ViewStudentSchedule> findByStudentId(int studentId) {

        String sql = "select * from view_student_schedule where student_id = ? order by lesson_time";
        List<ViewStudentSchedule> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ViewStudentSchedule viewStudentSchedule = new ViewStudentSchedule();
                viewStudentSchedule.setBooking_id(resultSet.getInt("booking_id"));
                viewStudentSchedule.setStudent_id(resultSet.getInt("student_id"));
                viewStudentSchedule.setStudent_name(resultSet.getString("student_name"));
                viewStudentSchedule.setTeacher_id(resultSet.getInt("teacher_id"));
                viewStudentSchedule.setTeacher_name(resultSet.getString("teacher_name"));
                viewStudentSchedule.setLesson_time(resultSet.getString("lesson_time"));
                viewStudentSchedule.setBooking_status(resultSet.getString("booking_status"));
                viewStudentSchedule.setZoom_link(resultSet.getString("zoom_link"));

                list.add(viewStudentSchedule);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }
}
