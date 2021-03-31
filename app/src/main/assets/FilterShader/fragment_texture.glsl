precision mediump float;
uniform int type; // 0: 亮度 1:普通融合 2 :柔光模式
uniform float percent;
uniform sampler2D a_Texture1;
uniform sampler2D a_Texture;
varying vec2 v_Coordinate;

mat3 convolutionKernel = mat3(-1.0, -1.0, 0.0,
                              -1.0, 0.0, 1.0,
                              0.0, 1.0, 1.0);

float imageWidthFactor = 1.0 / 1080.0;
float imageHeightFactor = 1.0 / 780.0;

/**/
void main() {

    // 锐化
    vec3 color00 = texture2D(a_Texture1, vec2(v_Coordinate.x - imageWidthFactor, v_Coordinate.y - imageHeightFactor)).rgb;
    vec3 color01 = texture2D(a_Texture1, vec2(v_Coordinate.x      , v_Coordinate.y - imageHeightFactor)).rgb;
    vec3 color02 = texture2D(a_Texture1, vec2(v_Coordinate.x + imageWidthFactor, v_Coordinate.y - imageHeightFactor)).rgb;

    vec3 color10 = texture2D(a_Texture1, vec2(v_Coordinate.x - imageWidthFactor, v_Coordinate.y)).rgb;
    vec3 color11 = texture2D(a_Texture1, vec2(v_Coordinate.x      , v_Coordinate.y)).rgb;
    //    vec3 color11 = texture2D(a_Texture1, v_Coordinate).rgb;
    vec3 color12 = texture2D(a_Texture1, vec2(v_Coordinate.x + imageWidthFactor, v_Coordinate.y)).rgb;

    vec3 color20 = texture2D(a_Texture1, vec2(v_Coordinate.x - imageWidthFactor, v_Coordinate.y + imageHeightFactor)).rgb;
    vec3 color21 = texture2D(a_Texture1, vec2(v_Coordinate.x      , v_Coordinate.y + imageHeightFactor)).rgb;
    vec3 color22 = texture2D(a_Texture1, vec2(v_Coordinate.x + imageWidthFactor, v_Coordinate.y + imageHeightFactor)).rgb;

    vec3 color = color00 * convolutionKernel[0][0] + color01 * convolutionKernel[0][1] + color02 * convolutionKernel[0][2] +
    color10 * convolutionKernel[1][0] + color11 * convolutionKernel[1][1] + color12 * convolutionKernel[1][2] +
    color20 * convolutionKernel[2][0] + color21 * convolutionKernel[2][1] + color22 * convolutionKernel[2][2];

    gl_FragColor = vec4(color, texture2D(a_Texture1, v_Coordinate).a);
    /*vec4 color = texture2D(a_Texture, v_Coordinate);
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
    } else if (type == 2) {
        vec4 color1 = texture2D(a_Texture1, v_Coordinate);
        float ra;
        if(color1.r <= 0.5){
            ra = color.r + (2.0 * color1.r - 1.0) * (color.r - color.r * color.r) + color.r;
        }else{
            ra = color.r + (2.0 * color1.r - 1.0) * (sqrt(color.r) - color.r) + color.r;
        }

        float ga;
        if(color1.g <= 0.5){
            ga = color.g + (2.0 * color1.g - 1.0) * (color.g - color.g * color.g) + color.g;
        }else{
            ga = color.g + (2.0 * color1.g - 1.0) * (sqrt(color.g) - color.g) + color.g;
        }

        float ba;
        if(color1.b <= 0.5){
            ba = color.b + (2.0 * color1.b - 1.0) * (color.b - color.b * color.b) + color.b;
        }else{
            ba = color.b + (2.0 * color1.b - 1.0) * (sqrt(color.b) - color.b) + color.b;
        }
        rgba = vec4(ra, ga, ba, 1.0);
    }

    gl_FragColor = rgba;*/
}