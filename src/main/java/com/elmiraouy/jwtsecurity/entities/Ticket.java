package com.elmiraouy.jwtsecurity.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Long updateUserId ;
    private Long associateUserId ;
    private String subject ;
    private String description;
    private StatusTicket status;
    private PriorityTicket priority;
    private Date creationDate;
    private Date updateDate;
    private Date closureDate;
    private String finalSolution;

    @ManyToMany
    private Collection<AppUser> users;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "resolution_id")
    private ResolutionProblem resolutionProblem;

}
