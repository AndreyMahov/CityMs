package com.mahov.citizens.controller;

import com.mahov.dto.PassportDto;
import com.mahov.dto.request.CitizenCreateDto;
import com.mahov.dto.request.CitizenEditDto;
import com.mahov.dto.response.CarResponseDto;
import com.mahov.dto.response.CitizenResponseDto;
import com.mahov.exception.handlers.GlobalExceptionHandler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Citizens", description = "The citizen's api")
public interface CitizenController {

  @Operation(summary = "Get all citizens", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Citizens found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CitizenResponseDto.class)))
          })
  })
  List<CitizenResponseDto> getAll();

  @Operation(summary = "Get citizen by id", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Citizen found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CitizenResponseDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Citizen no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  CitizenResponseDto getById(@PathVariable("id") Long id);

  @Operation(summary = "Create citizen", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Citizen created",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CitizenResponseDto.class))
          })
  })
  CitizenResponseDto create(@RequestBody CitizenCreateDto citizen);

  @Operation(summary = "Edit citizen", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Citizen edited",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CitizenResponseDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Citizen no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  CitizenResponseDto edit(@PathVariable("id") Long id, @RequestBody CitizenEditDto citizen);

  @Operation(summary = "delete citizen", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Citizen deleted"),
      @ApiResponse(
          responseCode = "404",
          description = "Citizen no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  void delete(@PathVariable("id") Long id);

  @Operation(summary = "Get all citizen's cars by id", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Citizen's cars found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CarResponseDto.class)))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Citizens no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  List<CarResponseDto> getCitizenCars(@PathVariable("id") Long id);

  @Operation(summary = "Get  citizen's passport filter by firs letter in surname", tags = "citizens")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Passports found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CarResponseDto.class)))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Citizens no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  List<PassportDto> getPassportByFirstLetterInLastName(@RequestParam String firstLetter);
}
