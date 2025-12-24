package ru.theater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.theater.entity.Actor;
import ru.theater.entity.Performance;
import ru.theater.repository.ActorRepository;
import ru.theater.repository.PerformanceRepository;

import java.util.List;
import java.util.Set;

@Service
public class PerformanceService {
    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private ActorRepository actorRepository;

    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public Performance getPerformanceById(Long id) {
        return performanceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Performance not found"));
    }

    @Transactional
    public Performance savePerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Transactional
    public Performance updatePerformance(Long id, Performance performanceDetails) {
        Performance performance = getPerformanceById(id);
        performance.setTitle(performanceDetails.getTitle());
        performance.setDescription(performanceDetails.getDescription());
        performance.setDateTime(performanceDetails.getDateTime());
        performance.setDuration(performanceDetails.getDuration());
        performance.setPrice(performanceDetails.getPrice());
        performance.setHall(performanceDetails.getHall());
        if (performanceDetails.getActors() != null) {
            performance.setActors(performanceDetails.getActors());
        }
        return performanceRepository.save(performance);
    }

    @Transactional
    public void deletePerformance(Long id) {
        performanceRepository.deleteById(id);
    }

    public List<Performance> searchPerformances(String keyword) {
        return performanceRepository.searchByKeyword(keyword);
    }

    public List<Performance> sortPerformances(String sortBy) {
        return switch (sortBy) {
            case "dateAsc" -> performanceRepository.findAllByOrderByDateTimeAsc();
            case "dateDesc" -> performanceRepository.findAllByOrderByDateTimeDesc();
            case "titleAsc" -> performanceRepository.findAllByOrderByTitleAsc();
            case "priceAsc" -> performanceRepository.findAllByOrderByPriceAsc();
            case "priceDesc" -> performanceRepository.findAllByOrderByPriceDesc();
            default -> getAllPerformances();
        };
    }
}

