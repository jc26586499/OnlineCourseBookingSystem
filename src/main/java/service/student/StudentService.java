package service.student;

import model.Student;

public interface StudentService {

    Student login(String account, String password);

    int getPoints(int studentId);

    // amountPaid
    // 回傳：true = 成功 / false = 失敗
    boolean topUp(int studentId, int amountPaid);
}
