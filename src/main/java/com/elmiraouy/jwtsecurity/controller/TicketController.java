package com.elmiraouy.jwtsecurity.controller;

import com.elmiraouy.jwtsecurity.Dto.request.AppUserRequestDto;
import com.elmiraouy.jwtsecurity.Dto.request.TicketRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.AppUserResponseDto;
import com.elmiraouy.jwtsecurity.Dto.response.TicketResponseDto;
import com.elmiraouy.jwtsecurity.handlerException.AppUserException;
import com.elmiraouy.jwtsecurity.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> findAllTicket( )  {
        return ResponseEntity.ok(ticketService.findAllTicket());
    }
    @PostMapping
    public ResponseEntity<?> addTicket(
            @RequestBody TicketRequestDto ticketRequestDto)  {
        return ResponseEntity.ok(ticketService.addTicket(ticketRequestDto));
    }
    @DeleteMapping
    public ResponseEntity<TicketResponseDto> deleteTicket(
            @Param("id") Long id)  {
        System.out.println("______________________________id :"+id);
        return ResponseEntity.ok(ticketService.deleteTicket(id));
    }

}
