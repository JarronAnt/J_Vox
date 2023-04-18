package Graphics.Model;

public class Model {
    private int vao;
    private int vertexCount;
    private int textureId;

    public Model(int vao, int vertexCount) {
        this.vao = vao;
        this.vertexCount = vertexCount;
    }


    //getters & setters
    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }
    public int getVao() {
        return vao;
    }
    public int getVertexCount() {
        return vertexCount;
    }
    public int getTextureId() {
        return textureId;
    }
}
