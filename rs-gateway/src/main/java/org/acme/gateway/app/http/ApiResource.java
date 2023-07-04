package org.acme.gateway.app.http;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.annotation.security.PermitAll;

import java.util.Set;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@Path("/login")
public class ApiResource {
    @GET
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String email = "email@email.com";
        return Jwt.issuer("rs-gateway")
            .upn(email)
            .groups(Set.of("user"))
            .claim(Claims.full_name, "Usuário teste")
            .claim(Claims.email, email)
            .subject("53517655-dbff-4731-83a2-fb9d9d64daa7")
            .innerSign()
            .encrypt();
    }
}
