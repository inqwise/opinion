package com.cint.entities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.cint.common.ICountry;
import com.cint.common.IRegion;

public class CountryEntity extends IdNameEntity implements ICountry {
    private List<IRegion> regions;

    public CountryEntity(Element element) {
    	XmlHelper.parse(element, this);
	}

    @Override
    public void onElementFound(Element element) {
    	switch(element.getTagName()){
    	case "region":
    		getRegions().add(new RegionEntity(element));
    		break;
		default:
			super.onElementFound(element);
			break;
    	}
    }
    
    public List<IRegion> getRegions() {
        if (regions == null) {
            regions = new ArrayList<IRegion>();
        }
        return this.regions;
    }    
}
