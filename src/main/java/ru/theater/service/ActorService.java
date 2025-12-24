package ru.theater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.theater.entity.Actor;
import ru.theater.repository.ActorRepository;

import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(Long id) {
        return actorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Actor not found"));
    }

    @Transactional
    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Transactional
    public Actor updateActor(Long id, Actor actorDetails) {
        Actor actor = getActorById(id);
        actor.setFirstName(actorDetails.getFirstName());
        actor.setLastName(actorDetails.getLastName());
        actor.setBirthDate(actorDetails.getBirthDate());
        actor.setBiography(actorDetails.getBiography());
        return actorRepository.save(actor);
    }

    @Transactional
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

    public List<Actor> searchActors(String keyword) {
        return actorRepository.searchByKeyword(keyword);
    }

    public List<Actor> sortActors(String sortBy) {
        return switch (sortBy) {
            case "lastNameAsc" -> actorRepository.findAllByOrderByLastNameAsc();
            case "lastNameDesc" -> actorRepository.findAllByOrderByLastNameDesc();
            case "firstNameAsc" -> actorRepository.findAllByOrderByFirstNameAsc();
            default -> getAllActors();
        };
    }
}

