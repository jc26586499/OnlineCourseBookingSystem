package dao.student.impl;

import dao.student.PointTransactionsDao;
import model.PointTransactions;
import util.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PointTransactionsDaoImpl implements PointTransactionsDao {

    Connection connection = Tool.getDb();

    public static void main(String[] args) {

        PointTransactionsDao pointTransactionsDao = new PointTransactionsDaoImpl();

        int studentId = 1; // Amy = 1

        // 1) 學生哪些月份有交易
        System.out.println("交易有資料的月份");
        List<String> availableMonths = pointTransactionsDao.findAvailableMonths(studentId);
        for (String month : availableMonths) {
            System.out.println(month);
        }

        // 2) 查某月份明細
        if (!availableMonths.isEmpty()) {

            String yearMonth = availableMonths.get(0); // 取最新月份測試
            System.out.println("\n==== " + yearMonth + " 交易明細 ====");

            List<PointTransactions> list = pointTransactionsDao.findByStudentIdAndMonth(studentId, yearMonth);
            for (PointTransactions transaction : list) {
                System.out.println(
                        "id=" + transaction.getId() +
                        ", s_id=" + transaction.getS_id() +
                        ", amount_paid=" + transaction.getAmount_paid() +
                        ", points_added=" + transaction.getPoints_added() +
                        ", tx_date=" + transaction.getTx_date()
                );
            }

        } else {
            System.out.println("");
        }

        // 3) 測試加值
        System.out.println("test");
        int amountPaid = 500; // NT$500
        int pointsAdded = 50; // 1:10 => 50 點

        int updatedRows = pointTransactionsDao.addStudentPoints(studentId, pointsAdded);
        int insertedRows = pointTransactionsDao.insertTransaction(studentId, amountPaid, pointsAdded);

        System.out.println("addStudentPoints updatedRows = " + updatedRows);
        System.out.println("insertTransaction insertedRows = " + insertedRows);

        System.out.println("\n==== 加值後，再查一次最新月份明細 ====");
        List<String> availableMonthsAfterTopUp = pointTransactionsDao.findAvailableMonths(studentId);

        if (!availableMonthsAfterTopUp.isEmpty()) {
            String newestMonth = availableMonthsAfterTopUp.get(0);

            List<PointTransactions> listAfter = pointTransactionsDao.findByStudentIdAndMonth(studentId, newestMonth);
            for (PointTransactions transaction : listAfter) {
                System.out.println(
                        "id=" + transaction.getId() +
                        ", amount_paid=" + transaction.getAmount_paid() +
                        ", points_added=" + transaction.getPoints_added() +
                        ", tx_date=" + transaction.getTx_date()
                );
            }
        }
    }

    @Override
    public int insertTransaction(int studentId, int amountPaid, int pointsAdded) {

        String sql =
                "INSERT INTO point_transactions(s_id, amount_paid, points_added) " +
                "VALUES(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, amountPaid);
            preparedStatement.setInt(3, pointsAdded);

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<PointTransactions> findByStudentId(int studentId) {

        String sql =
                "SELECT id, s_id, amount_paid, points_added, tx_date " +
                "FROM point_transactions " +
                "WHERE s_id=? " +
                "ORDER BY tx_date DESC";

        List<PointTransactions> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PointTransactions pointTransactions = new PointTransactions();
                pointTransactions.setId(resultSet.getInt("id"));
                pointTransactions.setS_id(resultSet.getInt("s_id"));
                pointTransactions.setAmount_paid(resultSet.getInt("amount_paid"));
                pointTransactions.setPoints_added(resultSet.getInt("points_added"));
                pointTransactions.setTx_date(resultSet.getString("tx_date")); 
                list.add(pointTransactions);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    @Override
    public List<PointTransactions> findByStudentIdAndMonth(int studentId, String yearMonth) {

        String sql =
                "SELECT id, s_id, amount_paid, points_added, tx_date " +
                "FROM point_transactions " +
                "WHERE s_id=? " +
                "AND tx_date >= STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d') " +
                "AND tx_date < DATE_ADD(STR_TO_DATE(CONCAT(?, '-01'), '%Y-%m-%d'), INTERVAL 1 MONTH) " +
                "ORDER BY tx_date DESC";

        List<PointTransactions> list = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, yearMonth);
            preparedStatement.setString(3, yearMonth);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PointTransactions pointTransactions = new PointTransactions();
                pointTransactions.setId(resultSet.getInt("id"));
                pointTransactions.setS_id(resultSet.getInt("s_id"));
                pointTransactions.setAmount_paid(resultSet.getInt("amount_paid"));
                pointTransactions.setPoints_added(resultSet.getInt("points_added"));
                pointTransactions.setTx_date(resultSet.getString("tx_date"));
                list.add(pointTransactions);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    @Override
    public List<String> findAvailableMonths(int studentId) {

        String sql =
                "SELECT DATE_FORMAT(tx_date, '%Y-%m') AS month_key " +
                "FROM point_transactions " +
                "WHERE s_id=? " +
                "GROUP BY month_key " +
                "ORDER BY month_key DESC";

        List<String> monthList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                monthList.add(resultSet.getString("month_key"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return monthList;
    }

    @Override
    public int addStudentPoints(int studentId, int pointsAdded) {

        String sql =
                "UPDATE student " +
                "SET student_points = student_points + ? " +
                "WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, pointsAdded);
            preparedStatement.setInt(2, studentId);

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }
}
