package com.apea.training.parkWebsite.view.jsp.tag;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class DisplayFormMsg extends SimpleTagSupport {

    private String formInputName;

    @Override
    public void doTag() throws JspException, IOException {
        AppAssets assets = AppAssets.getInstance();
        PageContext pageContext = (PageContext) getJspContext();
        Map<String, FrontendMessage> formMessages = (Map<String, FrontendMessage>)
                 pageContext.getRequest().getAttribute(assets.get("MESSAGES_ATTR_NAME"));
        if (formMessages != null && formMessages.get(formInputName) != null) {
            String[] languageParts = pageContext.getSession().getAttribute(assets.get("LANGUAGE_ATTR_NAME"))
                    .toString().split("_");
            ResourceBundle validationBundle = ResourceBundle.getBundle("webProject.i18n.validation",
                    new Locale(languageParts[0], languageParts[1]));

            FrontendMessage message = formMessages.get(formInputName);
            String msgType = ""+message.getType();
            String htmlText = String.format("<label class=\"messages %s\">%s</label>",
                    msgType.toLowerCase(), validationBundle.getString(message.getMessageKey()));
            pageContext.getOut().print(htmlText);
        }
    }

    public void setFormInputName(String formInputName) {
        this.formInputName = formInputName;
    }
}