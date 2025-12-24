package ru.theater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.theater.entity.Hall;
import ru.theater.service.HallService;

import java.util.List;

@Controller
@RequestMapping("/halls")
public class HallController {
    @Autowired
    private HallService hallService;

    @GetMapping
    public String listHalls(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            Model model) {
        List<Hall> halls;
        if (search != null && !search.isEmpty()) {
            halls = hallService.searchHalls(search);
            model.addAttribute("search", search);
        } else if (sort != null && !sort.isEmpty()) {
            halls = hallService.sortHalls(sort);
            model.addAttribute("sort", sort);
        } else {
            halls = hallService.getAllHalls();
        }
        model.addAttribute("halls", halls);
        return "halls/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("hall", new Hall());
        return "halls/form";
    }

    @PostMapping
    public String createHall(@ModelAttribute Hall hall) {
        hallService.saveHall(hall);
        return "redirect:/halls";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("hall", hallService.getHallById(id));
        return "halls/form";
    }

    @PostMapping("/{id}")
    public String updateHall(@PathVariable Long id, @ModelAttribute Hall hall) {
        hallService.updateHall(id, hall);
        return "redirect:/halls";
    }

    @PostMapping("/{id}/delete")
    public String deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
        return "redirect:/halls";
    }
}

