import java.math.BigDecimal;
import java.util.Objects;

public class Payment{
    private String paymentId;
    private String customerId;
    private String merchantId;
    private BigDecimal amount;
    enum PaymentStatus {
        CREATED,
        PENDING,
        SUCCESS,
        FAILED
    }

    private PaymentStatus status;

    Payment(String paymentId, String customerId, String merchantId, BigDecimal amount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.status = status;
    }

    // get paymentId
    String getPaymentId() {
        return paymentId;
    }

    // get customerId
    String getCustomerId() {
        return customerId;
    }

    // get merchantId
    String getMerchantId() {
        return merchantId;
    }

    // get amount
    BigDecimal getAmount() {
        return amount;
    }

    // get status
    PaymentStatus getStatus() {
        return status;
    }

    // update the payment status
    void updateStatus (PaymentStatus newStatus) {
        if (status==PaymentStatus.CREATED) {
            if (newStatus==PaymentStatus.PENDING) {
                status = newStatus;
            }
        } else if (status == PaymentStatus.PENDING) {
            if (newStatus==PaymentStatus.FAILED || newStatus==PaymentStatus.SUCCESS) {
                status = newStatus;
            }
        }
    }

    boolean matchesRequest (String cutId, String merId, BigDecimal amnt) {
        if (customerId.equals(cutId) && merchantId.equals(merId) && amount.compareTo(amnt)==0) {
            return true;
        }

        return false;
    }

}

