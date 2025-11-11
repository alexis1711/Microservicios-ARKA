package com.arka.sales.service.impl;

import com.arka.sales.dto.*;
import com.arka.sales.entity.SalesDetails;
import com.arka.sales.entity.SalesHeaders;
import com.arka.sales.mapper.SalesMapper;
import com.arka.sales.repository.SalesDetailsRepository;
import com.arka.sales.repository.SalesHeadersRepository;
import com.arka.sales.service.SalesDetailsService;
import com.arka.sales.service.SalesHeadersService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@AllArgsConstructor
public class SalesHeadersServiceImpl implements SalesHeadersService {

    private final SalesHeadersRepository repository;
    private final SalesDetailsService service;
    private final SalesMapper mapper;
    private RestTemplate restTemplate;

    @Override
    public SalesHeadersDto findById(Long id) {
        return mapper.headersToDTO(repository.findById(id).orElse(new SalesHeaders()));
    }

    @Override
    public SalesHeadersDto save(SalesHeadersDto salesHeadersDto) {
        SalesHeaders headers = mapper.headersToEntity(salesHeadersDto);
        return mapper.headersToDTO(repository.save(headers));
    }

    @Override
    public SalesHeadersDto update(Long id, SalesHeadersDto salesHeadersDto) {
        SalesHeaders existing = repository.findById(id).get();
        existing.setSale_date(salesHeadersDto.getSale_date());
        existing.setSale_value(salesHeadersDto.getSale_value());
        existing.setCustomer_id(salesHeadersDto.getCustomer_id());
        existing.setCustomer_name(salesHeadersDto.getCustomer_name());
        existing.setCustomer_email(salesHeadersDto.getCustomer_email());
        existing.setStatus(salesHeadersDto.getStatus());
        existing.setStore_id(salesHeadersDto.getStore_id());
        existing.setProvider_id(salesHeadersDto.getProvider_id());
        return mapper.headersToDTO(repository.save(existing));
    }

    @Override
    public HeadersDetailsDto paySale(SalesHeadersDto headersDto) {
        headersDto.setStatus(1);
        SalesHeadersDto dtoSaved = update(headersDto.getId(), headersDto);
        List<SalesDetailsDto> detailsDtos = service.getDetailsByHeader(dtoSaved.getId());
        HeadersDetailsDto headersDetailsDto = new HeadersDetailsDto();
        headersDetailsDto.setPedido(dtoSaved);
        headersDetailsDto.setProductos(detailsDtos);
        for(SalesDetailsDto salesDetails : detailsDtos){
            UpdateAmountDto updateAmountDto = new UpdateAmountDto();
            updateAmountDto.setStore_id(headersDto.getStore_id());
            updateAmountDto.setProduct_id(salesDetails.getProduct_id());
            updateAmountDto.setProvider_id(headersDto.getProvider_id());
            updateAmountDto.setAmount(salesDetails.getQuantity() * -1);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc2Mjc1MDg0NSwiZXhwIjoxNzYyNzU0NDQ1fQ.mhGvOnXVEXVD3aLGD-QSIzgLPyvTz-2CQTrMlcHCv3U");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UpdateAmountDto> request = new HttpEntity<>(updateAmountDto, httpHeaders);
            String url= "http://3.134.227.33:8084/api/v1/stock/amount";
            try{
                ResponseEntity<UpdateAmountRspDto> response = restTemplate.exchange(url, HttpMethod.POST, request, UpdateAmountRspDto.class);
                System.out.println("Response: " + response.getBody().getMessage());
            }catch (HttpClientErrorException e){
                System.out.println(e.getMessage());
                if(e.getStatusCode().equals(HttpStatus.CONFLICT))
                    return null;
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setEmail(dtoSaved.getCustomer_email());
        sendEmailDto.setNombre(dtoSaved.getCustomer_name());
        sendEmailDto.setTipoCorreo(2);
        sendEmailDto.setNumCarrito(Math.toIntExact(dtoSaved.getId()));
        sendEmailDto.setNumOrden(0);
        sendEmailDto.setEstado("Confirmado");
        HttpEntity<SendEmailDto> request = new HttpEntity<>(sendEmailDto, httpHeaders);
        String url= "https://ygu79o6he2.execute-api.us-east-1.amazonaws.com/default/lambda-emails";
        try{
            ResponseEntity<UpdateAmountRspDto> response = restTemplate.exchange(url, HttpMethod.POST, request, UpdateAmountRspDto.class);
            System.out.println("Response: " + response.getBody().getMessage());
        }catch (HttpClientErrorException e){
            System.out.println(e.getMessage());
            if(e.getStatusCode().equals(HttpStatus.CONFLICT))
                return null;
        }

        return headersDetailsDto;
    }
}
