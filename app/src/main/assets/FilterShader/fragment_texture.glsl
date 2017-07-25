precision mediump float;
uniform int type; // 0: 亮度 1:普通融合
uniform float percent;
uniform sampler2D a_Texture1;
uniform sampler2D a_Texture;
varying vec2 v_Coordinate;
/**/
void main() {
    vec4 color = texture2D(a_Texture, v_Coordinate);
    vec4 rgba;
    if (type == 0) {
        vec3 rgb = color.rgb + percent;
        rgba = vec4(rgb, color.a);
    } else if (type == 1) {
        vec4 color1 = texture2D(a_Texture1, v_Coordinate);
        rgba.r = color1.r + color.r * color.a * (1.0 - color1.a);
        rgba.g = color1.g + color.g * color.a * (1.0 - color1.a);
        rgba.b = color1.b + color.b * color.a * (1.0 - color1.a);
        rgba.a = color1.a + color.a * (1.0 - color1.a);
    }

    gl_FragColor = rgba;
}