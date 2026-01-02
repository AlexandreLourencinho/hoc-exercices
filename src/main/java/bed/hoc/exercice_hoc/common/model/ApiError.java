package bed.hoc.exercice_hoc.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
public class ApiError {

    private final Instant timeStamp = Instant.now();
    private int statusCode;
    private String message;
    private String path;
    private String errorCode;

    public ApiError(int statusCode, String message, String path, String errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }

}
