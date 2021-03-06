package particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/particles/particleVShader.txt";
	private static final String FRAGMENT_FILE = "src/particles/particleFShader.txt";

	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_texoffset1;
	private int location_texoffset2;
	private int location_texCoordInfo;
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}


	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_texoffset1 = super.getUniformLocation("texoffset1");
		location_texoffset2 = super.getUniformLocation("texoffset2");
		location_texCoordInfo = super.getUniformLocation("texCoordInfo");
	
	
	}


	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	protected void loadTextureCoordInfo(Vector2f offset1, Vector2f offset2, float numRows, float blend) {
		super.load2DVector(location_texoffset1, offset1);
		super.load2DVector(location_texoffset2, offset2);
		super.load2DVector(location_texCoordInfo, new Vector2f(numRows, blend));
	}
	
	protected void loadModelViewMatrix(Matrix4f modelView) {
		super.loadMatrix(location_modelViewMatrix, modelView);
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}
