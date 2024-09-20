package dev.eislyn.AutoTodo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles = "ROLE_USER";

    @Column(name = "enabled")
    private boolean enabled;

    private String token;

    public UserEntity(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.enabled = false;
    }
}
