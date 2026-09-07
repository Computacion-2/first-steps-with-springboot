package com.discography.demo.servlet;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.discography.demo.config.AppConfig;
import com.discography.demo.service.interf.ITrackService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tracks")
public class ServletTracks extends HttpServlet {
    
    @Autowired 
    private ITrackService trackService;

    @Override
    public void init() throws ServletException {

        super.init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        this.trackService = (ITrackService) context.getBean("trackService");

    }

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        

    }
}
