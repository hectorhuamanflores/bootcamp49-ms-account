package com.bootcamp.nttdata.repository;
import com.bootcamp.nttdata.model.AccountDetail;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface AccountDetailRepository extends ReactiveCrudRepository <AccountDetail,String>{

    Mono<AccountDetail> findByTyAccount(Integer tyAccount);

}
