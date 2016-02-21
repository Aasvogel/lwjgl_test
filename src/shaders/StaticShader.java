package shaders;

import org.lwjgl.util.vector.Matrix4f;

import toolbox.Maths;
import entities.Camera;

public class StaticShader extends ShaderProgram
{

	private static final String VERTEX_FILE = "src/shaders/vertexShader.vert";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.frag";
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;

	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_transformationMatrix = getUniformLocation("transformationMatrix");
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix)
	{
		loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}

}
