package com.arka.sales.controller;

import com.arka.sales.dto.HeadersDetailsDto;
import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.dto.SalesHeadersDto;
import com.arka.sales.mapper.SalesMapper;
import com.arka.sales.service.SalesDetailsService;
import com.arka.sales.service.SalesHeadersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesHeadersService headersService;
    private final SalesDetailsService detailsService;
    private final SalesMapper mapper;

    //Estados: 0 Pendiente, 1 Confirmado, 2 En Despacho, 3 Entregado

    @PostMapping("/saveDetail")
    public SalesDetailsDto saveDetail(@RequestBody SalesDetailsDto detailsDto){

        //Verifica si header ya existe
        if(detailsDto.getSales_header_id() == null){
            //Si no existe lo crea
            SalesHeadersDto headerDto = new SalesHeadersDto();
            headerDto.setId(null);
            headerDto.setSale_date(null);
            headerDto.setCustomer_id(null);
            headerDto.setCustomer_email(null);
            headerDto.setSale_value(0);
            headerDto.setStatus(0);
            SalesHeadersDto headerSaved = headersService.save(headerDto);
            System.out.println(headerSaved.getId());
            detailsDto.setSales_header_id(headerSaved.getId());
        }

        return detailsService.save(detailsDto);

    }

    @PostMapping("/paySale")
    public HeadersDetailsDto paySale(@RequestBody SalesHeadersDto headersDto){
        return headersService.paySale(headersDto);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id){
        detailsService.delete(id);
        return "Producto eliminado";
    }

}
