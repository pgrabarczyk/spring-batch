package com.pgrabarczyk.spring.batch.job;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope("step")
@PropertySource("classpath:config.properties")
public class MultiResourceUpdateStepListener implements StepExecutionListener, ApplicationContextAware {

	@Value("${targetResource}")
	private Resource targetResource;

	private Resource[] resources;

	@Setter
	private ApplicationContext applicationContext;

	@Value("${filePattern}")
	private String filePattern;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		MultiResourceItemReader reader = applicationContext.getBean("cvsMultiResourceItemReader",
				MultiResourceItemReader.class);
		try {
			resources = applicationContext.getResources(filePattern);
			reader.setResources(resources);
		} catch (IOException ex) {
			log.error("Unable to set file resources to bean multiResourceItemReader", ex);
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		boolean isStepCompleted = ExitStatus.COMPLETED.equals(stepExecution.getExitStatus())
				&& BatchStatus.COMPLETED.equals(stepExecution.getStatus()) && resources.length > 0;
		if (isStepCompleted) {
			try {
				validateTargetResource(targetResource);
				for (Resource sourceResource : resources) {
					if (sourceResource != null) {
						moveToDone(sourceResource, targetResource);
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return stepExecution.getExitStatus();
	}

	private void moveToDone(Resource sourceResource, Resource targetResource) throws IOException {
		String targetDirectory = targetResource.getURI().getPath();

		Path source = sourceResource.getFile().toPath();
		Path target = Paths.get(targetDirectory, sourceResource.getFilename());

		Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		log.debug("File \"{}\" moved", sourceResource.getFilename());
	}

	private void validateTargetResource(Resource targetResource) throws IOException {
		if (targetResource == null)
			throw new IllegalArgumentException("targetResource cannot be null");
		File resourceFile = targetResource.getFile();
		if (!resourceFile.exists())
			throw new IllegalArgumentException("resourceFile is not exists");
		if (!resourceFile.isDirectory())
			throw new IllegalArgumentException("resourceFile is not directory.");
	}

}
