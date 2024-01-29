package com.bootcamp.nttdata.service.impl;

import com.bootcamp.nttdata.model.Account;
import com.bootcamp.nttdata.model.dto.AccountByNumAccountResponse;
import com.bootcamp.nttdata.model.dto.UpdateAccountTrxRequest;
import com.bootcamp.nttdata.repository.AccountRepository;
import com.bootcamp.nttdata.service.AccountDetailService;
import com.bootcamp.nttdata.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountDetailService accountDetailService;
    @Override
    public Flux<Account> getAllaccount() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> getAccountById(String id) {
        return accountRepository.findById(id);
    }


    @Override
    public Mono<Account> createAccount(Account account) {

        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> updateAccount(Account account) {

        return accountRepository.save(account);
    }

    @Override
    public Mono<Void> deleteAccount(String id) {

        return accountRepository.deleteById(id);
    }

    @Override
    public Mono<AccountByNumAccountResponse> getAccountByNumAccount(String numAccount) {
        return accountRepository.findById(numAccount)
                .flatMap(r -> {
                    log.info("getNumAccount"+r.getNumAccount());
                    log.info("getTyAccount"+r.getTyAccount());
                    return accountDetailService.getAccountByTyAccount(r.getTyAccount())
                            .flatMap(f -> {
                                log.info("getTyAccount"+r.getTyAccount());
                                log.info("getCommission"+f.getCommission());
                                AccountByNumAccountResponse data = AccountByNumAccountResponse.builder()
                                        .numAccount(r.getNumAccount())
                                        .tyAccount(r.getTyAccount())
                                        .tyCustomer(r.getTyCustomer())
                                        .condition(r.getCondition())
                                        .subTyCustomer(r.getSubTyCustomer())
                                        .commission(f.getCommission())
                                        .movementTrxMax(f.getMovementTrxMax())
                                        .movement(r.getMovement())
                                        .balance(r.getBalance())
                                        .build();
                                return Mono.just(data);
                            });

                });
    }

    @Override
    public Flux<Account> getAccountByDocumentNumber(Integer numdoc) {
        log.error("INICIO_Account_DOCUMENT");
        log.info("numDoc: "+numDoc);
        return accountRepository.findByDocumentNumber(numDoc);
    }

    @Override
    public Mono<Account> updateAccountTrx(UpdateAccountTrxRequest trx) {
        log.error("INICIO_CREDIT_DOCUMENT");
        log.info("idCredit: "+trx.getNumAccount());
        log.info("type: "+trx.getType());
        log.info("amount: "+trx.getAmount());
        return accountRepository.findById(trx.getNumAccount())
                .flatMap( object ->{
                    object.setBalance(object.getBalance()+trx.getAmount()*trx.getType());
                    object.setMovement(object.getMovement()+1);
                    return accountRepository.save(object);
                });
    }
}
