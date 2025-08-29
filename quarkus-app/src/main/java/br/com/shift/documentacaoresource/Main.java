package br.com.shift.documentacaoresource;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.ServerVariable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.shift.documentacaoresource.dto.RespostaDto;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.core.Application;

@QuarkusMain
@OpenAPIDefinition(
    info = @Info(
        title = "Deep Thought API",
        version = "1.0.0",
          description = "Exemplos de todas as anotações do MicroProfile OpenAPI.",
        contact = @Contact(name = "Thiago Sato", email = "thiago.sato@exemplo.com.br",
            url = "https://github.com/thiagoriyusato")
    ),
    servers = {
        @Server(url = "http://localhost:5667", description = "Servidor local"),
        @Server(url = "https://api.exemplo.com", description = "Servidor Produção (não funciona essa URL)",
                variables = {
                    @ServerVariable(name = "port", defaultValue = "8080", description = "Porta do servidor")
                })
    },
    externalDocs = @ExternalDocumentation(description = "Documentação externa", url = "https://docs.exemplo.com"),
    tags = {
      @Tag(name = "OpenAPI", description = "Tag de exemplo"),
      @Tag(name = "Teste", description = "Operações relacionadas ao exemplo")
    },
    security = {
      @SecurityRequirement(name = "bearerAuth")
    },
    extensions = {
      @Extension(name = "x-custom-extension", value = "Valor da extensão personalizada")
    }
)
public class Main extends Application {
  public static void main(String... args) {
    Quarkus.run(args);
  }
}