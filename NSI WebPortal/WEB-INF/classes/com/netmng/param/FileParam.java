package com.netmng.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileParam implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1872123389604709158L;
	
	protected static List<String> permitImgExtension = new ArrayList<String>();
	
	static {
		// 아래의 4가지 이미지 파일 포맷만 업로드 가능
		permitImgExtension.add("IMAGE/JPEG");
		permitImgExtension.add("IMAGE/PJPEG");
		permitImgExtension.add("IMAGE/GIF");
		permitImgExtension.add("IMAGE/PNG");
		permitImgExtension.add("IMAGE/BMP");
	}
	
	private MultipartFile[] file;

	public MultipartFile[] getFile() {
		return this.file;
	}

	public void setFile(MultipartFile[] file) {
		this.file = file;
	}
	
	public String getFileDir(HttpServletRequest request, int content) {
		
		Calendar cal = Calendar.getInstance();
		
		StringBuffer dir = new StringBuffer(request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/"))
							.append(content == 0 ? "upload" : "WEB-INF/upload")
							.append("/").append(cal.get(Calendar.YEAR))
							.append("/").append(cal.get(Calendar.MONTH)+1)
							.append("/").append(cal.get(Calendar.DATE))
							.append("/");
		return dir.toString();
	}
	
	public String getImgUrl(HttpServletRequest request, String dir, String file) {
		return dir.replace(request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/"), request.getSession().getServletContext().getContextPath() + "/") + file;
	}
	
	public Boolean getImageFormatValidate(int idx) {
		if (!permitImgExtension.contains(this.file[idx].getContentType().toUpperCase())){
			return false;
		} else {
			return true;
		}
		/*
		try {
			//is = this.file[idx].getInputStream();
		    //iis = ImageIO.createImageInputStream(is);
			//ImageReader reader = ImageIO.getImageReaders(iis).next();
			
			if (!permitImgExtension.contains(this.file[idx].getContentType())){
				result = false;
			} else {
				result = true;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} finally{
			try {
				iis.close();
			} catch (IOException e1) {
				result = false;
			}
			try {
				is.close();
			} catch (IOException e1) {
				result = false;
			}
		}
		*/
	}
	
	public String getExtension(int idx) {
		
		String regex = "(?<=\\.)[^\\/\n]+$";
        String input = this.file[idx].getOriginalFilename();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		String result = null;
		if (matcher.find()){
			result = matcher.group();
		}

		return result;
		//System.out.println(this.file[idx].getOriginalFilename());
		//return this.file[idx].getOriginalFilename().replaceAll("(?<=\\.)[^\\/\n]+$", "");
	}
	
}
