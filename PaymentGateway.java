import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class PaymentGateway {
    
    private Map<String, Payment> map = new HashMap<>();
    private Map<String, Payment> ideMap = new HashMap<>();


    // To create payment request by the merchant
    Payment createPayment (String customerId, String merchantId, BigDecimal amount, String idemKey) {

        if (ideMap.containsKey(idemKey)) {
            Payment existingPayment = ideMap.get(idemKey);
            if (existingPayment.matchesRequest(customerId, merchantId, amount)) {
                return existingPayment;
            } else {
                throw new IdempotencyKeyConflictException("Request cannot be processed");
            }
        }

        // generate unique id for paymentId
        UUID uuid = UUID.randomUUID();
        String paymentId = uuid.toString();

        // object of Payment class
        Payment pay = new Payment(paymentId, customerId, merchantId, amount, Payment.PaymentStatus.CREATED);

        // maps the payment info
        map.put(paymentId, pay);


        // maps the idempotency key to payment info
        ideMap.put(idemKey, pay);

        return pay;

    }

    // payment associated with this ID
    Payment getPayment(String paymentId) {
        
        return map.get(paymentId);
    }

    // updating the payment status
    void processPayment (String paymentId) {
        Payment payment = map.get(paymentId);
        if (payment!=null) {
            payment.updateStatus(Payment.PaymentStatus.PENDING);
        }
    }

    // moving to final state
    void completePayment (String paymentId, boolean success) {
        Payment payment = map.get(paymentId);

        if (payment!=null) {
            if (success) {
                payment.updateStatus(Payment.PaymentStatus.SUCCESS);
            } else {
                payment.updateStatus(Payment.PaymentStatus.FAILED);
            }
        }
    }
}