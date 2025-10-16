package com.senac.projectmanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.senac.projectmanagement.entity.Projeto;

@Component
@FeignClient(name = "projectmanagement", url = "http://localhost:8086")
public interface ProjetoFeignClient {

    @GetMapping(value = "/listar/{id}")
    public ResponseEntity<Projeto> findById(@PathVariable Long id);
}
