package com.bianchini.apicrudclient.services;

import com.bianchini.apicrudclient.dtos.ClientDto;
import com.bianchini.apicrudclient.entities.Client;
import com.bianchini.apicrudclient.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service                    //Annotation que define uma classe de serviço, onde consta regras de negócio
public class ClientService {

    @Autowired                  //Annotation que faz injeção de dependecias, a responsabilidade fica por conta do framework.
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)                 //Annotation que garante a transação do método.
    public Page<ClientDto> findAllPaged(PageRequest pageRequest){
        Page<Client> clients = clientRepository.findAll(pageRequest);
        return clients.map(x -> new ClientDto(x));                  //método map() faz uma varredura na coleção; para cada objeto dentro da coleção ele cria um novo objeto determinado.
    }


}