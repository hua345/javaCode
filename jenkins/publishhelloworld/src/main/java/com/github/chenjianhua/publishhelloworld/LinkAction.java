package com.github.chenjianhua.publishhelloworld;
import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.Run;

public class LinkAction implements Action {
	private final static String PLUGIN_NAME = "publishhelloworld";
	private final static String URL_NAME = "publishhelloworld";
	private final static String DISPLAY_NAME = "Publish Helloworld";
	
	private Run<?,?> build;
	private TestResult result;
	public LinkAction(Run<?,?> build, TestResult result){
		this.build = build;
		this.result = result;
	}
	
	/*
     * Gets the file name of the icon.
	 */
	public String getIconFileName(){
		return "plugin/" + PLUGIN_NAME + "/icons/snowflake.png";
	}
	
    public TestResult getResult() {
		return this.result;
	}

	/*
     * Gets the string to be displayed.
     */
	public String getDisplayName(){
		return DISPLAY_NAME;
	}
	/*
	 * Gets the URL path name
	 * */
	public String getUrlName(){
		return URL_NAME;
	}
	
	public Run<?,?> getOnwer(){
		return this.build;
	}
	public void setOnwer(Run<?,?> build){
		this.build = build;
	}
}
