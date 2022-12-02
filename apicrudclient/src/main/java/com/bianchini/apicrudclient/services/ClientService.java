package com.bianchini.apicrudclient.services;

import com.bianchini.apicrudclient.dtos.ClientDto;
import com.bianchini.apicrudclient.entities.Client;
import com.bianchini.apicrudclient.repositories.ClientRepository;
import com.bianchini.apicrudclient.services.exceptions.ClientNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service                    //Annotation que define uma classe de serviço, onde consta regras de negócio
public class ClientService {

    @Autowired                  //Annotation que faz injeção de dependecias, a responsabilidade fica por conta do framework.
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)                 //Annotation que garante a transação do método.
    public Page<ClientDto> findAllPaged(PageRequest pageRequest){
        Page<Client> clients = clientRepository.findAll(pageRequest);
        return clients.map(x -> new ClientDto(x));                  //método map() faz uma varredura na coleção; para cada objeto dentro da coleção ele cria um novo objeto determinado.
    }

    /*
    Método está buscando o cliente por id, caso não encontre, lança uma exception
     */
    @Transactional(readOnly = true)
    public ClientDto findById(Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        Client client = clientOptional.orElseThrow(() -> new ClientNotFoundException("Client Not found"));
        return new ClientDto(client);
    }

    /*
    Eu quis usar o método estático copyProperties da classe BeanUtils como uma alternativa de criar um método; apenas como forma de conhecer outros meios de fazer essa operação, aparentemente funciona bem.
     */
    @Transactional
    public ClientDto saveClient(ClientDto clientDto){
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        client = clientRepository.save(client);
        return new ClientDto(client);
    }

    @Transactional
    public ClientDto updateClient(Long id, ClientDto clientDto){
        try {
            Client client = clientRepository.getReferenceById(id);
            copyDtoToEntity(clientDto, client);
            client = clientRepository.save(client);
            return new ClientDto(client);
        } catch (EntityNotFoundException e){
            throw new ClientNotFoundException("Client with id: " + id + " not found");
        }
    }

    public void deleteClient(Long id){
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ClientNotFoundException("Client with id: " + id + " not found");
        }
    }

    private void copyDtoToEntity(ClientDto clientDto, Client client) {

        client.setName(clientDto.getName());
        client.setCpf(clientDto.getCpf());
        client.setBirthDate(clientDto.getBirthDate());
        client.setIncome(clientDto.getIncome());
        client.setChildren(clientDto.getChildren());
    }



}
