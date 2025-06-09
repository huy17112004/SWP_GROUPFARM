package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WholesaleCustomer")
public class WholesaleCustomer extends Account {

    @Column(name = "ContactPerson", length = 50)
    private String contactPerson;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "Phone", length = 50)
    private String phone;

    @Column(name = "CompanyName", length = 50)
    private String companyName;

    @Column(name = "TaxCode", length = 50)
    private String taxCode;

    @Column(name = "BusinessType", length = 50)
    private String businessType;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "Status", length = 20)
    private String status;

    @OneToMany(mappedBy = "wholesaleCustomer", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CustomerAddress> customerAddresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Contract> contract;

    @OneToOne(mappedBy = "customer",fetch = FetchType.LAZY)
    private Cart cart;

    @OneToOne(mappedBy = "customer",fetch = FetchType.LAZY)
    private ShippingLog shippingLog;
}
