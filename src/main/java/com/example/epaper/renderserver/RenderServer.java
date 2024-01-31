package com.example.epaper.renderserver;

import de.maikmerten.miniserv.AuthChecker;
import de.maikmerten.miniserv.Miniserv;
import de.maikmerten.miniserv.Response;
import jakarta.servlet.http.HttpSession;

import java.awt.image.BufferedImage;

import com.example.epaper.renderserver.auth.Authenticator;
import com.example.epaper.renderserver.auth.TestAuthenticator;
import com.example.epaper.renderserver.model.Credentials;
import com.example.epaper.renderserver.model.ScheduleEnvelope;
import com.example.epaper.renderserver.renderers.ScheduleRenderer;
import com.example.epaper.upload.Uploader;
import com.example.epaper.util.ImageConverter;

/**
 *
 * @author merten
 */
public class RenderServer {

    private static Response imageResponse(BufferedImage bimg) {
        String contentType = "image/png";
        return new Response(200, ImageConverter.toBytes(bimg, contentType), contentType);
    }

    public static void main(String[] args) {
        Miniserv server = new Miniserv(8000, true);
        Authenticator auth = new TestAuthenticator();

        AuthChecker authChecker = (HttpSession session) -> {
            return auth.checkSession(session);
        };

        server.onPost("/api/login", (request) -> {
            Credentials cred = server.jsonToObject(request, Credentials.class);
            auth.login(cred, request.getSession());
            return "ok";
        });

        server.onPost("/api/render-schedule", (request) -> {
            ScheduleEnvelope env = server.jsonToObject(request, ScheduleEnvelope.class);
            BufferedImage bimg = ScheduleRenderer.renderSchedule(env);
            return imageResponse(bimg);
        }, authChecker);

        server.onPost("/api/upload-schedule", (request) -> {
            ScheduleEnvelope env = server.jsonToObject(request, ScheduleEnvelope.class);
            BufferedImage bimg = ScheduleRenderer.renderSchedule(env);

            Uploader up = new Uploader(env.getAp());
            up.uploadImage(bimg, env.getMac(), false);

            return "ok";
        }, authChecker);

        server.start();
    }

}
