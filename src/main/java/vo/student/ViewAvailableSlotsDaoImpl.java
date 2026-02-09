package vo.student;

import util.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

interface ViewAvailableSlotsDao {

    // 查全部可預約時段
    List<ViewAvailableSlots> findAll();

    // 依點數區間查詢
    List<ViewAvailableSlots> findByCostRange(int minimumCost, int maximumCost);

    // 依月份查詢，例如 "2026-02"
    List<ViewAvailableSlots> findByMonth(String yearMonth);
}

public class ViewAvailableSlotsDaoImpl implements ViewAvailableSlotsDao {
	
	// 測試
    public static void main(String[] args) {

        ViewAvailableSlotsDao dao = new ViewAvailableSlotsDaoImpl();

        System.out.println("==== 全部可預約時段 ====");
        List<ViewAvailableSlots> allList = dao.findAll();
        for (ViewAvailableSlots row : allList) {
            System.out.println(
                    "slot_id=" + row.getSlot_id() +
                    ", teacher=" + row.getTeacher_name() +
                    ", cost=" + row.getCost() +
                    ", time=" + row.getStart_time()
            );
        }

        System.out.println("\n 點數 80 ~ 90");
        List<ViewAvailableSlots> rangeList = dao.findByCostRange(80, 90);
        for (ViewAvailableSlots row : rangeList) {
            System.out.println(
                    "slot_id=" + row.getSlot_id() +
                    ", teacher=" + row.getTeacher_name() +
                    ", cost=" + row.getCost() +
                    ", time=" + row.getStart_time()
            );
        }

        System.out.println("\n==== 2026-02 ====");
        List<ViewAvailableSlots> monthList = dao.findByMonth("2026-02");
        for (ViewAvailableSlots row : monthList) {
            System.out.println(
                    "slot_id=" + row.getSlot_id() +
                    ", teacher=" + row.getTeacher_name() +
                    ", cost=" + row.getCost() +
                    ", time=" + row.getStart_time()
            );
        }
    }

    Connection connection = Tool.getDb();

    @Override
    public List<ViewAvailableSlots> findAll() {

        String sql =
                "SELECT slot_id, teacher_name, cost, start_time " +
                "FROM view_available_slots " +
                "ORDER BY start_time";

        List<ViewAvailableSlots> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ViewAvailableSlots row = new ViewAvailableSlots();
                row.setSlot_id(resultSet.getInt("slot_id"));
                row.setTeacher_name(resultSet.getString("teacher_name"));
                row.setCost(resultSet.getInt("cost"));
                row.setStart_time(resultSet.getString("start_time"));

                list.add(row);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ViewAvailableSlots> findByCostRange(int minimumCost, int maximumCost) {

        String sql =
                "SELECT slot_id, teacher_name, cost, start_time " +
                "FROM view_available_slots " +
                "WHERE cost >= ? AND cost <= ? " +
                "ORDER BY start_time";

        List<ViewAvailableSlots> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, minimumCost);
            preparedStatement.setInt(2, maximumCost);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ViewAvailableSlots row = new ViewAvailableSlots();
                row.setSlot_id(resultSet.getInt("slot_id"));
                row.setTeacher_name(resultSet.getString("teacher_name"));
                row.setCost(resultSet.getInt("cost"));
                row.setStart_time(resultSet.getString("start_time"));

                list.add(row);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ViewAvailableSlots> findByMonth(String yearMonth) {

        String sql =
                "SELECT slot_id, teacher_name, cost, start_time " +
                "FROM view_available_slots " +
                "WHERE start_time LIKE ? " +
                "ORDER BY start_time";

        List<ViewAvailableSlots> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, yearMonth + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                ViewAvailableSlots row = new ViewAvailableSlots();
                row.setSlot_id(resultSet.getInt("slot_id"));
                row.setTeacher_name(resultSet.getString("teacher_name"));
                row.setCost(resultSet.getInt("cost"));
                row.setStart_time(resultSet.getString("start_time"));

                list.add(row);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    
}
