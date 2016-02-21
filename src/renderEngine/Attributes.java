package renderEngine;

public enum Attributes
{
	Positions(0), TextureCoords(1);

	private Attributes(int number)
	{
		this.number = number;
	}

	private int number;

	public int number()
	{
		return number;
	}
}
