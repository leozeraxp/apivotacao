package com.desafiovagasicredi.service.impl;

import com.desafiovagasicredi.model.data.AssociadoDetail;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AssociadoDetailImpl implements UserDetailsService {

    @Autowired
    private AssociadoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Associado associado = repository.findByCpf(cpf).orElseThrow(() ->
                new UsernameNotFoundException("O CPF "+cpf+" não foi encontrado"));


        return new AssociadoDetail(associado);
    }
}
