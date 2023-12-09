package com.inqwise.opinion.payments.processors.paypal;

enum IpnTransactionType {
	Undefined(null), //Credit card chargeback if the case_type variable contains chargeback 
	Adjustment("adjustment"), //A dispute has been resolved and closed
	Cart("cart"), //Payment received for multiple items; source is Express Checkout or the PayPal Shopping Cart.
	ExpressCheckout("express_checkout"), //Payment received for a single item; source is Express Checkout
	Masspay("masspay"), //Payment sent using MassPay
	CreatedBillingAgreement("mp_signup"), //Created a billing agreement
	MonthlySubscriptionPaid("merch_pmt"), //Monthly subscription paid for PayPal Payments Pro
	NewCase("new_case"), //A new dispute was filed 
	RecurringPayment("recurring_payment"), //Recurring payment received
	RecurringPaymentExpired("recurring_payment_expired"), //Recurring payment expired
	RecurringPaymentProfileCreated("recurring_payment_profile_created"), //Recurring payment profile created
	RecurringPaymentSkipped("recurring_payment_skipped"), //Recurring payment skipped; it will be retried up to a total of 3 times, 5 days apart
	SendMoney("send_money"), //Payment received; source is the Send Money tab on the PayPal website
	SubscriptionCanceled("subscr_cancel"),
	SubscriptionExpired("subscr_eot"),
	SubscriptionPaymentFailed("subscr_failed"),
	SubscriptionModified("subscr_modify"),
	SubscriptionPaymentReceived("subscr_payment"),
	SubscriptionStarted("subscr_signup"),
	VirtualTerminalPaymentReceived("virtual_terminal"), //Payment received; source is Virtual Terminal
	WebAccept("web_accept"); //Payment received; source is a Buy Now, Donation, or Auction Smart Logos button
	
	private final String value;

	public String getValue() {
		return value;
	}
	
	private IpnTransactionType(String value) {
		this.value = value;
	}
	
	public static IpnTransactionType fromString(String value){
		
		for (IpnTransactionType b : IpnTransactionType.values()) { 
			if (value == b.value) { 
	          return b; 
	        }
        } 
		
		return Undefined;
	}
}
