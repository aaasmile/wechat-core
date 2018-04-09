package com.d1m.wechat.migrate;

import java.util.LinkedList;
import java.util.List;

/**
 * 类描述
 *
 * @author f0rb on 2016-12-08.
 */
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<?> getIgnored() {
		return ignored;
	}

	public void setIgnored(List<?> ignored) {
		this.ignored = ignored;
	}
    
}
