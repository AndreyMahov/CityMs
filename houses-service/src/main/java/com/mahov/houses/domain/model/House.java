package com.mahov.houses.domain.model;

import com.mahov.enums.Status;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "HOUSES")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class House {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  Long id;
  @Column(name = "street")
  String street;
  @Column(name = "number")
  Integer number;
  @Column(name = "STATUS")
  @Enumerated(value = EnumType.STRING)
  Status status;
  @ManyToMany
  @JoinTable(name = "OWNERS_HOUSES",
      joinColumns = @JoinColumn(name = "OWNER_ID"),
      inverseJoinColumns = @JoinColumn(name = "HOUSE_ID"))
  List<Owner> owners;
  @Transient
  static Integer updateCount = 0;
}
