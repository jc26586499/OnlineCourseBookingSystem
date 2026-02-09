package vo.teacher;

import util.Tool;
import vo.student.ViewStudentFeedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

interface TeacherFeedbackDao {
    List<String> findAvailableMonthsByTeacher(int teacher_id);
    List<ViewStudentFeedback> findByTeacherAndMonth(int teacher_id, String yearMonth);
}

public class TeacherFeedbackDaoImpl implements TeacherFeedbackDao {

    public static void main(String[] args) {

        TeacherFeedbackDao dao = new TeacherFeedbackDaoImpl();
        int teacher_id = 1;

        System.out.println("老師端：可查看評價月份");
        List<String> months = dao.findAvailableMonthsByTeacher(teacher_id);
        for (String m : months) {
            System.out.println(m);
        }

        String yearMonth = months.isEmpty() ? "2026-02" : months.get(0);

        System.out.println("\n 老師端：某月收到的評價");
        List<ViewStudentFeedback> list = dao.findByTeacherAndMonth(teacher_id, yearMonth);

        for (ViewStudentFeedback f : list) {
            System.out.println(
                "時間：" + f.getLesson_time()
              + " | student_id：" + f.getStudent_id()
              + " | 分數：" + f.getRating()
              + " | 評論：" + f.getComment()
            );
        }
    }

    Connection conn = Tool.getDb();

    @Override
    public List<String> findAvailableMonthsByTeacher(int teacher_id) {

        String sql =
            "select date_format(lesson_time, '%Y-%m') as month_key " +
            "from view_student_feedback " +
            "where teacher_id = ? " +
            "  and feedback_id is not null " +
            "group by month_key " +
            "order by month_key desc";

        List<String> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, teacher_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("month_key"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ViewStudentFeedback> findByTeacherAndMonth(int teacher_id, String yearMonth) {

        String sql =
            "select booking_id, student_id, teacher_id, teacher_name, " +
            "       lesson_time, booking_status, feedback_id, rating, comment " +
            "from view_student_feedback " +
            "where teacher_id = ? " +
            "  and feedback_id is not null " +
            "  and lesson_time >= str_to_date(concat(?, '-01'), '%Y-%m-%d') " +
            "  and lesson_time <  date_add(str_to_date(concat(?, '-01'), '%Y-%m-%d'), interval 1 month) " +
            "order by lesson_time desc";

        List<ViewStudentFeedback> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, teacher_id);
            preparedStatement.setString(2, yearMonth);
            preparedStatement.setString(3, yearMonth);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ViewStudentFeedback row = new ViewStudentFeedback();

                row.setBooking_id(resultSet.getInt("booking_id"));
                row.setStudent_id(resultSet.getInt("student_id"));
                row.setTeacher_id(resultSet.getInt("teacher_id"));
                row.setTeacher_name(resultSet.getString("teacher_name"));
                row.setLesson_time(resultSet.getString("lesson_time"));
                row.setBooking_status(resultSet.getString("booking_status"));

                Object feedbackIdObject = resultSet.getObject("feedback_id");
                row.setFeedback_id(feedbackIdObject == null ? null : ((Number) feedbackIdObject).intValue());

                Object ratingObject = resultSet.getObject("rating");
                row.setRating(ratingObject == null ? null : ((Number) ratingObject).intValue());

                row.setComment(resultSet.getString("comment"));

                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
