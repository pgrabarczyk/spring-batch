package com.pgrabarczyk.spring.batch.job;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveFlatFileItemReader<T> extends FlatFileItemReader<T> {

	@Setter
	private Resource targetResource;

	@Override
	protected void doClose() throws Exception {
		super.doClose();
		moveToDone(getResource(), targetResource);
	}

	private Resource getResource()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field f = super.getClass().getSuperclass().getDeclaredField("resource");
		f.setAccessible(true);
		return (Resource) f.get(this);
	}

	private void moveToDone(Resource sourceResource, Resource targetResource) throws IOException {
		validateMoveToDone(sourceResource,targetResource);

		String targetDirectory = targetResource.getURI().getPath();

		Path source = sourceResource.getFile().toPath();
		Path target = Paths.get(targetDirectory, sourceResource.getFilename());

		Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		log.debug("File \"{}\" moved", sourceResource.getFilename());
	}
	
	private void validateMoveToDone(Resource sourceResource, Resource targetResource) throws IOException {
		if (sourceResource == null)
			throw new IllegalArgumentException("sourceResource cannot be null");
		if (targetResource == null)
			throw new IllegalArgumentException("targetResource cannot be null");

//		if( ! targetResource.isReadable() ) {
//			throw new IllegalArgumentException("targetResource is not readable");
//		}
		
		File resourceFile = targetResource.getFile();
		if (! resourceFile.exists() )
			throw new IllegalArgumentException("resourceFile is not exists");
		if (! resourceFile.isDirectory() )
			throw new IllegalArgumentException("resourceFile is not directory.");
	}
}
