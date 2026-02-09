package dao.student;

import model.Student;

public interface StudentDao {

    // 登入成功回 Student（含 id、student_name、student_points），失敗回 null
    Student login(String account, String password);

    // 註冊前檢查帳號是否存在
    boolean existsAccount(String account);

    // 註冊（新增 student）
    int register(Student student);

    // 查詢目前點數
    int getStudentPoints(int studentId);

    // 扣點數：成功回 1；點數不足或找不到人回 0
    int deductStudentPoints(int studentId, int points);
}
