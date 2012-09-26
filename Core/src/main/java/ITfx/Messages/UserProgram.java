package ITfx.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Map;

public final class UserProgram extends BasicDBObject {
    public String getProgramId() {
        return getString("programId");
    }
    public String getCurrentPhaseId()
    {
        return getString("currentPhaseId");
    }

    @JsonCreator
    public UserProgram(
            @JsonProperty("programId") String programId,
            @JsonProperty("currentPhaseId") String currentPhaseId
    ) {
        put("programId", programId);
        put("currentPhaseId", currentPhaseId);
    }
    public UserProgram(DBObject object) {
        super((Map) object);
    }
}

