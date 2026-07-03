package com.example.tamilsfashion.service;

import com.example.tamilsfashion.dto.BillItemRequest;
import com.example.tamilsfashion.dto.BillRequest;
import com.example.tamilsfashion.entity.Bill;
import com.example.tamilsfashion.entity.BillItem;
import com.example.tamilsfashion.entity.Customer;
import com.example.tamilsfashion.entity.Product;
import com.example.tamilsfashion.repository.BillItemRepository;
import com.example.tamilsfashion.repository.BillRepository;
import com.example.tamilsfashion.repository.CustomerRepository;
import com.example.tamilsfashion.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PdfService pdfService;

    
    public Bill createBill(BillRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Bill bill = new Bill();
        bill.setBillNumber("BILL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        bill.setBillDate(LocalDateTime.now());
        bill.setCustomer(customer);

        List<BillItem> billItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (BillItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal price = product.getPrice();
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            BillItem item = new BillItem();
            item.setBill(bill);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(price);
            item.setSubtotal(subtotal);

            billItems.add(item);

            totalAmount = totalAmount.add(subtotal);

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);
        }

        bill.setTotalAmount(totalAmount);
        bill.setBillItems(billItems);

        Bill savedBill = billRepository.save(bill);

        billItemRepository.saveAll(billItems);

        return savedBill;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    
    public byte[] generateInvoice(Long billId) {
        Bill bill = getBillById(billId);

        if (bill == null) {
            throw new RuntimeException("Bill not found");
        }

        return pdfService.generateInvoice(bill);
    }

    public BigDecimal getTodaySales() {

    BigDecimal total = BigDecimal.ZERO;

    List<Bill> bills = billRepository.findAll();

        for (Bill bill : bills) {

            if (bill.getBillDate().toLocalDate().equals(LocalDate.now())) {
                total = total.add(bill.getTotalAmount());
            }

        }

    return total;
    }
    

}