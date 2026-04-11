package com.itesm.interfaces.rest;

import com.itesm.application.dto.CreateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.application.usecase.CreateTodoUseCase;
import com.itesm.application.usecase.DeleteTodoUseCase;
import com.itesm.domain.models.Todo;
import java.util.UUID;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public TodoResource(CreateTodoUseCase createTodoUseCase, DeleteTodoUseCase deleteTodoUseCase,
            AuthenticatedUserContext authenticatedUserContext) {
        this.createTodoUseCase = createTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    @POST
    public Response createTodo(CreateTodoDto createTodoDto) {
        Todo todo = createTodoUseCase.execute(createTodoDto);
        return Response.ok(todo).build();
    }

    @Path("/test")
    @GET
    public Response getTodo() {
        System.out.println("Desde el endpoint: " + authenticatedUserContext.getCurrentUser().getFullName());
        return Response.ok("TEST").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodo(@PathParam("id") UUID id) {
        // 🐛 BUG INTENCIONAL: Ignoramos el id recibido y borramos uno al azar (que no
        // existirá)
        // en vez de usar el ID que viene en el path.
        deleteTodoUseCase.execute(UUID.randomUUID());

        return Response.noContent().build();
    }

}
