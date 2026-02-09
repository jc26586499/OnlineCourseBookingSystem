package service.student.impl;

import dao.student.PointTransactionsDao;
import dao.student.StudentDao;
import dao.student.impl.PointTransactionsDaoImpl;
import dao.student.impl.StudentDaoImpl;
import model.Student;
import service.student.StudentService;

public class StudentServiceImpl implements StudentService {

    
    StudentDao studentDao = new StudentDaoImpl();
    PointTransactionsDao pointTransactionsDao = new PointTransactionsDaoImpl();

  
    public static void main(String[] args) {

        StudentService studentService = new StudentServiceImpl();

        System.out.println("");
        Student student = studentService.login("stu01", "123456");
        if (student == null) {
            System.out.println("登入失敗");
            return;
        }
        System.out.println("登入成功，studentId=" + student.getId() + " name=" + student.getStudent_name());

        System.out.println("\n getPoints 測試 ");
        int points = studentService.getPoints(student.getId());
        System.out.println("points=" + points);

        System.out.println("\n topUp ");
        boolean success = studentService.topUp(student.getId(), 500);
        System.out.println("topUp success" + success );

        System.out.println("\n topUp 後再查");
        int pointsAfter = studentService.getPoints(student.getId());
        System.out.println("pointsAfter=" + pointsAfter);
    }

    @Override
    public Student login(String account, String password) {

        if (account == null || account.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return studentDao.login(account.trim(), password.trim());
    }

    @Override
    public int getPoints(int studentId) {

        if (studentId <= 0) {
            return -1;
        }

        return studentDao.getStudentPoints(studentId);
    }

    @Override
    public boolean topUp(int studentId, int amountPaid) {

        if (studentId <= 0) {
            return false;
        }
        if (amountPaid <= 0) {
            return false;
        }

        // 1:10（
        int pointsAdded = amountPaid / 10;
        if (pointsAdded <= 0) {
            return false;
        }

        int updatedRows = pointTransactionsDao.addStudentPoints(studentId, pointsAdded);
        if (updatedRows <= 0) {
            return false;
        }

        int insertedRows = pointTransactionsDao.insertTransaction(studentId, amountPaid, pointsAdded);
        if (insertedRows <= 0) {
            return false;
        }

        return true;
    }
}
