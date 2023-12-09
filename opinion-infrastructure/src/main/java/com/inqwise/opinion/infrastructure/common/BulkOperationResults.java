package com.inqwise.opinion.infrastructure.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BulkOperationResults<Result extends IOperationResult> implements Collection<Result>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1283305659244612606L;
	
	private List<Result> failList;
	private List<Result> successList;
	private List<Result> all;
	
	public BulkOperationResults() {
		failList = new ArrayList<Result>();
		successList = new ArrayList<Result>();
		all = new ArrayList<Result>();
	}
	
	public boolean hasFailures(){
		return failList.size() > 0;
	}
	
	public boolean hasSuccesses(){
		return successList.size() > 0;
	}

	public Collection<Result> getFailures(){
		return failList;
	}
	
	public Collection<Result> getSuccesses(){
		return successList;
	}
	
	public Result getFirstFail(){
		return failList.get(0);
	}
	
	public Result getFirstSuccess(){
		return successList.get(0);
	}
	
	@Override
	public boolean add(Result result) {
		all.add(result);
		return (result.hasError() ? failList.add(result) : successList.add(result));
	}

	@Override
	public boolean addAll(Collection<? extends Result> results) {
		boolean retVal = true;
			for (Result result : results) {
				retVal &= add(result);
			}
		return retVal;
	}

	@Override
	public void clear() {
		failList.clear();
		successList.clear();
		all.clear();
	}

	@Override
	public boolean contains(Object result) {
		return all.contains(result);
	}

	@Override
	public boolean containsAll(Collection<?> results) {
		return all.containsAll(results);
	}

	@Override
	public boolean isEmpty() {
		return all.isEmpty();
	}

	@Override
	public Iterator<Result> iterator() {
		return all.iterator();
	}

	@Override
	public boolean remove(Object result) {
		failList.remove(result);
		successList.remove(result);
		return  all.remove(result);
	}

	@Override
	public boolean removeAll(Collection<?> results) {
		failList.removeAll(results);
		successList.removeAll(results);
		return  all.removeAll(results);
	}

	@Override
	public boolean retainAll(Collection<?> results) {
		failList.retainAll(results);
		successList.retainAll(results);
		return  all.retainAll(results);
	}

	@Override
	public int size() {
		return all.size();
	}

	@Override
	public Object[] toArray() {
		return all.toArray();
	}

	@Override
	public <T> T[] toArray(T[] items) {
		return all.toArray(items);
	}
	
}
