package ru.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.theater.entity.Hall;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findByNameContainingIgnoreCase(String name);
    List<Hall> findAllByOrderByNameAsc();
    List<Hall> findAllByOrderByCapacityAsc();
    List<Hall> findAllByOrderByCapacityDesc();
}

