package com.hamter.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hamter.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("SELECT b FROM Booking b WHERE b.date BETWEEN :start AND :end AND b.statusId = 'WAIT'")
    List<Booking> findBookingsBetweenDates(Date start, Date end);

    Optional<Booking> findTopByUserIdOrderByIdDesc(Long userId);

    int countByUserIdAndStatusId(Long userId, String statusId);
    
    List<Booking> findByUser_Id(Long userId);
    
    List<Booking> findByDoctorId(Long doctorId);
    
    @Query("SELECT b.statusId, COUNT(b) FROM Booking b GROUP BY b.statusId")
    List<Object[]> countByStatus();

    @Query("SELECT b.date, COUNT(b) FROM Booking b GROUP BY b.date")
    List<Object[]> countByDate();

    @Query("SELECT MONTH(b.date), COUNT(b) FROM Booking b GROUP BY MONTH(b.date)")
    List<Object[]> countByMonth();

    @Query("SELECT YEAR(b.date), COUNT(b) FROM Booking b GROUP BY YEAR(b.date)")
    List<Object[]> countByYear();
}
