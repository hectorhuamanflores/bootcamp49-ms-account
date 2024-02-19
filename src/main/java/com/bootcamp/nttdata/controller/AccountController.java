package com.bootcamp.nttdata.controller;

import com.bootcamp.nttdata.model.Account;
import com.bootcamp.nttdata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.bootcamp.nttdata.model.dto.AccountByNumAccountRequest;
import com.bootcamp.nttdata.model.dto.AccountByNumAccountResponse;
import com.bootcamp.nttdata.model.dto.AccountByNumDocRequest;
import com.bootcamp.nttdata.model.dto.UpdateAccountTrxRequest;
import java.net.URI;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    private AccountService accountService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Account>>>getAllaccount() {
        Flux<Account> list=this.accountService.getAllaccount();
        return  Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(list));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Account>> findById(@PathVariable("id") String id){
        return accountService.getAccountById(id)
                .map( e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Account>> createAccount(@RequestBody Account account, final ServerHttpRequest req){
        return accountService.createAccount(account)
                .map( e -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Account>> updateaccount(@PathVariable("id") String id  ,@RequestBody Account account){

        Mono<Account> bodyaccount = Mono.just(account);
        Mono<Account> bdaccount  = accountService.getAccountById(id);

        return bdaccount.zipWith(bodyaccount, (bd, body) ->{
                    bd.setId(id);
                    bd.setNumAccount(body.getNumAccount());
                    bd.setDocumentNumber(body.getDocumentNumber());
                    bd.setTyAccount(body.getTyAccount());
                    bd.setNameTyAccount(body.getNameTyAccount());
                    bd.setTyCustomer(body.getTyCustomer());
                    bd.setSubTyCustomer(body.getSubTyCustomer());
                    bd.setCondition(body.getCondition());
                    bd.setBalance(body.getBalance());
                    bd.setMovement(body.getMovement());
                    return bd;
                })
                .flatMap( e -> accountService.updateAccount(e))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccountById(@PathVariable String id){

        return accountService.getAccountById(id)
                .flatMap( e -> accountService.deleteAccount(e.getId())
                        .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping("/numAccount")
    public Mono<ResponseEntity<AccountByNumAccountResponse>> getAccountById(@RequestBody AccountByNumAccountRequest accountByNumAccountRequest){
        var Account=this.accountService.getAccountByNumAccount(accountByNumAccountRequest.getNumAccount());
        return Account.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/numberDocument")
    public Mono<ResponseEntity<Flux<Account>>> getAccountByNumDoc(@RequestBody AccountByNumDocRequest accountByNumDocRequest){
        Flux<Account> list=this.accountService.getAccountByDocumentNumber(accountByNumDocRequest.getDocumentNumber());
        return  Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(list));

    }

    @PutMapping("/updateAccountTrx")
    public Mono<ResponseEntity<Account>> updatePayment(@RequestBody UpdateAccountTrxRequest trx){

        return this.accountService.updateAccountTrx(trx)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}
