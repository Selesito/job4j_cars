package ru.job4j.servlets;

import ru.job4j.model.Ads;
import ru.job4j.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SoldServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String button = req.getParameter("sold");
        int adsId = Integer.parseInt(req.getParameter("idAds"));
        Ads ads = AdRepository.instOf().findByAds(adsId);
        if (button.equals("sold")) {
            ads.setStatus(true);
            AdRepository.instOf().updateAds(ads);
        } else if (button.equals("delete")) {
            AdRepository.instOf().deleteAds(ads);
            AdRepository.instOf().deleteCar(ads.getCar());
        }
        resp.sendRedirect(req.getContextPath() + "/userCars.jsp");
    }
}
