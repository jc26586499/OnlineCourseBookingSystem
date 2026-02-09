package service.student;

import java.util.List;

import vo.student.ViewAvailableSlots;
import vo.student.ViewStudentSchedule;

public interface BookingService {

    // listAvailableSlots：（全部 / 依點數區間 / 依月份）
    List<ViewAvailableSlots> listAvailableSlots();

    List<ViewAvailableSlots> listAvailableSlotsByCostRange(int minimumCost, int maximumCost);

    List<ViewAvailableSlots> listAvailableSlotsByMonth(String yearMonth);

    // bookLesson
    // cost： UI 從 ViewAvailableSlots （扣點用）
    // zoomLink： "" 或 null
    String bookLesson(int studentId, int slotId, int cost, String zoomLink);

    
    List<ViewStudentSchedule> mySchedule(int studentId);
}
