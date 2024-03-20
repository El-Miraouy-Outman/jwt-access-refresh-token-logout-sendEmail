package com.elmiraouy.jwtsecurity.service;

import com.elmiraouy.jwtsecurity.Dto.request.TicketRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.CustomerResponseDto;
import com.elmiraouy.jwtsecurity.Dto.response.TicketResponseDto;
import com.elmiraouy.jwtsecurity.entities.*;
import com.elmiraouy.jwtsecurity.handlerException.EntityNotFound;
import com.elmiraouy.jwtsecurity.handlerException.EntityNotFoundException;
import com.elmiraouy.jwtsecurity.handlerException.TicketException;
import com.elmiraouy.jwtsecurity.mappers.CustomerDtoMapper;
import com.elmiraouy.jwtsecurity.mappers.TicketDtoMapper;
import com.elmiraouy.jwtsecurity.repository.AppUserRepository;
import com.elmiraouy.jwtsecurity.repository.CustomerRepository;
import com.elmiraouy.jwtsecurity.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketDtoMapper ticketDtoMapper;
    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;
    private final AppUserService userService;
    private final AppUserRepository userRepository;

    @Override
    public List<TicketResponseDto> findAllTicket() {
        return ticketRepository.findAll()
                .stream().map(ticketDtoMapper).collect(Collectors.toList());
    }

    @Override
    public TicketResponseDto findTicketByEmailCustomer(String email) {
        return null;
    }

    @Override
    public TicketResponseDto findTicketById(Long id) {
       Ticket ticket= ticketRepository.findById(id).orElseThrow(
                ()-> new EntityNotFound("Ticket with [%s]  Not Found ".formatted(id)));
         return ticketDtoMapper.apply(ticket);
    }

    @Override
    public TicketResponseDto addTicket(TicketRequestDto ticketRequestDto) {
        Ticket ticket=new Ticket();
        //find creation user
        //find customer
        Customer customer= customerRepository.findById(ticketRequestDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFound(
                        "Customer With id [%s] not found".formatted(ticketRequestDto.getCustomerId()))
        );
        ticket.setCustomer(customer);
        //find user associeted if exist
        AppUser appUser = userRepository.findById(ticketRequestDto.getCreateUserId())
                .orElseThrow(() -> new EntityNotFound(
                        "User With id [%s] not found".formatted(ticketRequestDto.getCreateUserId())));
        ticket.getUsers().add(appUser);

        ticketRequestDto.setStatus(StatusTicket.WAITING);
        ticketRequestDto.setPriority(PriorityTicket.LOW);

        ticket.setPriority(ticketRequestDto.getPriority());
        ticket =ticketDtoMapper.tickerRequestDtoToTicket(ticketRequestDto);

        ticket.setCreationDate(new Date());
        ticket.setUpdateDate(null);
        ticket.setClosureDate(null);
        ticket=ticketRepository.save(ticket);
        return ticketDtoMapper.apply(ticket);

    }

    @Override
    public TicketResponseDto deleteTicket(Long id) {
        TicketResponseDto ticketById = findTicketById(id);
        ticketRepository.deleteById(id);
        return ticketById;
    }

    @Override
    public TicketResponseDto updateTicket(Long id, TicketRequestDto TicketRequestDto) throws TicketException {
        return null;
    }
}
