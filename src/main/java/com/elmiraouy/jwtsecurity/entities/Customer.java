package com.elmiraouy.jwtsecurity.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String uuid;
    private String ville;
    private Date expiredUuid;
    private Long activeHubId ;
    private boolean allowedDailySumUp;
    private boolean allowedShutdown;
    private boolean allowedPhaseChange;
    private boolean allowedBudgetAlert;
    private boolean allowedAutoMeterReading;

    @ManyToMany
    private Collection<AppUser> users;

    @OneToMany(mappedBy = "customer")
    private Collection<Ticket> tickets;
    @OneToMany(mappedBy = "customer")
    private Collection<HistoryCustomer> listHistoryCustomers;
    @ManyToMany
    @JoinTable(name = "customers_hubs",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "hubs_id"))
    private Collection<Hub> hubs;

    @ElementCollection
    @CollectionTable(name = "customer_house_names",
            joinColumns = @JoinColumn(name = "customer_id"))
    @MapKeyColumn(name = "house_names_key")
    private Map<String,String> housesNames = new HashMap<>();

}
