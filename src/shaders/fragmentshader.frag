#version 400 core

in vec2 pass_textureCoords;
in vec3 colour;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void)
{
	//out_Color = vec4(colour,1.0);
	out_Color = texture(textureSampler,pass_textureCoords);
}