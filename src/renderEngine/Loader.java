package renderEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Loader
{
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, float[] textureCoords,
	        int[] indices)
	{
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(Attributes.Positions.number(), 3, positions);
		storeDataInAttributeList(Attributes.TextureCoords.number(), 2,
		        textureCoords);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	public int loadTexture(String fileName)
	{
		Texture texture = null;
		try
		{
			texture = TextureLoader.getTexture("PNG", new FileInputStream(
			        "res/" + fileName + ".png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		int textureID = texture.getTextureID();
		textures.add(Integer.valueOf(textureID));
		return textureID;
	}

	public void cleanUp()
	{
		for (Integer vao : vaos)
			GL30.glDeleteVertexArrays(vao.intValue());
		for (Integer vbo : vbos)
			GL15.glDeleteBuffers(vbo.intValue());
		for (Integer textureID : textures)
			GL11.glDeleteTextures(textureID.intValue());
	}

	private int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(Integer.valueOf(vaoID));
		GL30.glBindVertexArray(vaoID); // TODO rausziehen damit symmetrie
		// erkennbar!
		return vaoID;
	}

	private void storeDataInAttributeList(int attributeNumber,
	        int coordinateSize, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(Integer.valueOf(vboID));

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		{
			FloatBuffer buffer = storeDataInFloatBuffer(data);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(attributeNumber, coordinateSize,
			        GL11.GL_FLOAT, false, 0, 0);
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}

	private void bindIndicesBuffer(int[] indices)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(Integer.valueOf(vboID));

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		{
			IntBuffer buffer = storeDataInIntBuffer(indices);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer,
			        GL15.GL_STATIC_DRAW);
		}
		// GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); !Hier nicht!
		// warum?
	}

	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
