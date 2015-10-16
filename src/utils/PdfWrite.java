package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;

import wrapper.ServiceNowWrappers;

public class PdfWrite {

	public static void createNewPdf(String pdfName) {
		
		// Get the pdf file name	
		File file = new File("C:\\ServiceNow\\reports\\"+pdfName);
		
		// check if exist, delete it
		try {
			if(file.exists())
				new File(file.getCanonicalPath()).delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Create a document and add a page to it
        PDDocument doc = new PDDocument();
  
        // Save the results and ensure that the document is properly closed:
        try {
        	doc.save("C:\\ServiceNow\\reports\\"+pdfName);	
        	doc.close();
        } catch (COSVisitorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static void appendReport(String pdfName, String desc) {
		
		PDDocument doc = null;
	    try
	    {
	        doc = PDDocument.load("C:\\ServiceNow\\reports\\"+pdfName);

	        // Create a new font object selecting one of the PDF base fonts
	        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	        
	        //we will add the image to the first page.
	        PDPage page =  new PDPage();
	        doc.addPage(page);
	        
	        PDXObjectImage ximage = null;
	        PDPageContentStream cos = new PDPageContentStream(doc, page, true, true);
	        
	        // Define a text content stream using the selected font, move the cursor and draw some text
	        cos.beginText();
	        cos.setFont(fontBold, 12);
	        
	        // Need to write wrap option here:
	        cos.moveTextPositionByAmount(20, page.getMediaBox().getHeight() - 50);
	        cos.drawString(desc);
	        cos.endText();
	        
	        try {
				RemoteWebDriver driver = new ServiceNowWrappers().getDriver();
				File src = driver.getScreenshotAs(OutputType.FILE);	        
				
				// add an image
				try {
				    BufferedImage awtImage = ImageIO.read(src);
				    ximage = new PDPixelMap(doc, awtImage);
				    float scale = 0.4f; // alter this value to set the image size
				    cos.drawXObject(ximage, 20, 400, ximage.getWidth()*scale, ximage.getHeight()*scale);
				} catch (FileNotFoundException fnfex) {
				    
				}
			} catch (WebDriverException e) {
				
			}
	    
	        cos.close();
	        doc.save("C:\\ServiceNow\\reports\\"+pdfName);
	    }
	    catch (COSVisitorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    {
	        if( doc != null )
	        {
	            try {
					doc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
	
    
}