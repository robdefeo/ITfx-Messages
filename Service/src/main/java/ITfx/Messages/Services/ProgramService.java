package ITfx.Messages.Services;

import ITfx.Messages.DataAccess.IProgramRepository;
import ITfx.Messages.DataAccess.ProgramRepository;
import ITfx.Messages.Phase;
import ITfx.Messages.PhaseMessage;
import ITfx.Messages.Program;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.mongodb.BasicDBObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.List;

@Path("/program")
public class ProgramService {
    public static String PROGRAM_NOT_FOUND_ERROR = "program not found";
    public static String PHASE_NOT_FOUND_ERROR = "phase not found";
    public static String PROGRAM_NULL_ERROR = "null program";

    final IProgramRepository programRepository;

    public ProgramService() throws UnknownHostException {
        this.programRepository = new ProgramRepository();
    }

    public ProgramService(IProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String ListAll() {
        List<Program> programList = programRepository.getAll();
        throw new NotImplementedException();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        final BasicDBObject program = programRepository.getById(id);
        if (program == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(program)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(Program item) {
        final BasicDBObject program = programRepository.insert(item);
        if (program == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(PROGRAM_NULL_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(program)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/phases")
    public Response insertPhase(@PathParam("id") String programId, Phase phase) {
        final BasicDBObject program = programRepository.getById(programId);
        if (program == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(PROGRAM_NOT_FOUND_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        final Phase insertedPhase = programRepository.addPhase(new Program(program), phase);

        if (insertedPhase == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(insertedPhase)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{programId}/phases/{phaseId}/messages")
    public Response insertPhaseMessage(final @PathParam("programId") String programId,
                                       final @PathParam("phaseId") String phaseId,
                                       PhaseMessage phaseMessage) {
        final BasicDBObject programDBObject = programRepository.getById(programId);
        if (programDBObject == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(PROGRAM_NOT_FOUND_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();        }
        final Program program = new Program(programDBObject);

        final BasicDBObject phaseDBObject = Iterables.find(program.getPhases(), new Predicate<Phase>() {
            @Override
            public boolean apply(@Nullable Phase phase) {
                return phase.get_id().equals(phaseId);
            }
        }, null);

        if (phaseDBObject == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(PHASE_NOT_FOUND_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();        }

        PhaseMessage insertedPhaseMessage = programRepository.addPhaseMessage(program, new Phase(phaseDBObject), phaseMessage);

        if (phaseMessage == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return Response.status(Response.Status.OK).entity(insertedPhaseMessage).build();
        }


    }
}