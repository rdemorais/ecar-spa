package br.gov.saude.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/META-INF/ecarTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RegExTest {
	
	@Test
	public void regExStripHtmlTest() {
		Pattern regex = Pattern.compile("<(font|span|p)[^>]*(style|face)=.*?>(.*?)</(font|span|p)>");
		
		String toReplace = "FOO <span style='font-family:Verdana;mso-bidi-font-family:\"Times New Roman\";display:none;mso-hide:all'>BAR</span> "
				+ "OT <span style='font-family:Verdana;mso-bidi-font-family:\"Times New Roman\";display:none;mso-hide:all'>Outro</span> "
				+ "<font face=Calibre>ee</font> font <b>bold</b> <table class='sds'>sd<p style='font-family:'>sd</p></table> <font size='2'>Size 2</font>";
		
		Matcher m = regex.matcher(toReplace);
		
		StringBuffer parecerClean = new StringBuffer();
		
		while(m.find()) {
			m.appendReplacement(parecerClean, m.group(3));
		}
		m.appendTail(parecerClean);
		System.out.println(parecerClean.toString());
	}
}