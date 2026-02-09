package vo.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Tool;

interface ViewTeacherRevenueDao {
    List<ViewTeacherRevenue> findAllRevenue();
    
    List<ViewTeacherRevenue> findByTeacherId(int teacher_id);
}
public class ViewTeacherRevenueDaoImpl implements ViewTeacherRevenueDao {

	public static void main(String[] args) {
		List<ViewTeacherRevenue> list = new ViewTeacherRevenueDaoImpl().findAllRevenue();
        System.out.println("==== 老師營收排行榜 (Points) ====");
        for (ViewTeacherRevenue r : list) {
            System.out.println("老師：" + r.getTeacher_name() + " | 總收益：" + r.getTotal_points_earned() + " pts");
        }
    }


	Connection conn = Tool.getDb();
	
	@Override
	public List<ViewTeacherRevenue> findAllRevenue() {
		String sql =
			    "select * " +
			    "from view_teacher_revenue " +
			    "order by total_points_earned desc";
;
		List<ViewTeacherRevenue> list = new ArrayList<>();
		
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
                ViewTeacherRevenue viewTeacherRevenue = new ViewTeacherRevenue();
                viewTeacherRevenue.setTeacher_id(resultSet.getInt("teacher_id"));
                viewTeacherRevenue.setReport_month(resultSet.getString("report_month"));
                viewTeacherRevenue.setTotal_lessons(resultSet.getLong("total_lessons"));
                viewTeacherRevenue.setTeacher_name(resultSet.getString("teacher_name"));
                viewTeacherRevenue.setTotal_points_earned(resultSet.getInt("total_points_earned"));
                
                list.add(viewTeacherRevenue);
			}  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ViewTeacherRevenue> findByTeacherId(int teacher_id) {

	    String sql =
	            "select * " +
	            "from view_teacher_revenue " +
	            "where teacher_id = ? " +
	            "order by report_month";

	    List<ViewTeacherRevenue> list = new ArrayList<>();

	    try {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setInt(1, teacher_id);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            ViewTeacherRevenue viewTeacherRevenue = new ViewTeacherRevenue();
	            viewTeacherRevenue.setTeacher_id(resultSet.getInt("teacher_id"));
	            viewTeacherRevenue.setTeacher_name(resultSet.getString("teacher_name"));
	            viewTeacherRevenue.setReport_month(resultSet.getString("report_month"));
	            viewTeacherRevenue.setTotal_lessons(resultSet.getLong("total_lessons"));
	            viewTeacherRevenue.setTotal_points_earned(resultSet.getInt("total_points_earned"));

	            list.add(viewTeacherRevenue);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
