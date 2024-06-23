package org.kgromov.rest;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.quarkus.runtime.util.StringUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kgromov.owner.Owner;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/owners")
@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource {

    // TODO: replace with @MultipartForm
    @POST
    @Path("")
    @Transactional
    public Response createOwner(@Valid Owner owner) {
        Owner newOwner = new Owner();
        updateOwnerFields(owner, newOwner);
        return Response.status(201)
                .location(URI.create("/owners/" + newOwner.id))
                .build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateOwner(@PathParam("id") Long ownerId, @Valid Owner updateRequest) {
        Owner owner = Owner.findById(ownerId);
        if (Objects.isNull(owner)) {
            return notFound();
        }
        updateOwnerFields(updateRequest, owner);
        return Response.ok("Updated")
                .location(URI.create("/owners/" + owner.id))
                .build();
    }

    @GET
    @Path("{id}")
    public Response findOwnerById(@PathParam("id") Long ownerId) {
        return Owner.findByIdOptional(ownerId)
                .map(owner -> Response.ok(owner).build())
                .orElseGet(this::notFound);
    }

    @GET
    @Path("")
    public Response findOwners(@QueryParam("lastName") String lastName,
                               @QueryParam("page") @DefaultValue("1") Integer page) {
        var owners = this.findPaginatedForOwnersLastName(lastName, page);
        if (owners.isEmpty()) {
            return notFound();
        }
        return Response.ok(owners).build();
    }

    private List<Owner> findPaginatedForOwnersLastName(String lastName, int page) {
        int pageSize = 5;
        var query = StringUtil.isNullOrEmpty(lastName)
                ? Owner.findAll()
                : Owner.find("LOWER(lastName) LIKE LOWER(:lastName)",
                Sort.ascending("lastName"),
                Parameters.with("lastName", '%' + lastName + '%')
        );
        return query
                .page(Page.of(page - 1, pageSize))
                .list();
    }

    private void updateOwnerFields(Owner owner, Owner newOwner) {
        newOwner.firstName = owner.firstName;
        newOwner.lastName = owner.lastName;
        newOwner.address = owner.address;
        newOwner.city = owner.city;
        newOwner.telephone = owner.telephone;
        newOwner.persist();
    }

    private Response notFound() {
        return Response.status(404)
                .entity("No owner found")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
