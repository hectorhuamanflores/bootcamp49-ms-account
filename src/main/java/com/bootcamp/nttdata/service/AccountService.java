package com.bootcamp.nttdata.service;

import com.bootcamp.nttdata.model.Account;
import com.bootcamp.nttdata.model.dto.AccountByNumAccountResponse;
import com.bootcamp.nttdata.model.dto.UpdateAccountTrxRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    public Flux<Account> getAllaccount();
    public Mono<Account> getAccountById(String id);
    public Mono<Account> createAccount(Account account);
    public Mono<Account> updateAccount(Account account);
    public Mono<Void> deleteAccount(String id);

    public Mono<AccountByNumAccountResponse> getAccountByNumAccount(String numAccount);   // num cuenta bancaria
    public Flux<Account> getAccountByDocumentNumber(Integer numdoc);
    public Mono<Account> updateAccountTrx(UpdateAccountTrxRequest trx); // deposito - retiros

}
