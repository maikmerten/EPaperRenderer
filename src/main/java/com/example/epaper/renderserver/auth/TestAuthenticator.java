package com.example.epaper.renderserver.auth;

import java.util.HashMap;
import java.util.Map;

import com.example.epaper.renderserver.model.Credentials;

import de.maikmerten.miniserv.exceptions.AuthException;
import jakarta.servlet.http.HttpSession;

/*
 * Example authenticator with fixed credentials, not for production
 */
public class TestAuthenticator implements Authenticator {

    private Map<String, String> pwDb = new HashMap<>();

    public TestAuthenticator() {
        pwDb.put("test", "sesame");
    }

    public void login(Credentials c, HttpSession s) throws AuthException {
        s.removeAttribute("auth");
        String pass = pwDb.get(c.getUser().trim());
        if(pass != null) {
            if(pass.equals(c.getPassword())) {
                s.setAttribute("auth", true);
                return;
            }
        }
        throw new AuthException("invalid credentials");
    }

    public boolean checkSession(HttpSession s) {
        Object authStatus = s.getAttribute("auth");
        if(authStatus != null && authStatus.equals(true)) {
            return true;
        }
        return false;
    }

}