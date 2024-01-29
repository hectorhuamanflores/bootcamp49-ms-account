package com.bootcamp.nttdata.service;

import com.bootcamp.nttdata.model.AccountDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountDetailService {
    public Flux<AccountDetail> getAllAccountDetail();
    public Mono<AccountDetail> getAccountDetailById(String id);
    public Mono<AccountDetail> createAccountDetail(AccountDetail Account);
    public Mono<AccountDetail> updateAccountDetail(AccountDetail Account);
    public Mono<Void> deleteAccountDetail(String id);
    public Mono<AccountDetail> getAccountByTyAccount(Integer tyAccount);

}
