package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IOrderEventCompleted;
import com.inqwise.opinion.cint.common.OrderEventType;

public class OrderEventCompletedEntity extends OrderEventEntity implements IOrderEventCompleted  {

    private double actualPrice;
    private double pricePerComplete;
    private Integer actualNumberOfCompletes;
    private String currency;

    @Override
	public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double value) {
        this.actualPrice = value;
    }

    @Override
	public double getPricePerComplete() {
        return pricePerComplete;
    }

    public void setPricePerComplete(double value) {
        this.pricePerComplete = value;
    }

    @Override
	public Integer getActualNumberOfCompletes() {
        return actualNumberOfCompletes;
    }

    public void setActualNumberOfCompletes(Integer value) {
        this.actualNumberOfCompletes = value;
    }

    @Override
	public String getCurrency() {
        return currency;
    }

    public void setCurrency(String value) {
        this.currency = value;
    }
    
	protected OrderEventCompletedEntity(Element element) {
		super(element);
	}

	@Override
	public OrderEventType getEventType() {
		return OrderEventType.Completed;
	}
	
	@Override
	public void onElementFound(Element element) {
		switch(element.getTagName()){
		case "actual-price":
			setActualPrice(Double.parseDouble(element.getTextContent()));
			break;
		case "price-per-complete":
			setPricePerComplete(Double.parseDouble(element.getTextContent()));
			break;
		case "actual-number-of-completes":
			setActualNumberOfCompletes(Integer.valueOf(element.getTextContent()));
			break;
		case "currency":
			setCurrency(element.getTextContent());
			break;
		default:
			super.onElementFound(element);
			break;
		}
	}
}
