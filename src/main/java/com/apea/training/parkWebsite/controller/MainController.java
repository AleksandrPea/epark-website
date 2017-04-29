package com.apea.training.parkWebsite.controller;

import com.apea.training.parkWebsite.controller.requestHandler.HandlerProviderImpl;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.service.ServiceException;
import com.apea.training.parkWebsite.view.JspResolver;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class MainController extends HttpServlet {

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
            String abstractViewName = HandlerProviderImpl.getInstance()
                    .getRequestHandler(request).handle(request, response);
            dispatch(abstractViewName, request, response);

        } catch (DaoException | ServiceException | DispatchException | NoSuchElementException e) {
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
            String resolvedViewName;
            if (AppAssets.getInstance().isViewPublic(viewName)) {
                resolvedViewName = JspResolver.getInstance().resolvePublicViewName(viewName);
            } else {
                resolvedViewName = JspResolver.getInstance().resolvePrivateViewName(viewName);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(resolvedViewName);
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new DispatchException("Can't dispatch " + viewName, e);
        }
    }

    private void performRedirect(String viewName, HttpServletRequest request, HttpServletResponse response) {
        sendRedirect(response, viewName);
    }

    private void logExceptionAndRedirectToErrorPage(HttpServletRequest request, HttpServletResponse response,
                                                    RuntimeException e) {
        Logger.getLogger(MainController.class).error("Error in handling request " + request.getRequestURI(), e);

        sendRedirect(response, AppAssets.getInstance().get("GENERAL_ERROR_PAGE"));
    }

    private void sendRedirect(HttpServletResponse response, String redirectUri) {
        try {
            response.sendRedirect(redirectUri);
        } catch (IOException e) {
            throw new DispatchException("Can't redirect to " + redirectUri, e);
        }
    }
}