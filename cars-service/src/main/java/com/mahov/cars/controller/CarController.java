package com.mahov.cars.controller;


import com.mahov.dto.request.CarRequestDto;
import com.mahov.dto.response.CarResponseDto;
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

@Tag(name = "Cars", description = "The car's api")
public interface CarController {

  @Operation(summary = "Get all cars", tags = "cars")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cars found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = CarResponseDto.class)))
          })
  })
  List<CarResponseDto> getAll();

  @Operation(summary = "Get car by id", tags = "car")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Car found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CarResponseDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Car not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  CarResponseDto getByid(@PathVariable("id") Long id);

  @Operation(summary = "Create car", tags = "car")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Car created",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CarResponseDto.class))
          })
  })
  CarResponseDto create(@RequestBody CarRequestDto car);

  @Operation(summary = "Edit car by id", tags = "car")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Car edited",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CarResponseDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Car not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  CarResponseDto edit(@PathVariable("id") Long id, @RequestBody CarRequestDto car);

  @Operation(summary = "Delete car by id", tags = "car")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Car deleted"),
      @ApiResponse(
          responseCode = "404",
          description = "Car not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  void delete(@PathVariable("id") Long id);

  @Operation(summary = "Delete all cars by owner id", tags = "car")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Cars deleted"),
      @ApiResponse(
          responseCode = "404",
          description = "Car not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  void deleteByOwnerId(@PathVariable("ownerId") Long id);
}
