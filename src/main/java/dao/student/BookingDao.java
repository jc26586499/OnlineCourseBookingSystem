package dao.student;

public interface BookingDao {

    // 由 slot_id 查老師 id（booking 需要 t_id）
    Integer findTeacherIdBySlotId(int slotId);

    // 鎖定時段（is_available: 1 → 0）
    // 成功回 1，失敗回 0
    int lockTeacherSlot(int slotId);

    // 新增一筆 booking
    int insertBooking(int studentId,
                      int teacherId,
                      int slotId,
                      String status,
                      String zoomLink);
}
