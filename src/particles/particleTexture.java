package particles;

public class particleTexture {

	private int textureID;
	private int numberofRows;
	
	public particleTexture(int textureID, int numberofRows) {
		super();
		this.textureID = textureID;
		this.numberofRows = numberofRows;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public int getNumberofRows() {
		return numberofRows;
	}

	public void setNumberofRows(int numberofRows) {
		this.numberofRows = numberofRows;
	}
	
	
	
}
