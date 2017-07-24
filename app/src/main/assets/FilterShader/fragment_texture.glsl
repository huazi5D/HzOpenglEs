precision mediump float;
uniform float percent;
uniform sampler2D a_Texture;
uniform sampler2D a_Texture2;
varying vec2 v_Coordinate;
/**/
void main() {
    vec4 color = texture2D(a_Texture, v_Coordinate);
    vec3 rgb = color.rgb + percent;
    gl_FragColor = vec4(rgb, color.a);
}