package br.gov.saude.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.saude.auth.SentinelaPasswordHash;

@ContextConfiguration("/META-INF/ecarTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EcarSentinelaTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarSentinelaTest.class);
	
	@Autowired
	private SentinelaPasswordHash sentinelaPasswordHash;
	
	@Test
	public void testSenha() {
		String user = "admin";
		String pass = "serenaya";
		logger.debug(sentinelaPasswordHash.criptografar(user + pass));
	}
}
