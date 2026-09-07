package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.model.Publisher;
import com.discography.demo.service.interf.IPublisherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "servletPublisher", value = "/publisher")
public class ServletPublisher extends HttpServlet {
    
    private ConfigurableApplicationContext context;
    private IPublisherService publisherService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.context = new AnnotationConfigApplicationContext("com.discography.demo");
        this.publisherService = this.context.getBean(IPublisherService.class);
    }

    @Override
    public void destroy() {
        if (this.context != null) {
            this.context.close();
        }
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html><body>");
        out.println("<nav>");
        out.println("<a href='artist'>Manage Artists</a> | ");
        out.println("<a href='tracks'>Manage Tracks</a> | ");
        out.println("<a href='publisher'>View All Publications</a>");
        out.println("</nav><hr>");

        out.println("<h2>Search Tracks by Artist Name</h2>");
        out.println("<form method='get' action='publisher'>");
        out.println(" Artist Name: <input type='text' name='name' required />");
        out.println(" <button type='submit'>Search</button>");
        out.println("</form><hr>");

        String artistName = req.getParameter("name");
        if (artistName != null && !artistName.trim().isEmpty()) {
            // Búsqueda por nombre de artista
            List<Publisher> publishers = publisherService.findTracksByNameArtist(artistName);
            out.println("<h3>Results for Artist: " + artistName + "</h3>");
            if (publishers != null && !publishers.isEmpty()) {
                out.println("<ul>");
                for (Publisher p : publishers) {
                    out.println("<li>Publisher ID: " + p.getIdPublisher() 
                        + " - Track: <strong>" + (p.getTrack() != null ? p.getTrack().getTitle() : "N/A") + "</strong>"
                        + " (Release Date: " + p.getReleaseDate() + ")</li>");
                }
                out.println("</ul>");
            } else {
                out.println("<p style='color:red;'>No tracks found for this artist.</p>");
            }
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirige o procesa peticiones POST si decides agregar más acciones aquí en el futuro
        resp.sendRedirect("publisher");
    }
}