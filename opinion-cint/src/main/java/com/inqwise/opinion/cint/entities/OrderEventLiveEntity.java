package com.inqwise.opinion.cint.entities;

import org.w3c.dom.Element;

import com.inqwise.opinion.cint.common.IOrderEventLive;
import com.inqwise.opinion.cint.common.OrderEventType;

public class OrderEventLiveEntity extends OrderEventEntity implements
		IOrderEventLive {

    private Integer totalNumberOfInvitations;
    private Integer totalNumberOfCompletes;
    private Integer totalScreenOuts;
    private Integer totalQuotaFulls;

    @Override
	public Integer getTotalNumberOfInvitations() {
        return totalNumberOfInvitations;
    }

    public void setTotalNumberOfInvitations(Integer value) {
        this.totalNumberOfInvitations = value;
    }

    @Override
	public Integer getTotalNumberOfCompletes() {
        return totalNumberOfCompletes;
    }

    public void setTotalNumberOfCompletes(Integer value) {
        this.totalNumberOfCompletes = value;
    }

    @Override
	public Integer getTotalScreenOuts() {
        return totalScreenOuts;
    }

    public void setTotalScreenOuts(Integer value) {
        this.totalScreenOuts = value;
    }

    @Override
	public Integer getTotalQuotaFulls() {
        return totalQuotaFulls;
    }

    public void setTotalQuotaFulls(Integer value) {
        this.totalQuotaFulls = value;
    }
    
	protected OrderEventLiveEntity(Element element) {
		super(element);
	}

	@Override
	public OrderEventType getEventType() {
		return OrderEventType.Live;
	}

	@Override
	public void onElementFound(Element element) {
		switch(element.getTagName()){
		case "total-number-of-invitations":
			setTotalNumberOfInvitations(Integer.valueOf(element.getTextContent()));
			break;
		case "total-number-of-completes":
			setTotalNumberOfCompletes(Integer.valueOf(element.getTextContent()));
			break;
		case "total-screen-outs":
			setTotalScreenOuts(Integer.valueOf(element.getTextContent()));
			break;
		case "total-quota-fulls":
			setTotalQuotaFulls(Integer.valueOf(element.getTextContent()));
			break;
		default:
			super.onElementFound(element);
			break;
		}
	}
}
