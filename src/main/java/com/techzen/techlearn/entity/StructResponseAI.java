package com.techzen.techlearn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_struct_response_AI")
public class StructResponseAI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "content_struct", columnDefinition = "NTEXT")
    String contentStruct;
    @Column(name = "is_Active", columnDefinition = "NTEXT")
    Boolean isActive;

    @Column(name = "type", columnDefinition = "NTEXT")
    String type;

}
