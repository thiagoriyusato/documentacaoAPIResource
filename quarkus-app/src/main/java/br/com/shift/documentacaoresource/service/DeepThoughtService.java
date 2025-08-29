package br.com.shift.documentacaoresource.service;

import br.com.shift.documentacaoresource.dto.RespostaDto;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class DeepThoughtService {

  public RespostaDto fazerPergunta(String questaoFundamentalVidaUniversoTudoMais) {
    return RespostaDto.builder()
        .nome("DeepThought")
        .questaoFundamentalVidaUniversoTudoMais(questaoFundamentalVidaUniversoTudoMais)
        .build();
  }

}
