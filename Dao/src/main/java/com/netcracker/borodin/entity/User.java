package com.netcracker.borodin.entity;

import com.netcracker.borodin.entity.enums.Role;
import com.netcracker.borodin.entity.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "m2m_user_book",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private Set<Genre> books = new HashSet<>();

}
