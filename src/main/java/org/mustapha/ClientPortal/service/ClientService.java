package org.mustapha.ClientPortal.service;

import org.mustapha.ClientPortal.dto.request.ClientDtoRequest;
import org.mustapha.ClientPortal.dto.response.ClientDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    ClientDtoResponse createClient(ClientDtoRequest request);
    ClientDtoResponse updateClient(Long clientId, ClientDtoRequest request);
    void deleteClient(Long clientId);
    ClientDtoResponse getClientById(Long clientId);
    Page<ClientDtoResponse> getAllClients(Pageable pageable);

}
