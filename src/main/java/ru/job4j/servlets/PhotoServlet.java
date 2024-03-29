package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class PhotoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int idAds = Integer.parseInt(req.getParameter("idAds"));
        String path = getServletContext().getInitParameter("path") + File.separator + idAds;
        String fileName = idAds + ".jpg";

        File downloadFile = null;
        for (File file : Objects.requireNonNull(new File(path).listFiles())) {
            if (fileName.equals(file.getName())) {
                downloadFile = file;
                break;
            }
        }
        resp.setContentType("application/octet-stream");
        if (downloadFile != null) {
            resp.setHeader("Content-Disposition", "attachment; filename=\""
                    + downloadFile.getName() + "\"");
            try (FileInputStream stream = new FileInputStream(downloadFile)) {
                resp.getOutputStream().write(stream.readAllBytes());
            }
        }
    }
}
