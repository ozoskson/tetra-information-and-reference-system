package ru.theater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.theater.entity.Performance;
import ru.theater.service.ActorService;
import ru.theater.service.HallService;
import ru.theater.service.PerformanceService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/performances")
public class PerformanceController {
    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private HallService hallService;

    @Autowired
    private ActorService actorService;

    @GetMapping
    public String listPerformances(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            Model model) {
        List<Performance> performances;
        if (search != null && !search.isEmpty()) {
            performances = performanceService.searchPerformances(search);
            model.addAttribute("search", search);
        } else if (sort != null && !sort.isEmpty()) {
            performances = performanceService.sortPerformances(sort);
            model.addAttribute("sort", sort);
        } else {
            performances = performanceService.getAllPerformances();
        }
        model.addAttribute("performances", performances);
        return "performances/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("performance", new Performance());
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("actors", actorService.getAllActors());
        return "performances/form";
    }

    @PostMapping
    public String createPerformance(@ModelAttribute Performance performance,
                                   @RequestParam(required = false) List<Long> actorIds) {
        if (actorIds != null && !actorIds.isEmpty()) {
            performance.setActors(actorService.getAllActors().stream()
                .filter(actor -> actorIds.contains(actor.getId()))
                .collect(Collectors.toSet()));
        }
        performanceService.savePerformance(performance);
        return "redirect:/performances";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("performance", performanceService.getPerformanceById(id));
        model.addAttribute("halls", hallService.getAllHalls());
        model.addAttribute("actors", actorService.getAllActors());
        return "performances/form";
    }

    @PostMapping("/{id}")
    public String updatePerformance(@PathVariable Long id, 
                                   @ModelAttribute Performance performance,
                                   @RequestParam(required = false) List<Long> actorIds) {
        if (actorIds != null && !actorIds.isEmpty()) {
            performance.setActors(actorService.getAllActors().stream()
                .filter(actor -> actorIds.contains(actor.getId()))
                .collect(Collectors.toSet()));
        } else {
            performance.setActors(new java.util.HashSet<>());
        }
        performanceService.updatePerformance(id, performance);
        return "redirect:/performances";
    }

    @PostMapping("/{id}/delete")
    public String deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
        return "redirect:/performances";
    }
}

