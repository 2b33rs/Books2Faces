/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hsog;

/**
 *
 * @author student
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.Icon;

public class DBQueries {

    private static void executeQuery(String query) {

        Connection con = null;
        Statement st = null;

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(query);
            st.executeUpdate(query);

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static int executeQuery(String query, int column) {
        int count = 0;
        ResultSet rs = null;
        Connection con = null;
        Statement st = null;

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(query);
            rs = st.executeQuery(query);

            rs.next();
            count = rs.getInt(column);
            //System.out.println("Anzahl: " + count);

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }

    static List<DTO> executeQueryConvertToDTO(String query) {

        ResultSet rs = null;
        Connection con = null;
        Statement st = null;
        DTO dto = null;
        List<DTO> dtolist = new ArrayList<>();

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(query);
            rs = st.executeQuery(query);

//1 ISBN	VARCHAR2(100)
//2 EMAIL	VARCHAR2(100)
//3 TITLE	VARCHAR2(400)
//4 KATEGORIE	VARCHAR2(100)
//5 SEITEN	NUMBER(38)
//6 COVER	BLOB
//c DTO( String isbn, String email, String title, Icon cover, String kategorie, int seitenanzahl)
            while (rs.next()) {
                dto = new DTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        Converter.Blob2Icon(rs.getBlob(6)),
                        rs.getString(4),
                        rs.getInt(5)
                );
                dtolist.add(dto);
            };

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dtolist;
    }

    static List<String> executeQueryList(String query, String col) {

        ResultSet rs = null;
        Connection con = null;
        Statement st = null;
        DTO dto = null;
        List<String> dtolist = new ArrayList<>();

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(query);
            rs = st.executeQuery(query);

//1 ISBN	VARCHAR2(100)
//2 EMAIL	VARCHAR2(100)
//3 TITLE	VARCHAR2(400)
//4 KATEGORIE	VARCHAR2(100)
//5 SEITEN	NUMBER(38)
//6 COVER	BLOB
//c DTO( String isbn, String email, String title, Icon cover, String kategorie, int seitenanzahl)
            while (rs.next()) {
                dtolist.add(rs.getString(col));
            };

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dtolist;
    }

    static int count() {
        return executeQuery("select count(*)\n" + "from books", 1);
    }

    static boolean login(String email, String passwd) {

        String q = "select count(*) from users where email='" + email + "' and passwd='" + passwd + "'";

        int count = executeQuery(q, 1);
        return (count == 1);
    }

    static boolean registerUser(String email, String passwd) {
        if (emailTaken(email)) {
            System.out.println("User exsistiert schon.");
            return false;
        } else {
            String q = "INSERT INTO users VALUES ('" + email + "', '" + passwd + "')";
            executeQuery(q);
            return true;
        }

    }

    static DTO first() {

        DTO first = executeQueryConvertToDTO("SELECT * FROM books ORDER BY isbn ASC").get(0);

        return first;
    }

    private static boolean emailTaken(String email) {

        String q = "select count(*) from users where email='" + email + "'";

        int count = executeQuery(q, 1);
        return count >= 1;

    }

    static void delete(String sql) {
        Connection con = null;
        Statement st = null;

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(sql);
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static double executeQueryBewertung(String query) {

        ResultSet rs = null;
        Connection con = null;
        Statement st = null;
        double av = 0;

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(query);
            rs = st.executeQuery(query);

            int i = 0;
            int sum = 0;
            while (rs.next()) {
                i++;
                sum += rs.getInt("gefallen");
            };
            if (i != 0) {
                av = sum / i;
            }
            System.out.println(av);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(
                    DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return av;

    }

    static String executeQueryComments(String query) {

        ResultSet rs = null;
        Connection con = null;
        Statement st = null;
        String ans = "";

        try {
            con = Globals.getPoolConnection();
            st = con.createStatement();

            System.out.println(query);
            rs = st.executeQuery(query);

            StringBuilder resultString = new StringBuilder();
            while (rs.next()) {
                resultString.append(rs.getString("kommentar"));
                resultString.append("\n");
            };

            ans = resultString.toString();

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ans;

    }

    static boolean hasISBN(String isbn) {
        return executeQuery("select count(*) from books where isbn='" + isbn + "'", 1) >= 1;
    }

    static void update(String bn, String title, String kategorie, int seiten, Icon img, String mail) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {
            con = Globals.getPoolConnection();
            //st = con.createStatement();
            Blob blob = Converter.Icon2Blob(img, con);
            /* System.out.println(blobBytes.toString());

            StringBuilder hex = new StringBuilder();
            Formatter formatter = new Formatter(hex);
            for (byte b : blobBytes) {
                formatter.format("%02x", b);
            }
            formatter.close();
            System.out.println(hex);
             */

            pst = con.prepareStatement(
                    "UPDATE books set TITLE = ?, KATEGORIE = ?, SEITEN = ?, COVER = ?, EMAIL = ?  where ISBN = ?");

            pst.setString(1, title);
            pst.setString(2, kategorie);
            pst.setInt(3, seiten);
            pst.setBlob(4, blob);
            pst.setString(5, mail);
            pst.setString(6, bn);

            System.out.println(pst.toString());

            pst.executeUpdate();

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static void update(String email, String isbn, int gefallen, String kommentar) {

        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {
            con = Globals.getPoolConnection();

            pst = con.prepareStatement(
                    "UPDATE bewertung set gefallen = ?, kommentar = ?  where ISBN = ? and email = ?");

            pst.setInt(1, gefallen);
            pst.setString(2, kommentar);
            pst.setString(3, isbn);
            pst.setString(4, email);

            System.out.println(pst.toString());

            pst.executeUpdate();

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static void insert(String bn, String usr, String title, String kategorie, int seiten, Icon img, String email) {
        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            String q = "INSERT INTO books(isbn,email,title,kategorie,seiten,cover) VALUES(?, ?, ?, ?, ?, ?)";

            con = Globals.getPoolConnection();
            st = con.createStatement();

            Blob blob = Converter.Icon2Blob(img, con);
            pst = con.prepareStatement(q);

            pst.setString(1, bn);
            pst.setString(2, email);
            pst.setString(3, title);
            pst.setString(4, kategorie);
            pst.setInt(5, seiten);
            pst.setBlob(6, blob);

            System.out.println(q);
            //st.executeUpdate(q);

            pst.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static void insert(String email, String isbn, int gefallen, String kommentar) {
        Connection con = null;
        Statement st = null;
        PreparedStatement pst = null;

        try {

            String q = "INSERT INTO bewertung(email,isbn,gefallen,kommentar) VALUES(?, ?, ?, ?)";

            con = Globals.getPoolConnection();
            st = con.createStatement();

            //Blob blob = Converter.Icon2Blob(img, con);
            pst = con.prepareStatement(q);

            pst.setString(1, email);
            pst.setString(2, isbn);
            pst.setInt(3, gefallen);
            pst.setString(4, kommentar);

            System.out.println(q);
            //st.executeUpdate(q);

            pst.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DBQueries.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static boolean hasMyComment(String isbn, String email) {
        String q = "select count(*) from bewertung where email='" + email + "' and isbn='" + isbn + "'";

        return executeQuery(q, 1) >= 1;

    }

}
