package com.bianchini.apicrudclient.resources;

import com.bianchini.apicrudclient.dtos.ClientDto;
import com.bianchini.apicrudclient.entities.Client;
import com.bianchini.apicrudclient.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
