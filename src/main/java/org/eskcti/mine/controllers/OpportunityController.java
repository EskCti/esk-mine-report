package org.eskcti.mine.controllers;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eskcti.mine.dto.OpportunityDTO;
import org.eskcti.mine.services.OpportunityService;

import java.util.Date;
import java.util.List;

@Path("/api/opportunity")
@Authenticated
public class OpportunityController {
    @Inject
    OpportunityService opportunityService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed({"user", "manager"})
    public Response generateReport() {
        try {
            return Response
                    .ok(
                            opportunityService.generateCSVOpportunityReport(),
                            MediaType.APPLICATION_OCTET_STREAM
                    )
                    .header(
                            "content-disposition",
                            "attachment; filename=" + new Date() + "--opportunity-venda.csv"
                    )
                    .build();
        } catch (ServerErrorException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/data")
    @RolesAllowed({"user", "manager"})
    public List<OpportunityDTO> generateData() {
        return opportunityService.generateOpportunityData();
    }
}
