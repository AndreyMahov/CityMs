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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "OWNERS")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Owner {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  Long id;
  @Column(name = "CITIZEN_ID")
  Long citizenId;
  @Column(name = "CITIZEN_ID")
  @ManyToMany(mappedBy = "owners")
  List<House> houses;
  @Column(name = "STATUS")
  @Enumerated(value = EnumType.STRING)
  Status status;
}
