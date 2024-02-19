package com.bootcamp.nttdata.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.nttdata.model.AccountDetail;
import com.bootcamp.nttdata.service.AccountDetailService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accountDetail")
public class AccountDetailController {

    private final AccountDetailService accountDetailService;

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountDetail>>>getAllAccountDetail() {
        Flux<AccountDetail> list=this.accountDetailService.getAllAccountDetail();
        return  Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(list));
    }

    @GetMapping("/{tyAccount}")
    public Mono<ResponseEntity<AccountDetail>> getAccountDetailById(@PathVariable Integer tyAccount){
        var accountDetail=this.accountDetailService.getAccountByTyAccount(tyAccount);
        return accountDetail.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDetail> create(@RequestBody AccountDetail accountDetail){
        return this.accountDetailService.createAccountDetail(accountDetail);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountDetail>> updateAccountDetail(@PathVariable("id") String id, @RequestBody AccountDetail accountDetail){
        Mono<AccountDetail> bodyAccountDetail = Mono.just(accountDetail);
        Mono<AccountDetail> bdAccountDetail  = accountDetailService.getAccountDetailById(id);

        return bdAccountDetail.zipWith(bodyAccountDetail, (bd, body) ->{
                    bd.setId(id);
                    bd.setTyAccount(body.getTyAccount());
                    bd.setNameTyAccount(body.getNameTyAccount());
                    bd.setCommission(body.getCommission());
                    bd.setMovementTrxMax(body.getMovementTrxMax());
                    return bd;
                })
                .flatMap( e -> accountDetailService.updateAccountDetail(e))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccountDetailById(@PathVariable String id){
        return this.accountDetailService.deleteAccountDetail(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
