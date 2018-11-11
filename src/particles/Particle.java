package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Player;
import renderEngine.DisplayManager;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;

	private particleTexture texture;
	
	private Vector2f texoffset1 = new Vector2f();
	private Vector2f texoffset2 = new Vector2f();
	private float blend;
	
	private float elapsedTime=0;
	
	public Particle(particleTexture texture,Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		super();
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector2f getTexoffset1() {
		return texoffset1;
	}

	public void setTexoffset1(Vector2f texoffset1) {
		this.texoffset1 = texoffset1;
	}

	public Vector2f getTexoffset2() {
		return texoffset2;
	}

	public void setTexoffset2(Vector2f texoffset2) {
		this.texoffset2 = texoffset2;
	}

	public float getBlend() {
		return blend;
	}

	public void setBlend(float blend) {
		this.blend = blend;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public particleTexture getTexture() {
		return texture;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	protected boolean update() {
		velocity.y +=Player.GRAVITY*gravityEffect*DisplayManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f (velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
	
	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberofRows()*texture.getNumberofRows();
		float atlasProgression = lifeFactor* stageCount;
		int index1 =(int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount -1? index1 +1 :index1;
		this.blend = atlasProgression %1;
		setTextureOffset(texoffset1, index1);
		setTextureOffset(texoffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index) {
		int column = index % texture.getNumberofRows();
		int row = index /texture.getNumberofRows();
		offset.x = (float) column / texture.getNumberofRows();
		offset.y = (float) row / texture.getNumberofRows();
	}
}
