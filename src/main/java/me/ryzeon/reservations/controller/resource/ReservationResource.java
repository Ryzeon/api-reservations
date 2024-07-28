package me.ryzeon.reservations.controller.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import me.ryzeon.reservations.dto.ErrorDto;
import me.ryzeon.reservations.dto.ReservationDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Reservation", description = "Operations about the reservation entity")
public interface ReservationResource {

    @Operation(description = "Get the information of all the reservations", responses = {
            @ApiResponse(responseCode = "200", description = "Return the information of all the reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "500", description = "Something bad happens to obtain th reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))), })
    ResponseEntity<List<ReservationDto>> getReservations();

    @Operation(description = "Get the information about one reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Return the information of one reservation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReservationDto.class))),

            @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),

            @ApiResponse(responseCode = "500", description = "Something bad happens to obtain th reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id of the reservation to search", example = "1") })
    ResponseEntity<ReservationDto> getReservation(@Min(1) @PathVariable Long id);

    @Operation(description = "Create one reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Return the created reservation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReservationDto.class))),

            @ApiResponse(responseCode = "400", description = "Bad request of the information to persist", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),

            @ApiResponse(responseCode = "500", description = "Something bad happens to obtain th reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))) }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(name = "Reservation", summary = "Example reservation to create", value = """
                    {
                      "passengers": [
                        {
                          "firstName": "Andres",
                          "lastName": "Sacco",
                          "documentNumber": "12345678",
                          "documentType": "DNI",
                          "birthDate": "1985-01-01"
                        }
                      ],
                      "itinerary": {
                        "segments": [
                          {
                            "origin": "BUE",
                            "destination": "MIA",
                            "departure": "2024-12-31",
                            "arrival": "2025-01-01",
                            "carrier": "AA"
                          }
                        ],
                        "price": {
                          "totalPrice": 1,
                          "totalTax": 0,
                          "basePrice": 1
                        }
                      }
                    }
                    """))))
    ResponseEntity<ReservationDto> createReservation(@RequestBody @Valid ReservationDto reservation);

    @Operation(description = "Update one reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Return the updated reservation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReservationDto.class))),

            @ApiResponse(responseCode = "400", description = "Bad request of the information to persist", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),

            @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),

            @ApiResponse(responseCode = "500", description = "Something bad happens to obtain th reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id of the reservation to update", example = "1") }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(name = "Reservation", summary = "Example reservation to create", value = """
                            {
                              "id": 1,
                              "passengers": [
                                {
                                  "firstName": "Andres",
                                  "lastName": "Sacco",
                                  "documentNumber": "12345678",
                                  "documentType": "DNI",
                                  "birthDate": "1985-01-01"
                                }
                              ],
                              "itinerary": {
                                "segments": [
                                  {
                                    "origin": "EZE",
                                    "destination": "MIA",
                                    "departure": "2024-12-31",
                                    "arrival": "2025-01-01",
                                    "carrier": "AA"
                                  }
                                ],
                                "price": {
                                  "totalPrice": 1,
                                  "totalTax": 0,
                                  "basePrice": 1
                                }
                              }
                            }
                            """))))
    ResponseEntity<ReservationDto> updateReservation(@Min(1) @PathVariable Long id,
            @RequestBody @Valid ReservationDto reservation);

    @Operation(description = "Delete one reservation", responses = {
            @ApiResponse(responseCode = "200", description = "Return nothing", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),

            @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))),

            @ApiResponse(responseCode = "500", description = "Something bad happens to obtain th reservations", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class))) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Id of the reservation to delete", example = "1") })
    ResponseEntity<Void> deleteReservation(@Min(1) @PathVariable Long id);
}