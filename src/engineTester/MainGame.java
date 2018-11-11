package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import particles.Particle;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.particleTexture;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import Terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGame {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);



		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(5);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		
		Entity entity = new Entity(barrelModel, new Vector3f(150, 5, -150), 0, 0, 0, 1f);
		Entity entity1 = new Entity(barrelModel, new Vector3f(150, 5, -100), 0, 0, 0, 1f);
		Entity entity2 = new Entity(barrelModel, new Vector3f(100, 5, -100), 0, 0, 0, 1f);
		Entity entity3 = new Entity(barrelModel, new Vector3f(200, 5, -200), 0, 0, 0, 1f);
		Entity entity4 = new Entity(barrelModel, new Vector3f(200, 5, -150), 0, 0, 0, 1f);
		Entity entity5 = new Entity(barrelModel, new Vector3f(150, 5, -200), 0, 0, 0, 1f);
		Entity entity6 = new Entity(barrelModel, new Vector3f(100, 5, -150), 0, 0, 0, 1f);
		Entity entity7 = new Entity(barrelModel, new Vector3f(100, 5, -200), 0, 0, 0, 1f);
		Entity entity8 = new Entity(barrelModel, new Vector3f(200, 5, -100), 0, 0, 0, 1f);
		normalMapEntities.add(entity);

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);

		MasterRenderer renderer = new MasterRenderer(loader);

		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		
		RawModel playerModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel playerSkin = new TexturedModel(playerModel, new ModelTexture(
				loader.loadTexture("playerTexture")));

		Player player = new Player(playerSkin, new Vector3f(50, 5, -100), 0, 100, 0, 0.6f);
		entities.add(player);
		Camera camera = new Camera(player);
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		particleTexture particleTexture = new particleTexture(loader.loadTexture("firep"),1);
		
		ParticleSystem system = new ParticleSystem(particleTexture, 10000, 50, 2, 1, 1);
		
		
		while (!Display.isCloseRequested()) {
			player.move(terrain);
			camera.move();
			picker.update();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
				if(player.getPosition().getX()<170 && player.getPosition().getX()>130 &&player.getPosition().getZ()>-170&& player.getPosition().getZ()<-130) {
					system.generateParticles(new Vector3f(150, 5, -150));
					normalMapEntities.remove(entity);
				}	else if(player.getPosition().getX()<170 && player.getPosition().getX()>130 &&player.getPosition().getZ()>-120&& player.getPosition().getZ()<-80) {
					system.generateParticles(new Vector3f(150, 5, -100));
					normalMapEntities.remove(entity1);
				}else if(player.getPosition().getX()<120 && player.getPosition().getX()>80 &&player.getPosition().getZ()>-120&& player.getPosition().getZ()<-80) {
					system.generateParticles(new Vector3f(100, 5, -100));
					normalMapEntities.remove(entity2);
				}else if(player.getPosition().getX()<220 && player.getPosition().getX()>180 &&player.getPosition().getZ()>-220&& player.getPosition().getZ()<-180) {
					system.generateParticles(new Vector3f(200, 5, -200));
					normalMapEntities.remove(entity3);
				}else if(player.getPosition().getX()<220 && player.getPosition().getX()>180 &&player.getPosition().getZ()>-170&& player.getPosition().getZ()<-130) {
					system.generateParticles(new Vector3f(200, 5, -150));
					normalMapEntities.remove(entity4);
				}else if(player.getPosition().getX()<170 && player.getPosition().getX()>130 &&player.getPosition().getZ()>-220&& player.getPosition().getZ()<-180) {
					system.generateParticles(new Vector3f(150, 5, -200));
					normalMapEntities.remove(entity5);
				}else if(player.getPosition().getX()<120 && player.getPosition().getX()>80 &&player.getPosition().getZ()>-170&& player.getPosition().getZ()<-130) {
					system.generateParticles(new Vector3f(100, 5, -150));
					normalMapEntities.remove(entity6);
				}else if(player.getPosition().getX()<120 && player.getPosition().getX()>80 &&player.getPosition().getZ()>-220&& player.getPosition().getZ()<-180) {
					system.generateParticles(new Vector3f(100, 5, -200));
					normalMapEntities.remove(entity7);
				}else if(player.getPosition().getX()<220 && player.getPosition().getX()>180 &&player.getPosition().getZ()>-120&& player.getPosition().getZ()<-80) {
					system.generateParticles(new Vector3f(200, 5, -100));
					normalMapEntities.remove(entity8);
				}
			}
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				
				Random rand = new Random();
				int  n = rand.nextInt(9);
				
				if(!normalMapEntities.contains(entity) && n ==0) {
				normalMapEntities.add(entity);	
				}else if (!normalMapEntities.contains(entity1) && n ==1) {
				normalMapEntities.add(entity1);	
				}else if(!normalMapEntities.contains(entity2) && n ==2) {
					normalMapEntities.add(entity2);	
				}else if(!normalMapEntities.contains(entity3) && n ==3) {
					normalMapEntities.add(entity3);	
				}else if(!normalMapEntities.contains(entity4) && n ==4) {
					normalMapEntities.add(entity4);	
				}else if(!normalMapEntities.contains(entity5) && n ==5) {
					normalMapEntities.add(entity5);	
				}else if(!normalMapEntities.contains(entity6) && n ==6) {
					normalMapEntities.add(entity6);	
				}else if(!normalMapEntities.contains(entity7) && n ==7) {
					normalMapEntities.add(entity7);	
				}else if(!normalMapEntities.contains(entity8) && n ==8) {
					normalMapEntities.add(entity8);	
				}
			}
			
			ParticleMaster.update();
			
			entity.increaseRotation(0, 0, 0);
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			

			float distance = 2 * (camera.getPosition().y);
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, 0));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 0));
			

			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);

			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));	
			
			ParticleMaster.renderParticles(camera);
			
			guiRenderer.render(guiTextures);
			
			DisplayManager.updateDisplay();
		}
		
		ParticleMaster.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}


}