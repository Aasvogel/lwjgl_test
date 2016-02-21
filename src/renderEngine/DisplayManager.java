package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int FPS_CAP = 120;

	public static void createDisplay()
	{
		try
		{
			ContextAttribs attribs = new ContextAttribs(3, 2)
					.withForwardCompatible(true).withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);

			Display.setTitle("test");
		} catch (LWJGLException e)
		{
			throw new RuntimeException(e);
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	public static void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
	}

	public static void closeDisplay()
	{
		Display.destroy();
	}
}
