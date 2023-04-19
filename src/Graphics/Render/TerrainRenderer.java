package Graphics.Render;

import Graphics.Camera.Camera;
import Graphics.Shaders.StaticShader;
import Utils.Maths;
import World.World;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL30;

import static World.World.CHUNK_WIDTH;

public class TerrainRenderer {
    private final Matrix4f projectionMatrix;
    public TerrainRenderer() {
        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.perspective(70, 16f/9f, 0.1f, 5000);
    }

    public void render(World world, StaticShader shader, Camera camera) {
        GL30.glEnable(GL30.GL_DEPTH_TEST);

        shader.loadProjectionMatrix(projectionMatrix);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, world.getTextureId());

        long timer = System.nanoTime();
        world.chunks.forEach((position, chunk) -> {
            if (chunk.getModel() == null) return;

            GL30.glBindVertexArray(chunk.getModel().getVao());
            GL30.glEnableVertexAttribArray(0);
            GL30.glEnableVertexAttribArray(1);
            shader.loadTranslation(position);
            GL30.glDrawElements(GL30.GL_TRIANGLES, chunk.getModel().getVertexCount(), GL30.GL_UNSIGNED_INT, 0);
        });
        GL30.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
