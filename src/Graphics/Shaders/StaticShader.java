package Graphics.Shaders;

import Graphics.Camera.Camera;
import Utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class StaticShader extends ShaderProgram{

    private static final String VERTEX_FILE = "Res/Shaders/vertex.glsl";
    private static final String FRAGMENT_FILE = "Res/Shaders/fragment.glsl";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationTranslation;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationTranslation = super.getUniformLocation("translation");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "color");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(locationViewMatrix, Maths.createViewMatrix(camera));
    }

    public void loadTranslation(Vector2f position) {
        super.loadVector(locationTranslation, new Vector2f(position).mul(32));
    }

}