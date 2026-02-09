package service.teacher.impl;

import model.Teacher;
import service.teacher.TeacherLoginService;
import dao.teacher.TeacherDao;
import dao.teacher.impl.TeacherDaoImpl;

public class TeacherLoginServiceImpl implements TeacherLoginService {
	
	public static void main(String[] args) {

        TeacherLoginService service = new TeacherLoginServiceImpl();

        Teacher teacher = service.login("t01", "123456");

        if (teacher == null) {
            System.out.println("登入失敗");
        } else {
            System.out.println("登入成功");
            System.out.println("teacher_id=" + teacher.getId());
            System.out.println("teacher_name=" + teacher.getTeacher_name());
        }
    }

    TeacherDao teacherDao = new TeacherDaoImpl();

    @Override
    public Teacher login(String account, String password) {

        if (account == null || account.trim().isEmpty()) return null;
        if (password == null || password.trim().isEmpty()) return null;

        return teacherDao.login(account.trim(), password.trim());
    }

    
    
}
