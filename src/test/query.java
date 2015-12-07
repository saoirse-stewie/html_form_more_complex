package test;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by saoirse on 06/12/2015.
 */
public class query extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        Connection conn=null;
        PreparedStatement find = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?user=root&password=root");
            String query = "SELECT *  from books where author = "+ "'" + request.getParameter("author") + "'" + "AND qty > 0 ORDER BY author ASC, title ASC";
            find = conn.prepareStatement(query);
            ResultSet result = find.executeQuery();

            out.println("<html><head><title>Query Results</title></head><body>");
            out.println("<h2>Thank you for your test.query.</h2>");
            out.println("<p> your test.query is " + query + "</p>");

            int count =0;
            while(result.next())
            {
                out.println("<p>" + result.getString("author")
                + ", " + result.getString("title") + ", $" +
                        result.getDouble("price") + "</p>" );
                ++count;
            }
            out.println("<p>====" + count + "records found ====</p>");
            out.println("</body></html>");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
            out.close();
        }
if(find != null) try {
    find.close();
    if (conn!=null)
    conn.close();
} catch (SQLException e) {
    e.printStackTrace();
}


    }
}
