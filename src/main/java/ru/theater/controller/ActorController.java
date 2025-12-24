package ru.theater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.theater.entity.Actor;
import ru.theater.service.ActorService;

import java.util.List;

@Controller
@RequestMapping("/actors")
public class ActorController {
    @Autowired
    private ActorService actorService;

    @GetMapping
    public String listActors(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            Model model) {
        List<Actor> actors;
        if (search != null && !search.isEmpty()) {
            actors = actorService.searchActors(search);
            model.addAttribute("search", search);
        } else if (sort != null && !sort.isEmpty()) {
            actors = actorService.sortActors(sort);
            model.addAttribute("sort", sort);
        } else {
            actors = actorService.getAllActors();
        }
        model.addAttribute("actors", actors);
        return "actors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("actor", new Actor());
        return "actors/form";
    }

    @PostMapping
    public String createActor(@ModelAttribute Actor actor) {
        actorService.saveActor(actor);
        return "redirect:/actors";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("actor", actorService.getActorById(id));
        return "actors/form";
    }

    @PostMapping("/{id}")
    public String updateActor(@PathVariable Long id, @ModelAttribute Actor actor) {
        actorService.updateActor(id, actor);
        return "redirect:/actors";
    }

    @PostMapping("/{id}/delete")
    public String deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return "redirect:/actors";
    }
}

