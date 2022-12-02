package com.bianchini.apicrudclient.repositories;

import com.bianchini.apicrudclient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Essa interface tem a função de acessar o banco de dados, ela extende a interface JpaRepository que já consta vários métodos de um crud.
 */

@Repository                 //Annotation que define ser um repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
