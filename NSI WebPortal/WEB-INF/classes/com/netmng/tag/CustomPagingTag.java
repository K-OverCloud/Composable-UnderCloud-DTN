package com.netmng.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.netmng.util.PropUtil;

public class CustomPagingTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7027553315597591887L;

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		
		JspWriter out = this.pageContext.getOut();
		PropUtil propUtil = new PropUtil();
		try {
			this.setTotPage((int)Math.ceil((double)this.getTotRow() / (double)this.getRow()));
			
			StringBuffer bTag = new StringBuffer("");
			/*
			bTag.append("<table width='100%' border='0'>")
				.append("<tr>")
				.append("<td height='25'>&nbsp;</td>")
				.append("<td height='25' align='center' valign='bottom'>");
			*/
			StringBuffer eTag = new StringBuffer("");
			/*
			eTag.append("</td>")
				.append("<td height='25'>&nbsp;</td>")
				.append("</tr>")
				.append("</table>");	
			*/
			StringBuffer page = new StringBuffer();
			StringBuffer prePage = new StringBuffer();
			StringBuffer nextPage = new StringBuffer();
			StringBuffer firstPage = new StringBuffer();
			StringBuffer lastPage = new StringBuffer();
			StringBuffer script = new StringBuffer();
			script.append("<a href='#none' onClick='")
				.append(this.func);
				
			this.ePage = this.totPage;
			
			if(this.totPage > this.vPage) {
				if(this.pageIdx <= this.vPage) {
					this.sPage = 1;
				} else {
					if(this.pageIdx%this.vPage == 0) {
						this.sPage = (int)((Math.floor(this.pageIdx/this.vPage)-1)*this.vPage+1);
					} else {
						this.sPage = (int)((Math.floor(this.pageIdx/this.vPage))*this.vPage+1);						
					}
				}
				this.ePage = this.sPage + this.vPage-1;
				this.ePage = this.ePage > this.totPage ? this.totPage : this.ePage;
				
				// 
				if((this.sPage + this.vPage) < this.totPage) {
					nextPage.append("&nbsp;&nbsp;")
							.append(script.toString())
							.append("(")
							.append(this.ePage+1)
							.append(")'>")
							.append("<img src='").append(this.getCxtPath()).append("/"+propUtil.getProp("context.root")+"/images/btn_next.gif' alt='다음'></a>");
					lastPage.append("&nbsp;")
							.append(script.toString())
							.append("(")
							.append(this.totPage)
							.append(")'>")
							.append("<img src='").append(this.getCxtPath()).append("/"+propUtil.getProp("context.root")+"/images/btn_last.gif' alt='마지막'></a>");
				} else {
					// 마지막 페이지를 만들지 않는다.
				}
				if(this.sPage > this.vPage) {
					prePage.append(script.toString())
							.append("(")
							.append(this.sPage-1)
							.append(")' >")
							.append("<img src='").append(this.getCxtPath()).append("/"+propUtil.getProp("context.root")+"/images/btn_prev.gif' alt='이전'></a>&nbsp;&nbsp;");
					firstPage.append(script.toString())
							.append("(1)' >")
							.append("<img src='").append(this.getCxtPath()).append("/"+propUtil.getProp("context.root")+"/images/btn_first.gif' alt='처음'></a>");
				} else {
					// 처음페이지를 만들지 않는다.
				}
			}
			
			for(int i=this.sPage; i<=this.ePage; i++) {
				// 현재 페이지 표시
				if(this.pageIdx == i) {
					page
						.append(script.toString())
						.append("(")
						.append(i)
						.append(")' >")
						.append("<b>")
						.append(i)
						.append("</b>")
						.append("</a>&nbsp;&nbsp;");
				}else{
					page.append(script.toString())
						.append("(")
						.append(i)
						.append(")' >")
						.append(i)
						.append("</a>&nbsp;&nbsp;");
				}
			}
			
			out.println(bTag.toString() + firstPage.toString() + prePage.toString() + page.toString() + nextPage.toString() + lastPage.toString() + eTag.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CustomPagingTag.SKIP_BODY;
	}
	
	private int totRow 	= 0;
	private int row		= 0;
	private int pageIdx = 1;
	private int totPage = 1;
	private int sPage	= 1;
	private int ePage   = 1;
	private int vPage	= 10; 					// 한번에 보여지는 페이지개수
	private String func = null;
	private String cxtPath = null;
	
	public int getTotRow() {
		return this.totRow;
	}
	public void setTotRow(int totRow) {
		this.totRow = totRow;
	}
	public int getRow() {
		return this.row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getPageIdx() {
		return this.pageIdx;
	}
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
	public int getTotPage() {
		return this.totPage;
	}
	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}
	public int getvPage() {
		return this.vPage;
	}
	public void setvPage(int vPage) {
		this.vPage = vPage;
	}
	public String getFunc() {
		return this.func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public String getCxtPath() {
		return this.cxtPath;
	}
	public void setCxtPath(String cxtPath) {
		this.cxtPath = cxtPath;
	}
	
}
