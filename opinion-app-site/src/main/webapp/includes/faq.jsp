<%@page import="com.inqwise.opinion.library.common.errorHandle.OperationResult"%>
<%@page import="java.util.List"%>
<%@page import="com.inqwise.opinion.cms.common.faq.IFaq"%>
<%@page import="com.inqwise.opinion.cms.managers.FaqsManager"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.inqwise.opinion.cms.common.IPage"%>

<%
	IPage p = (IPage)request.getAttribute(IPage.PAGE_OBJECT_ATTRIBUTE_NAME);
	String absoluteURL = p.getRootAbsoluteUrl();
	String applicationURL = p.getApplicationUrl();
	String searchWord = StringUtils.trimToNull(request.getParameter("search_word"));
%>

<h1 style="width:450px"><%= p.getHeader() %></h1>
<div>
	<div class="wrapper-content-left">
		<div style="padding-bottom: 12px;">
			Browse through our help articles below, or use the search box on the right to find what you're looking for.
		</div>
		
<%
OperationResult<List<IFaq>> faqsResults = FaqsManager.searchFaqs(searchWord, null, 100);
if(!faqsResults.hasError()){
	List<IFaq> faqs = faqsResults.getValue();
	Hashtable<String, List<IFaq>> faqsByCategories = new Hashtable<String, List<IFaq>>();
	for(IFaq faq : faqs){
		List<IFaq> faqsInCategory;
		if(faq.getCategories().size() > 0){
			for(String categoryName : faq.getCategories()){
				if(faqsByCategories.containsKey(categoryName)){
					faqsInCategory = faqsByCategories.get(categoryName);
				} else {
					faqsInCategory = new ArrayList<IFaq>();
					faqsByCategories.put(categoryName, faqsInCategory);
				}
				faqsInCategory.add(faq);
			}
		} else {
			final String categoryName = "Uncategorized";
			if(faqsByCategories.containsKey(categoryName)){
				faqsInCategory = faqsByCategories.get(categoryName);
			} else {
				faqsInCategory = new ArrayList<IFaq>();
				faqsByCategories.put(categoryName, faqsInCategory);
			}
			faqsInCategory.add(faq);
		}
		
	}
	
	Enumeration<String> keys = faqsByCategories.keys();
	while(keys.hasMoreElements()) {
		String categoryName = keys.nextElement();
	%>
		<div style="padding: 12px 0 0 0px;">
			<h3 class="ui-header-light"><%=categoryName %></h3>
		</div>
	<%
		List<IFaq> faqsInCategory = faqsByCategories.get(categoryName);
		for(IFaq faq : faqsInCategory){
	%>
			<div style="padding: 12px 0 0 0px;">
				<a href="javascript:toggle(<%=faq.getId() %>);" id="expander_<%=faq.getId() %>" class="arrow-grey" title="<%=faq.getQuestion() %>"><h3><%=faq.getQuestion() %></h3></a>
				<div style="padding: 0 0 0 8px; color: #999" id="descriptor_<%=faq.getId() %>">
					<div><%=StringEscapeUtils.escapeHtml4(faq.getAnswer()).substring(0, (faq.getAnswer().length() > 200 ? 200 : faq.getAnswer().length())) %><%=(faq.getAnswer().length() > 200 ? "..." : "") %></div>
				</div>
				<div style="padding: 12px 0 0 8px; display: none;" id="block_<%=faq.getId() %>">
					<div><%=faq.getAnswer() %></div>
					<div style="display: none; padding-top: 12px;">
						<div style="clear: both;">
							<table cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td>Was this answer helpful?</td>
									<td style="text-align: right"><a href="javascript:;" title="Yes" class="button-white"><span>Yes</span></a>&nbsp;<a href="javascript:;" title="No" class="button-white"><span>No</span></a></td>
								</tr>
							</table>
						</div>
						<div style="clear: both; display: none">Thanks for your feedback! Over time we use user feedback to improve the quality of our content and how we deliver it to you.</div>
					</div>
				</div>
			</div>
	<%
		}
	}
}
%>
	</div>
	<div class="wrapper-content-middle">
		<h3 class="ui-header">Top Questions</h3>
		<div>
			<ul class="ll">
<%
OperationResult<List<IFaq>> faqsResultsTop = FaqsManager.searchFaqs(searchWord, null, 10);
if(!faqsResultsTop.hasError()){
	List<IFaq> faqs = faqsResultsTop.getValue();
	for(IFaq faq : faqs){
%>
				<li><a href="<%=absoluteURL %>/faq/search/<%=URLEncoder.encode(faq.getQuestion()) %>" title="<%=faq.getQuestion() %>" class="arrow-grey"><%=faq.getQuestion() %></a></li>
<%
	}
}
%>
			</ul>
		</div>
	</div>
	<div class="wrapper-content-right">
		<h3 class="ui-header">Search Answers</h3>
		<div style="padding: 12px 0 0 8px;">
			<div class="row">
				<div class="cell">
					<input id="input_search" type="text" class="text-field" style="width: 144px;" placeholder="Search answers" onkeydown="catchSearch(event)" />
				</div>
				<div class="cell" style="padding-left: 6px;">
					<a class="button-blue" title="Search" href="javascript:validateSearch();"><span>Search</span></a>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var toggle = function(blockId) {
	var expander = $('#expander_' + blockId);
	var block = $('#block_' + blockId);
	var descriptor = $('#descriptor_' + blockId);
	if(block.is(':visible')) {
		descriptor.show();
		block.hide();
		expander.removeClass('collapsed');
	} else {
		block.show();
		descriptor.hide();
		expander.addClass('collapsed');
	}
};

var catchSearch = function(e){var code;if(!e)var e=window.event;if(e.keyCode)code=e.keyCode;else if(e.which)code=e.which;if(code==13)validateSearch();}
var validateSearch = function(){
	var q = $.trim($('#input_search').val());
	if(q.length>0) {
		location.href="<%=absoluteURL %>/faq/search/" + escape(q);
	}
};
</script>
