package ru.theater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.theater.entity.Hall;
import ru.theater.repository.HallRepository;

import java.util.List;

@Service
public class HallService {
    @Autowired
    private HallRepository hallRepository;

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Hall getHallById(Long id) {
        return hallRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hall not found"));
    }

    @Transactional
    public Hall saveHall(Hall hall) {
        return hallRepository.save(hall);
    }

    @Transactional
    public Hall updateHall(Long id, Hall hallDetails) {
        Hall hall = getHallById(id);
        hall.setName(hallDetails.getName());
        hall.setCapacity(hallDetails.getCapacity());
        hall.setDescription(hallDetails.getDescription());
        return hallRepository.save(hall);
    }

    @Transactional
    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }

    public List<Hall> searchHalls(String keyword) {
        return hallRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Hall> sortHalls(String sortBy) {
        return switch (sortBy) {
            case "nameAsc" -> hallRepository.findAllByOrderByNameAsc();
            case "capacityAsc" -> hallRepository.findAllByOrderByCapacityAsc();
            case "capacityDesc" -> hallRepository.findAllByOrderByCapacityDesc();
            default -> getAllHalls();
        };
    }
}

