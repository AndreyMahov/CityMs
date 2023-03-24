package com.mahov.citizens.domain.model;

import com.mahov.citizens.domain.enums.Gender;
import com.mahov.dto.PassportDto;
import com.mahov.dto.response.CarResponseDto;
import com.mahov.dto.response.HouseResponseDto;
import com.mahov.enums.PlaceWork;
import com.mahov.enums.Status;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "CITIZENS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Citizen {
  @Column(name = "STATUS")
  @Enumerated(value = EnumType.STRING)
  Status status = Status.ACTIVE;
  @Column(name = "place_of_work")
  @Enumerated(value = EnumType.STRING)
  PlaceWork placeWork;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  Long id;
  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  Gender gender;
  @Column(name = "name")
  String name;
  @Column(name = "surname")
  String surname;
  @Transient
  List<CarResponseDto> cars;
  @Transient
  List<HouseResponseDto> houses;
  @Transient
  PassportDto passport;
}
