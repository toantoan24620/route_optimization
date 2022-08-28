package com.toanhv22.routeoptimization.locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private final ResourceBundleMessageSource messageSource;

    public Translator(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocaleMessage(int code) {
        return getMessage(code + StringUtils.EMPTY, messageSource);
    }

    private String getMessage(String code, ResourceBundleMessageSource messageSource) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(code, null, locale);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }
}
