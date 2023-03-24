package com.mahov.houses.controller;


import com.mahov.dto.response.HouseResponseDto;
import com.mahov.exception.handlers.GlobalExceptionHandler.ErrorResponse;
import com.mahov.houses.domain.dto.request.HouseCreateDto;
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

@Tag(name = "Houses", description = "The house's api")
public interface HouseController {

  @Operation(summary = "Get all houses", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Houses found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = HouseResponseDto.class)))
          })
  })
  List<HouseResponseDto> getAll();

  @Operation(summary = "Get house by id", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "House found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = HouseResponseDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "House no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  HouseResponseDto getById(@PathVariable("id") Long id);

  @Operation(summary = "Create house", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "House created",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = HouseResponseDto.class))
          })
  })
  HouseResponseDto create(@RequestBody HouseCreateDto houseCreateDto);

  @Operation(summary = "Edit house by id", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "House edited",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = HouseResponseDto.class))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "House no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  HouseResponseDto edit(@PathVariable("id") Long id, @RequestBody HouseCreateDto houseCreateDto);

  @Operation(summary = "Delete house by id", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "House deleted"),
      @ApiResponse(
          responseCode = "404",
          description = "House no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  void delete(@PathVariable("id") Long id);

  @Operation(summary = "Get all houses owners on the street", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Owners found",
          content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = HouseResponseDto.class)))
          }),
      @ApiResponse(
          responseCode = "404",
          description = "Houses no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  List<Long> getAllOwner(@RequestParam String street);

  @Operation(summary = "delete owner by id", tags = "houses")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "delete owner"),
      @ApiResponse(
          responseCode = "404",
          description = "Houses no Found",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
          })
  })
  void deleteOwnerById(@PathVariable("id") Long ownerId);
}
