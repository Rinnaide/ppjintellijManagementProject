package com.senac.projectmanagement.service;

import org.springframework.stereotype.Service;

import com.senac.projectmanagement.client.ProjetoFeignClient;
import com.senac.projectmanagement.entity.Projeto;

@Service
public class ProjetoService {

    private final ProjetoFeignClient projetoFeignClient;

    public ProjetoService(ProjetoFeignClient projetoFeignClient) {
        this.projetoFeignClient = projetoFeignClient;
    }

    public Projeto findById(Long id) {
        return projetoFeignClient.findById(id).getBody();
    }
}
