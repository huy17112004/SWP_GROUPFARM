package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private int id;

    @Column(name = "Username", nullable = false, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 100)
    private String password;

    @Column(name = "OTP", length = 10)
    private String otp;
    
    @Column(name = "OTP_Expired_At")
    private LocalDateTime otpExpiredAt;
}
