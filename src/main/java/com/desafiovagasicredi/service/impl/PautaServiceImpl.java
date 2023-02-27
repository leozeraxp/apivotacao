package com.desafiovagasicredi.service.impl;

import com.desafiovagasicredi.exceptions.NotFoundException;
import com.desafiovagasicredi.exceptions.RegraNegocioException;
import com.desafiovagasicredi.exceptions.SenhaInvalidaException;
import com.desafiovagasicredi.model.entity.Associado;
import com.desafiovagasicredi.model.entity.Pauta;
import com.desafiovagasicredi.model.entity.enums.OpcoesVoto;
import com.desafiovagasicredi.model.repository.AssociadoRepository;
import com.desafiovagasicredi.model.repository.PautaRepository;
import com.desafiovagasicredi.service.PautaService;
import com.desafiovagasicredi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PautaServiceImpl implements PautaService {

    @Autowired
    private PautaRepository repository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Override
    public Pauta salvar(Pauta pauta){
        verificarAssociado(pauta.getCriador());
        validar(pauta);

        Associado associado = associadoRepository.findByCpf(pauta.getCriador().getCpf()).get();
        pauta.setCriador(associado);

        Pauta pautaSalva = repository.save(pauta);

        return pautaSalva;
    }

    @Override
    public void iniciarVotacaoPauta(Integer idPauta, Associado associado, Integer duracao) {
        verificarAssociado(associado);

        Pauta pauta = repository.findById(idPauta).orElseThrow(
                () -> new NotFoundException("Não foi encontrado nenhuma pauta com este ID"));

        Associado findAssociado = associadoRepository.findByCpf(associado.getCpf()).get();

        if(!findAssociado.getId().equals(pauta.getCriador().getId())){
            throw new RegraNegocioException("Apenas o criador da pauta pode iniciar a votação!");
        }

        if(Objects.nonNull(pauta.getInicioSessao()) || Objects.nonNull(pauta.getFimSessao())){
            throw new RegraNegocioException("Esta sessão já foi iniciada!");
        }

        if(duracao == null) {
            duracao = 60000;
        }

        pauta.setInicioSessao(new Date());
        pauta.setFimSessao(new Date(System.currentTimeMillis()+duracao));

        repository.save(pauta);
    }

    @Override
    public void votarPauta(Associado associado, Integer idPauta, OpcoesVoto voto) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(
                () -> new NotFoundException("Não foi encontrado nenhuma pauta com este ID"));

        if(pauta.getFimSessao().before(new Date())){
            throw new RegraNegocioException("Esta sessão já foi encerrada!");
        }

        verificarAssociado(associado);


        if(Objects.nonNull(pauta.getVotos())){
            pauta.getVotos().add(voto);
        }else{
            List<OpcoesVoto> votos = new ArrayList<>();
            votos.add(voto);

            pauta.setVotos(votos);
        }

        associado = associadoRepository.findByCpf(associado.getCpf()).get();
        List<Associado> listaAssociados = pauta.getAssociados();

        if(Objects.nonNull(listaAssociados)){
            if(listaAssociados.contains(associado)){
                throw new RegraNegocioException("Um mesmo associado não pode votar mais de uma vez!");
            }

            listaAssociados.add(associado);
        }else{
            List<Associado> associados = new ArrayList<Associado>();
            associados.add(associado);

            pauta.setAssociados(associados);
        }

        repository.save(pauta);
    }

    @Override
    public Map<String, Integer> retornarResultadoVotacaoPauta(Integer idPauta) {
        Pauta pauta = repository.findById(idPauta).orElseThrow(
                () -> new NotFoundException("Não foi encontrado nenhuma pauta com este ID"));

        if(pauta.getFimSessao().after(new Date())){
            throw new RegraNegocioException("Sessão ainda não foi finalizada!");
        }

        if(Objects.isNull(pauta.getVotos()) || pauta.getVotos().isEmpty()){
            throw new NotFoundException("Esta pauta não teve nenhum voto durante sua sessão!");
        }


        Integer sim = Collections.frequency(pauta.getVotos(), OpcoesVoto.SIM);
        Integer nao = Collections.frequency(pauta.getVotos(), OpcoesVoto.NAO);
        Integer total = pauta.getVotos().size();

        Map<String, Integer> resultado = new HashMap<String,Integer>();
        resultado.put("sim",sim);
        resultado.put("nao",nao);
        resultado.put("total",total);


        return resultado;
    }

    private void validar(Pauta pauta) {
        List<String> erros = verificarErros(pauta);

        if(!erros.isEmpty()){
            throw new RegraNegocioException(String.join("\r\n",erros));
        }
    }

    public List<String> verificarErros(Pauta pauta){
        List<String> msg = new ArrayList<>();

        if(Utils.isNotValid(pauta.getTema())){
            msg.add("Deve ser adicionado um tema para a Pauta!");
        }

        return msg;
    }

    public void verificarAssociado(Associado associado) {
        if(Utils.isNotValid(associado.getCpf())){
            throw new RegraNegocioException("O CPF não pode ser nulo!");
        }

        if (!associadoRepository.existsByCpf(associado.getCpf())) {
            throw new NotFoundException("Este CPF não foi encontrado em nossa base de dados!");
        }
    }
}
