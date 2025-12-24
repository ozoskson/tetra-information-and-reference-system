package ru.theater.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.theater.entity.Ticket;
import ru.theater.entity.User;
import ru.theater.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PerformanceService performanceService;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    public List<Ticket> getTicketsByPerformanceId(Long performanceId) {
        return ticketRepository.findByPerformanceId(performanceId);
    }

    @Transactional
    public Ticket purchaseTicket(Long performanceId, Integer seatNumber, User user) {
        if (ticketRepository.existsByPerformanceIdAndSeatNumber(performanceId, seatNumber)) {
            throw new RuntimeException("Seat already taken");
        }
        Ticket ticket = new Ticket();
        ticket.setPerformance(performanceService.getPerformanceById(performanceId));
        ticket.setSeatNumber(seatNumber);
        ticket.setPurchaseDate(LocalDateTime.now());
        ticket.setUser(user);
        ticket.setIsSold(true);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}

