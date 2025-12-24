package ru.theater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.theater.entity.Ticket;
import ru.theater.service.PerformanceService;
import ru.theater.service.TicketService;
import ru.theater.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listTickets(Authentication authentication, Model model) {
        String username = authentication.getName();
        var user = userService.getUserByUsername(username);
        List<Ticket> tickets = ticketService.getTicketsByUserId(user.getId());
        model.addAttribute("tickets", tickets);
        return "tickets/list";
    }

    @GetMapping("/purchase/{performanceId}")
    public String showPurchaseForm(@PathVariable Long performanceId, Model model) {
        model.addAttribute("performance", performanceService.getPerformanceById(performanceId));
        return "tickets/purchase";
    }

    @PostMapping("/purchase/{performanceId}")
    public String purchaseTicket(
            @PathVariable Long performanceId,
            @RequestParam Integer seatNumber,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            var user = userService.getUserByUsername(username);
            ticketService.purchaseTicket(performanceId, seatNumber, user);
            return "redirect:/tickets?success=true";
        } catch (Exception e) {
            return "redirect:/performances?error=" + e.getMessage();
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }
}

