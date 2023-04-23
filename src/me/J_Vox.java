package me;

import Graphics.Model.ModelLoader;
import Graphics.Render.TerrainRenderer;
import Graphics.Shaders.StaticShader;
import World.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL30;

import Graphics.Display.Window;
import Graphics.Camera.Camera;



public class J_Vox {

    private final Window window;
    public static ModelLoader modelLoader;
    public static TerrainRenderer terrainRenderer;
   // private final WorldRenderer worldRenderer;
    public J_Vox() {
        this.window = new Window(1280, 720, "JVox");
        modelLoader = new ModelLoader();
        this.terrainRenderer = new TerrainRenderer();
    }

    public void init() {
        GL.createCapabilities();
        StaticShader shader = new StaticShader();
        Camera cam = new Camera();

        window.show();
        window.setClearColor(0.5f, 0.125f, 0.25f, 1f);

        World world = new World(modelLoader);

        int mouse = 0;

        new Thread(() -> {
            while (window.keepOpen()) {
                world.generateNextChunk();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        float timer = System.nanoTime();
        float delta = 1f;

        while(window.keepOpen()){
            window.clearBuffers();
            cam.update(window.getId(), delta);
            terrainRenderer.updateFrustum(cam);
            shader.start();
            shader.loadViewMatrix(cam);
            terrainRenderer.render(world, shader, cam);
            shader.stop();

            long update = System.nanoTime();
            world.updateNextChunk();
            System.out.println((System.nanoTime() - update) / 1000000f);


            if (GLFW.glfwGetMouseButton(window.getId(), 0) == 0 &&
                    GLFW.glfwGetMouseButton(window.getId(), 1) == 0) mouse = 0;

            if (mouse != 1 && GLFW.glfwGetMouseButton(window.getId(), 0) != 0) {
                mouse = 1;
                world.raycast(cam.getPosition(), cam.getDirection(), 10, false);
            }

            if (mouse != 1 && GLFW.glfwGetMouseButton(window.getId(), 1) != 0) {
                mouse = 1;
                world.raycast(cam.getPosition(), cam.getDirection(), 10, true);
            }

            window.update();

            delta = (System.nanoTime() - timer) / 1000000000f;
            timer = System.nanoTime();

            GLFW.glfwSetWindowTitle(window.getId(), "J_Vox Engine | FPS: " + (int) (1f / delta));
        }
    }

}
