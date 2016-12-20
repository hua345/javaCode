package com.github.chenjianhua.publishhelloworld;

import java.io.Serializable;

import hudson.model.Action;
import hudson.model.Run;
import hudson.model.AbstractBuild;

public class TestResult {
	
	private Run<?,?> onwer;
	private ResultInfo info;
	

	public TestResult(Run<?,?> build){
		this.onwer = build;
	}
	
	public Run<?,?> getOnwer(){
		return this.onwer;
	}
	public void setOnwer(AbstractBuild<?,?> onwer){
		this.onwer = onwer;
	}
	public ResultInfo getResultInfo() {
		return info;
	}

	public void setResultInfo(ResultInfo info) {
		this.info = info;
	}
}
