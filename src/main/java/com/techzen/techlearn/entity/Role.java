package com.techzen.techlearn.entity;

import com.techzen.techlearn.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType name;

}
