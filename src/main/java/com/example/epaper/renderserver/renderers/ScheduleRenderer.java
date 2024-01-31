package com.example.epaper.renderserver.renderers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.example.epaper.renderserver.model.Schedule;
import com.example.epaper.renderserver.model.ScheduleEnvelope;


public class ScheduleRenderer {

    private static class RenderConfig {
        private int width, height, fontSize, rowHeight, rowTextOffset;
        private int xRoom, xDate, xTime, xDescr;
    }
   
    private static RenderConfig configRenderer(ScheduleEnvelope env) {
        RenderConfig c = new RenderConfig();

        c.width = env.getWidth() & 0x7FF;
        c.height = env.getHeight() & 0x7FF;

        // default for 296x128
        c.fontSize = 12;
        c.rowHeight = 16;
        c.rowTextOffset = -3;
        c.xRoom = 2;
        c.xDate = 225;
        c.xTime = 0;
        c.xDescr = 78;

        if(c.width == 152 && c.height == 152) {
            c.xDate = 85;
            c.xDescr = 72;
        } else if(c.width == 400 && c.height == 300) {
            c.fontSize = 17;
            c.rowHeight= 30;
            c.rowTextOffset = -8;
            c.xRoom = 3;
            c.xDate = 300;
            c.xTime = 2;
            c.xDescr = 105;
        } else if(c.width == 640 && c.height == 384) {
            c.fontSize = 22;
            c.rowHeight= 38;
            c.rowTextOffset = -10;
            c.xRoom = 3;
            c.xDate = 520;
            c.xTime = 2;
            c.xDescr = 135;
        }

        return c;
    }

    public static BufferedImage renderSchedule(ScheduleEnvelope env) {
        RenderConfig c = configRenderer(env);
        Schedule sched = env.getSchedule();

        Font boldFont = new Font("Arial", Font.BOLD, c.fontSize);
        Font plainFont = new Font("Arial", Font.PLAIN, c.fontSize);
        int row = 1;

        BufferedImage bimg = new BufferedImage(c.width, c.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bimg.getGraphics();
        
        // no font-antialising!
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        
        // fill image with white
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, c.width, c.height);

        // header: white text on black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, c.width, c.rowHeight);
        g.setColor(Color.WHITE);
        g.setFont(boldFont);
        int ypos = row * c.rowHeight + c.rowTextOffset;
        g.drawString(sched.getRoom(), c.xRoom, ypos);     // room
        g.drawString(sched.getDate(), c.xDate, ypos);   // date

        // draw each calendar entry
        g.setColor(Color.BLACK);
        g.setFont(plainFont);
        for(String[] entry : sched.getEntries()) {
            ypos = (++row) * c.rowHeight + c.rowTextOffset;

            g.drawString(entry[0], c.xTime, ypos);    // time
            g.drawString(entry[1], c.xDescr, ypos);   // description

            ypos -= c.rowTextOffset;
            g.drawLine(0, ypos, c.width, ypos);
        }

        return bimg;
    }
}
