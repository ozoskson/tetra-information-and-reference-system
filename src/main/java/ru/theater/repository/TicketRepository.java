package ru.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.theater.entity.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByPerformanceId(Long performanceId);
    List<Ticket> findByPerformanceIdAndIsSold(Long performanceId, Boolean isSold);
    boolean existsByPerformanceIdAndSeatNumber(Long performanceId, Integer seatNumber);
}

