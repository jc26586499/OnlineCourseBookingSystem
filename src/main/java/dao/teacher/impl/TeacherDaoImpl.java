package dao.teacher.impl;

import model.Teacher;
import util.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.teacher.TeacherDao;

public class TeacherDaoImpl implements TeacherDao {

    public static void main(String[] args) {

        TeacherDao dao = new TeacherDaoImpl();

        Teacher teacher = dao.login("t01", "123456"); 
        System.out.println(teacher == null ? "登入失敗" : "登入成功：" + teacher.getTeacher_name());
    }

    Connection connection = Tool.getDb();

    @Override
    public Teacher login(String account, String password) {

        String sql =
                "SELECT id, account, password, teacher_name, point_per_lesson " +
                "FROM teacher " +
                "WHERE account=? AND password=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setAccount(resultSet.getString("account"));
                teacher.setPassword(resultSet.getString("password"));
                teacher.setTeacher_name(resultSet.getString("teacher_name"));
                teacher.setPoint_per_lesson(resultSet.getInt("point_per_lesson"));

                return teacher;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
