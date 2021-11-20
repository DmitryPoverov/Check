package by.clevertec.web.servlet;

import by.clevertec.console.Check;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/check")
public class CheckServlet extends HttpServlet {

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        Map<String, String[]> parameterMap = req.getParameterMap();
        String[] ids = parameterMap.get("id");
        Check check = new Check(ids);
        List<String> stringsToPrint = check.createList();

        List<String> dottedStringToPrint = new ArrayList<>();
        for (String s : stringsToPrint) {
            dottedStringToPrint.add("<code>" + s.replace(" ", ".") + "</code>");
        }

        req.setAttribute("strings", dottedStringToPrint);
        req.getRequestDispatcher("/WEB-INF/jsp/check.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<String> args2 = new ArrayList<>();

        String id1 = req.getParameter("id1");
        String quantity1 = req.getParameter("quantity1");
        String id2 = req.getParameter("id2");
        String quantity2 = req.getParameter("quantity2");
        String id3 = req.getParameter("id3");
        String quantity3 = req.getParameter("quantity3");
        String discountCard = req.getParameter("discountCard");

        int counter=0;

        if (id1!=null && quantity1!=null) {
            args2.add(id1 + "-" + quantity1);
            counter++;
        }
        if (id2!=null && quantity2!=null) {
            args2.add(id2 + "-" + quantity2);
            counter++;
        }
        if (id3!=null && quantity3!=null) {
            args2.add(id3 + "-" + quantity3);
            counter++;
        }
        if (discountCard!=null) {
            args2.add("card-" + discountCard);
            counter++;
        }

        String[] newArgs = new String[counter];
        for (int i=0, k=0; i<args2.size(); i++) {
            if (args2.get(i)!=null && args2.get(i)!="-") {
                newArgs[k++] = args2.get(i);
            }
        }

        Check check = new Check(newArgs);
        List<String> stringList = check.createList();

        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = resp.getWriter()) {
            for (String s : stringList) {
                writer.write("<p><code>" + s.replace(" " , ".") + "</code></p>");
            }
        }
    }
}
