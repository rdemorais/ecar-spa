package br.gov.saude.file;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

public class EcarFileSystemImpl implements EcarFileSystem, ResourceLoaderAware {
	
	private ResourceLoader resourceLoader;
	
	private static String ECAR_REPORT_DIR = "classpath:reports/eCarPEReports/";

	@Override
	public BufferedImage getImageFromContext(String name) throws IOException {
		return ImageIO.read(findFileInputStream(name));
	}
	
	@Override
	public InputStream findFileInputStream(String fileName) throws IOException {
		return resourceLoader.getResource(ECAR_REPORT_DIR+fileName).getInputStream();
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}