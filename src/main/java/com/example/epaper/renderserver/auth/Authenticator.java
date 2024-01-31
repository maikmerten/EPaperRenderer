package com.example.epaper.renderserver.auth;

import jakarta.servlet.http.HttpSession;

import com.example.epaper.renderserver.model.Credentials;

import de.maikmerten.miniserv.exceptions.AuthException;

public interface Authenticator {
    public void login(Credentials c, HttpSession s) throws AuthException;
    public boolean checkSession(HttpSession s);
}
