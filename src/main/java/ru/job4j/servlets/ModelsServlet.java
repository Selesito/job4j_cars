package ru.job4j.servlets;

import org.json.JSONArray;
import ru.job4j.store.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ModelsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("idBrand"));
        PrintWriter writer = resp.getWriter();
        JSONArray json  = new JSONArray(new ArrayList<>(AdRepository.instOf().findByModels(id)));
        writer.println(json);
        writer.flush();
    }
}
