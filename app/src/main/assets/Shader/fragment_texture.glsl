precision mediump float;
uniform int a_Type;
uniform sampler2D a_Texture;
varying vec2 v_Coordinate;
/*在GLSL中，颜色是用包含四个浮点的向量vec4表示，四个浮点分别表示RGBA四个通道，取值范围为0.0-1.0。我们先读取图片每个像素的色彩值，再对读取到的色彩值进行调整，这样就可以完成对图片的色彩处理了。
  我们应该都知道，黑白图片上，每个像素点的RGB三个通道值应该是相等的。知道了这个，将彩色图片处理成黑白图片就非常简单了。我们直接出处像素点的RGB三个通道，相加然后除以3作为处理后每个通道的值就可以得到一个黑白图片了。这是均值的方式是常见黑白图片处理的一种方法。类似的还有权值方法（给予RGB三个通道不同的比例）、只取绿色通道等方式。
  与之类似的，冷色调的处理就是单一增加蓝色通道的值，暖色调的处理可以增加红绿通道的值。还有其他复古、浮雕等处理也都差不多。*/
void main() {
    vec4 color = texture2D(a_Texture, v_Coordinate);
    if(a_Type==0)
        gl_FragColor = color;
    else if(a_Type==1){
            float c = color.r * 0.2989 + color.g * 0.5870 + color.b * 0.1140;
            gl_FragColor = vec4(c, c, c, color.a);
    }
    /*switch(a_Type) {
        case 0:
            gl_FragColor = color;
            break;
        case 1:
            float c = color.r * 0.2989 + color.g * 0.5870 + color.b * 0.1140;
            gl_FragColor = vec4(c, c, c, color.a);
            break;
    }*/
}