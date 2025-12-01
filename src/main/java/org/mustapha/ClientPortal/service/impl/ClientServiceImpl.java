package org.mustapha.ClientPortal.service.impl;

import lombok.RequiredArgsConstructor;
import org.mustapha.ClientPortal.dto.request.ClientDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClientDtoResponse;
import org.mustapha.ClientPortal.exception.ResourceNotFoundException;
import org.mustapha.ClientPortal.mapper.ClientMapper;
import org.mustapha.ClientPortal.model.Client;
import org.mustapha.ClientPortal.repository.ClientRepository;
import org.mustapha.ClientPortal.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDtoResponse createClient(ClientDtoRequest request) {
        Client client = clientMapper.toEntity(request);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDtoResponse updateClient(Long clientId, ClientDtoRequest request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));

        client.setCompanyName(request.getCompanyName());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());

        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
        clientRepository.delete(client);
    }

    @Override
    public ClientDtoResponse getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
        return clientMapper.toDto(client);
    }

    @Override
    public Page<ClientDtoResponse> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::toDto);
    }
}
