package com.discography.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.config.AppConfig;
import com.discography.demo.model.Publisher;
import com.discography.demo.service.interf.IPublisherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "servletPublisher", value = "/publisher")
public class ServletPublisher extends HttpServlet{
    
    private  IPublisherService publisherService;

    @Override
    public void init() throws ServletException {

        super.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        this.publisherService = (IPublisherService) context.getBean("publisherService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html><body>");
        out.println("<h2>Search Artist By Name (All Info)</h2>");
        out.println("<hr>");
        out.println("<form method='post' action='/demo/publisher");
        out.println("  Nombre: <input type='text' name='name' required /><br/>");
        out.println("  <button type='submit'>Guardar</button>");
        out.println("</form>");
        out.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");

        if (name == null || name.isEmpty()) resp.sendError(400, "Sintaxis error");

        List<Publisher> publishers = publisherService.findTracksByNameArtist(name);

        if (publishers == null) resp.sendError(404, "No found Artist with that name");

    }
}
