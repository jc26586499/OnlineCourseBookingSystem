package dao.teacher;

import model.Teacher;

public interface TeacherDao {
    Teacher login(String account, String password);
}
