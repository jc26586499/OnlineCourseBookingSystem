	package vo.student;
	
	import util.Tool;
	
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;
	
	interface ViewStudentFeedbackDao {
	
	    //學生端
	    List<String> findAvailableMonthsByStudent(int studentId);
	
	    // 已完成 + 未評價
	    List<ViewStudentFeedback> findCandidatesByStudentAndMonth(int studentId, String yearMonth);
	
	    // 已評價
	    List<ViewStudentFeedback> findRatedByStudentAndMonth(int studentId, String yearMonth);
	
	    // 老師端
	    List<String> findAvailableMonthsByTeacher(int teacherId);
	
	    // 老師查某月收到的評價（feedback_id NOT NULL）
	    List<ViewStudentFeedback> findRatedByTeacherAndMonth(int teacherId, String yearMonth);
	}
	
	public class ViewStudentFeedbackDaoImpl implements ViewStudentFeedbackDao {
		
		 // 學生端 + 老師端
	    public static void main(String[] args) {
	
	        ViewStudentFeedbackDao dao = new ViewStudentFeedbackDaoImpl();
	
	        // 學生端測試 
	        int studentId = 1;
	
	        System.out.println("學生端：可用月份");
	        List<String> studentMonths = dao.findAvailableMonthsByStudent(studentId);
	        for (String month : studentMonths) {
	            System.out.println(month);
	        }
	
	        String studentYearMonth = studentMonths.isEmpty() ? "2026-02" : studentMonths.get(0);
	
	        System.out.println("\n 學生端：可評價（已完成 + 未評價）");
	        List<ViewStudentFeedback> candidates = dao.findCandidatesByStudentAndMonth(studentId, studentYearMonth);
	        if (candidates.isEmpty()) {
	            System.out.println("沒有可評價課程。");
	        } else {
	            for (ViewStudentFeedback row : candidates) {
	                System.out.println("booking_id=" + row.getBooking_id()
	                        + ", teacher=" + row.getTeacher_name()
	                        + ", time=" + row.getLesson_time());
	            }
	        }
	
	        System.out.println("\n==== 學生端：已評價清單 ====");
	        List<ViewStudentFeedback> ratedByStudent = dao.findRatedByStudentAndMonth(studentId, studentYearMonth);
	        if (ratedByStudent.isEmpty()) {
	            System.out.println("本月沒有已評價紀錄。");
	        } else {
	            for (ViewStudentFeedback row : ratedByStudent) {
	                System.out.println("booking_id=" + row.getBooking_id()
	                        + ", rating=" + row.getRating()
	                        + ", comment=" + row.getComment());
	            }
	        }
	
	        // 老師端測試
	        int teacherId = 1;
	
	        System.out.println("\n 可用月份");
	        List<String> teacherMonths = dao.findAvailableMonthsByTeacher(teacherId);
	        for (String month : teacherMonths) {
	            System.out.println(month);
	        }
	
	        String teacherYearMonth = teacherMonths.isEmpty() ? "2026-02" : teacherMonths.get(0);
	
	        System.out.println("\n某月評價");
	        List<ViewStudentFeedback> ratedByTeacher = dao.findRatedByTeacherAndMonth(teacherId, teacherYearMonth);
	        if (ratedByTeacher.isEmpty()) {
	            System.out.println("本月沒有收到評價。");
	        } else {
	            for (ViewStudentFeedback row : ratedByTeacher) {
	                System.out.println("booking_id=" + row.getBooking_id()
	                        + ", student_id=" + row.getStudent_id()
	                        + ", time=" + row.getLesson_time()
	                        + ", rating=" + row.getRating()
	                        + ", comment=" + row.getComment());
	            }
	        }
	    }
	
	    
	    Connection connection = Tool.getDb();
	
	    // 學生端 可用月份
	    @Override
	    public List<String> findAvailableMonthsByStudent(int studentId) {
	
	        String sql =
	                "SELECT DATE_FORMAT(lesson_time, '%Y-%m') AS month_key " +
	                "FROM view_student_feedback " +
	                "WHERE student_id=? " +
	                "AND booking_status='已完成' " +
	                "GROUP BY month_key " +
	                "ORDER BY month_key DESC";
	
	        return findMonths(sql, studentId);
	    }
	
	    // 學生端：可評價清單（已完成 + 未評價）
	    @Override
	    public List<ViewStudentFeedback> findCandidatesByStudentAndMonth(int studentId, String yearMonth) {
	
	        String sql =
	                "SELECT booking_id, student_id, teacher_id, teacher_name, lesson_time, booking_status, " +
	                "       feedback_id, rating, comment " +
	                "FROM view_student_feedback " +
	                "WHERE student_id=? " +
	                "  AND booking_status='已完成' " +
	                "  AND feedback_id IS NULL " +
	                "  AND lesson_time >= STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d') " +
	                "  AND lesson_time < DATE_ADD(STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d'), INTERVAL 1 MONTH) " +
	                "ORDER BY lesson_time DESC";
	
	        return queryListWithYearMonth(sql, studentId, yearMonth);
	    }
	
	//  學生端：已評價清單
	    @Override
	    public List<ViewStudentFeedback> findRatedByStudentAndMonth(int studentId, String yearMonth) {

	        
	        String sql =
	                "SELECT booking_id, student_id, teacher_id, teacher_name, lesson_time, booking_status, " +
	                "       feedback_id, rating, comment " +
	                "FROM view_student_feedback " +
	                "WHERE student_id=? " +                  // 第一個條件使用 WHERE
	                "  AND booking_status='已完成' " +        // 後續使用 AND
	                "  AND feedback_id IS NOT NULL " + 
	                "  AND lesson_time >= STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d') " +
	                "  AND lesson_time < DATE_ADD(STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d'), INTERVAL 1 MONTH) " +
	                "ORDER BY lesson_time DESC";

	        return queryListWithYearMonth(sql, studentId, yearMonth);
	    }
	
	    // 老師端：可用月份 
	    @Override
	    public List<String> findAvailableMonthsByTeacher(int teacherId) {
	
	        String sql =
	                "SELECT DATE_FORMAT(lesson_time, '%Y-%m') AS month_key " +
	                "FROM view_student_feedback " +
	                "WHERE teacher_id=? " +
	                "GROUP BY month_key " +
	                "ORDER BY month_key DESC";
	
	        return findMonths(sql, teacherId);
	    }
	
	    // 老師端：某月收到的評價
	    public List<ViewStudentFeedback> findRatedByTeacherAndMonth(int teacherId, String yearMonth) {
	
	        String sql =
	                "SELECT booking_id, student_id, teacher_id, teacher_name, lesson_time, booking_status, " +
	                "       feedback_id, rating, comment " +
	                "FROM view_student_feedback " +
	                "WHERE teacher_id=? " +
	                "  AND feedback_id IS NOT NULL " +
	                "  AND lesson_time >= STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d') " +
	                "  AND lesson_time < DATE_ADD(STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d'), INTERVAL 1 MONTH) " +
	                "ORDER BY lesson_time DESC";
	
	        return queryListWithYearMonth(sql, teacherId, yearMonth);
	    }
	
	    // ===== 共用：查月份清單 =====
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
	
	    // ===== 共用：查某月資料清單（學生 or 老師）=====
	    private List<ViewStudentFeedback> queryListWithYearMonth(String sql, int id, String yearMonth) {
	
	        List<ViewStudentFeedback> list = new ArrayList<>();
	
	        try {
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	
	            preparedStatement.setInt(1, id);
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
	
	        } catch (SQLException exception) {
	            exception.printStackTrace();
	        }
	
	        return list;
	    }
	
	   
	}
