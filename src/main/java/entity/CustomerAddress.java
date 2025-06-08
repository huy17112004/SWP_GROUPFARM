package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerAddressID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "WholesaleCustomerID", nullable = false)
    private WholesaleCustomer wholesaleCustomer;

    @OneToOne
    @JoinColumn(name = "AddressID", nullable = false)
    private Address address;
}
