package br.com.shift.documentacaoresource.resource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.callbacks.Callback;
import org.eclipse.microprofile.openapi.annotations.callbacks.CallbackOperation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.links.Link;
import org.eclipse.microprofile.openapi.annotations.links.LinkParameter;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.ServerVariable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.spi.OASFactoryResolver;

import br.com.shift.documentacaoresource.dto.RespostaDto;
import br.com.shift.documentacaoresource.service.DeepThoughtService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;

@Path("/deep-thought")
@Slf4j
@Tag(name = "OpenAPI", description = "Tag global exemplo")
public class DeepThoughtResource {

  @Inject
  DeepThoughtService exemploService;

  @Context
  UriInfo uriInfo;

  @POST
  @Path("/{name}")
  @Operation(
      summary = "Retorna exemplo",
      description = "Retorna um DTO de exemplo",
      operationId = "getExemplo"
  )
  @Tag(
      name = "Exemplo",
      description = "Operações relacionadas ao exemplo"
  )
  @APIResponse(
      responseCode = "200",
      description = "Resposta do Deep Thought",
      content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(
              type = SchemaType.OBJECT,
              implementation = RespostaDto.class
          ),
          examples = {
              @ExampleObject(
                  name = RespostaDto.NOME_EXEMPLO,
                  value = RespostaDto.RESPOSTA_DTO_REF
              )
          }
      ),
      headers = {
        @Header(
            name = "content-length",
            description = "Tamanho do conteúdo",
            schema = @Schema(
                type = SchemaType.INTEGER
            )
        ),
        @Header(
            name = "content-type",
            description = "Tipo do conteúdo",
            schema = @Schema(
                type = SchemaType.STRING
            )
        )
      }
      )
  @Callback(
      name = "getResposta",
      callbackUrlExpression = "/deep-thought",
      operations = @CallbackOperation(
          method = "GET",
          summary = "Callback para obter resposta",
          description = "Este callback é chamado após a pergunta ser feita ao Deep Thought",
          responses = {
              @APIResponse(
                  responseCode = "200",
                  description = "Resposta do Deep Thought",
                  content = @Content(
                      mediaType = MediaType.APPLICATION_JSON,
                      schema = @Schema(
                          type = SchemaType.STRING,
                          examples = "42"
                      )
                  )
              )
          }
      )
  )
  public RespostaDto fazerPergunta(
    @Parameter(
        in = ParameterIn.PATH,
        description = "Qual seu nome?",
        required = true,
        examples = @ExampleObject(
            name = "Thiago",
            value = "Thiago"
        ),
        content = @Content(
            mediaType = MediaType.TEXT_PLAIN,
            schema = @Schema(
                type = SchemaType.STRING
            )
        )
    ) @PathParam("name") String nome) {
    String respostaPergunta = null;
    try {
      String baseUrl = uriInfo.getBaseUri().toString();
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(baseUrl + "/deep-thought"))
          .GET()
          .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      respostaPergunta = response.body();
    } catch (Exception e) {
      respostaPergunta = "Não sei :(";
    }
    return exemploService.fazerPergunta(respostaPergunta);
  }

  @GET
  @APIResponse(
      responseCode = "200",
      description = "Resposta do Deep Thought",
      content = @Content(
          mediaType = MediaType.TEXT_PLAIN,
          schema = @Schema(
              type = SchemaType.STRING, examples = "42"
          )
      )
  )
  @Operation(
      summary = "Obtém a resposta do Deep Thought",
      description = """
        ## 🤖 Deep Thought responde!

        Este endpoint retorna a resposta do Deep Thought para a pergunta feita à máquina mais inteligente do universo.

        > **Pergunte e receba a resposta definitiva!**

        ✨ *Exemplo de resposta*: `42`

        🔒 Requer papel de usuário (`USER`)
        """,
      operationId = "getResposta",
      extensions = {
          @Extension(name = "x-requires-role", value = "USER")
      },
      hidden = false)
  public String getResposta() {
    return "42";
  }
}
