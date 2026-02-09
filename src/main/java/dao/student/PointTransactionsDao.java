package dao.student;

import java.util.List;
import model.PointTransactions;

public interface PointTransactionsDao {

    // 新增一筆儲值交易紀錄(point_transactions）
    int insertTransaction(int studentId, int amountPaid, int pointsAdded);

    // 學生全部交易紀錄
    List<PointTransactions> findByStudentId(int studentId);

    // 學生某月份交易紀錄
    List<PointTransactions> findByStudentIdAndMonth(int studentId, String yearMonth);

    //此學生有哪些月份的交易紀錄
    List<String> findAvailableMonths(int studentId);

    // 將學生點數加上指定點數
    int addStudentPoints(int studentId, int pointsAdded);
}
