package com.apivotacao.apivotacao.service.impl;

import com.apivotacao.apivotacao.model.entity.Associado;
import com.apivotacao.apivotacao.repository.AssociadoRepository;
import com.apivotacao.apivotacao.service.AssociadoService;
import com.apivotacao.apivotacao.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociadoServiceImpl implements AssociadoService {

    private AssociadoRepository repository;

    public AssociadoServiceImpl(AssociadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Associado create(Associado associado) {
        validar(associado);

        return repository.save(associado);
    }


    private void validar(Associado associado) {
        validarCpf(associado);

        if(Utils.isNotValid(associado.getNome())){
            throw new IllegalArgumentException("O Nome deve ser preenchido!");
        }
    }

    private void validarCpf(Associado associado) {
        if(Utils.isNotValid(associado.getCpf())){
            throw new IllegalArgumentException("O CPF deve ser preenchido!");
        }

        if(repository.existsByCpf(associado.getCpf())){
            throw new IllegalArgumentException("Este cpf já está cadastrado em nossa base de dados!");
        }
    }
}
