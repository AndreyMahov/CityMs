package com.mahov.passports.domain.model;


import com.mahov.enums.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "PASSPORTS")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Passport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;
  @Column(name = "NUMBER")
  int number;
  @Column(name = "ownerId")
  @NotNull
  Long ownerId;
  @Column(name = "STATUS")
  @Enumerated(value = EnumType.STRING)
  Status status = Status.ACTIVE;
  @Transient
  static Integer updateCount = 0;
}
