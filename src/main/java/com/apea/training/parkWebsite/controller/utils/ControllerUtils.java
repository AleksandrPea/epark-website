package com.apea.training.parkWebsite.controller.utils;

import com.apea.training.parkWebsite.controller.message.FrontendMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.apea.training.parkWebsite.controller.AppAssets.*;

public class ControllerUtils {

    private ControllerUtils() {}

    /** @return -1 if there is no current user */
    public static Integer getCurrentUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(CURRENT_USER_ATTR_NAME);
        return user != null ? user.getId() : -1;
    }

    public static User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(CURRENT_USER_ATTR_NAME);
    }

    public static int getFirstIdFromUri(String uri) {
        Matcher matcher = Pattern.compile("\\d+").matcher(uri);

        if (!matcher.find()) {
            throw new IllegalArgumentException("There is no id in uri " +uri);
        }

        return Integer.parseInt(matcher.group());
    }

    public static void saveGeneralMsgsInSession(HttpServletRequest request,
                                                List<FrontendMessage> generalMessages) {
        Map<String, List<FrontendMessage>> frontMessageMap = new HashMap<>();
        frontMessageMap.put(GENERAL_MESSAGES_BLOCK_NAME, generalMessages);
        request.getSession().setAttribute(MESSAGES_ATTR_NAME, frontMessageMap);
    }
}