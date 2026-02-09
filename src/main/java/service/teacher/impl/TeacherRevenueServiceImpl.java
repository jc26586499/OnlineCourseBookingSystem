	package service.teacher.impl;
	
	import vo.teacher.ViewTeacherRevenue;
	
	import vo.teacher.ViewTeacherRevenueDaoImpl;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import service.teacher.TeacherRevenueService;
	
	public class TeacherRevenueServiceImpl implements TeacherRevenueService {
		
		public static void main(String[] args) {
	
	        TeacherRevenueService service = new TeacherRevenueServiceImpl();
	
	        List<ViewTeacherRevenue> list = service.findRevenueRanking();
	        int rank = 1;
	
	        for (ViewTeacherRevenue r : list) {
	            System.out.println(
	                rank++ + ". "
	              + r.getTeacher_name()
	              + " | month=" + r.getReport_month()
	              + " | points=" + r.getTotal_points_earned()
	            );
	        }
	    }
	
	    ViewTeacherRevenueDaoImpl viewTeacherRevenueDao = new ViewTeacherRevenueDaoImpl();
	
	    @Override
	    public List<ViewTeacherRevenue> findRevenueRanking() {
	
	        List<ViewTeacherRevenue> list = viewTeacherRevenueDao.findAllRevenue();
	        return list == null ? new ArrayList<>() : list;
	    }
	
	    
	    
	}
