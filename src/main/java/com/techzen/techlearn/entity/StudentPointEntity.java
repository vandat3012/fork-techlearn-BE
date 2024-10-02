package com.techzen.techlearn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Where(clause = "is_deleted = false")
@Table(name = "tbl_student_point")
public class StudentPointEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "id_points")
    Integer idPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    UserEntity userEntity;
}
