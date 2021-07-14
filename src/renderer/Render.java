package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

/**
 * render class
 */
public class Render {
    /*  field  */
    public ImageWriter _imageWriter = null;
    public Camera _camera = null;
    public RayTracerBase _rayTracerBase = null;

     /**
     * seters format builder
     * */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracerBase = rayTracer;
        return this;
    }

    /**
     * * This function create the buffer pf pixels and check that have a camera,imagewriter
     * @param alfa
     */
    public void renderImage(double alfa) {
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (_rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = _rayTracerBase.traceRay(ray,alfa);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }



        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }

    /**
     * Printing the grid with a fixed interval between lines
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, Color color) {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * writes to an image
     */
    public void writeToImage() {
        if (_imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        _imageWriter.writeToImage();
    }

    /**
     * Mini Project 2 -Adaptive supersampling
     * @param count degpth of recursive
     * @param p1_j
     * @param p1_i
     * @param p2_j
     * @param p2_i
     * @param p3_j
     * @param p3_i
     * @param p4_j
     * @param p4_i
     */
    /*
    public void funcMiniProject2(int count, int p1_j, int p1_i, int p2_j, int p2_i, int p3_j, int p3_i, int p4_j,int p4_i)
    {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        while(count < 3)
        {

            //color p1
            Ray ray1 = _camera.constructRayThroughPixel(nX, nY, p1_i, p1_j);
            Color pixelColor1 = _rayTracerBase.traceRay(ray1,0);

            //color p2
            Ray ray2 = _camera.constructRayThroughPixel(nX, nY, p2_i, p2_j);
            Color pixelColor2 = _rayTracerBase.traceRay(ray2,0);

            //color p3
            Ray ray3 = _camera.constructRayThroughPixel(nX, nY, p3_i, p3_j);
            Color pixelColor3 = _rayTracerBase.traceRay(ray3,0);

            //color p4
            Ray ray4 = _camera.constructRayThroughPixel(nX, nY, p4_i, p4_j);
            Color pixelColor4 = _rayTracerBase.traceRay(ray4 , 0);

            if(pixelColor1 == pixelColor2 && pixelColor2 == pixelColor3 &&
                    pixelColor3 == pixelColor4) // if 4 points same color, all pixels inside same color
            {
                for(int inew = p1_i; inew < p2_i; inew++)
                    for(int jnew = p1_j; jnew < p3_j; jnew++)
                    {
                        _imageWriter.writePixel(inew, jnew, pixelColor1);
                    }

                return;
            }

            else //if colors are not the same:
            {   //function recursive, stop after 6
                count ++;
                //send new pixels - 1
                funcMiniProject2(count, p1_j, p1_i, p2_j / 2, p2_i, p3_j, p3_i / 2, p4_j / 2, p4_i / 2);

                //send new pixels - 2
                funcMiniProject2(count, p2_j/2, p2_i, p2_j, p2_i, p4_j / 2, p4_i / 2, p4_j, p4_i / 2);

                //send new pixels - 3
                funcMiniProject2(count, p1_j, p3_i / 2, p4_j / 2, p4_i / 2, p3_j, p3_i, p4_j / 2, p4_i);

                //send new pixels - 4
                funcMiniProject2(count, p4_j / 2, p4_i / 2, p4_j, p4_i / 2, p4_j / 2, p4_i, p4_j, p4_i);

            }
        } //end while count < 3

        if(count > 3)
        {

            Ray ray1 = _camera.constructRayThroughPixel(nX, nY, p1_i, p1_j);
            Color pixelColor1 = _rayTracerBase.traceRay(ray1,0);

            //color p2
            Ray ray2 = _camera.constructRayThroughPixel(nX, nY, p2_i, p2_j);
            Color pixelColor2 = _rayTracerBase.traceRay(ray2,0);

            //color p3
            Ray ray3 = _camera.constructRayThroughPixel(nX, nY, p3_i, p3_j);
            Color pixelColor3 = _rayTracerBase.traceRay(ray3,0);

            //color p4
            Ray ray4 = _camera.constructRayThroughPixel(nX, nY, p4_i, p4_j);
            Color pixelColor4 = _rayTracerBase.traceRay(ray4,0);

            double RED = pixelColor1.getColor().getRed();
            RED += pixelColor2.getColor().getRed();
            RED += pixelColor3.getColor().getRed();
            RED += pixelColor4.getColor().getRed();
            RED = RED / 4;

            double GREEN = pixelColor1.getColor().getGreen();
            GREEN += pixelColor2.getColor().getGreen();
            GREEN += pixelColor3.getColor().getGreen();
            GREEN += pixelColor4.getColor().getGreen();
            GREEN = GREEN / 4;

            int BLUE = pixelColor1.getColor().getBlue();
            BLUE += pixelColor2.getColor().getBlue();
            BLUE += pixelColor3.getColor().getBlue();
            BLUE += pixelColor4.getColor().getBlue();
            BLUE = BLUE / 4;

            Color coloraprox = new Color(RED, GREEN, BLUE);

            for(int inew = 0; inew < p2_j; inew++)
                for(int jnew = 0; jnew < p3_i; jnew++)
                {
                    _imageWriter.writePixel(inew, jnew, coloraprox);
                }
        }

     */
    }
