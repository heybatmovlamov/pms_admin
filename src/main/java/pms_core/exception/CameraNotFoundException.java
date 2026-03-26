package pms_core.exception;

public class CameraNotFoundException extends CommonException {

    public CameraNotFoundException() {
        super("camera not found", "Camera not found");
    }
}
