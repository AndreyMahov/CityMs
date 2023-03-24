package com.mahov.passports.controller;


import com.mahov.dto.PassportDto;
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


@Tag(name = "passport", description = "The passport's api")
public interface PassportController {

  @Operation(summary = "Get all passports", tags = "passports")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "passports found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = PassportDto.class)))
          }),
      @ApiResponse(
          responseCode = "400",
          description = "passports no Found")
  })
  List<PassportDto> getAll();

  @Operation(summary = "Get passport by id", tags = "passport")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "passport found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PassportDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "passport not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  Object getByid(@PathVariable("id") Long id);

  @Operation(summary = "Create passport", tags = "passport")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "passport created",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PassportDto.class))
          })
  })
  List<PassportDto> getAllPassportByOwnersIds(@RequestBody List<Long> ownersIds);

  @Operation(summary = "Edit passport by id", tags = "passport")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "passport edited",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = PassportDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "passport not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  PassportDto create(@PathVariable Long ownerId, @RequestBody PassportDto passportDto);

  @Operation(summary = "Delete passport by owner id", tags = "passport")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "passport deleted"),
      @ApiResponse(
          responseCode = "404",
          description = "passport not found ",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  void deleteByOwnerId(@PathVariable("ownerId") Long ownerId);
}
