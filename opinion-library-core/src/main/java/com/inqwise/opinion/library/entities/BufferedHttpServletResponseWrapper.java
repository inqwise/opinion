package com.inqwise.opinion.library.entities;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * For capturing the output of a servlet into a buffer
 */
public class BufferedHttpServletResponseWrapper extends
        HttpServletResponseWrapper {

    public BufferedHttpServletResponseWrapper(
            HttpServletResponse response) {
        super (response);
    }

    public ServletOutputStream getOutputStream() {
        return bsos;
    }

    public PrintWriter getWriter() {
        return bsos.pw;
    }

    public void reset() {
        bsos.reset();
    }

    public byte[] getByteContent() {
        return bsos.getByteContent();
    }

    public String getStringContent() {
        return bsos.getStringContent();
    }

    public boolean isCommitted() {
        return false;
    }

    private BufferedServletOutputStream bsos = new BufferedServletOutputStream(
            getCharacterEncoding());

}

class BufferedServletOutputStream extends ServletOutputStream {

    BufferedServletOutputStream(String enc) {
        try {
            baos = new ByteArrayOutputStream();
            pw = new PrintWriter(new OutputStreamWriter(baos, enc));
            encoding = enc;
        } catch (java.io.UnsupportedEncodingException e) {
            // XXX should not happen, checked by servlet methods - assert()
        }
    }

    public void write(int i) {
        baos.write(i);
    }

    public void reset() {
        baos.reset();
    }

    public byte[] getByteContent() {
        return baos.toByteArray();
    }

    public String getStringContent() {
        try {
            return baos.toString(encoding);
        } catch (java.io.UnsupportedEncodingException e) {
            // XXX should not happen, checked by servlet methods - assert()
        }
        return "encoding error";
    }

    ByteArrayOutputStream baos;
    PrintWriter pw;
    String encoding;
	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		
	}

}