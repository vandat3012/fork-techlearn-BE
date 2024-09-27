package com.techzen.techlearn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teacher")
public class Teacher extends BaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 7)
    private String color;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherCalendar> teacherCalendars;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_course",
            joinColumns=@JoinColumn(name="teacher_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="course_id", referencedColumnName = "id")
    )
    private List<CourseEntity> courses;
}
