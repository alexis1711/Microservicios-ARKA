package com.arka.sales.dto;

import lombok.Data;

@Data
public class SendEmailDto {

    private String email;
    private String nombre;
    private String usuario;
    private int tipoCorreo;
    private int numCarrito;
    private int numOrden;
    private String estado;

}
