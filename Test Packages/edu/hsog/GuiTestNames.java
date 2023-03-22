/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hsog;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JSliderFixture;
import org.assertj.swing.fixture.JTextComponentFixture;

/**
 *
 * @author Philipp Schweizer
 */
public class GuiTestNames {
    
    private static final String CONNECTION_BUTTON = "connectionJBtn";
    private static final String EXIT_BUTTON = "exitJBtn";
    private static final String COUNT_BUTTON = "countJBtn";
    private static final String SAVE_COMMENT_BUTTON = "saveCommentJBtn";
    private static final String DELETE_BUTTON = "deleteJBtn";
    private static final String FIRST_BUTTON = "firstJBtn";
    private static final String LAST_BUTTON = "lastJBtn";
    private static final String LOADIMAGE_BUTTON = "loadImageJBtn";
    private static final String LOGIN_BUTTON = "loginJBtn";
    private static final String NEXT_BUTTON = "nextJBtn";
    private static final String PREVIOS_BUTTON = "previosJBtn";
    private static final String REGISTER_BUTTON = "registerJBtn";
    private static final String SAVE_BUTTON = "saveJBtn";
    private static final String FIND_NEXT_BUTTON = "findBookJBtn";
    private static final String CLEAR_BUTTON = "clearJBtn";
    
    private static final String COMMENT_TEXTFIELD = "commentJTxt";
    private static final String ISBN_TEXTFIELD = "isbnJTxt";
    private static final String PASSWORD_TEXTFIELD = "passwdJTxt";
    private static final String TITLE_TEXTFIELD = "titleJTxt";
    private static final String USER_TEXTFIELD = "userJTxt";
    private static final String PAGENUMBER_TEXTFIELD = "pageNumberJTxt"; 
    private static final String CATEGORY_TEXTFIELD = "categoryJTxt"; 
            
    private static final String COUNT_LABEL = "countJLbl";
    private static final String IMAGE_LABEL = "imageJLbl";
    private static final String STATUS_LABEL = "statusJLbl";
    private static final String RATING_LABEL = "ratingJLbl";
    private static final String OWNER_LABEL = "ownerJLbl";
    
    private static final String LIKE_SLIDER = "likeJSli";
    
    private static final String COMMENTS_TEXTAREA = "commentsJTxtA";
    
    private static final String CATEGORY_JCOMBOBOX = "categoryJCmb";
    
    private static final String IMAGE_FILE_CHOOSER = "imageJFch";
    
    
    public static JButtonFixture getFIND_NEXT_BUTTON(FrameFixture frame) {
        return frame.button(FIND_NEXT_BUTTON);
    }

    public static JButtonFixture getCONNECTION_BUTTON(FrameFixture frame) {
        return frame.button(CONNECTION_BUTTON);
    }

    public static JButtonFixture getEXIT_BUTTON(FrameFixture frame) {
        return frame.button(EXIT_BUTTON);
    }

    public static JButtonFixture getCOUNT_BUTTON(FrameFixture frame) {
        return frame.button(COUNT_BUTTON);
    }

    public static JButtonFixture getSAVE_COMMENT_BUTTON(FrameFixture frame) {
        return frame.button(SAVE_COMMENT_BUTTON);
    }

    public static JButtonFixture getDELETE_BUTTON(FrameFixture frame) {
        return frame.button(DELETE_BUTTON);
    }

    public static JButtonFixture getFIRST_BUTTON(FrameFixture frame) {
        return frame.button(FIRST_BUTTON);
    }

    public static JButtonFixture getLAST_BUTTON(FrameFixture frame) {
        return frame.button(LAST_BUTTON);
    }

    public static JButtonFixture getLOADIMAGE_BUTTON(FrameFixture frame) {
        return frame.button(LOADIMAGE_BUTTON);
    }

    public static JButtonFixture getLOGIN_BUTTON(FrameFixture frame) {
        return frame.button(LOGIN_BUTTON);
    }

    public static JButtonFixture getNEXT_BUTTON(FrameFixture frame) {
        return frame.button(NEXT_BUTTON);
    }

    public static JButtonFixture getPREVIOS_BUTTON(FrameFixture frame) {
        return frame.button(PREVIOS_BUTTON);
    }

    public static JButtonFixture getREGISTER_BUTTON(FrameFixture frame) {
        return frame.button(REGISTER_BUTTON);
    }

    public static JButtonFixture getSAVE_BUTTON(FrameFixture frame) {
        return frame.button(SAVE_BUTTON);
    }
    
    public static JButtonFixture getCLEAR_BUTTON(FrameFixture frame) {
        return frame.button(CLEAR_BUTTON);
    }

    public static JTextComponentFixture getCOMMENT_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(COMMENT_TEXTFIELD);
    }

    public static JTextComponentFixture getISBN_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(ISBN_TEXTFIELD);
    }

    public static JTextComponentFixture getPASSWORD_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(PASSWORD_TEXTFIELD);
    }

    public static JTextComponentFixture getTITLE_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(TITLE_TEXTFIELD);
    }

    public static JTextComponentFixture getUSER_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(USER_TEXTFIELD);
    }

    
    public static JTextComponentFixture getPAGENUMBER_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(PAGENUMBER_TEXTFIELD);
    }
    
    public static JTextComponentFixture getCATEGORY_TEXTFIELD(FrameFixture frame) {
        return frame.textBox(CATEGORY_TEXTFIELD);
    }

    public static JLabelFixture getCOUNT_LABEL(FrameFixture frame) {
        return frame.label(COUNT_LABEL);
    }

    public static JLabelFixture getIMAGE_LABEL(FrameFixture frame) {
        return frame.label(IMAGE_LABEL);
    }
    
    public static JLabelFixture getOWNER_LABEL(FrameFixture frame) {
        return frame.label(OWNER_LABEL);
    }

    public static JLabelFixture getSTATUS_LABEL(FrameFixture frame) {
        return frame.label(STATUS_LABEL);
    }
    
    public static JLabelFixture getRATING_LABEL(FrameFixture frame) {
        return frame.label(RATING_LABEL);
    }

    public static JSliderFixture getLIKE_SLIDER(FrameFixture frame) {
        return frame.slider(LIKE_SLIDER);
    }

    public static JTextComponentFixture getCOMMENTS_TEXTAREA(FrameFixture frame) {
        return frame.textBox(COMMENTS_TEXTAREA);
    }
    
    public static JComboBoxFixture getCATEGORY_JCOMBOBOX(FrameFixture frame) {
        return frame.comboBox(CATEGORY_JCOMBOBOX);
    }
    
    public static JFileChooserFixture getIMAGE_FILE_CHOOSER(FrameFixture frame) {
        return frame.fileChooser(IMAGE_FILE_CHOOSER);
    }
}
