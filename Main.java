import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        PaymentGateway t1 = new PaymentGateway();
        Payment Req = t1.createPayment("CUS001", "MER001", new BigDecimal("500"), "ORDER-001");
        System.out.print("Request 1:");
        System.out.println(Req.getStatus());
        System.out.println(Req.getPaymentId());


        System.out.println("Request 2:");
        Payment Req2 = t1.createPayment("CUS001", "MER001", new BigDecimal("500"), "ORDER-001");
        System.out.println(Req2.getPaymentId());

    }
}
