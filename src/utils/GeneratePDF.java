package utils;
	
	import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
	
	public class GeneratePDF{
		private static String FILE = "./guide_livraisons.pdf";
		private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
		private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);

		/**tu Appelle cette fonction**/
		public static void writePdfFile(String instructions){
			try {
				Document document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(FILE));
				document.open();
				addContent(document,instructions);
				document.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private static void addContent(Document document,String instructions)
				throws DocumentException {
			Paragraph preface = new Paragraph();
			//one empty line
			addEmptyLine(preface, 1);
			
			// Lets write a big header
			preface.add(new Paragraph("Guide Instructions des Livraisons", catFont));
			preface.setAlignment(preface.ALIGN_CENTER);
			addEmptyLine(preface, 1);
			
			// generated by: _name, _date
			preface.add(new Paragraph("Fiche g�n�r� par Devo Optimod "  + ", " + new Date(),smallBold));
			addEmptyLine(preface, 3);

			preface.add(new Paragraph(instructions));
			document.add(preface);
			// Start a new page
			document.newPage();
		}

		private static void addEmptyLine(Paragraph paragraph, int number) {
			for (int i = 0; i < number; i++) {
				paragraph.add(new Paragraph(" "));
			}
		}
	
	

	
	}