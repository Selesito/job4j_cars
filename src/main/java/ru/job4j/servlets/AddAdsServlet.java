package ru.job4j.servlets;

import ru.job4j.model.*;
import ru.job4j.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
public class AddAdsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("brands", AdRepository.instOf().findBrand());
        req.setAttribute("engines", AdRepository.instOf().findEngine());
        req.setAttribute("transmissions", AdRepository.instOf().findTransmission());
        req.setAttribute("colors", AdRepository.instOf().findColor());
        req.setAttribute("bodyTypes", AdRepository.instOf().findBodyType());
        req.getRequestDispatcher("addAds.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Ads ads = Ads.of(req.getParameter("nameAds"), req.getParameter("description"),
                false, req.getParameter("price"));
        String vinNumber = req.getParameter("vin");
        Car car = AdRepository.instOf().findByCarVin(vinNumber);
        if (car != null) {
            resp.sendRedirect(req.getContextPath() + "/vinError.jsp");
            return;
        } else {
            car = Car.of(req.getParameter("km"), req.getParameter("yearRelese"),
                    req.getParameter("owners"), req.getParameter("power"), vinNumber);
        }
        car.setBodyType(BodyType.of(Integer.parseInt(req.getParameter("bodyType"))));
        car.setEngine(Engine.of(Integer.parseInt(req.getParameter("engine"))));
        car.setTransmission(Transmission.of(Integer.parseInt(req.getParameter("transmission"))));
        car.setColor(Color.of(Integer.parseInt(req.getParameter("color"))));
        Model model = Model.of(Integer.parseInt(req.getParameter("model")));
        model.setBrand(Brand.of(Integer.parseInt(req.getParameter("brand"))));
        car.setModel(model);
        AdRepository.instOf().addCar(car);
        ads.setCar(car);
        Author author = (Author) req.getSession().getAttribute("user");
        ads.setAuthor(author);
        AdRepository.instOf().addAds(ads);
        if (ads.getId() != 0) {
            String path = getServletContext().getInitParameter("path") + File.separator + ads.getId();
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            List<Part> fileParts = req.getParts().stream()
                    .filter(part -> "file".equals(part.getName()) && part.getSize() > 0)
                    .collect(Collectors.toList());
            for (Part filePart : fileParts) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                String fileType = fileName.substring(fileName.lastIndexOf('.'));
                String filePath = path + File.separator + ads.getId() + fileType;
                filePart.write(filePath);
                ads.addPhoto(Photo.of(filePath));
            }
            AdRepository.instOf().updateAds(ads);
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
