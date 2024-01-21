package com.inqwise.opinion.automation;

import com.inqwise.opinion.automation.common.errorHandle.AutomationBaseOperationResult;
import com.inqwise.opinion.automation.common.events.ChargeStatusChangedEventArgs;

public class Test {
	public static void main(String[] args) {
		
		ChargeStatusChangedEventArgs eventArgs = new ChargeStatusChangedEventArgs(2, 2/*Paid*/, 1);
		
		
		AutomationBaseOperationResult result;
		/*
		System.out.println("Sending Invoice paid event");
		try {
			 result = EventsProvider.getInstance().fireInvoicePaidEvent(eventArgs);
			 System.out.println(result.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		*/
		
		/*
		System.out.println("Sending registration event");
		RegistrationEventArgs registrationEventArgs = new RegistrationEventArgs(UUID.randomUUID(), 1, "test", "HE");
		try {
			result = EventsProvider.getInstance().fireRegistrationEvent(registrationEventArgs );
			 System.out.println(result.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Sending payment event");
		PaymentEventArgs paymentEventArgs = new PaymentEventArgs(UUID.randomUUID(), 1L, 2L);
		try {
			result = EventsProvider.getInstance().firePaymentEvent(paymentEventArgs );
			System.out.println(result.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		/*
		System.out.println("Get Jobs");
		
		try {
			JAXBContext context = JAXBContext.newInstance(AutomationOperationResult.class, ArrayList.class, JobSettings.class);
			
			JobSettings[] j = new JobSettings[1];
			j[0] = new JobSettings();
			
			List<JobSettings> l = new ArrayList<JobSettings>();
			l.add(new JobSettings());
			AutomationOperationResult<List<JobSettings>> r = new AutomationOperationResult<List<JobSettings>>();
			r.setValue(l);
			context.createMarshaller().marshal(r,System.out);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			AutomationOperationResult<JobSettings[]> getResult = EventsProviderSystem.getInstance().getJobs();
			System.out.println(getResult);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		/*
		System.out.println("Sending charge changed event");
		ChargeStatusChangedEventArgs invoiceEventArgs = new ChargeStatusChangedEventArgs(86, 2, UUID.randomUUID());
		try {
			result = EventsProviderSystem.getInstance().fireChargeStatusChangedEvent(invoiceEventArgs);
			 System.out.println(result.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		*/
		
		
	}
}
