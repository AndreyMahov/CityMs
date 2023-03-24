package com.mahov.cars.domain.model;

import com.mahov.enums.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "CARS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(
        name = "Car.findAllCarsByOwnerId",
        query = "select c from Car c where c.ownerId = :insertId"
    )
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;
  @Column(name = "FEDERAL_NUMBER")
  String federalNumber;
  @Column(name = "OWNER_ID")
  Long ownerId;
  @Column(name = "STATUS")
  @Enumerated(value = EnumType.STRING)
  Status status;
}
