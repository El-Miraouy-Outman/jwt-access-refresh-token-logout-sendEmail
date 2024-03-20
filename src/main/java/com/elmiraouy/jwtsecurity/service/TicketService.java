package com.elmiraouy.jwtsecurity.service;

import com.elmiraouy.jwtsecurity.Dto.request.TicketRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.TicketResponseDto;
import com.elmiraouy.jwtsecurity.handlerException.TicketException;

import java.util.List;

public interface TicketService {
    public List<TicketResponseDto> findAllTicket();
    public TicketResponseDto findTicketByEmailCustomer(String email) throws TicketException;
    public TicketResponseDto findTicketById(Long id) throws TicketException;
    public TicketResponseDto addTicket(TicketRequestDto TicketRequestDto);
    public TicketResponseDto deleteTicket(Long id);
    public TicketResponseDto updateTicket(Long id ,TicketRequestDto TicketRequestDto) throws TicketException;
}
