package edu.njnu.opengms.r2.utils;

import com.mxgraph.canvas.mxGraphicsCanvas2D;
import com.mxgraph.canvas.mxICanvas2D;
import com.mxgraph.reader.mxSaxOutputHandler;
import com.mxgraph.util.mxUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * @ClassName MxGraphUtils
 * @Description ass we see
 * @Author sun_liber
 * @Date 2020/5/15
 * @Version 1.0.0
 */
public class MxGraphUtils {

    public static void exportImg(String xml, int w, int h, String filePath) throws IOException, SAXException, ParserConfigurationException {
        BufferedImage image = mxUtils.createBufferedImage(w, h, Color.WHITE);
        Graphics2D g2 = image.createGraphics();
        mxUtils.setAntiAlias(g2, true, true);
        mxGraphicsCanvas2D gc2 = new mxGraphicsCanvas2D(g2);
        parseXmlSax(xml, gc2);
        ImageIO.write(image, "png", new File(filePath));
    }

    protected static void parseXmlSax(String xml, mxICanvas2D canvas)
            throws ParserConfigurationException, IOException, SAXException {

        mxSaxOutputHandler handler = new mxSaxOutputHandler(canvas);


        XMLReader reader = SAXParserFactory.newInstance().newSAXParser()
                .getXMLReader();
        reader.setContentHandler(handler);


        reader.parse(new InputSource(new StringReader(xml)));
    }
}
