package com.techzen.techlearn.entity;

import com.techzen.techlearn.enums.SubmitStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@Table(name = "tbl_submission")
public class SubmitionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "link_github")
    private String linkGithub;

    @Column(name = "review", columnDefinition = "NTEXT")
    private String review;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SubmitStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
