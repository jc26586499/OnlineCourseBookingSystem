package dao.student.impl;

import dao.student.BookingDao;
import util.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDaoImpl implements BookingDao {

   
    Connection connection = Tool.getDb();

  
    public static void main(String[] args) {

        BookingDao bookingDao = new BookingDaoImpl();

        Integer teacherId = bookingDao.findTeacherIdBySlotId(2);
        System.out.println("teacherId=" + teacherId);

        int locked = bookingDao.lockTeacherSlot(2);
        System.out.println("locked=" + locked);

        int inserted = bookingDao.insertBooking(
                1,          // studentId
                teacherId,  
                2,          // slotId
                "已預約",
                "https://zoom.us/test"
        );
        System.out.println("inserted=" + inserted);
    }

    @Override
    public Integer findTeacherIdBySlotId(int slotId) {

        String sql = "SELECT t_id FROM teacher_slots WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, slotId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("t_id");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public int lockTeacherSlot(int slotId) {

        // 防止多人同時搶同一時段
        String sql =
                "UPDATE teacher_slots " +
                "SET is_available=0 " +
                "WHERE id=? AND is_available=1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, slotId);

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    @Override
    public int insertBooking(int studentId,
                             int teacherId,
                             int slotId,
                             String status,
                             String zoomLink) {

        String sql =
                "INSERT INTO bookings(s_id, t_id, slot_id, status, zoom_link) " +
                "VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, teacherId);
            preparedStatement.setInt(3, slotId);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, zoomLink);

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }
}
