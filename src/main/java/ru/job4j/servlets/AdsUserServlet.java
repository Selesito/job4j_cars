package ru.job4j.servlets;

import ru.job4j.model.Ads;
import ru.job4j.model.Author;
import ru.job4j.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdsUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Author author = (Author) req.getSession().getAttribute("user");
        List<Ads> rsl = AdRepository.instOf().findAdsUser(author.getId());
        req.setAttribute("adsUser", rsl);
    }
}
