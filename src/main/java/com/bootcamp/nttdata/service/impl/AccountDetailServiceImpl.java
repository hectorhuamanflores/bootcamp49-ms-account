package com.bootcamp.nttdata.service.impl;

import com.bootcamp.nttdata.model.AccountDetail;
import com.bootcamp.nttdata.repository.AccountDetailRepository;
import com.bootcamp.nttdata.service.AccountDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDetailServiceImpl implements AccountDetailService {
    private  final AccountDetailRepository accountDetailRepository;

    @Override
    public Flux<AccountDetail> getAllAccountDetail() {
        return accountDetailRepository.findAll();
    }

    @Override
    public Mono<AccountDetail> getAccountDetailById(String id) {
        return accountDetailRepository.findById(id);
    }

    @Override
    public Mono<AccountDetail> createAccountDetail(AccountDetail Account) {
        return accountDetailRepository.save(Account);
    }

    @Override
    public Mono<AccountDetail> updateAccountDetail(AccountDetail Account) {
        return accountDetailRepository.save(Account);
    }

    @Override
    public Mono<Void> deleteAccountDetail(String id) {
        return accountDetailRepository.deleteById(id);
    }

    @Override
    public Mono<AccountDetail> getAccountByTyAccount(Integer tyAccount) {
        return null;
    }
}
