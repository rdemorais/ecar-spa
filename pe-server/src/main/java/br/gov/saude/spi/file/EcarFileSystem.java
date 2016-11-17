package br.gov.saude.spi.file;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public interface EcarFileSystem {
	public BufferedImage getImageFromContext(String name) throws IOException;
	public InputStream findFileInputStream(String fileName) throws IOException;
}
