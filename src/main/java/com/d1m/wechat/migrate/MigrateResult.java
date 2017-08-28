package com.d1m.wechat.migrate;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 类描述
 *
 * @author f0rb on 2016-12-08.
 */
@Getter
@Setter
public class MigrateResult {
    private String message;
    private List<String> errors;
    private List<String> messages;
    private List<?> ignored;

    public void addError(String s) {
        internalGetErrors().add(s);
    }

    public void addMessage(String s) {
        internalGetMessages().add(s);
    }

    private synchronized List<String> internalGetErrors() {
        if (errors == null) {
            errors = new LinkedList<>();
        }
        return errors;
    }

    private synchronized List<String> internalGetMessages() {
        if (messages == null) {
            messages = new LinkedList<>();
        }
        return messages;
    }
}
