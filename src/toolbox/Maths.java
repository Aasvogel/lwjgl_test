package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

public class Maths
{

	private Maths()
	{
	};

	public static Matrix4f createTransformationMatrix(Vector3f translation,
	        float rx, float ry, float rz, float scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.translate(translation).rotate(toRad(rx), new Vector3f(1, 0, 0)) //
		        .rotate(toRad(ry), new Vector3f(0, 1, 0)) //
		        .rotate(toRad(rz), new Vector3f(0, 0, 1)) //
		        .scale(new Vector3f(scale, scale, scale));

		return matrix;
	}

	private static float toRad(float degree)
	{
		return (float) Math.toRadians(degree);
	}

	public static Matrix4f createViewMatrix(Camera cam)
	{
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();

		Vector3f negCameraPos = new Vector3f(-cam.getPosition().x, //
		        -cam.getPosition().y, //
		        -cam.getPosition().z);

		viewMatrix //
		        .rotate(toRad(cam.getPitch()), new Vector3f(1, 0, 0)) //
		        .rotate(toRad(cam.getYaw()), new Vector3f(0, 1, 0)) //
		        .rotate(toRad(cam.getRoll()), new Vector3f(0, 0, 1)) //
		.translate(negCameraPos);

		return viewMatrix;
	}
}
