package com.example.tamilsfashion.controller;

import com.example.tamilsfashion.dto.BillRequest;
import com.example.tamilsfashion.entity.Bill;
import com.example.tamilsfashion.service.BillingService;
import com.example.tamilsfashion.service.CustomerService;
import com.example.tamilsfashion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    
    @GetMapping
    public String billingPage(Model model) {

        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("billRequest", new BillRequest());

        return "billing";
    }

    
    @PostMapping("/save")
    public String saveBill(@ModelAttribute BillRequest billRequest) {

        Bill bill = billingService.createBill(billRequest);

        return "redirect:/billing/invoice/" + bill.getId();
    }

    
    @GetMapping("/invoice/{billId}")
    public ResponseEntity<ByteArrayResource> downloadInvoice(
            @PathVariable Long billId) {

        byte[] pdf = billingService.generateInvoice(billId);

        ByteArrayResource resource = new ByteArrayResource(pdf);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Invoice_" + billId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(resource);
    }

    /**
     * Bill History Page
     */
    @GetMapping("/history")
    public String billHistory(Model model) {

        model.addAttribute("bills", billingService.getAllBills());

        return "bill-history";
    }

}