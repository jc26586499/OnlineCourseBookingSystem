package service.teacher;

import model.Teacher;

public interface TeacherLoginService {
    Teacher login(String account, String password);
}
