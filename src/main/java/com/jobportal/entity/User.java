package com.jobportal.entity;

import com.jobportal.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String skills;

    private String resumeUrl;

    private String companyName;

    private String website;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "postedBy")
    private List<JobPost> jobPosts = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Application> applications = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
