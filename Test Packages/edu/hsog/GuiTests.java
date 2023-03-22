/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hsog;

import java.awt.Frame;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import static org.assertj.swing.timing.Pause.pause;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Philipp Schweizer
 */
public class GuiTests extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private static Helper helper;
    private static Helper.User[] validUser;
    private static Helper.User invalidUser;
    private final static String DEFAULT_STATUS = "nicht verbunden";
    private final static String CONNECTED_STATUS = "verbunden";
    private final static String CONNECTED_LOGIN_STATUS = "verbunden, eingeloggt";
    private final static String CONNECTED_REGISTER_STATUS = "verbunden, registriert";
    
    @BeforeClass
    public static void createTestState(){
        System.out.println("edu.hsog.GuiTests.testSetup()");
        try {
            helper = new Helper();
            helper.initConnection();
            validUser = helper.getValidUsers();
            invalidUser = helper.getUser(UUID.randomUUID().toString(), "1234");
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("DB connection failed");
        }
    }
    
    @AfterClass
    public static void destroyestState(){
        try {
            helper.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(GuiTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("edu.hsog.GuiTests.testSetup()");
    }

    @Override
    protected void onSetUp() {
        System.out.println("edu.hsog.GuiTests.onSetUp()");
        window = new FrameFixture(robot(), (Frame) GuiActionRunner.execute(() -> new BookGUI()));
        window.show();
    }
    
    @Override
    protected void onTearDown () {
        System.out.println("edu.hsog.GuiTests.onTearDown()");
        window.cleanUp();
    }
    
    public void searchRightBook(String isbn) {
        System.out.println(helper.bookExistsInDB(isbn));
        if(!helper.bookExistsInDB(isbn))
            return;
        GuiTestNames.getLAST_BUTTON(window).click();
        
        pause(100);
        
        GuiTestNames.getFIRST_BUTTON(window).click();
        
        pause(100);
        String old = "";
        for(int i = 0; i < helper.getBookCount(); i++) {
            String newS = GuiTestNames.getISBN_TEXTFIELD(window).text();
            if(newS.equals(isbn) || newS.equals("") || newS.equals(old)){
                break;
            }
            old = newS;
            pause(100);
            GuiTestNames.getNEXT_BUTTON(window).click();
        }
    }
    
    public void login(Helper.User user) {
        GuiTestNames.getUSER_TEXTFIELD(window).setText(user.email);
        GuiTestNames.getPASSWORD_TEXTFIELD(window).setText(user.password);
        pause(100);
        GuiTestNames.getLOGIN_BUTTON(window).click();
    }
    
    public void insertBook(Helper.Book b) {
        
        
        GuiTestNames.getISBN_TEXTFIELD(window).setText(b.isbn);
        GuiTestNames.getTITLE_TEXTFIELD(window).setText(b.title);
        GuiTestNames.getPAGENUMBER_TEXTFIELD(window).setText(b.pages + "");
        GuiTestNames.getCATEGORY_TEXTFIELD(window).setText(b.category);
        pause(100);
        GuiTestNames.getLOADIMAGE_BUTTON(window).click();
        pause(100);
        GuiTestNames.getIMAGE_FILE_CHOOSER(window).selectFile(new File("/home/student/Bilder/shutterstock_506790496-300x200.jpg")).approve();
        
        GuiTestNames.getSAVE_BUTTON(window).click();
    }
    
    public void checkBook(Helper.Book b, boolean onlyPages) {
        if(onlyPages) {
            GuiTestNames.getPAGENUMBER_TEXTFIELD(window).requireText(b.pages + "");
            return;
        }
        GuiTestNames.getISBN_TEXTFIELD(window).requireText(b.isbn);
        GuiTestNames.getTITLE_TEXTFIELD(window).requireText(b.title);
        GuiTestNames.getPAGENUMBER_TEXTFIELD(window).requireText(b.pages + "");
        if(GuiTestNames.getCATEGORY_TEXTFIELD(window).text().equals(""))
            Assert.assertEquals(b.category, GuiTestNames.getCATEGORY_JCOMBOBOX(window).selectedItem());
        else
            GuiTestNames.getCATEGORY_TEXTFIELD(window).requireText(b.category);
        GuiTestNames.getOWNER_LABEL(window).requireText(b.email);
    }
    
    
    @Test
    public void STATUS_SHOULD_BE_DEFAULT_WHEN_NO_INPUT() {
        GuiTestNames.getSTATUS_LABEL(window).requireVisible().requireEnabled().requireText(DEFAULT_STATUS);
    }
    
    @Test
    public void STATUS_SHOULD_BE_CONNECTED_WHEN_CONNECTION_BUTTON_PRESSED() {
        GuiTestNames.getSTATUS_LABEL(window).requireVisible().requireEnabled().requireText(DEFAULT_STATUS);
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).requireVisible().requireEnabled().click();
        pause(100);
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_STATUS);
    }
    
    @Test
    public void COMBOBOX_SHOULD_HAVE_CATEGORIES_AFTER_CONNECTION() {
        GuiTestNames.getCONNECTION_BUTTON(window).requireVisible().requireEnabled().click().click().click();
        pause(1000);
        
        String[] comboBoxContent = GuiTestNames.getCATEGORY_JCOMBOBOX(window).contents(); 
        
        Assert.assertEquals(helper.getAllCategories().length, comboBoxContent.length);
  
        Arrays.stream(helper.getAllCategories()).forEach(e -> {
           Assert.assertEquals(e+" needs to be in categories", true, Arrays.stream(comboBoxContent).anyMatch(e::equals));
        });
    }
    
    @Test
    public void COUNT_SHOULD_RETURN_CORRECT_NUMBER_WHEN_DATA_IS_PRESENT() {
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        
        pause(100);
        GuiTestNames.getCOUNT_BUTTON(window).requireEnabled().requireVisible().click();
        pause(200);
        GuiTestNames.getCOUNT_LABEL(window).requireText(helper.getBookCount()+"");
     
    }
    
    @Test
    public void LOGIN_SHOULD_FAIL_WHEN_EMAIL_IS_NOT_FOUND() {
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(invalidUser);
        
        pause(100);
        
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_STATUS);
    }
    
    @Test
    public void LOGIN_SHOULD_FAIL_WHEN_PASSWORD_IS_WRONG() {
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(helper.getUser(validUser[0].email, validUser[0].password+"aaa"));
        
        pause(100);
        
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_STATUS);
    }
    
    @Test
    public void LOGIN_SHOULD_FAIL_WHEN_EMAIL_AND_PASSWORD_ARE_CORRECT() {
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        
        pause(100);
        
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_LOGIN_STATUS);
    }
    @Test
    public void REGISTER_SHOULD_NOT_WORK_WHEN_EMAIL_IS_TAKEN() {
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        GuiTestNames.getUSER_TEXTFIELD(window).setText(validUser[0].email);
        GuiTestNames.getPASSWORD_TEXTFIELD(window).setText(validUser[0].password);
        pause(100);
        GuiTestNames.getREGISTER_BUTTON(window).click();
        pause(100);
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_STATUS);
    }
    @Test
    public void CLEAR_BUTTON_SHOULD_EMPTY_GUI() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        pause(100);
        GuiTestNames.getFIRST_BUTTON(window).click();
        pause(100);
        GuiTestNames.getCLEAR_BUTTON(window).click();
        pause(100);
        GuiTestNames.getISBN_TEXTFIELD(window).requireText("");
        GuiTestNames.getTITLE_TEXTFIELD(window).requireText("");
        GuiTestNames.getCATEGORY_TEXTFIELD(window).requireText("");
        GuiTestNames.getPAGENUMBER_TEXTFIELD(window).requireText("");
        Assert.assertNull(GuiTestNames.getIMAGE_LABEL(window).target().getIcon());
        GuiTestNames.getOWNER_LABEL(window).requireText("");
    }
    @Test
    public void REGISTER_SHOULD_WORK_WHEN_EMAIL_IS_NOT_TAKEN() {
        String email = UUID.randomUUID().toString();
        String password = "1234";
        
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        GuiTestNames.getUSER_TEXTFIELD(window).setText(email);
        GuiTestNames.getPASSWORD_TEXTFIELD(window).setText(password);
        GuiTestNames.getREGISTER_BUTTON(window).click();
        
        pause(100);
        
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_REGISTER_STATUS);
        
        pause(100);
        
        login(helper.getUser(email, password));
        
        pause(100);
        
        GuiTestNames.getSTATUS_LABEL(window).requireText(CONNECTED_LOGIN_STATUS);
    }
    @Test
    public void INSERT_NEW_BOOK_SHOULD_WORK_WHEN_ISBN_IS_NOT_PRESENT() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        pause(100);
        login(validUser[0]);
        pause(100);
        GuiTestNames.getLOGIN_BUTTON(window).click();
        
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        
        GuiTestNames.getISBN_TEXTFIELD(window).setText(b.isbn);
        GuiTestNames.getTITLE_TEXTFIELD(window).setText(b.title);
        GuiTestNames.getPAGENUMBER_TEXTFIELD(window).setText(b.pages + "");
        GuiTestNames.getCATEGORY_TEXTFIELD(window).setText(b.category);
        pause(100);
        GuiTestNames.getLOADIMAGE_BUTTON(window).click();
        pause(100);
        GuiTestNames.getIMAGE_FILE_CHOOSER(window).selectFile(new File("/home/student/Bilder/shutterstock_506790496-300x200.jpg")).approve();
        
        GuiTestNames.getSAVE_BUTTON(window).click();
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        pause(100);
        
        checkBook(b, false);

    }
    @Test
    public void INSERT_NEW_BOOK_SHOULD_WORK_WHEN_ISBN_IS_NOT_PRESENT_COMBOBOX() {
        String[] allCategories = helper.getAllCategories();
        
        if(allCategories.length < 5)
            Assert.assertTrue("Pls load the default DB Data", false);
            
        String category = allCategories[new Random().nextInt(allCategories.length)];
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        pause(100);
        login(validUser[0]);
        pause(100);
        GuiTestNames.getLOGIN_BUTTON(window).click();
        
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        b.category = category;
        
        GuiTestNames.getISBN_TEXTFIELD(window).setText(b.isbn);
        GuiTestNames.getTITLE_TEXTFIELD(window).setText(b.title);
        GuiTestNames.getPAGENUMBER_TEXTFIELD(window).setText(b.pages + "");
        GuiTestNames.getCATEGORY_TEXTFIELD(window).setText("");
        GuiTestNames.getCATEGORY_JCOMBOBOX(window).selectItem(b.category);
        pause(100);
        GuiTestNames.getLOADIMAGE_BUTTON(window).click();
        pause(100);
        GuiTestNames.getIMAGE_FILE_CHOOSER(window).selectFile(new File("/home/student/Bilder/shutterstock_506790496-300x200.jpg")).approve();
        
        GuiTestNames.getSAVE_BUTTON(window).click();
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        pause(100);
        
        checkBook(b, false);

    }
    @Test
    public void UPDATE_BOOK_SHOULD_WORK_WHEN_USER_IS_THE_SAME() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        pause(100);
        login(validUser[0]);
        pause(100);
        GuiTestNames.getLOGIN_BUTTON(window).click();
        
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        
        insertBook(b);
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        String newTitle = UUID.randomUUID().toString();
        
        GuiTestNames.getTITLE_TEXTFIELD(window).setText(newTitle);
        
        GuiTestNames.getSAVE_BUTTON(window).click();
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        pause(100);
        
        GuiTestNames.getTITLE_TEXTFIELD(window).requireText(newTitle);
        
    }
    @Test
    public void UPDATE_BOOK_SHOULD_NOT_WORK_WHEN_USER_IS_THE_NOT_SAME() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        
        insertBook(b);
        
        pause(100);
        
        login(validUser[1]);
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        String newTitle = "new title";//UUID.randomUUID().toString();
        
        GuiTestNames.getTITLE_TEXTFIELD(window).setText(newTitle);
        
        GuiTestNames.getSAVE_BUTTON(window).click();
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        pause(1000);
        
        GuiTestNames.getTITLE_TEXTFIELD(window).requireText(b.title);
        
    }
    @Test
    public void DELETE_BOOK_SHOULD_WORK_WHEN_SAME_USER() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        b.isbn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + b.email;
        
        insertBook(b);
        
        pause(100);
        
        searchRightBook(b.isbn);
        
        GuiTestNames.getDELETE_BUTTON(window).click();
        
        pause(100);
        
        GuiTestNames.getFIRST_BUTTON(window).click();
        
        pause(100);
        
        Assert.assertNotEquals(GuiTestNames.getISBN_TEXTFIELD(window).text(), b.isbn);
    }
    @Test
    public void DELETE_BOOK_SHOULD_NOT_WORK_WHEN_OTHER_USER() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        b.isbn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + b.email;
        
        insertBook(b);
        
        pause(100);
        
        pause(100);
        login(validUser[1]);
        pause(100);
        
        searchRightBook(b.isbn);
        
        GuiTestNames.getDELETE_BUTTON(window).click();
        
        pause(100);
        
        GuiTestNames.getFIRST_BUTTON(window).click();
        
        pause(100);
        
        GuiTestNames.getISBN_TEXTFIELD(window).requireText(b.isbn);
        
        //cleanup
        
        pause(100);
        login(validUser[0]);
        pause(100);
        GuiTestNames.getFIRST_BUTTON(window).click();
        pause(100);
        GuiTestNames.getDELETE_BUTTON(window).click();
    }
    @Test
    public void RATING_AND_COMMENT_TEST() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        pause(100);
        
        Helper.Book b = helper.getBookData();
        b.email = validUser[0].email;
        
        insertBook(b);
        pause(100);
        searchRightBook(b.isbn);
        pause(100);
        
        GuiTestNames.getCOMMENT_TEXTFIELD(window).setText("Bot Kommentar");
        GuiTestNames.getLIKE_SLIDER(window).slideTo(8);
        GuiTestNames.getSAVE_COMMENT_BUTTON(window).click();
        
        pause(100);
        searchRightBook(b.isbn);
        pause(100);
        
        GuiTestNames.getCOMMENT_TEXTFIELD(window).requireText("Bot Kommentar");
        GuiTestNames.getRATING_LABEL(window).requireText("8.0");
        GuiTestNames.getCOMMENTS_TEXTAREA(window).requireText("Bot Kommentar");
        
        pause(100);
        login(validUser[1]);
        pause(100);
        
        pause(100);
        searchRightBook(b.isbn);
        pause(100);
        
        GuiTestNames.getCOMMENT_TEXTFIELD(window).setText("Bot Kommentar");
        GuiTestNames.getLIKE_SLIDER(window).slideTo(4);
        GuiTestNames.getSAVE_COMMENT_BUTTON(window).click();
        
        pause(100);
        searchRightBook(b.isbn);
        pause(100);
        
        GuiTestNames.getCOMMENT_TEXTFIELD(window).requireText("Bot Kommentar");
        GuiTestNames.getRATING_LABEL(window).requireText("6.0");
        GuiTestNames.getCOMMENTS_TEXTAREA(window).requireText("Bot Kommentar"+System.lineSeparator()+"Bot Kommentar");
        
        pause(100);
        login(validUser[0]);
        pause(100);
        
        searchRightBook(b.isbn);
        pause(100);
        
        GuiTestNames.getCOMMENT_TEXTFIELD(window).setText("Bot2_Kommentar");
        GuiTestNames.getLIKE_SLIDER(window).slideTo(10);
        GuiTestNames.getSAVE_COMMENT_BUTTON(window).click();
        
        pause(100);
        searchRightBook(b.isbn);
        pause(100);
        
        GuiTestNames.getCOMMENT_TEXTFIELD(window).requireText("Bot2_Kommentar");
        GuiTestNames.getRATING_LABEL(window).requireText("7.0");
        Assert.assertTrue(GuiTestNames.getCOMMENTS_TEXTAREA(window).text().contains("Bot2_Kommentar"));
        Assert.assertTrue(GuiTestNames.getCOMMENTS_TEXTAREA(window).text().contains("Bot Kommentar"));
        Assert.assertTrue(GuiTestNames.getCOMMENTS_TEXTAREA(window).text().contains(System.lineSeparator()));
    }
    @Test
    public void FIRST_LAST_NEXT_PREVIOUS() {
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        login(validUser[0]);
        pause(100);
        
        List<Helper.Book> allBooks = helper.getallBooks();
        
        if(allBooks.size() < 50)
            Assert.assertTrue("Pls load the default DB Data", false);
        
        GuiTestNames.getFIRST_BUTTON(window).click();
        pause(100);
        checkBook(allBooks.get(0), false);
        pause(100);
        GuiTestNames.getPREVIOS_BUTTON(window).click();
        pause(100);
        checkBook(allBooks.get(0), false);
        pause(100);
        
        
        for(int i = 1; i< 5; i++){ 
            GuiTestNames.getNEXT_BUTTON(window).click();
            pause(25);
            checkBook(allBooks.get(i), false);
        }
        
        GuiTestNames.getFIRST_BUTTON(window).click();
        pause(100);
        checkBook(allBooks.get(0), false);
        
        Collections.reverse(allBooks);
        
        GuiTestNames.getLAST_BUTTON(window).click();
        pause(100);
        checkBook(allBooks.get(0), false);
        
        for(int i = 1; i< 5; i++){ 
            GuiTestNames.getPREVIOS_BUTTON(window).click();
            pause(25);
            checkBook(allBooks.get(i), false);
        }
        
        GuiTestNames.getLAST_BUTTON(window).click();
        pause(100);
        checkBook(allBooks.get(0), false);
        pause(100);
        GuiTestNames.getNEXT_BUTTON(window).click();
        pause(100);
        checkBook(allBooks.get(0), false);
        pause(100);
    }
    @Test
    public void FIND_BOOK() {
        String[] allCategories = helper.getAllCategories();
        
        if(allCategories.length < 5)
            Assert.assertTrue("Pls load the default DB Data", false);
            
        String categories = allCategories[new Random().nextInt(allCategories.length)];
        
        List<Helper.Book> allBooks = helper.getallBooksFromCategorieSortedByPagesDesc(categories);
        
        pause(100);
        GuiTestNames.getCONNECTION_BUTTON(window).click().click().click();
        pause(100);
        login(validUser[0]);
        pause(100);
        
        searchRightBook(allBooks.get(0).isbn);
        pause(100);
        
        for(int i = 1; i <allBooks.size();i++){
            GuiTestNames.getFIND_NEXT_BUTTON(window).click();
            pause(100);
            checkBook(allBooks.get(i), true);
        }
    }
}