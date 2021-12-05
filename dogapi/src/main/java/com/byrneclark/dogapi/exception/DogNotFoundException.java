package com.byrneclark.dogapi.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class DogNotFoundException extends RuntimeException implements GraphQLError {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738736018032125359L;
	public static final String EXT_INVALID_DOG_ID = "invalidDogId";
	private Map<String, Object> extensions = new HashMap<>();

	public DogNotFoundException(String message, Long id) {
		super(message);
		extensions.put(EXT_INVALID_DOG_ID, id);
	}

	@Override
	public List<SourceLocation> getLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getExtensions(){
		return extensions ;
	}
	
	@Override
	public ErrorType getErrorType() {
		return ErrorType.DataFetchingException;
	}

}
