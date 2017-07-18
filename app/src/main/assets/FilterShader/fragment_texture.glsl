precision mediump float;
uniform float percent;
uniform sampler2D a_Texture;
varying vec2 v_Coordinate;
/**/
void main() {
    vec4 color = texture2D(a_Texture, v_Coordinate);
//    percent = 0.0f;
    vec3 rgb = color.rgb + percent;
//    float c = color.r * 1.0;
    gl_FragColor = vec4(rgb, color.a);
}