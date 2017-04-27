package com.apea.training.parkWebsite.controller;

import com.apea.training.parkWebsite.controller.requestHandler.HandlerProvider;
import com.apea.training.parkWebsite.controller.requestHandler.HandlerProviderImpl;
import com.apea.training.parkWebsite.view.JspResolver;
import com.apea.training.parkWebsite.view.ViewResolver;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    private AppAssets assets = AppAssets.getInstance();
    private HandlerProvider handlerProvider = HandlerProviderImpl.getInstance();
    private ViewResolver viewResolver = JspResolver.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String abstractViewName = handlerProvider.getRequestHandler(request).handle(request, response);
            dispatch(abstractViewName, request, response);

        } catch (RuntimeException e) {
            logExceptionAndRedirectToErrorPage(request, response, e);
        }
    }

    private void dispatch(String abstractViewName, HttpServletRequest request, HttpServletResponse response) {
        String[] viewNameParts = abstractViewName.split(":");
        String dispatchType = viewNameParts[0];
        String viewName = viewNameParts[1];

        switch (dispatchType) {
            case "forward":
                performForward(viewName, request, response);
                break;
            case "redirect":
                performRedirect(viewName, request, response);
                break;
            case "noAction":
                break;
            default:
                throw new IllegalArgumentException("Incorrect dispatch type in " + abstractViewName);
        }
    }

    private void performForward(String viewName, HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    viewResolver.resolvePrivateViewName(viewName));
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new DispatchException("Can't dispatch " + viewName, e);
        }
    }

    private void performRedirect(String viewName, HttpServletRequest request, HttpServletResponse response) {
        sendRedirect(viewName, request, response);
    }

    private void logExceptionAndRedirectToErrorPage(HttpServletRequest request, HttpServletResponse response,
                                                    RuntimeException e) {
        LOGGER.error("Error in handling request " + request.getRequestURI(), e);

        sendRedirect(viewResolver.resolvePublicViewName(assets.get("GENERAL_ERROR_PAGE_VIEW_NAME")),
                request, response);
    }

    private void sendRedirect(String redirectUri, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(redirectUri);
        } catch (IOException e) {
            throw new DispatchException("Can't redirect to " + redirectUri, e);
        }
    }
}