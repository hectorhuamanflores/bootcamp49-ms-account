package com.bootcamp.nttdata.repository;

import com.bootcamp.nttdata.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Flux<Account> findByDocumentNumber(Integer documentNumber);

    Mono<Account> findByNumAccount(Integer documentNumber);

}
