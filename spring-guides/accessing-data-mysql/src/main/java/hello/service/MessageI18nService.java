package hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author CHENJIANHUA
 * @date 2019/5/30 10:05
 */
@Component
public class MessageI18nService {

    @Autowired
    private MessageSource messageSource;

    /**
     * get message
     */
    public String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
}