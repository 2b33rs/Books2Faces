/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hsog;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Icon;


public class Converter {

    public static Icon loadIconFromFile(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        int maxX, maxY, max;
        double ratio = 0;
        maxX = image.getWidth(null);
        maxY = image.getHeight(null);
        max = Math.max(maxX, maxY);
        ratio = (250.0 / (float) max);
        image = image.getScaledInstance((int) (maxX * ratio),
                (int) (maxY * ratio), Image.SCALE_DEFAULT);
        ImageIcon ic = new ImageIcon(image);
        return (Icon) ic;
    }

    public static ImageIcon Image2ImageIcon(Image i) {
         if (i == null)  return null;
        
        ImageIcon ic = new ImageIcon(i);
        return ic;
    }

    public static Image ImageIcon2Image(ImageIcon ic) {
         if (ic == null)  return null;
        
        Image i = ic.getImage();
        return i;
    }

    public static Blob ImageIcon2Blob(ImageIcon myImageIcon, Connection con) {
        if (myImageIcon == null)  return null;
        Blob myBlob = null;
         
        try {
           
            ByteArrayOutputStream myByteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage buImg = new BufferedImage(myImageIcon.getIconWidth(), myImageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            buImg.getGraphics().drawImage(myImageIcon.getImage(), 0, 0, myImageIcon.getImageObserver());
            ImageIO.write(buImg, "png", myByteArrayOutputStream);
            byte[] myByteArray = myByteArrayOutputStream.toByteArray();
            myBlob =  con.createBlob();
            myBlob.setBytes(1, myByteArray);
        } catch (Exception ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myBlob;
    }
    
    public static Blob Icon2Blob(Icon myIcon, Connection con) {
        return ImageIcon2Blob((ImageIcon) myIcon, con);
    }

    public static ImageIcon Blob2ImageIcon(Blob myBlob) {
       
        if (myBlob == null)  return null;

        ImageIcon myImageIcon = null;
        try {
            byte[] byteArray = myBlob.getBytes((long) 1, (int) myBlob.length());
            myImageIcon = new ImageIcon(byteArray);

        } catch (SQLException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myImageIcon;
    }
    
     public static  Icon Blob2Icon(Blob myBlob) {       
        return (Icon) Blob2ImageIcon(myBlob);
    }
}
