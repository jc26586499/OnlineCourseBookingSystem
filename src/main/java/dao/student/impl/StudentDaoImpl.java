package dao.student.impl;

import dao.student.StudentDao;
import model.Student;
import util.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao {

    Connection connection = Tool.getDb();

    public static void main(String[] args) {

        StudentDao studentDao = new StudentDaoImpl();

        
        System.out.println("test");
        Student student = studentDao.login("stu01", "123456"); 

        if (student != null) {
            System.out.println("登入成功");
            System.out.println("id = " + student.getId());
            System.out.println("account = " + student.getAccount());
            System.out.println("student_name = " + student.getStudent_name());
            System.out.println("student_points = " + student.getStudent_points());
        } else {
            System.out.println("登入失敗");
        }

        // 2) test
        System.out.println("");
        Student failLogin = studentDao.login("stu01", "wrong_password");

        if (failLogin == null) {
            System.out.println("登入失敗");
        } else {
            System.out.println("");
        }

        // 3) 測試查詢點數 
        System.out.println("");
        int studentId = 1; 
        int points = studentDao.getStudentPoints(studentId);
        System.out.println("studentId=" + studentId + " points=" + points);

        //  4)測試註冊
        /*
        System.out.println("");
        Student newStudent = new Student("stu_test", "1234", "TestUser", 0);

        boolean exists = studentDao.existsAccount(newStudent.getAccount());
        if (exists) {
            System.out.println("帳號已存在，無法註冊");
        } else {
            int inserted = studentDao.register(newStudent);
            System.out.println("register insertedRows = " + inserted);
        }
        */
    }

    @Override
    public Student login(String account, String password) {

        String sql =
                "SELECT id, account, password, student_name, student_points " +
                "FROM student WHERE account=? AND password=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setAccount(resultSet.getString("account"));
                student.setPassword(resultSet.getString("password"));
                student.setStudent_name(resultSet.getString("student_name"));
                student.setStudent_points(resultSet.getInt("student_points"));
                return student;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean existsAccount(String account) {

        String sql = "SELECT 1 FROM student WHERE account=? LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public int register(Student student) {

        String sql =
                "INSERT INTO student(account, password, student_name, student_points) " +
                "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, student.getAccount());
            preparedStatement.setString(2, student.getPassword());
            preparedStatement.setString(3, student.getStudent_name());
            preparedStatement.setInt(4, student.getStudent_points());

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getStudentPoints(int studentId) {

        String sql = "SELECT student_points FROM student WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("student_points");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return -1; // 查不到或錯誤
    }

    @Override
    public int deductStudentPoints(int studentId, int points) {

        // 防止扣到負數
        String sql =
                "UPDATE student " +
                "SET student_points = student_points - ? " +
                "WHERE id=? AND student_points >= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, points);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setInt(3, points);

            return preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }
}
