package renderEngine;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import shaders.StaticShader;
import toolbox.Maths;
import entities.Entity;

public class Renderer
{
	private static final float FIELDOFVIEW = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;

	private static Matrix4f projectionMatrix;

	static
	// zum anlegen der Matrix.
	{
		// Mathemagie! funktioniert halt so.
		float aspectRatio = (float) Display.getWidth()
				/ (float) Display.getHeight();
		float x_scale = (float) (1f / Math
				.tan(Math.toRadians(FIELDOFVIEW / 2f)));
		float y_scale = x_scale * aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE - NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;

	}

	public Renderer(StaticShader shader)
	{
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(1, 0, 0, 1);
	}

	public void render(Entity entity, StaticShader shader)
	{
		TexturedModel texturedModel = entity.getModel();
		RawModel rawModel = texturedModel.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());

		for (Attributes attr : Attributes.values())
		{
			GL20.glEnableVertexAttribArray(attr.number());
		}

		Matrix4f transformationMatrix = Maths.createTransformationMatrix(
				entity.getPosition(), //
				entity.getRotX(), //
				entity.getRotY(),//
				entity.getRotZ(), //
				entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture()
				.getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(),
				GL11.GL_UNSIGNED_INT, 0);

		for (Attributes attr : Attributes.values())
		{
			GL20.glDisableVertexAttribArray(attr.number());
		}

		GL30.glBindVertexArray(0);
	}

}
