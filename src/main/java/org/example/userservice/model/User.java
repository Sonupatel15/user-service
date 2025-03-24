package org.example.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID userId;


    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    @Column(unique = true, nullable = false)
    private String mobile;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isActive = true;  // True means active, False means deleted

    @NotBlank(message = "Address cannot be empty")
    @Column(nullable = false)
    private String address;

    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dob;


}
