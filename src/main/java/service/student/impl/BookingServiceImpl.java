package service.student.impl;

import dao.student.BookingDao;
import dao.student.StudentDao;
import dao.student.impl.BookingDaoImpl;
import dao.student.impl.StudentDaoImpl;
import service.student.BookingService;
import vo.student.ViewAvailableSlots;
import vo.student.ViewAvailableSlotsDaoImpl;
import vo.student.ViewStudentSchedule;
import vo.student.ViewStudentScheduleDaoImpl;
import dao.student.PointTransactionsDao;
import dao.student.impl.PointTransactionsDaoImpl;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    StudentDao studentDao = new StudentDaoImpl();
    BookingDao bookingDao = new BookingDaoImpl();
    PointTransactionsDao ptDao = new PointTransactionsDaoImpl();


    
    ViewAvailableSlotsDaoImpl viewAvailableSlotsDao = new ViewAvailableSlotsDaoImpl();
    ViewStudentScheduleDaoImpl viewStudentScheduleDao = new ViewStudentScheduleDaoImpl();

   
    public static void main(String[] args) {

        BookingService bookingService = new BookingServiceImpl();

        System.out.println("==== listAvailableSlots 測試 ====");
        List<ViewAvailableSlots> allSlots = bookingService.listAvailableSlots();
        for (ViewAvailableSlots slot : allSlots) {
            System.out.println(
                    "slot_id=" + slot.getSlot_id() +
                    ", teacher=" + slot.getTeacher_name() +
                    ", cost=" + slot.getCost() +
                    ", time=" + slot.getStart_time()
            );
        }

        System.out.println("\n==== mySchedule 測試 ====");
        List<ViewStudentSchedule> scheduleList = bookingService.mySchedule(1);
        for (ViewStudentSchedule schedule : scheduleList) {
            System.out.println(
                    "time=" + schedule.getLesson_time() +
                    ", teacher=" + schedule.getTeacher_name() +
                    ", status=" + schedule.getBooking_status() +
                    ", zoom=" + schedule.getZoom_link()
            );
        }

        
    }

  

    @Override
    public List<ViewAvailableSlots> listAvailableSlots() {
        return viewAvailableSlotsDao.findAll();
    }

    @Override
    public List<ViewAvailableSlots> listAvailableSlotsByCostRange(int minimumCost, int maximumCost) {

        if (minimumCost < 0 || maximumCost < 0) {
            return viewAvailableSlotsDao.findAll();
        }
        if (minimumCost > maximumCost) {
            return viewAvailableSlotsDao.findAll();
        }

        return viewAvailableSlotsDao.findByCostRange(minimumCost, maximumCost);
    }

    @Override
    public List<ViewAvailableSlots> listAvailableSlotsByMonth(String yearMonth) {

        if (yearMonth == null || yearMonth.trim().isEmpty()) {
            return viewAvailableSlotsDao.findAll();
        }

        return viewAvailableSlotsDao.findByMonth(yearMonth.trim());
    }

    // ===== bookLesson =====

    @Override
    public String bookLesson(int studentId, int slotId, int cost, String zoomLink) {

        if (studentId <= 0) {
            return "預約失敗：studentId 不正確";
        }
        if (slotId <= 0) {
            return "預約失敗：slotId 不正確";
        }
        if (cost <= 0) {
            return "預約失敗：cost 不正確";
        }

        // 1) 查老師 id
        Integer teacherId = bookingDao.findTeacherIdBySlotId(slotId);
        if (teacherId == null) {
            return "預約失敗：找不到這個時段的老師";
        }

        // 2) 先鎖時段
        int lockedRows = bookingDao.lockTeacherSlot(slotId);
        if (lockedRows <= 0) {
            return "預約失敗：此時段已被預約或不可預約";
        }

        // 3) 檢查點數夠不夠
        int studentPoints = studentDao.getStudentPoints(studentId);
        if (studentPoints < cost) {
            return "預約失敗：點數不足（目前點數：" + studentPoints + "，需要：" + cost + "）";
        }

        // 4) 扣點（扣不到代表點數不足或資料異常）
        int deductedRows = studentDao.deductStudentPoints(studentId, cost);
        if (deductedRows <= 0) {
            return "預約失敗：扣點失敗（可能點數不足）";
        }

        // 5) 新增 booking
        String bookingStatus = "已預約";
        String zoomLinkValue = (zoomLink == null) ? "" : zoomLink;

        int insertedRows = bookingDao.insertBooking(
                studentId,
                teacherId,
                slotId,
                bookingStatus,
                zoomLinkValue
        );

        if (insertedRows <= 0) {
            return "預約失敗：新增預約紀錄失敗";
        }
        
      //寫入扣點交易明細（amount_paid=0, points_added = -cost）
        ptDao.insertTransaction(studentId, 0, -cost);

        return "預約成功：已完成預約（扣點：" + cost + "）";
    }
    
    

    //

    @Override
    public List<ViewStudentSchedule> mySchedule(int studentId) {

        if (studentId <= 0) {
            return new java.util.ArrayList<>();
        }

        return viewStudentScheduleDao.findByStudentId(studentId);
    }
}
