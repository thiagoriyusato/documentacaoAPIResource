package br.com.shift.documentacaoresource.dto;

import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Builder;

@Builder
@Schema(
    name = "Resposta",
    description = "Objeto que representa uma resposta do sistema",
    extensions = {
        @Extension(name = "x-displayName", value = "Resposta"),
        @Extension(name = "x-order", value = "1")
    }
)
public record RespostaDto(

    String nome,
    String questaoFundamentalVidaUniversoTudoMais

) {

  public static final String NOME_EXEMPLO = "Fulano de Tal";

  public static final String RESPOSTA_DTO_REF = """
      {
        "nome": "Fulano de Tal",
        "questaoFundamentalVidaUniversoTudoMais": "42"
      }
      """;

}
