package ru.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.theater.entity.Performance;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT p FROM Performance p WHERE p.title LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Performance> searchByKeyword(@Param("keyword") String keyword);
    
    List<Performance> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    
    List<Performance> findAllByOrderByDateTimeAsc();
    List<Performance> findAllByOrderByDateTimeDesc();
    List<Performance> findAllByOrderByTitleAsc();
    List<Performance> findAllByOrderByPriceAsc();
    List<Performance> findAllByOrderByPriceDesc();
}

