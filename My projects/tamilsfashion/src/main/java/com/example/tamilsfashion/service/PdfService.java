package com.example.tamilsfashion.service;

import com.example.tamilsfashion.entity.Bill;
import com.example.tamilsfashion.entity.BillItem;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.lowagie.text.Image;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateInvoice(Bill bill) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();
                 try {

                 InputStream is = getClass().getResourceAsStream("/static/images/logo.png");

                 if (is == null) {
                  is = getClass().getResourceAsStream("/images/logo.png");
                     }

                    if (is != null) {

                        byte[] imageBytes = is.readAllBytes();

                        Image logo = Image.getInstance(imageBytes);

                        logo.scaleToFit(80, 80);

                        logo.setAlignment(Element.ALIGN_CENTER);

                        document.add(logo);

                    }

} catch (Exception e) {

    e.printStackTrace();

}

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            Paragraph title = new Paragraph("TAMILS FASHION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            Paragraph subTitle = new Paragraph("Boutique Invoice", headingFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(subTitle);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Bill Number : " + bill.getBillNumber(), normalFont));
            document.add(new Paragraph("Bill Date   : " + bill.getBillDate(), normalFont));
            document.add(new Paragraph("Customer    : " + bill.getCustomer().getCustomerName(), normalFont));
            document.add(new Paragraph("Phone       : " + bill.getCustomer().getPhone(), normalFont));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            addHeaderCell(table, "Product");
            addHeaderCell(table, "Size");
            addHeaderCell(table, "Qty");
            addHeaderCell(table, "Price");
            addHeaderCell(table, "Subtotal");

            for (BillItem item : bill.getBillItems()) {

                table.addCell(item.getProduct().getProductName());
                table.addCell(item.getProduct().getSize());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(item.getPrice().toString());
                table.addCell(item.getSubtotal().toString());

            }

            document.add(table);

            document.add(new Paragraph(" "));

            Paragraph total = new Paragraph(
                    "Total Amount : ₹ " + bill.getTotalAmount(),
                    headingFont);

            total.setAlignment(Element.ALIGN_RIGHT);

            document.add(total);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("--------------------------------------------"));
            document.add(new Paragraph("Thank You For Shopping With Tamils Fashion!",
                    headingFont));
            document.add(new Paragraph("Visit Again.", normalFont));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF Invoice", e);
        }

    }

    private void addHeaderCell(PdfPTable table, String text) {

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);

    }

}