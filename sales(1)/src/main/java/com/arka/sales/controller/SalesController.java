package com.arka.sales.controller;

import com.arka.sales.dto.HeadersDetailsDto;
import com.arka.sales.dto.SalesDetailsDto;
import com.arka.sales.dto.SalesHeadersDto;
import com.arka.sales.entity.SalesHeaders;
import com.arka.sales.mapper.SalesMapper;
import com.arka.sales.service.SalesDetailsService;
import com.arka.sales.service.SalesHeadersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesHeadersService headersService;
    private final SalesDetailsService detailsService;
    private final SalesMapper mapper;

    @PostMapping("/saveDetail")
    public Mono<SalesDetailsDto> saveDetail(@RequestBody SalesDetailsDto detailsDto){

        if(detailsDto.getSales_header_id() == null){
            SalesHeadersDto headersDto = null;
            headersDto.setId(null);
            headersDto.setSale_date(LocalDateTime.now());
            headersDto.setCustomer_id(null);
            headersDto.setCustomer_email(null);
            headersDto.setSale_value(0);
            headersDto.setStatus(0);

            Mono<SalesHeadersDto> dto = headersService.save(headersDto);
            dto.map(saved -> {
                detailsDto.setSales_header_id(saved.getId());
                return detailsService.save(detailsDto);
            });
        }else
            return detailsService.save(detailsDto);

        return null;
    }

    @PostMapping("/updateHeader")
    public Mono<SalesHeadersDto> updateHeader(@RequestBody SalesHeadersDto headersDto){
        return headersService.update(headersDto.getId(), headersDto);
    }

    @DeleteMapping("/deleteDetail/{id}")
    public Mono<String> deleteDetail(@PathVariable Long id){
        detailsService.delete(id);
        return Mono.just("Producto eliminado");
    }

    @GetMapping("/{id}")
    public Mono<HeadersDetailsDto> getSale(@PathVariable Long id){
        return headersService.findById(id)
                .flatMap(headerEntity -> {
                    SalesHeadersDto headerDto = mapper.headersToDTO(headerEntity);

                    return detailsService.getDetailsByHeader(headerDto.getId())
                            .collectList() // convierte Flux -> Mono<List<SalesDetailsDto>>
                            .map(detailsList -> HeadersDetailsDto.builder()
                                    .pedido(headerDto)
                                    .productos(detailsList)
                                    .build());
                });
    }


}
