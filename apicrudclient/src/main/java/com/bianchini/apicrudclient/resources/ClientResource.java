package com.bianchini.apicrudclient.resources;

import com.bianchini.apicrudclient.dtos.ClientDto;
import com.bianchini.apicrudclient.entities.Client;
import com.bianchini.apicrudclient.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController                 //Annotation que define um controlador REST e assim facilita o desenvolvimento de um projeto sendo Restful.
@RequestMapping(value = "/clients")                 //Annotation onde o endereço no qual é mapeado para fazer as requisições.
public class ClientResource {

    @Autowired                  //Annotation para injeção de depêndencias.
    private ClientService clientService;

    @GetMapping                 //Annotation que define o verbo Http, no caso é o verbo para trazer dados.
    public ResponseEntity<Page<ClientDto>> findAllPaged(                    //Parametros passado para o tipo paginado
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "07") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ClientDto> clients = clientService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id){
        ClientDto dto = clientService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    /*
    Método salvar um cliente, está seguindo o padrão de trazer no header da resposta, o location, no caso o endereço da recurso criado.
     */
    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto clientDto){
        ClientDto dto = clientService.saveClient(clientDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clientDto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto){
        ClientDto dto = clientService.updateClient(id, clientDto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
