package org.mustapha.ClientPortal.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClientDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClientDtoResponse;
import org.mustapha.ClientPortal.enums.LeadStatus;
import org.mustapha.ClientPortal.enums.UserRole;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ClientMapper;
import org.mustapha.ClientPortal.model.Client;
import org.mustapha.ClientPortal.model.Lead;
import org.mustapha.ClientPortal.repository.ClientRepository;
import org.mustapha.ClientPortal.repository.LeadRepository;
import org.mustapha.ClientPortal.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final LeadRepository leadRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDtoResponse createClient(ClientDtoRequest request) {
        if (clientRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Client client = Client.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.CLIENT)
                .active(true)
                .companyName(request.getCompanyName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    @Transactional
    public void convertLeadToClient(Long leadId) {
        // 1. Fetch Lead
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));

        // 2. ✅ FIXED: Check if already converted using Enum comparison
        if (lead.getStatus() == LeadStatus.CONVERTED) {
            throw new RuntimeException("Lead is already converted");
        }

        // 3. Check email uniqueness
        if (clientRepository.findByEmail(lead.getContactInfo()).isPresent()) {
            throw new RuntimeException("A client with this email already exists");
        }

        // 4. Create Client Entity (User Account)
        Client client = Client.builder()
                .username(lead.getContactInfo()) // Use contact info/email as username
                .email(lead.getContactInfo())
                .password(passwordEncoder.encode("client123")) // Default Password
                .role(UserRole.CLIENT)
                .active(true)
                .companyName(lead.getSource())
                .phone(lead.getContactInfo())
                .build();

        clientRepository.save(client);

        // 5. Update Lead Status using Enum
        lead.setStatus(LeadStatus.CONVERTED);
        leadRepository.save(lead);
    }

    @Override
    public ClientDtoResponse updateClient(Long clientId, ClientDtoRequest request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));

        if(request.getCompanyName() != null) client.setCompanyName(request.getCompanyName());
        if(request.getPhone() != null) client.setPhone(request.getPhone());
        if(request.getAddress() != null) client.setAddress(request.getAddress());

        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found");
        }
        clientRepository.deleteById(clientId);
    }

    @Override
    public ClientDtoResponse getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return clientMapper.toDto(client);
    }

    @Override
    public Page<ClientDtoResponse> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::toDto);
    }
}