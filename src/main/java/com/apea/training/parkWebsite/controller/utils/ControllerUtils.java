package com.apea.training.parkWebsite.controller.utils;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;
import com.apea.training.parkWebsite.view.JspResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerUtils {

    private static AppAssets assets = AppAssets.getInstance();

    private ControllerUtils() {}

    /** @return null if there is no current user */
    public static Integer getCurrentUserId(HttpServletRequest request) {
        return (Integer)request.getSession().getAttribute(assets.get("CURRENT_USER_ID_ATTR_NAME"));
    }

    /** @return null if there is no current user */
    public static User.Role getCurrentUserRole(HttpServletRequest request) {
        return (User.Role)request.getSession().getAttribute(assets.get("CURRENT_USER_ROLE_ATTR_NAME"));
    }

    /** @return null if there is no current user */
    public static User getCurrentUser(HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute(assets.get("CURRENT_USER_ID_ATTR_NAME"));
        if (id == null) {
            return null;
        }
        return ServiceFactoryImpl.getInstance().getUserService().getById(id);
    }

    public static int getFirstIdFromUri(String uri) {
        return getIntFromUri(uri, 0);
    }

    public static int getIntFromUri(String uri, int index) {
        Matcher matcher = Pattern.compile("\\d+").matcher(uri);

        for(int i = 0; i <= index; i++) {
            if (!matcher.find()) {
                throw new IllegalArgumentException("There is no id in uri " + uri);
            }
        }

        return Integer.parseInt(matcher.group());
    }

    public static void saveGeneralMsgsInSession(HttpServletRequest request,
                                                List<FrontendMessage> generalMessages) {
        Map<String, List<FrontendMessage>> frontMessageMap = new HashMap<>();
        frontMessageMap.put(assets.get("GENERAL_MESSAGES_BLOCK_NAME"), generalMessages);
        HttpSession session = request.getSession();
        session.setAttribute(assets.get("MESSAGES_ATTR_NAME"), frontMessageMap);
    }

    public static List<Plant> getCurrentUserPlants(HttpServletRequest request) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (currentUser.getRole() == User.Role.OWNER) {
            return ServiceFactoryImpl.getInstance().getPlantService().getAll();
        } else {
            List<Plant> userPlants = new ArrayList<>();
            ServiceFactoryImpl.getInstance().getUserService().getAttachedAreas(currentUser)
                    .stream()
                    .map(area -> ServiceFactoryImpl.getInstance().getPlantService().getAllOn(area.getId()))
                    .forEach(userPlants::addAll);
            return userPlants;
        }
    }

    public static String resolveViewName(String viewName) {
        String resolvedViewName;
        if (AppAssets.getInstance().isViewPublic(viewName)) {
            resolvedViewName = JspResolver.getInstance().resolvePublicViewName(viewName);
        } else {
            resolvedViewName = JspResolver.getInstance().resolvePrivateViewName(viewName);
        }
        return resolvedViewName;
    }

    public static String getErrorViewName(Throwable exception) {
        AppAssets assets = AppAssets.getInstance();
        if (exception instanceof DaoException) {
            return assets.get("STORAGE_ERROR_VIEW_NAME");
        }

        if (exception instanceof NoSuchElementException) {
            return assets.get("404_ERROR_VIEW_NAME");
        }

        if (exception instanceof AccessDeniedException) {
            return assets.get("ACCESS_DENIED_ERROR_VIEW_NAME");
        }

        return assets.get("GENERAL_ERROR_VIEW_NAME");
    }

    /** @return empty optional if name is valid. Otherwise returns error message */
    public static Optional<FrontendMessage> validateName(String name) {
        AppAssets assets = AppAssets.getInstance();
        if (name.matches(assets.get("NAME_REGEX"))) {
            return Optional.empty();
        } else {
            return  Optional.of(FrontMessageFactory.getInstance().getError(assets.get("NAME_VALIDATION_FAILED")));
        }
    }

    public static Optional<FrontendMessage> validateText(String text) {
        AppAssets assets = AppAssets.getInstance();
        if (text.matches(assets.get("TEXT_REGEX"))) {
            return Optional.empty();
        } else {
            return  Optional.of(FrontMessageFactory.getInstance().getError(assets.get("TEXT_VALIDATION_FAILED")));
        }
    }

    public static Optional<FrontendMessage> validatePassword(String password) {
        AppAssets assets = AppAssets.getInstance();
        if (password.matches(assets.get("PASSWORD_REGEX"))) {
            return Optional.empty();
        } else {
            return  Optional.of(FrontMessageFactory.getInstance().getError(assets.get("PASSWORD_VALIDATION_FAILED")));
        }
    }

    public static Optional<FrontendMessage> validateEmail(String email) {
        AppAssets assets = AppAssets.getInstance();
        if (email.matches(assets.get("EMAIL_REGEX"))) {
            return Optional.empty();
        } else {
            return  Optional.of(FrontMessageFactory.getInstance().getError(assets.get("EMAIL_VALIDATION_FAILED")));
        }
    }
}