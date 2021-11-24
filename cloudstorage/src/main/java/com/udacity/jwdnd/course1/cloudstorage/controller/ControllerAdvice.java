package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	private final static String errorPage = "result";
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxSizeException(
			MaxUploadSizeExceededException exc,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		ModelAndView modelAndView = new ModelAndView(errorPage);
		modelAndView.addObject(CloudStorageController.SUCCESS_KEY, false);
		modelAndView.addObject(CloudStorageController.MESSAGE_KEY, FileController.UPLOAD_FILE_FILE_TOO_LARGE_ERROR_MESSAGE);
		modelAndView.addObject(CloudStorageController.ACTIVE_TAB_KEY, FileController.ACTIVE_TAB_FILES);
		return modelAndView;
	}
	
}
