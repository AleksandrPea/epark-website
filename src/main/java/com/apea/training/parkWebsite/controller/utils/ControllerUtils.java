package com.apea.training.parkWebsite.controller.utils;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}