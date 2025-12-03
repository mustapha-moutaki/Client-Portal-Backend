package org.mustapha.ClientPortal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClientDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClientDtoResponse;
import org.mustapha.ClientPortal.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client Management", description = "Endpoints for managing clients")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Create a new client manually")
    @PostMapping
    public ResponseEntity<ClientDtoResponse> createClient(@Valid @RequestBody ClientDtoRequest request) {
        ClientDtoResponse created = clientService.createClient(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Convert a Lead to a Client (Creates User Account)")
    @PostMapping("/convert/{leadId}")
    public ResponseEntity<String> convertLeadToClient(@PathVariable Long leadId) {
        clientService.convertLeadToClient(leadId);
        return ResponseEntity.ok("Lead converted successfully to Client. Default password is 'client123'");
    }

    @Operation(summary = "Get all clients")
    @GetMapping
    public ResponseEntity<Page<ClientDtoResponse>> getAllClients(Pageable pageable) {
        return ResponseEntity.ok(clientService.getAllClients(pageable));
    }
}