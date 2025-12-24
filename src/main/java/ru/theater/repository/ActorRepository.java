package ru.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.theater.entity.Actor;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    @Query("SELECT a FROM Actor a WHERE a.firstName LIKE %:keyword% OR a.lastName LIKE %:keyword%")
    List<Actor> searchByKeyword(@Param("keyword") String keyword);
    
    List<Actor> findAllByOrderByLastNameAsc();
    List<Actor> findAllByOrderByLastNameDesc();
    List<Actor> findAllByOrderByFirstNameAsc();
}

