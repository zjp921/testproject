package cn.newtouch.pdf;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PdfUtil {

	Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
	
	String basePath = "/";
	
	public PdfUtil(String basePath) throws IOException {
		this.basePath = basePath;
		conf.setClassForTemplateLoading(PdfUtil.class, "/");
	}

	public void generate(String htmlPath, Map<String,Object> params, OutputStream out) throws IOException, TemplateException, DocumentException{
		//process html with freemarker
		Template t = this.conf.getTemplate(htmlPath);
        StringWriter stringWriter = new StringWriter();     
        BufferedWriter writer = new BufferedWriter(stringWriter); 
		t.process(params, writer);
		
		//generate pdf
		ITextRenderer iTextRenderer = new ITextRenderer();
		ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
//		URL simsun = this.getClass().getResource("/Fonts/SIMSUN.TTC");//
//		fontResolver.addFont(simsun.toString(),BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//		URL simhei = this.getClass().getResource("/Fonts/simhei.ttf");
//		fontResolver.addFont(simhei.toString(),BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		URL msyh = this.getClass().getResource("/Fonts/MSYH.TTC");
		fontResolver.addFont(msyh.toString(),BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		iTextRenderer.setDocumentFromString(stringWriter.toString(), this.basePath);
		
		iTextRenderer.layout();
		iTextRenderer.createPDF(out);
		
	}
	
}
