package com.desafiovagasicredi.service.impl;

import com.desafiovagasicredi.exceptions.RegraNegocioException;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.repository.AssociadoRepository;
import com.desafiovagasicredi.security.JwtGenerator;
import com.desafiovagasicredi.service.AssociadoService;
import com.desafiovagasicredi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AssociadoServiceImpl implements AssociadoService {

    private AssociadoRepository repository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public AssociadoServiceImpl(AssociadoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Associado salvar(Associado associado, String confirmarSenha) {
        validar(associado, confirmarSenha);

        associado.setSenha(passwordEncoder.encode(associado.getSenha()));
        Associado associadoSalvo = repository.save(associado);
        return associadoSalvo;
    }

    @Override
    public String login(String username, String password) {
        /*try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtGenerator.generateToken(authentication);
        }catch (Exception e){
            throw e;
        }*/
        return "Desativado!";
    }

    private void validar(Associado associado, String confirmarSenha) {
        List<String> erros = verificarErros(associado, confirmarSenha);

        if(!erros.isEmpty()){
            throw new RegraNegocioException(String.join("\r\n",erros));
        }
    }


    public List<String> verificarErros(Associado associado, String confirmarSenha){
        List<String> msg = new ArrayList<>();

        if(Utils.isNotValid(associado.getNome())){
            msg.add("O nome não pode estar vazio!");
        }

        if(Utils.isNotValid(associado.getCpf())){
            msg.add("O cpf não pode estar vazio!");
        }

        if(repository.existsByCpf(associado.getCpf())){
            msg.add("O cpf já foi cadastrado!");
        }

        if(Utils.isNotValid(associado.getSenha())){
            msg.add("A senha não pode estar vazio!");
        }

        if(!associado.getSenha().equals(confirmarSenha)){
            msg.add("As senhas divergem!");
        }

        return msg;
    }
}
