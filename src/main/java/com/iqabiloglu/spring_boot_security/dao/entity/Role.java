package com.iqabiloglu.spring_boot_security.dao.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;


}
