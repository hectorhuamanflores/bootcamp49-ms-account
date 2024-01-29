package com.bootcamp.nttdata.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccountTrxRequest {
    private String   numAccount; // numero de cuenta bancaria
    private Integer  type;     // type=1=deposito   type=-1=retiro
    private Double   amount;   // monto de la transaccion
}
