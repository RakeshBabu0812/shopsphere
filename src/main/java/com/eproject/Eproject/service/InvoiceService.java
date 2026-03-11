package com.eproject.Eproject.service;

import java.io.File;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.eproject.Eproject.entity.Order;
import com.eproject.Eproject.entity.OrderItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;




@Service
public class InvoiceService {
	
	
	public void generateInvoice(Order order) {
		
		
		try {
			
			
			String fileName="invoices/invoice_"+order.getId()+".pdf";
			
			File dir=new File("invoices");
			
			if(!dir.exists()) {
				
				dir.mkdirs();
				
			}
			
		   PdfWriter writer=new PdfWriter(new FileOutputStream(fileName));
		   
		   PdfDocument pdf=new PdfDocument(writer);
		   
		   
		   Document document=new Document(pdf);
		   
		   document.add(new Paragraph("INVOICE"));
		   
		   document.add(new Paragraph("Order ID: "+order.getId()));
		   
		   document.add(new Paragraph("Customer: "+order.getUser().getEmail()));
		   
		   document.add(new Paragraph("Date: "+order.getCreatedAt().format(DateTimeFormatter.ISO_DATE)));
		   
		   document.add(new Paragraph("-------------------------------------------------------------------------------"));
		   
		   
		   for(OrderItem item:order.getItems()) {
			   
			   String line=item.getProduct().getName()
					                 +"   |  Qty:  "+item.getQuantity()
					                 +"   |  Price:  "+item.getPriceAtPurchase();
			   
			   document.add(new Paragraph(line));
			   
		   }
		   
		   document.add(new Paragraph("-------------------------------------------------------------------------------"));
		   
		 document.add(new Paragraph("Total: "+order.getTotalAmount()));
		 
		 
		 document.close();
				   
	
		}
		catch(Exception e) {
			
			throw new RuntimeException("Invoice generation failed");
			
			
		}
		

		
	}
	
	
	

}
