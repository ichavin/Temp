package com.chavin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.core.listener.FileUploadListener;

@Controller
@RequestMapping("/common")
public class CommonAction {

	@RequestMapping(value = "/getProgress")
	@ResponseBody
	public int getProgress(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");

		HttpSession session = request.getSession(true);
		if (session == null) {
			return 0;
		}

		FileUploadListener uploadProgressListener = (FileUploadListener) session.getAttribute("uploadProgressListener");
		if (uploadProgressListener == null) {
			return 0;
		}
		int ret = uploadProgressListener.getPercentDone();
		return ret;
	}
	
	@RequestMapping(value = "/downLoadTemplate")
	public void downLoadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.reset();
        response.setContentType("application/zip;charset=utf-8");  //打包下载模板
        try {
			response.setHeader("Content-Disposition", "attachment;filename="+ new String(("上传模板" + ".zip").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        ServletOutputStream out = response.getOutputStream();
        String path = request.getServletContext().getRealPath("/");
        File file = new File(path + File.separator + "excel-template" + File.separator + "上传模板.zip");
        if(!file.exists()){
        	out.close();
        	return;
        }
        InputStream inputStream = new FileInputStream(file);
        byte[] b = new byte[1024];
        while(inputStream.read(b) != -1){
        	out.write(b, 0, b.length);
        }
        out.flush();
        inputStream.close();
        out.close();
	}

}
