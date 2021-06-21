package ru.job4j.servlets;

import ru.job4j.model.*;
import ru.job4j.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("brands", AdRepository.instOf().findBrand());
        req.setAttribute("engines", AdRepository.instOf().findEngine());
        req.setAttribute("transmissions", AdRepository.instOf().findTransmission());
        req.setAttribute("colors", AdRepository.instOf().findColor());
        req.setAttribute("bodyTypes", AdRepository.instOf().findBodyType());
        List<Ads> rsl = null;
        String filter = String.valueOf(req.getSession().getAttribute("filter"));
        if (filter.equals("null")) {
            filter = "0";
        }
        int count = Integer.parseInt(filter);
        switch (count) {
            case 0:
                rsl = AdRepository.instOf().findAds();
                break;
            case -1:
                rsl = AdRepository.instOf().findAllPhoto();
                break;
            case -2:
                rsl = AdRepository.instOf().findAllData();
                break;
            case 1, 2, 3, 4, 5:
                rsl = AdRepository.instOf().findAllBrand(count);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + count);
        }
        req.setAttribute("ads", rsl);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("filter", req.getParameter("filter"));
        doGet(req, resp);
    }
}
