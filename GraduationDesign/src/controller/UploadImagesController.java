package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class UploadImagesController {

	//上传文件
		@RequestMapping(value="/upload", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
		@ResponseBody
		public String uploadImg(@RequestParam("file") MultipartFile file,
	            HttpServletRequest request) {
			
			long time = System.nanoTime();
			
			String filePath = "F:/DesignMe/vue-element-scss/src/image/"+time+"/";
			String contentType = file.getContentType();
	        String fileName = file.getOriginalFilename();
	        System.out.println("fileName-->" + fileName);
	        System.out.println("fileName-->" + filePath);
	        System.out.println("getContentType-->" + contentType);
	        try {
	            uploadFile(file.getBytes(), filePath, fileName);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        //return ResponseData.ok("success");
	        return "{\"code\":1,\"path\":\""+filePath +fileName+"\"}";
	    }
		
		public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
	        File targetFile = new File(filePath);  
	        if(!targetFile.exists()){    
	            targetFile.mkdirs();    
	        }       
	        FileOutputStream out = new FileOutputStream(filePath+fileName);
	        out.write(file);
	        out.flush();
	        out.close();
	    }
		
		//读取文件并展示
		@RequestMapping(value = "/displayImage", method = RequestMethod.GET)
		public ResponseEntity<byte[]> displayImage(@RequestParam("path")String path) throws IOException{
			System.out.println(path);
			File file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_PNG);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
					headers, HttpStatus.OK);
		}
}
