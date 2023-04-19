package me;

import Graphics.Model.ModelLoader;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import Graphics.Display.Window;
import Graphics.Camera.Camera;



public class J_Vox {

    private final Window window;
    public static ModelLoader modelLoader;
   // private final WorldRenderer worldRenderer;
    public J_Vox() {
        this.window = new Window(1280, 720, "JVox");
    }

    public void init() {
        GL.createCapabilities();
        window.show();
        window.setClearColor(0.5f, 0.125f, 0.25f, 1f);

        Camera cam = new Camera();

        float timer = System.nanoTime();
        float delta = 1f;

        while(window.keepOpen()){
            window.clearBuffers();
            cam.update(window.getId(), delta);
            window.update();

            delta = (System.nanoTime() - timer) / 1000000000f;
            timer = System.nanoTime();

            GLFW.glfwSetWindowTitle(window.getId(), "J_Vox | FPS: " + (int) (1f / delta) + " (delta: " + delta + "s)");
        }
    }

}
